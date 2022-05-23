create table users
(
    id        bigserial not null primary key,
    username  text not null unique
    password  text not null
    role      varray
);