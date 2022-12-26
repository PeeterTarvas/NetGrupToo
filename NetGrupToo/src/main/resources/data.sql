INSERT INTO data.roles(role_name)
VALUES ('ROLE_USER');

INSERT INTO data.roles(role_name)
VALUES ('ROLE_ADMIN');

INSERT INTO data.roles(role_name)
VALUES ('ROLE_BUSINESS');

INSERT INTO data."user"(username, email, role_name, password)
VALUES ('admin', 'admin@admin.com','ROLE_ADMIN', 'admin');

INSERT INTO data."user"(username, email, role_name, password)
VALUES ('Peeter Tarvas', 'peetertarvas@gmail.com','ROLE_USER', '123');

