# FastDelivery

**FastDelivery** — backend-проект сервиса доставки еды, построенный на микросервисной архитектуре.

Проект включает регистрацию и авторизацию пользователей, управление ресторанами, категориями меню, блюдами, заказами и маршрутизацию запросов через API Gateway.

---

## Содержание

- [Технологии](#технологии)
- [Архитектура](#архитектура)
- [Сервисы](#сервисы)
- [Структура проекта](#структура-проекта)
- [Базы данных](#базы-данных)
- [Модели данных](#модели-данных)
- [API Endpoints](#api-endpoints)
- [Аутентификация](#аутентификация)
- [Запуск через Docker Compose](#запуск-через-docker-compose)
- [Локальный запуск](#локальный-запуск)
- [Переменные окружения](#переменные-окружения)
- [Конфигурация Gateway](#конфигурация-gateway)
- [Особенности реализации](#особенности-реализации)
- [План развития](#план-развития)

---

## Технологии

| Категория | Стек |
|---|---|
| Язык | Java 21 |
| Фреймворк | Spring Boot |
| API Gateway | Spring Cloud Gateway |
| Безопасность | Spring Security + JWT |
| ORM | Spring Data JPA / Hibernate |
| База данных | PostgreSQL |
| Валидация | Jakarta Validation |
| Сборка | Gradle Kotlin DSL |
| Контейнеризация | Docker, Docker Compose |
| Мониторинг | Spring Boot Actuator |
| Утилиты | Lombok |

---

## Архитектура

Проект построен по микросервисному принципу: каждый сервис отвечает за свою бизнес-область и владеет собственной базой данных.

```text
                    ┌──────────────────┐
                    │  gateway-service │
                    │     port 8080    │
                    └────────┬─────────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
┌───────▼───────┐  ┌─────────▼─────────┐  ┌───────▼───────┐
│  user-service │  │ restaurant-service│  │ order-service │
│   port 8081   │  │     port 8082     │  │   port 8083   │
└───────┬───────┘  └─────────┬─────────┘  └───────┬───────┘
        │                    │                    │
┌───────▼───────┐   ┌────────▼────────┐   ┌───────▼───────┐
│    user-db    │   │  restaurant-db  │   │   order-db    │
│   PostgreSQL  │   │    PostgreSQL   │   │  PostgreSQL   │
└───────────────┘   └─────────────────┘   └───────────────┘
```

### Основная идея

- `gateway-service` принимает внешние HTTP-запросы.
- `user-service` отвечает за пользователей, регистрацию, авторизацию и выдачу JWT.
- `restaurant-service` отвечает за рестораны, категории меню и блюда.
- `order-service` отвечает за создание и обработку заказов.
- Каждый сервис использует отдельную PostgreSQL-базу.
- Сервисы не обращаются напрямую к чужим базам данных.

---

## Сервисы

| Сервис | Порт | Назначение |
|---|---:|---|
| `gateway-service` | `8080` | Единая точка входа, маршрутизация, проверка JWT |
| `user-service` | `8081` | Пользователи, регистрация, авторизация, JWT |
| `restaurant-service` | `8082` | Рестораны, категории меню, блюда |
| `order-service` | `8083` | Заказы и позиции заказа |

---

## Структура проекта

```text
FastDelivery/
├── gateway-service/
│   ├── src/main/java/...
│   ├── src/main/resources/
│   └── Dockerfile
│
├── user-service/
│   ├── src/main/java/...
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   ├── src/main/resources/
│   └── Dockerfile
│
├── restaurant-service/
│   ├── src/main/java/...
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   ├── src/main/resources/
│   └── Dockerfile
│
├── order-service/
│   ├── src/main/java/...
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   ├── src/main/resources/
│   └── Dockerfile
│
├── docker-compose.yml
├── .env.example
└── README.md
```

---

## Базы данных

Каждый микросервис использует отдельную PostgreSQL-базу.

| Сервис | База данных | Docker service |
|---|---|---|
| `user-service` | `userservice_db` | `user-db` |
| `restaurant-service` | `restaurantservice_db` | `restaurant-db` |
| `order-service` | `orderservice_db` | `order-db` |

### Почему отдельные базы

В микросервисной архитектуре каждый сервис должен владеть своими данными.

Например:

- `order-service` хранит `userId`, но не хранит сущность пользователя.
- `order-service` хранит `restaurantId`, но не хранит сущность ресторана.
- `restaurant-service` хранит `ownerId`, но не имеет связи `@ManyToOne` с пользователем.
- сервисы взаимодействуют через API Gateway, REST, а в будущем — через Kafka.

---

## Модели данных

### user-service

#### `AppUser`

Пользователь системы.

Основные поля:

```text
id
fullName
email
phone
passwordHash
role
status
createdAt
updatedAt
```

#### `UserAddress`

Адрес пользователя.

Основные поля:

```text
id
city
street
house
apartment
user
```

#### `UserRole`

```text
CUSTOMER
COURIER
RESTAURANT_OWNER
ADMIN
```

#### `UserStatus`

```text
ACTIVE
BLOCKED
DELETED
```

---

### restaurant-service

#### `Restaurant`

Ресторан.

Основные поля:

```text
id
name
description
address
phone
ownerId
status
createdAt
updatedAt
```

#### `RestaurantStatus`

```text
PENDING_REVIEW
ACTIVE
CLOSED
BLOCKED
DELETED
```

#### `MenuCategory`

Категория меню внутри ресторана.

Основные поля:

```text
id
restaurantId
name
sortOrder
active
createdAt
updatedAt
```

#### `MenuItem`

Блюдо ресторана.

Основные поля:

```text
id
restaurantId
categoryId
type
name
description
price
available
mediaURLs
createdAt
updatedAt
```

#### `MenuItemType`

```text
BURGER
PIZZA
SUSHI
DRINK
DESSERT
SALAD
SOUP
BREAKFAST
COMBO
OTHER
```

---

### order-service

#### `Order`

Заказ.

Основные поля:

```text
id
userId
restaurantId
status
totalPrice
deliveryAddress
comment
createdAt
updatedAt
```

#### `OrderItem`

Позиция заказа.

Основные поля:

```text
id
orderId
menuItemId
menuItemName
quantity
price
totalPrice
```

`OrderItem` хранит снимок блюда на момент заказа: название и цену. Это нужно, чтобы старые заказы не менялись, если ресторан позже изменит цену или название блюда.

#### `OrderStatus`

```text
CREATED
PAID
ACCEPTED_BY_RESTAURANT
COOKING
READY_FOR_DELIVERY
DELIVERING
DELIVERED
CANCELLED
FAILED
```

---

## API Endpoints

Все запросы рекомендуется отправлять через `gateway-service`:

```text
http://localhost:8080
```

---

## user-service

### Auth — `/api/auth`

| Метод | Путь | Описание | Auth |
|---|---|---|---|
| POST | `/api/auth/register` | Регистрация пользователя | Нет |
| POST | `/api/auth/login` | Авторизация и получение JWT | Нет |

### Пример регистрации

```json
POST /api/auth/register

{
  "fullName": "Иван Иванов",
  "email": "ivan@example.com",
  "phone": "+79990000000",
  "password": "password123"
}
```

### Пример логина

```json
POST /api/auth/login

{
  "email": "ivan@example.com",
  "password": "password123"
}
```

### Пример ответа

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer"
}
```

---

### Users — `/api/user`

| Метод | Путь | Описание | Auth |
|---|---|---|---|
| GET | `/api/user/all` | Получить всех пользователей | JWT |
| GET | `/api/user/{id}` | Получить пользователя по ID | JWT |
| PATCH | `/api/user/{id}` | Обновить пользователя | JWT |
| DELETE | `/api/user/{id}` | Удалить пользователя | JWT |

---

## restaurant-service

### Restaurants — `/api/restaurants`

| Метод | Путь | Описание | Auth |
|---|---|---|---|
| POST | `/api/restaurants/create` | Создать ресторан | JWT |
| GET | `/api/restaurants/all` | Получить все рестораны | JWT |
| GET | `/api/restaurants/{restaurantId}` | Получить ресторан по ID | JWT |
| PATCH | `/api/restaurants/{restaurantId}` | Обновить ресторан | JWT |
| PATCH | `/api/restaurants/{restaurantId}/status` | Изменить статус ресторана | JWT |

### Пример создания ресторана

```json
POST /api/restaurants/create

{
  "name": "Pizza House",
  "description": "Пиццерия с доставкой",
  "address": "Казань, Баумана 10",
  "phone": "+79991112233",
  "ownerId": 1
}
```

---

### Menu Categories — `/api/restaurants/{restaurantId}/categories`

| Метод | Путь | Описание | Auth |
|---|---|---|---|
| POST | `/api/restaurants/{restaurantId}/categories/create` | Создать категорию меню | JWT |
| GET | `/api/restaurants/{restaurantId}/categories/all` | Получить категории ресторана | JWT |
| GET | `/api/restaurants/{restaurantId}/categories/{menuCategoryId}` | Получить категорию по ID | JWT |
| PATCH | `/api/restaurants/{restaurantId}/categories/{menuCategoryId}` | Обновить категорию | JWT |
| PATCH | `/api/restaurants/{restaurantId}/categories/{menuCategoryId}/active` | Изменить активность категории | JWT |

### Пример создания категории

```json
POST /api/restaurants/1/categories/create

{
  "name": "Пицца",
  "sortOrder": 1
}
```

---

### Menu Items — `/api/restaurants/{restaurantId}/menu`

| Метод | Путь | Описание | Auth |
|---|---|---|---|
| POST | `/api/restaurants/{restaurantId}/menu/create` | Создать блюдо | JWT |
| GET | `/api/restaurants/{restaurantId}/menu/all` | Получить все блюда ресторана | JWT |
| GET | `/api/restaurants/{restaurantId}/menu/available` | Получить доступные блюда ресторана | JWT |
| GET | `/api/restaurants/{restaurantId}/menu/category/{categoryId}` | Получить блюда по категории | JWT |
| GET | `/api/restaurants/{restaurantId}/menu/{menuItemId}` | Получить блюдо по ID | JWT |
| PATCH | `/api/restaurants/{restaurantId}/menu/{menuItemId}` | Обновить блюдо | JWT |
| PATCH | `/api/restaurants/{restaurantId}/menu/{menuItemId}/availability` | Изменить доступность блюда | JWT |

### Пример создания блюда

```json
POST /api/restaurants/1/menu/create

{
  "categoryId": 1,
  "type": "PIZZA",
  "name": "Пепперони",
  "description": "Пицца с пепперони и сыром",
  "price": 599.00,
  "available": true,
  "mediaURLs": [
    "https://example.com/pizza-pepperoni.jpg"
  ]
}
```

---

### Global Menu Items — `/api/menu-items`

Глобальные ручки ищут блюда по всем ресторанам.

| Метод | Путь | Описание | Auth |
|---|---|---|---|
| GET | `/api/menu-items/type/{type}` | Получить блюда по типу | JWT |
| GET | `/api/menu-items/type/{type}/available` | Получить доступные блюда по типу | JWT |

Пример:

```text
GET /api/menu-items/type/PIZZA
GET /api/menu-items/type/BURGER/available
```

---

## order-service

### Orders — `/api/orders`

| Метод | Путь | Описание | Auth |
|---|---|---|---|
| POST | `/api/orders/create` | Создать заказ | JWT |
| GET | `/api/orders/all` | Получить все заказы | JWT |
| GET | `/api/orders/{orderId}` | Получить заказ по ID | JWT |
| GET | `/api/orders/user/{userId}` | Получить заказы пользователя | JWT |
| GET | `/api/orders/restaurant/{restaurantId}` | Получить заказы ресторана | JWT |
| PATCH | `/api/orders/{orderId}/status` | Изменить статус заказа | JWT |
| PATCH | `/api/orders/{orderId}/cancel` | Отменить заказ | JWT |

### Пример создания заказа

```json
POST /api/orders/create

{
  "userId": 1,
  "restaurantId": 1,
  "deliveryAddress": "Казань, Кремлёвская 1",
  "comment": "Не звонить, написать в чат",
  "items": [
    {
      "menuItemId": 1,
      "menuItemName": "Пепперони",
      "quantity": 2,
      "price": 599.00
    },
    {
      "menuItemId": 2,
      "menuItemName": "Кола",
      "quantity": 1,
      "price": 149.00
    }
  ]
}
```

### Пример изменения статуса

```json
PATCH /api/orders/1/status

{
  "status": "COOKING"
}
```

---

## Аутентификация

Проект использует JWT Bearer Token.

### Публичные эндпоинты

```text
POST /api/auth/register
POST /api/auth/login
```

### Защищённые эндпоинты

Для остальных запросов нужно передавать заголовок:

```text
Authorization: Bearer <accessToken>
```

JWT создаётся в `user-service`, а проверяется в `gateway-service`.

---

## Запуск через Docker Compose

### Требования

- Docker
- Docker Compose

### 1. Создать `.env`

В корне проекта создать файл `.env` на основе `.env.example`.

Пример:

```env
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres

USER_DB_NAME=userservice_db
RESTAURANT_DB_NAME=restaurantservice_db
ORDER_DB_NAME=orderservice_db

JWT_SECRET=super-secret-key-super-secret-key-123456
```

### 2. Запустить проект

```bash
docker compose up --build
```

### 3. Запустить в фоне

```bash
docker compose up --build -d
```

### 4. Посмотреть логи

```bash
docker compose logs -f
```

Логи конкретного сервиса:

```bash
docker compose logs -f gateway-service
docker compose logs -f user-service
docker compose logs -f restaurant-service
docker compose logs -f order-service
```

### 5. Остановить проект

```bash
docker compose down
```

### 6. Остановить и удалить данные БД

```bash
docker compose down -v
```

---

## Docker services

| Docker service | Назначение | Внешний порт |
|---|---|---:|
| `gateway-service` | API Gateway | `8080` |
| `user-service` | Пользователи | `8081` |
| `restaurant-service` | Рестораны и меню | `8082` |
| `order-service` | Заказы | `8083` |
| `user-db` | PostgreSQL для пользователей | `8091` |
| `restaurant-db` | PostgreSQL для ресторанов | `8092` |
| `order-db` | PostgreSQL для заказов | `8093` |

Внутри Docker Compose сервисы общаются по именам контейнеров:

```text
user-service -> user-db:5432
restaurant-service -> restaurant-db:5432
order-service -> order-db:5432
gateway-service -> user-service:8081
gateway-service -> restaurant-service:8082
gateway-service -> order-service:8083
```

Важно: внутри контейнера `localhost` означает сам контейнер, поэтому для связи между контейнерами используются имена сервисов.

---

## Локальный запуск

Можно запускать сервисы отдельно из IntelliJ IDEA.

### Пример локальных datasource URL

```properties
user-service:
jdbc:postgresql://localhost:8091/userservice_db

restaurant-service:
jdbc:postgresql://localhost:8092/restaurantservice_db

order-service:
jdbc:postgresql://localhost:8093/orderservice_db
```

### Запуск только баз данных

```bash
docker compose up user-db restaurant-db order-db -d
```

После этого сервисы можно запускать локально из IDE.

---

## Переменные окружения

### Общие переменные

| Переменная | Описание | Пример |
|---|---|---|
| `POSTGRES_USER` | Пользователь PostgreSQL | `postgres` |
| `POSTGRES_PASSWORD` | Пароль PostgreSQL | `postgres` |
| `JWT_SECRET` | Секрет для подписи JWT | `change-me` |

### Базы данных

| Переменная | Описание |
|---|---|
| `USER_DB_NAME` | Имя БД user-service |
| `RESTAURANT_DB_NAME` | Имя БД restaurant-service |
| `ORDER_DB_NAME` | Имя БД order-service |

### Spring-переменные сервисов

| Переменная | Описание |
|---|---|
| `SERVER_PORT` | Порт сервиса |
| `SPRING_DATASOURCE_URL` | JDBC URL базы данных |
| `SPRING_DATASOURCE_USERNAME` | Пользователь БД |
| `SPRING_DATASOURCE_PASSWORD` | Пароль БД |

---

## Конфигурация Gateway

`gateway-service` маршрутизирует запросы на внутренние сервисы.

Основные маршруты:

```yaml
/api/auth/**        -> user-service
/api/user/**        -> user-service
/api/restaurants/** -> restaurant-service
/api/menu-items/**  -> restaurant-service
/api/orders/**      -> order-service
```

В Docker Compose gateway обращается к сервисам по внутренним адресам:

```text
http://user-service:8081
http://restaurant-service:8082
http://order-service:8083
```

---

## Особенности реализации

### DTO вместо Entity

Контроллеры возвращают DTO, а не JPA-сущности.

Это нужно, чтобы:

- не отдавать лишние поля наружу;
- не раскрывать внутреннюю структуру БД;
- избежать проблем с lazy loading;
- держать API стабильным.

### BigDecimal для денег

Для цен используется `BigDecimal`, а не `double` или `float`.

```java
private BigDecimal price;
private BigDecimal totalPrice;
```

### Заказы хранят снимок товара

`OrderItem` хранит:

```text
menuItemId
menuItemName
price
quantity
totalPrice
```

Это позволяет сохранить корректную историю заказа, даже если ресторан позже изменит цену или название блюда.

### Отдельная база на сервис

Каждый сервис владеет своей БД:

```text
user-service       -> userservice_db
restaurant-service -> restaurantservice_db
order-service      -> orderservice_db
```

Другие сервисы не обращаются к чужой базе напрямую.

---

## План развития

### Kafka

Планируется добавить событийное взаимодействие между сервисами.

Возможные события:

```text
order.created
order.status.changed
order.cancelled
```

### Redis

Планируется использовать Redis для:

```text
кэша меню
кэша популярных блюд
rate limiting
blacklist JWT
```

### Prometheus + Grafana

Планируется добавить мониторинг:

```text
Spring Boot Actuator
Micrometer
Prometheus
Grafana
```

### Возможные будущие сервисы

```text
payment-service
delivery-service
notification-service
review-service
```

---

## Статус проекта

Реализовано:

- `user-service`
- регистрация и авторизация
- JWT generation
- `gateway-service`
- JWT validation через gateway
- `restaurant-service`
- управление ресторанами
- управление категориями меню
- управление блюдами
- `order-service`
- создание заказов
- позиции заказа
- изменение статуса заказа
- Dockerfile для сервисов
- Docker Compose окружение
- отдельные PostgreSQL контейнеры для сервисов

В разработке:

- Kafka events
- Redis cache
- Prometheus + Grafana monitoring
- улучшение обработки ошибок через `@ControllerAdvice`
- dev data seeding
