CREATE DATABASE phone_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Chèn dữ liệu cho manufactures
INSERT INTO manufactures (name, country)
VALUES ('Apple', 'USA');
INSERT INTO manufactures (name, country)
VALUES ('Samsung', 'South Korea');
INSERT INTO manufactures (name, country)
VALUES ('Google', 'USA');
INSERT INTO manufactures (name, country)
VALUES ('Xiaomi', 'China');


-- Chèn dữ liệu cho phones
-- Apple
INSERT INTO phones (name, price, color, manufacture_id)
VALUES ('iPhone 14 Pro', 25000000, 'Deep Purple', 1);
INSERT INTO phones (name, price, color, manufacture_id)
VALUES ('iPhone 14', 20000000, 'Blue', 1);
INSERT INTO phones (name, price, color, manufacture_id)
VALUES ('iPhone SE', 11000000, 'Midnight', 1);

-- Samsung
INSERT INTO phones (name, price, color, manufacture_id)
VALUES ('Galaxy S23 Ultra', 30000000, 'Phantom Black', 2);
INSERT INTO phones (name, price, color, manufacture_id)
VALUES ('Galaxy Z Fold 4', 40000000, 'Graygreen', 2);

-- Google
INSERT INTO phones (name, price, color, manufacture_id)
VALUES ('Pixel 7 Pro', 22000000, 'Hazel', 3);

-- Xiaomi
INSERT INTO phones (name, price, color, manufacture_id)
VALUES ('Xiaomi 13 Pro', 21000000, 'Ceramic White', 4);

