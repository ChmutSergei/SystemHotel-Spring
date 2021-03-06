CREATE DATABASE HotelSystem2  CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE HotelSystem2;
CREATE TABLE Contacts(id INT PRIMARY KEY AUTO_INCREMENT, email CHAR(20), telephone CHAR(20), country CHAR(20),
                      city CHAR(20), address CHAR(120), zip CHAR (10));
CREATE TABLE Users(id INT PRIMARY KEY AUTO_INCREMENT, login CHAR(20), password CHAR(60), name CHAR(20),lastname CHAR(20),
                   role CHAR (15),contact_id INT, FOREIGN KEY (contact_id) REFERENCES Contacts(id) ON DELETE  CASCADE  ON UPDATE CASCADE);
CREATE TABLE Rooms(id INT PRIMARY KEY AUTO_INCREMENT, roomNumber INT,type CHAR(15),bedType INT,
                   price INT, description TEXT);
CREATE TABLE Reservation (id INT PRIMARY KEY AUTO_INCREMENT, user_id INT, room_id INT,checkIn DATE,checkOut DATE, date DATETIME, payment INT,
  FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE  CASCADE  ON UPDATE CASCADE,
  FOREIGN KEY (room_id) REFERENCES Rooms(id) ON DELETE  CASCADE  ON UPDATE CASCADE);
CREATE TABLE persistent_logins (username VARCHAR(64) NOT NULL ,series VARCHAR(64) NOT NULL PRIMARY KEY,
token VARCHAR(64) NOT NULL, last_used TIMESTAMP NOT NULL);

INSERT INTO Users (login, password , name , lastname, role) VALUES ("admin", "$2a$10$BEzOBPTm7mvKC2WbQU9VW.T7oEAmrbWmN8Ax7WXOUA.QYSX9UdMii","Valeriy", "Manager", "ROLE_ADMIN");
INSERT INTO Contacts(email, telephone, country, city, address, zip) VALUES ("cl@tu.by","37529424454","Belarus","Minsk","some adr","220000");
INSERT INTO Contacts(email, telephone, country, city, address, zip) VALUES ("d@tut.by","37533454","Russia","Smolensk","some adr","220000");
INSERT INTO Contacts(email, telephone, country, city, address, zip) VALUES ("rt@tut.by","05424454","Belarus","Vitebsk","some adr","220000");
INSERT INTO Users (login, password, name, lastname, role, contact_id) VALUES ("user1", "$2a$10$Zar85MYgJBQidp/AOxOi9uZZLrvKH1VFMtH1jetIGkiqTmhSg7xgi","Sergei", "Leshko", "ROLE_USER",1);
INSERT INTO Users (login, password, name, lastname, role, contact_id) VALUES ("user2", "$2a$10$Zar85MYgJBQidp/AOxOi9uZZLrvKH1VFMtH1jetIGkiqTmhSg7xgi", "Vasya", "Leshko", "ROLE_USER",1);
INSERT INTO Users (login, password, name, lastname, role, contact_id) VALUES ("user3", "$2a$10$Zar85MYgJBQidp/AOxOi9uZZLrvKH1VFMtH1jetIGkiqTmhSg7xgi","Sasha", "Leshko", "ROLE_USER",1);
INSERT INTO Users (login, password, name, lastname, role, contact_id) VALUES ("user4", "$2a$10$Zar85MYgJBQidp/AOxOi9uZZLrvKH1VFMtH1jetIGkiqTmhSg7xgi","Vanya", "Puko", "ROLE_USER",2);
INSERT INTO Users (login, password, name, lastname, role, contact_id) VALUES ("user5", "$2a$10$Zar85MYgJBQidp/AOxOi9uZZLrvKH1VFMtH1jetIGkiqTmhSg7xgi","Sveta", "Puko", "ROLE_USER",2);
INSERT INTO Users (login, password, name, lastname, role, contact_id) VALUES ("user6", "$2a$10$Zar85MYgJBQidp/AOxOi9uZZLrvKH1VFMtH1jetIGkiqTmhSg7xgi","Jenya", "Derevyanko", "ROLE_USER",3);
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (1,'Супер Люкс',3,1289,
        'Приятный и уютный номер распологается на втором этаже с видом во двор.');
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (2,'Люкс',2,5890,
        'Уютный номер распологается на втором этаже с видом во двор.');
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (3,'Супер Люкс',2,1159,
        'Приятный и уютный номер распологается на втором этаже с видом во двор.');
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (4,'Люкс',3,10990,
        'Уютный номер распологается на втором этаже с видом во двор.');
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (5,'Люкс',1,8990,
        'Приятный и уютный номер распологается на первом этаже с видом на паркинг');
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (6,'Люкс',1,7990,
        'Приятный и уютный номер распологается на первом этаже с видом на паркинг');
INSERT INTO Rooms(roomNumber, type, bedType, price, description)
VALUES (7,'Люкс',1,8290,
        'Приятный и уютный номер распологается на первом этаже с видом на паркинг');
INSERT INTO Rooms(roomNumber, type, bedType, price,  description)
VALUES (8,'СуперЛюкс',1,8990,
        'Приятный и уютный номер распологается на первом этаже с видом на паркинг');
INSERT INTO Rooms(roomNumber, type, bedType, price,  description)
VALUES (9,'СуперЛюкс',4,18950,
        'Приятный и уютный номер распологается на первом этаже с видом на город');
INSERT INTO Reservation(room_id, user_id,checkIn,checkOut, date, payment) VALUES (2,1,'2018-09-03','2018-09-05','2018-07-10 10:00:00',1);
INSERT INTO Reservation(room_id, user_id,checkIn,checkOut, date, payment) VALUES (3,1,'2018-09-05','2018-09-06','2018-07-10 10:00:00',1);
INSERT INTO Reservation(room_id, user_id,checkIn,checkOut, date, payment) VALUES (4,2,'2018-09-05','2018-09-07','2018-07-10 10:00:00',1);
INSERT INTO Reservation(room_id, user_id,checkIn,checkOut, date, payment) VALUES (5,2,'2018-09-02','2018-09-05','2018-07-10 10:00:00',1);
INSERT INTO Reservation(room_id, user_id,checkIn,checkOut, date, payment) VALUES (6,3,'2018-09-05','2018-09-07','2018-07-10 10:00:00',1);
INSERT INTO Reservation(room_id, user_id,checkIn,checkOut, date, payment) VALUES (7,3,'2018-09-05','2018-09-07','2018-07-10 10:00:00',1);


