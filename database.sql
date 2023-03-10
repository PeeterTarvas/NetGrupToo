--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1
-- Dumped by pg_dump version 15.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: data; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA data;


--
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: catalogue; Type: TABLE; Schema: data; Owner: -
--

CREATE TABLE data.catalogue (
    catalogue_id bigint NOT NULL,
    catalogue_name character varying(80) NOT NULL,
    catalogue_owner character varying(40) NOT NULL
);


--
-- Name: catalogue_catalogue_id_seq; Type: SEQUENCE; Schema: data; Owner: -
--

CREATE SEQUENCE data.catalogue_catalogue_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: catalogue_catalogue_id_seq; Type: SEQUENCE OWNED BY; Schema: data; Owner: -
--

ALTER SEQUENCE data.catalogue_catalogue_id_seq OWNED BY data.catalogue.catalogue_id;


--
-- Name: catalogue_catalogue_reference; Type: TABLE; Schema: data; Owner: -
--

CREATE TABLE data.catalogue_catalogue_reference (
    reference_id bigint NOT NULL,
    parent_catalogue_id bigint NOT NULL,
    child_catalogue_id bigint NOT NULL
);


--
-- Name: catalogue_catalogue_reference_reference_id_seq; Type: SEQUENCE; Schema: data; Owner: -
--

CREATE SEQUENCE data.catalogue_catalogue_reference_reference_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: catalogue_catalogue_reference_reference_id_seq; Type: SEQUENCE OWNED BY; Schema: data; Owner: -
--

ALTER SEQUENCE data.catalogue_catalogue_reference_reference_id_seq OWNED BY data.catalogue_catalogue_reference.reference_id;


--
-- Name: catalogue_product_reference; Type: TABLE; Schema: data; Owner: -
--

CREATE TABLE data.catalogue_product_reference (
    reference_id bigint NOT NULL,
    catalogue_id bigint NOT NULL,
    product_id bigint NOT NULL
);


--
-- Name: catalogue_product_reference_reference_id_seq; Type: SEQUENCE; Schema: data; Owner: -
--

CREATE SEQUENCE data.catalogue_product_reference_reference_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: catalogue_product_reference_reference_id_seq; Type: SEQUENCE OWNED BY; Schema: data; Owner: -
--

ALTER SEQUENCE data.catalogue_product_reference_reference_id_seq OWNED BY data.catalogue_product_reference.reference_id;


--
-- Name: condition; Type: TABLE; Schema: data; Owner: -
--

CREATE TABLE data.condition (
    condition_id bigint NOT NULL,
    condition character varying(20) NOT NULL
);


--
-- Name: condition_condition_id_seq; Type: SEQUENCE; Schema: data; Owner: -
--

CREATE SEQUENCE data.condition_condition_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: condition_condition_id_seq; Type: SEQUENCE OWNED BY; Schema: data; Owner: -
--

ALTER SEQUENCE data.condition_condition_id_seq OWNED BY data.condition.condition_id;


--
-- Name: product; Type: TABLE; Schema: data; Owner: -
--

CREATE TABLE data.product (
    product_id bigint NOT NULL,
    name character varying(100) NOT NULL,
    serial_number character varying(80),
    picture_link character varying(200) NOT NULL,
    product_owner character varying(40) NOT NULL,
    condition character varying(20) NOT NULL,
    description text,
    amount bigint,
    CONSTRAINT amount_is_not_negative CHECK ((amount >= 0))
);


--
-- Name: product_product_id_seq; Type: SEQUENCE; Schema: data; Owner: -
--

CREATE SEQUENCE data.product_product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: product_product_id_seq; Type: SEQUENCE OWNED BY; Schema: data; Owner: -
--

ALTER SEQUENCE data.product_product_id_seq OWNED BY data.product.product_id;


--
-- Name: roles; Type: TABLE; Schema: data; Owner: -
--

CREATE TABLE data.roles (
    role_id bigint NOT NULL,
    role_name character varying(20) NOT NULL
);


--
-- Name: roles_role_id_seq; Type: SEQUENCE; Schema: data; Owner: -
--

CREATE SEQUENCE data.roles_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: roles_role_id_seq; Type: SEQUENCE OWNED BY; Schema: data; Owner: -
--

ALTER SEQUENCE data.roles_role_id_seq OWNED BY data.roles.role_id;


--
-- Name: user; Type: TABLE; Schema: data; Owner: -
--

CREATE TABLE data."user" (
    user_id bigint NOT NULL,
    username character varying(40) NOT NULL,
    email character varying(40) NOT NULL,
    role_name character varying(20) DEFAULT 'ROLE_USER'::character varying NOT NULL,
    reference_user_username character varying(40) DEFAULT NULL::character varying,
    password character varying(50) NOT NULL,
    number_of_items bigint DEFAULT 0 NOT NULL,
    maximum_items bigint DEFAULT 1000000 NOT NULL,
    cost numeric DEFAULT 0 NOT NULL,
    CONSTRAINT if_user_is_business_then_reference_person_exists CHECK (((NOT ((role_name)::text = 'ROLE_BUSINESS'::text)) OR (reference_user_username IS NOT NULL)))
);


--
-- Name: user_user_id_seq; Type: SEQUENCE; Schema: data; Owner: -
--

CREATE SEQUENCE data.user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: user_user_id_seq; Type: SEQUENCE OWNED BY; Schema: data; Owner: -
--

ALTER SEQUENCE data.user_user_id_seq OWNED BY data."user".user_id;


--
-- Name: catalogue catalogue_id; Type: DEFAULT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue ALTER COLUMN catalogue_id SET DEFAULT nextval('data.catalogue_catalogue_id_seq'::regclass);


--
-- Name: catalogue_catalogue_reference reference_id; Type: DEFAULT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue_catalogue_reference ALTER COLUMN reference_id SET DEFAULT nextval('data.catalogue_catalogue_reference_reference_id_seq'::regclass);


--
-- Name: catalogue_product_reference reference_id; Type: DEFAULT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue_product_reference ALTER COLUMN reference_id SET DEFAULT nextval('data.catalogue_product_reference_reference_id_seq'::regclass);


--
-- Name: condition condition_id; Type: DEFAULT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.condition ALTER COLUMN condition_id SET DEFAULT nextval('data.condition_condition_id_seq'::regclass);


--
-- Name: product product_id; Type: DEFAULT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.product ALTER COLUMN product_id SET DEFAULT nextval('data.product_product_id_seq'::regclass);


--
-- Name: roles role_id; Type: DEFAULT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.roles ALTER COLUMN role_id SET DEFAULT nextval('data.roles_role_id_seq'::regclass);


--
-- Name: user user_id; Type: DEFAULT; Schema: data; Owner: -
--

ALTER TABLE ONLY data."user" ALTER COLUMN user_id SET DEFAULT nextval('data.user_user_id_seq'::regclass);


--
-- Data for Name: catalogue; Type: TABLE DATA; Schema: data; Owner: -
--

INSERT INTO data.catalogue VALUES (1, 'Home', 'Peeter');
INSERT INTO data.catalogue VALUES (2, 'Storage', 'Peeter');


--
-- Data for Name: catalogue_catalogue_reference; Type: TABLE DATA; Schema: data; Owner: -
--

INSERT INTO data.catalogue_catalogue_reference VALUES (1, 1, 2);


--
-- Data for Name: catalogue_product_reference; Type: TABLE DATA; Schema: data; Owner: -
--

INSERT INTO data.catalogue_product_reference VALUES (1, 1, 1);


--
-- Data for Name: condition; Type: TABLE DATA; Schema: data; Owner: -
--

INSERT INTO data.condition VALUES (1, 'GOOD');
INSERT INTO data.condition VALUES (2, 'BAD');
INSERT INTO data.condition VALUES (3, 'BROKEN');


--
-- Data for Name: product; Type: TABLE DATA; Schema: data; Owner: -
--

INSERT INTO data.product VALUES (1, 'ABC', '123', 'none', 'Peeter', 'GOOD', 'Its good', 2);


--
-- Data for Name: roles; Type: TABLE DATA; Schema: data; Owner: -
--

INSERT INTO data.roles VALUES (1, 'ROLE_USER');
INSERT INTO data.roles VALUES (2, 'ROLE_ADMIN');
INSERT INTO data.roles VALUES (3, 'ROLE_BUSINESS');


--
-- Data for Name: user; Type: TABLE DATA; Schema: data; Owner: -
--

INSERT INTO data."user" VALUES (1, 'admin', 'admin@admin.com', 'ROLE_ADMIN', NULL, 'admin', 0, 1000000, 0);
INSERT INTO data."user" VALUES (2, 'Peeter', 'peetertarvas@gmail.com', 'ROLE_USER', NULL, '123', 0, 1000000, 0);
INSERT INTO data."user" VALUES (3, 'Peeter Ou', 'ou@ou.com', 'ROLE_BUSINESS', 'Peeter', '123', 101, 100, 0.1);


--
-- Name: catalogue_catalogue_id_seq; Type: SEQUENCE SET; Schema: data; Owner: -
--

SELECT pg_catalog.setval('data.catalogue_catalogue_id_seq', 2, true);


--
-- Name: catalogue_catalogue_reference_reference_id_seq; Type: SEQUENCE SET; Schema: data; Owner: -
--

SELECT pg_catalog.setval('data.catalogue_catalogue_reference_reference_id_seq', 1, true);


--
-- Name: catalogue_product_reference_reference_id_seq; Type: SEQUENCE SET; Schema: data; Owner: -
--

SELECT pg_catalog.setval('data.catalogue_product_reference_reference_id_seq', 1, true);


--
-- Name: condition_condition_id_seq; Type: SEQUENCE SET; Schema: data; Owner: -
--

SELECT pg_catalog.setval('data.condition_condition_id_seq', 3, true);


--
-- Name: product_product_id_seq; Type: SEQUENCE SET; Schema: data; Owner: -
--

SELECT pg_catalog.setval('data.product_product_id_seq', 1, true);


--
-- Name: roles_role_id_seq; Type: SEQUENCE SET; Schema: data; Owner: -
--

SELECT pg_catalog.setval('data.roles_role_id_seq', 3, true);


--
-- Name: user_user_id_seq; Type: SEQUENCE SET; Schema: data; Owner: -
--

SELECT pg_catalog.setval('data.user_user_id_seq', 3, true);


--
-- Name: catalogue_product_reference ak_catalogue_id_parent_id; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue_product_reference
    ADD CONSTRAINT ak_catalogue_id_parent_id UNIQUE (catalogue_id, product_id);


--
-- Name: catalogue ak_catalogue_name_and_catalogue_owner; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue
    ADD CONSTRAINT ak_catalogue_name_and_catalogue_owner UNIQUE (catalogue_name, catalogue_owner);


--
-- Name: condition ak_condition_name; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.condition
    ADD CONSTRAINT ak_condition_name UNIQUE (condition);


--
-- Name: catalogue_catalogue_reference ak_dont_allow_child_to_be_parent; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue_catalogue_reference
    ADD CONSTRAINT ak_dont_allow_child_to_be_parent UNIQUE (child_catalogue_id, parent_catalogue_id);


--
-- Name: user ak_email; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data."user"
    ADD CONSTRAINT ak_email UNIQUE (email);


--
-- Name: user ak_name_is_unique; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data."user"
    ADD CONSTRAINT ak_name_is_unique UNIQUE (username);


--
-- Name: roles ak_role_name; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.roles
    ADD CONSTRAINT ak_role_name UNIQUE (role_name);


--
-- Name: catalogue pk_catalogue_id; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue
    ADD CONSTRAINT pk_catalogue_id PRIMARY KEY (catalogue_id);


--
-- Name: catalogue_product_reference pk_catalogue_id_product_id; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue_product_reference
    ADD CONSTRAINT pk_catalogue_id_product_id PRIMARY KEY (reference_id);


--
-- Name: condition pk_condition_id; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.condition
    ADD CONSTRAINT pk_condition_id PRIMARY KEY (condition_id);


--
-- Name: catalogue_catalogue_reference pk_parent_catalogue_id_child_catalogue_id; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue_catalogue_reference
    ADD CONSTRAINT pk_parent_catalogue_id_child_catalogue_id PRIMARY KEY (reference_id);


--
-- Name: product pk_product_id; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.product
    ADD CONSTRAINT pk_product_id PRIMARY KEY (product_id);


--
-- Name: roles pk_role_id; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.roles
    ADD CONSTRAINT pk_role_id PRIMARY KEY (role_id);


--
-- Name: user pk_user_id; Type: CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data."user"
    ADD CONSTRAINT pk_user_id PRIMARY KEY (user_id);


--
-- Name: catalogue_product_reference fk_catalogue_id; Type: FK CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue_product_reference
    ADD CONSTRAINT fk_catalogue_id FOREIGN KEY (catalogue_id) REFERENCES data.catalogue(catalogue_id);


--
-- Name: catalogue fk_catalogue_owner; Type: FK CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue
    ADD CONSTRAINT fk_catalogue_owner FOREIGN KEY (catalogue_owner) REFERENCES data."user"(username);


--
-- Name: catalogue_catalogue_reference fk_child_catalogue_id; Type: FK CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue_catalogue_reference
    ADD CONSTRAINT fk_child_catalogue_id FOREIGN KEY (child_catalogue_id) REFERENCES data.catalogue(catalogue_id);


--
-- Name: product fk_condition; Type: FK CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.product
    ADD CONSTRAINT fk_condition FOREIGN KEY (condition) REFERENCES data.condition(condition);


--
-- Name: catalogue_catalogue_reference fk_parent_catalogue_id; Type: FK CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue_catalogue_reference
    ADD CONSTRAINT fk_parent_catalogue_id FOREIGN KEY (parent_catalogue_id) REFERENCES data.catalogue(catalogue_id);


--
-- Name: catalogue_product_reference fk_product_id; Type: FK CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.catalogue_product_reference
    ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES data.product(product_id);


--
-- Name: product fk_product_owner; Type: FK CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data.product
    ADD CONSTRAINT fk_product_owner FOREIGN KEY (product_owner) REFERENCES data."user"(username);


--
-- Name: user fk_role_name; Type: FK CONSTRAINT; Schema: data; Owner: -
--

ALTER TABLE ONLY data."user"
    ADD CONSTRAINT fk_role_name FOREIGN KEY (role_name) REFERENCES data.roles(role_name) ON UPDATE CASCADE;


--
-- PostgreSQL database dump complete
--

