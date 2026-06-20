package com.rapitor3.fastdelivery.orderservice.service;

import com.rapitor3.fastdelivery.orderservice.event.OrderCancelledEvent;
import com.rapitor3.fastdelivery.orderservice.event.OrderCreatedEvent;
import com.rapitor3.fastdelivery.orderservice.event.OrderStatusChangedEvent;
import com.rapitor3.fastdelivery.orderservice.repository.OrderItemRepository;
import com.rapitor3.fastdelivery.orderservice.repository.OrderRepository;
import com.rapitor3.fastdelivery.orderservice.dto.request.CreateOrderItemRequest;
import com.rapitor3.fastdelivery.orderservice.dto.request.CreateOrderRequest;
import com.rapitor3.fastdelivery.orderservice.dto.response.OrderDTO;
import com.rapitor3.fastdelivery.orderservice.dto.response.OrderItemDTO;
import com.rapitor3.fastdelivery.orderservice.model.Order;
import com.rapitor3.fastdelivery.orderservice.model.OrderItem;
import com.rapitor3.fastdelivery.orderservice.model.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, OrderEventProducer orderEventProduce) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderEventProducer = orderEventProduce;
    }


    /* POST | CREATE
     *
     * Create an order with order items
     *
     */
    @Transactional
    public OrderDTO createOrder(CreateOrderRequest request) {


        BigDecimal totalPrice = getTotalPrice(request.items());

        Order order = Order.builder()
                .userId(request.userId())
                .restaurantId(request.restaurantId())
                .deliveryAddress(request.deliveryAddress())
                .comment(request.comment())
                .status(OrderStatus.CREATED)
                .totalPrice(totalPrice)
                .build();

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = request.items().stream()
                .map(item -> {
                            BigDecimal itemTotalPrice = item.price().multiply(BigDecimal.valueOf(item.quantity()));

                            return OrderItem.builder()
                                    .orderId(savedOrder.getId())
                                    .menuItemId(item.menuItemId())
                                    .menuItemName(item.menuItemName())
                                    .quantity(item.quantity())
                                    .price(item.price())
                                    .totalPrice(itemTotalPrice)
                                    .build();
                        }
                ).toList();

        List<OrderItem> savedItems = orderItemRepository.saveAll(orderItems);

        orderEventProducer.sendOrderCreatedEvent(
                new OrderCreatedEvent(
                        savedOrder.getId(),
                        savedOrder.getUserId(),
                        savedOrder.getRestaurantId(),
                        savedOrder.getStatus(),
                        savedOrder.getTotalPrice(),
                        LocalDateTime.now()
                )
        );

        return toDTO(savedOrder, savedItems);

    }

    /* GET | READ
     *
     * Get all orders
     *
     */
    public List<OrderDTO> getAll() {
        return orderRepository.findAll().stream().map(this::toDTOWithItems).toList();
    }

    /* GET | READ
     *
     * Get all restaurant orders
     *
     */
    public List<OrderDTO> getByRestaurantId(Long restaurantId) {
        return orderRepository.findAllByRestaurantId(restaurantId).stream().map(this::toDTOWithItems).toList();
    }


    /* GET | READ
     *
     * Get all user orders
     *
     */
    public List<OrderDTO> getByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId).stream().map(this::toDTOWithItems).toList();
    }

    /* GET | READ
     *
     * Get an order with order items by order id
     *
     */
    public OrderDTO getById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException());

        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());

        return toDTO(order, orderItems);
    }

    /* PATCH | UPDATE
     *
     * Change an order status
     *
     */
    @Transactional
    public OrderDTO changeOrderStatus(Long orderId, OrderStatus status) {

        if (status == null) {
            throw new IllegalArgumentException("Status must not be null");
        }

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException());
        OrderStatus old  = order.getStatus();
        order.setStatus(status);
        Order savedOrder = orderRepository.save(order);
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(savedOrder.getId());


        orderEventProducer.sendOrderStatusChangedEvent(
                new OrderStatusChangedEvent(
                        savedOrder.getId(),
                        savedOrder.getUserId(),
                        savedOrder.getRestaurantId(),
                        old,
                        savedOrder.getStatus(),
                        LocalDateTime.now()
                )
        );


        return toDTO(savedOrder, orderItems);

    }

    /* PATCH | UPDATE
     *
     * Cancel an order status
     *
     */
    @Transactional
    public OrderDTO cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException());
        order.setStatus(OrderStatus.CANCELLED);
        Order savedOrder = orderRepository.save(order);
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(savedOrder.getId());


        orderEventProducer.sendOrderCancelledEvent(
                new OrderCancelledEvent(
                        savedOrder.getId(),
                        savedOrder.getUserId(),
                        savedOrder.getRestaurantId(),
                        "no reason",
                        savedOrder.getUpdatedAt()
                )
        );

        return toDTO(savedOrder, orderItems);

    }


    private OrderDTO toDTO(Order order, List<OrderItem> items) {
        return new OrderDTO(
                order.getId(),
                order.getUserId(),
                order.getRestaurantId(),
                order.getStatus(),
                order.getTotalPrice(),
                order.getDeliveryAddress(),
                order.getComment(),
                items.stream()
                        .map(this::toItemDTO)
                        .toList(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

    private OrderItemDTO toItemDTO(OrderItem item) {
        return new OrderItemDTO(
                item.getId(),
                item.getMenuItemId(),
                item.getMenuItemName(),
                item.getQuantity(),
                item.getPrice(),
                item.getTotalPrice()
        );
    }

    private BigDecimal getTotalPrice(List<CreateOrderItemRequest> list) {

        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        BigDecimal totalPrice = list.stream()
                .map(item -> {
                    if (item.quantity() == null || item.quantity() <= 0) {
                        throw new IllegalArgumentException("Quantity must be positive");
                    }

                    if (item.price() == null || item.price().compareTo(BigDecimal.ZERO) <= 0) {
                        throw new IllegalArgumentException("Price must be positive");
                    }
                    return item.price().multiply(BigDecimal.valueOf(item.quantity()));
                }).reduce(BigDecimal.ZERO, BigDecimal::add);


        return totalPrice;
    }

    private OrderDTO toDTOWithItems(Order order) {
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());
        return toDTO(order, orderItems);
    }


}
