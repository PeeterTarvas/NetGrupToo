INSERT INTO data.roles(role_name)
VALUES ('ROLE_USER');

INSERT INTO data.roles(role_name)
VALUES ('ROLE_ADMIN');

INSERT INTO data.roles(role_name)
VALUES ('ROLE_BUSINESS');

INSERT INTO data.condition(condition)
VALUES ('GOOD');

INSERT INTO data.condition(condition)
VALUES ('BAD');

INSERT INTO data.condition(condition)
VALUES ('BROKEN');


INSERT INTO data."user"(username, email, role_name, password)
VALUES ('admin', 'admin@admin.com','ROLE_ADMIN', 'admin');

INSERT INTO data."user"(username, email, role_name, password)
VALUES ('Peeter', 'peetertarvas@gmail.com','ROLE_USER', '123');

INSERT INTO data."user"(username, email, role_name, reference_user_username, password, number_of_items, maximum_items, cost)
VALUES ('Peeter Ou', 'ou@ou.com' , 'ROLE_BUSINESS', 'Peeter', '123', 101, 100, 0.1);

INSERT INTO data.catalogue(catalogue_owner, catalogue_name)
 VALUES ('Peeter', 'Home');

INSERT INTO data.product(name, serial_number, picture_link, product_owner, condition, description, amount)
 VALUES ('ABC', '123', 'none', 'Peeter', 'GOOD', 'Its good', 2);

INSERT INTO data.catalogue_product_reference(catalogue_id, product_id)
VALUES (1,1);

INSERT INTO data.catalogue(catalogue_owner, catalogue_name)
VALUES ('Peeter', 'Storage');

INSERT INTO data.catalogue_catalogue_reference(parent_catalogue_id, child_catalogue_id)
VALUES (1,2);

