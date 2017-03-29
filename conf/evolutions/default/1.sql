# Users schema

# --- !Ups
CREATE TABLE Users (
    id serial PRIMARY KEY,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    forename varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    address varchar(255) NOT NULL,
    zipcode int NOT NULL,
    city varchar(255) NOT NULL,
    role varchar(255) NOT NULL,
    inactive bit
);
INSERT INTO Users(id, email, password, forename, name, address, zipcode, city, role, inactive) VALUES(1, 'padrone@suez.de', 'Suez82346', 'Herbert', 'Padrone', 'Kientalstr. 10', 82346, 'Andechs', 'Mitarbeiter', B'0');
INSERT INTO Users(id, email, password, forename, name, address, zipcode, city, role, inactive) VALUES(2, 'emil@gmx.de', 'Susanne82343', 'Susanne', 'Emil', 'Ulrichstr. 1 ', 82343, 'Pöcking', 'Kunde', B'0');


# --- !Downs
DROP TABLE Users,

# Menu schema

# --- !Ups
CREATE TABLE Menu (
    id serial PRIMARY KEY,
    name varchar(255) NOT NULL,
    price double NOT NULL,
    category varchar(255) NOT NULL,
    ordered bit,
    active bit
);
INSERT INTO Menu(id, name, price, category, ordered, active) VALUES(101, 'Pizza Margarita', 0.23, 'Pizza', B'0', B'1');
INSERT INTO Menu(id, name, price, category, ordered, active) VALUES(102, 'Pizza Regina', 0.27, 'Pizza', B'0', B'1');
INSERT INTO Menu(id, name, price, category, ordered, active) VALUES(201, 'Sprite', 0.3, 'Getränk', B'0', B'1');
INSERT INTO Menu(id, name, price, category, ordered, active) VALUES(202, 'Cola', 0.3, 'Getränk', B'0', B'1');
INSERT INTO Menu(id, name, price, category, ordered, active) VALUES(301, 'Schokokuchen', 2.0, 'Dessert', B'0', B'1');
INSERT INTO Menu(id, name, price, category, ordered, active) VALUES(302, 'Schokoeis', 2.0, 'Dessert', B'0', B'1');

# --- !Downs

# Orderbill schema

# --- !Ups
CREATE TABLE Orderbill (
    id serial PRIMARY KEY,
    customerId serial NOT NULL,
    pizzaName varchar(255),
    pizzaNumber int,
    pizzaSize varchar(255),
    beverageName varchar(255),
    beverageNumber int,
    beverageSize varchar(255),
    dessertName varchar(255),
    dessertNumber int,
);

# --- !Downs
DROP TABLE Orderbill;

# Orderhistory schema

# --- !Ups
Create Table Orderhistory (
    orderID serial NOT NULL PRIMARY KEY,
    customerID serial NOT NULL,
    customerData varchar(255),
    orderedProducts varchar(255),
    sumOfOrder double,
    orderDate varchar(255),
);

# --- !Downs
DROP TABLE Orderhistory;
