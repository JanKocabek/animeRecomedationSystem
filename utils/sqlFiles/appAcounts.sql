CREATE TABLE app_accounts
(
    id            INT AUTO_INCREMENT
        PRIMARY KEY,
    email         VARCHAR(255)                        NULL,
    username      VARCHAR(255)                        NOT NULL,
    password_hash TEXT                                NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    CONSTRAINT username
        UNIQUE (username)
);


