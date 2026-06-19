package com.rapitor3.fastdelivery.orderservice.controller;


import com.rapitor3.fastdelivery.orderservice.dto.request.ChangeOrderStatusRequest;
import com.rapitor3.fastdelivery.orderservice.dto.request.CreateOrderRequest;
import com.rapitor3.fastdelivery.orderservice.dto.response.OrderDTO;
import com.rapitor3.fastdelivery.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(
            @RequestBody @Valid CreateOrderRequest request

    ) {
        try {
            OrderDTO response = orderService.createOrder(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders(
    ) {
        List<OrderDTO> orders = orderService.getAll();
        return ResponseEntity.ok(orders);

    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(
            @PathVariable Long orderId
    ) {
        try {
            OrderDTO response = orderService.getById(orderId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrderByUserId(
            @PathVariable Long userId
    ) {
        try {
            List<OrderDTO> response = orderService.getByUserId(userId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderDTO>> getOrderByRestaurantId(
            @PathVariable Long restaurantId
    ) {
        try {
            List<OrderDTO> response = orderService.getByRestaurantId(restaurantId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> changeOrderStatus(
            @PathVariable Long orderId,
            @RequestBody @Valid ChangeOrderStatusRequest request
    ) {
        try {

            OrderDTO response = orderService.changeOrderStatus(orderId, request.status());
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }


    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(
            @PathVariable Long orderId
    ) {
        try {

            OrderDTO response = orderService.cancelOrder(orderId);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }


    }

}
