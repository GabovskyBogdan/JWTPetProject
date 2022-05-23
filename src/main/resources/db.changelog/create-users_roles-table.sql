CREATE TABLE users_roles (
    roles_id bigserial not null,
    users_id bigserial not null,
    FOREIGN KEY (roles_id) REFERENCES roles(id),
    FOREIGN KEY (users_id) REFERENCES users(id)
);