CREATE SCHEMA IF NOT EXISTS data;

DROP TABLE IF EXISTS data.roles CASCADE;

DROP TABLE IF EXISTS data."user" CASCADE;

CREATE TYPE data.product_condition AS ENUM ('GOOD', 'BAD', 'BROKEN');

CREATE TABLE data.roles
(
    role_id BIGSERIAL UNIQUE NOT NULL,
    role_name varchar(20) NOT NULL,
    CONSTRAINT PK_role_id PRIMARY KEY (role_id),
    CONSTRAINT AK_role_name UNIQUE (role_name)
);

CREATE TABLE data."user"
(
    user_id BIGSERIAL NOT NULL,
    username varchar(40) NOT NULL,
    email varchar(40) NOT NULL,
    role_name varchar(20) NOT NULL DEFAULT 'ROLE_USER',
    reference_user_username varchar(40) DEFAULT NULL,
    password varchar(50) NOT NULL,
    number_of_items BIGINT NOT NULL DEFAULT 0,
    maximum_items BIGINT NOT NULL DEFAULT 1000000,
    cost BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT PK_user_id PRIMARY KEY (user_id),
    CONSTRAINT FK_role_name FOREIGN KEY (role_name) REFERENCES roles(role_name)
        ON DELETE No Action  ON UPDATE CASCADE,
    CONSTRAINT AK_name_and_password_are_unique UNIQUE (username, password),
    CONSTRAINT AK_email UNIQUE (email),
    CONSTRAINT if_user_is_business_then_reference_person_exists CHECK (
        (NOT role_name='ROLE_BUSINESS') OR (reference_user_username IS NOT NULL)
        )
);

CREATE TABLE data.product
(
    product_id BIGSERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    serial_number BIGINT,
    picture_link VARCHAR(200) NOT NULL,
    product_owner BIGINT NOT NULL,
    condition data.product_condition,
    CONSTRAINT PK_product_id PRIMARY KEY (product_id),
    CONSTRAINT FK_product_owner FOREIGN KEY (product_owner) REFERENCES data.user(user_id)

);

CREATE TABLE data.catalogue
(
  catalogue_id BIGSERIAL NOT NULL,
  catalogue_owner BIGINT NOT NULL,
  CONSTRAINT PK_catalogue_id PRIMARY KEY (catalogue_id),
  CONSTRAINT FK_catalogue_owner FOREIGN KEY (catalogue_owner) REFERENCES data.user(user_id)
);

CREATE TABLE data.catalogue_product_reference
(
    catalogue_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT PK_catalogue_id_product_id PRIMARY KEY (catalogue_id, product_id),
    CONSTRAINT FK_catalogue_id FOREIGN KEY (catalogue_id) REFERENCES data.catalogue(catalogue_id),
    CONSTRAINT FK_product_id FOREIGN KEY (product_id) REFERENCES data.product(product_id)

);
CREATE TABLE data.catalogue_catalogue_reference
(
    parent_catalogue_id BIGINT NOT NULL,
    child_catalogue_id BIGINT NOT NULL,
    CONSTRAINT PK_parent_catalogue_id_child_catalogue_id PRIMARY KEY (parent_catalogue_id, child_catalogue_id),
    CONSTRAINT AK_dont_allow_child_to_be_parent UNIQUE (child_catalogue_id, parent_catalogue_id),
    CONSTRAINT FK_parent_catalogue_id FOREIGN KEY (parent_catalogue_id) REFERENCES data.catalogue(catalogue_id),
    CONSTRAINT FK_child_catalogue_id FOREIGN KEY (child_catalogue_id) REFERENCES data.catalogue(catalogue_id)
);