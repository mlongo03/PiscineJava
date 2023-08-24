DROP SCHEMA IF EXISTS numbers CASCADE;

CREATE SCHEMA IF NOT EXISTS numbers;


CREATE TABLE IF NOT EXISTS numbers.product (
    identifier int PRIMARY KEY,
    name VARCHAR(255),
    price DECIMAL(10, 2)
);
