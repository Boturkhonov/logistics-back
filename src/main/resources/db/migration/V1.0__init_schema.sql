create table "order"
(
    id            bigserial unique not null,
    customer_id   bigint           not null,
    order_date    timestamp,
    finished_date timestamp,
    comment       text
);

create table route
(
    id        bigserial unique not null,
    driver_id bigint           not null,
    order_id  bigint           not null,
    finished  boolean          not null
);

create table route_address
(
    route_id   bigint not null,
    address_id bigint not null
);

create table vehicle
(
    id            bigserial unique not null,
    name          varchar(255)     not null,
    start_address bigint           not null
);

create table customer
(
    id           bigserial unique not null,
    name         varchar(255),
    phone_number varchar(255)
);

create table address
(
    id   bigserial unique not null,
    name text             not null,
    lon  double precision not null,
    lat  double precision not null
);

create table order_address
(
    order_id   bigint not null,
    address_id bigint not null
);

create table app_user
(
    id           bigserial unique not null,
    name         varchar(255)     not null,
    surname      varchar(255)     not null,
    patronymic   varchar(255),
    login        varchar(255)     not null,
    password     varchar(255)     not null,
    phone_number varchar(255),
    vehicle_id   bigint
);

create table role
(
    id   bigserial unique not null,
    name varchar(32)      not null
);

create table user_role
(
    user_id bigint not null,
    role_id bigint not null
);

--
-- Индексы сохранённых таблиц
--
alter table only "order"
    add primary key (id);
alter table only route
    add primary key (id);
alter table only vehicle
    add primary key (id);
alter table only customer
    add primary key (id);
alter table only address
    add primary key (id);
alter table only app_user
    add primary key (id);
alter table only role
    add primary key (id);
--
-- Ограничения внешнего ключа сохраненных таблиц
--
alter table only "order"
    add constraint order_customer_id_fk foreign key (customer_id) references customer (id);

alter table only route
    add constraint route_driver_id_fk foreign key (driver_id) references app_user (id),
    add constraint route_order_id_fk foreign key (order_id) references "order" (id);

alter table only route_address
    add constraint route_address_route_id_fk foreign key (route_id) references route (id),
    add constraint route_address_address_id_fk foreign key (address_id) references address (id);

alter table only app_user
    add constraint app_user_vehicle_id_fk foreign key (vehicle_id) references vehicle (id);

alter table only vehicle
    add constraint vehicle_start_address_fk foreign key (start_address) references address (id);

alter table only user_role
    add constraint user_role_user_id_fk foreign key (user_id) references app_user (id),
    add constraint user_role_role_id_fk foreign key (role_id) references role (id);

alter table only order_address
    add constraint order_address_order_id_fk foreign key (order_id) references "order" (id),
    add constraint order_address_address_id_fk foreign key (address_id) references address (id);