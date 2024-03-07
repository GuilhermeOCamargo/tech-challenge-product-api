CREATE DATABASE IF NOT EXISTS tech_challenge_product;

CREATE USER IF NOT EXISTS application_user IDENTIFIED BY 'SENHA123';

USE tech_challenge_product;

GRANT ALL PRIVILEGES ON tech_challenge_product.* TO 'application_user'@'%';