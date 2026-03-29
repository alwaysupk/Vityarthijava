CREATE DATABASE medicine_system;
USE medicine_system;

CREATE TABLE medicines (
                           medicine_id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(100) NOT NULL,
                           type VARCHAR(50) NOT NULL,
                           dosage VARCHAR(50),
                           quantity INT NOT NULL,
                           low_stock_threshold INT NOT NULL,
                           expiry_date DATE NOT NULL,
                           notes VARCHAR(255)
);

CREATE TABLE reminders (
                           reminder_id INT PRIMARY KEY AUTO_INCREMENT,
                           medicine_id INT,
                           reminder_time DATETIME NOT NULL,
                           status VARCHAR(20) DEFAULT 'PENDING',
                           FOREIGN KEY (medicine_id) REFERENCES medicines(medicine_id)
);