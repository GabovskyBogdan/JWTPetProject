create table roles
(
    id        bigserial not null primary key,
    name      text not null unique
);