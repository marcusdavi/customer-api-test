CREATE TABLE IF NOT EXISTS CUSTOMER (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    gender CHAR(1) NOT NULL
);