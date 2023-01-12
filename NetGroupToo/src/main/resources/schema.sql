CREATE SCHEMA IF NOT EXISTS data;

DROP TABLE IF EXISTS data.roles CASCADE;

DROP TABLE IF EXISTS data."user" CASCADE;

DROP TABLE IF EXISTS data.product CASCADE;

DROP TABLE IF EXISTS data.catalogue CASCADE;

DROP TABLE IF EXISTS data.catalogue_product_reference CASCADE;

DROP TABLE IF EXISTS data.catalogue_catalogue_reference CASCADE;

DROP TABLE IF EXISTS data.condition CASCADE;



CREATE TABLE data.roles
(
    role_id BIGSERIAL NOT NULL,
    role_name varchar(20) NOT NULL,
    CONSTRAINT PK_role_id PRIMARY KEY (role_id),
    CONSTRAINT AK_role_name UNIQUE (role_name)
);

CREATE TABLE data.condition
(
    condition_id BIGSERIAL NOT NULL,
    condition varchar(20) NOT NULL,
    CONSTRAINT PK_condition_id PRIMARY KEY (condition_id),
    CONSTRAINT AK_condition_name UNIQUE (condition)
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
    cost DECIMAL  NOT NULL DEFAULT 0,
    CONSTRAINT PK_user_id PRIMARY KEY (user_id),
    CONSTRAINT FK_role_name FOREIGN KEY (role_name) REFERENCES data.roles(role_name)
        ON DELETE No Action  ON UPDATE CASCADE,
    CONSTRAINT AK_name_is_unique UNIQUE (username),
    CONSTRAINT AK_email UNIQUE (email),
    CONSTRAINT if_user_is_business_then_reference_person_exists CHECK (
        (NOT role_name='ROLE_BUSINESS') OR (reference_user_username IS NOT NULL)
        )
);

CREATE TABLE data.product
(
    product_id BIGSERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    serial_number varchar(80),
    picture_link VARCHAR(200) NOT NULL,
    product_owner varchar(40) NOT NULL,
    condition varchar(20) NOT NULL,
    description text,
    amount bigint,
    CONSTRAINT PK_product_id PRIMARY KEY (product_id),
    CONSTRAINT FK_product_owner FOREIGN KEY (product_owner) REFERENCES data.user(username),
    CONSTRAINT FK_condition FOREIGN KEY (condition) references data.condition(condition),
    CONSTRAINT Amount_is_not_negative CHECK ( amount >= 0)
);

CREATE TABLE data.catalogue
(
  catalogue_id BIGSERIAL NOT NULL,
  catalogue_name varchar(80) NOT NULL,
  catalogue_owner varchar(40) NOT NULL,
  CONSTRAINT PK_catalogue_id PRIMARY KEY (catalogue_id),
  CONSTRAINT FK_catalogue_owner FOREIGN KEY (catalogue_owner) REFERENCES data.user(username),
  CONSTRAINT AK_catalogue_name_and_catalogue_owner UNIQUE (catalogue_name, catalogue_owner)
);

CREATE TABLE data.catalogue_product_reference
(
    reference_id BIGSERIAL NOT NULL,
    catalogue_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT PK_catalogue_id_product_id PRIMARY KEY (reference_id),
    CONSTRAINT AK_catalogue_id_parent_id UNIQUE (catalogue_id, product_id),
    CONSTRAINT FK_catalogue_id FOREIGN KEY (catalogue_id) REFERENCES data.catalogue(catalogue_id),
    CONSTRAINT FK_product_id FOREIGN KEY (product_id) REFERENCES data.product(product_id)

);
CREATE TABLE data.catalogue_catalogue_reference
(
    reference_id BIGSERIAL NOT NULL,
    parent_catalogue_id BIGINT NOT NULL,
    child_catalogue_id BIGINT NOT NULL,
    CONSTRAINT PK_parent_catalogue_id_child_catalogue_id PRIMARY KEY (reference_id),
    CONSTRAINT AK_dont_allow_child_to_be_parent UNIQUE (child_catalogue_id, parent_catalogue_id),
    CONSTRAINT FK_parent_catalogue_id FOREIGN KEY (parent_catalogue_id) REFERENCES data.catalogue(catalogue_id),
    CONSTRAINT FK_child_catalogue_id FOREIGN KEY (child_catalogue_id) REFERENCES data.catalogue(catalogue_id)
);