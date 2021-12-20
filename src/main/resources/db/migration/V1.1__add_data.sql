insert into address (name, lon, lat)
values ('103, Вилюйская улица, 644019, Иркутск, Россия', 104.376239, 52.283908749999995);

insert into app_user (name, surname, patronymic, login, password)
values ('Оператор', '', '', 'admin', '$2a$12$5uJvH5KUlA8z20ScHs3/EO8oe3NtGBpFxh1EGljGookBd8eL7i5oC'),
       ('Иван', 'Иванов', 'Иванович', 'driver', '$2a$12$5uJvH5KUlA8z20ScHs3/EO8oe3NtGBpFxh1EGljGookBd8eL7i5oC');

insert into role(name)
values ('ADMIN'),
       ('DRIVER');

insert into user_role(user_id, role_id)
values (1, 1),
       (1, 2),
       (2, 2);

alter sequence address_id_seq restart with 2;
alter sequence app_user_id_seq restart with 2;