create sequence users_seq;
create table users (
    id bigint primary key default nextval('users_seq'),
    first_name varchar(20) not null ,
    second_name varchar(20) not null ,
    email varchar(100) unique not null,
    password_hash text not null ,
    telephone varchar(20) not null,
    current_token text,
    role varchar(20) check ( role in('user', 'admin') ) not null
);

create table category(
    id bigserial primary key,
    name varchar(100)
);

create table product(
    id bigserial primary key,
    name varchar(50) unique not null ,
    category_id integer references category(id) not null,
    price numeric not null,
    picture_path text
);

create table cart(
    id bigserial primary key,
    user_id int references users(id) not null,
    product_id int references product(id) not null
);

create table orders(
    order_id bigserial primary key,
    user_id int references users(id) not null,
    status varchar(50) check (status in ('Оформлен', 'Доставляется', 'Доставлен', 'Отменен')) default 'Оформлен' not null ,
    products_id int[] not null,
    created_at timestamp default current_timestamp not null
);


