CREATE TABLE IF NOT EXISTS currency
(
    id       INT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    currency_code       VARCHAR(10)  NOT NULL,
    currency_name       VARCHAR(32)  NOT NULL
);
