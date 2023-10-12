
-- БАЗА ДАНИХ
-- Можливість створення БД з метою уникнення некваліфікованих
-- дій, краще залишити за розробником.
-- Тому такий функціонал у додатку не прописуємо.
-- Можемо створити БД через візуальний інструмент, наприклад,
-- MySQL Workbench.
CREATE DATABASE demo_db;


-- ТАБЛИЦІ
-- Можливість створення таблиць БД, з метою уникнення некваліфікованих
-- дій, краще залишити за розробником.
-- Тому такий функціонал у додатку не прописуємо.
-- Попередньо, необхідно спроектувати таблиці та їх зв'язки,
-- на основі сутностей реального світу.
-- Можемо створити таблиці БД через візуальний інструмент, наприклад,
-- MySQL Workbench.

CREATE TABLE IF NOT EXISTS contacts
( id INTEGER NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(128) NOT NULL,
  last_name VARCHAR(128) NOT NULL,
  phone VARCHAR(56) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
( id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL
);

