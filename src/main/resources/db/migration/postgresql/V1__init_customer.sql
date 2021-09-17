CREATE TYPE status as enum (
    'ACTIVE',
    'PASSIVE'
);

CREATE TYPE gender as enum (
    'MALE',
    'FEMALE'
);

CREATE TYPE segment as enum (
    'CLASSIC',
    'GOLD',
    'PLATINUM'
);

CREATE TABLE customer
(
    id               uuid                   NOT NULL DEFAULT uuid_generate_v4(),
    email            character varying(255) NOT NULL,
    first_name       character varying(255),
    last_name        character varying(255),
    birthdate        date,
    gender           gender,
    profession_id    uuid,
    segment          segment                NOT NULL DEFAULT 'CLASSIC',
    status           status                 NOT NULL DEFAULT 'ACTIVE',
    version          bigint                 NOT NULL DEFAULT 0,
    created_at       timestamp without time zone NOT NULL DEFAULT now(),
    created_by       uuid,
    last_modified_at timestamp without time zone NOT NULL DEFAULT now(),
    last_modified_by uuid
);

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);
