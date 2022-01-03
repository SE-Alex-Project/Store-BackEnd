-- drop SCHEMA storeDB;
-- CREATE SCHEMA storeDB;
-- use storeDB;

CREATE TABLE Cart(
	cartId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    userEmail VARCHAR(30),
    buyDate DATETIME
);

CREATE TABLE Customer(
	email VARCHAR(50) PRIMARY KEY NOT NULL,
	fName VARCHAR(30) NOT NULL,
    lName VARCHAR(30) NOT NULL,
    passW VARCHAR(30) NOT NULL,
    cartId INT NOT NULL,
	FOREIGN KEY(cartId) REFERENCES Cart(cartId) ON UPDATE CASCADE ON DELETE CASCADE
);

ALTER TABLE Cart ADD FOREIGN KEY(userEmail) REFERENCES Customer(email);

CREATE TABLE Store(
	storeId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    location VARCHAR(100) NOT NULL,
    storeName VARCHAR(45) NOT NULL
);


CREATE TABLE Employee(
	email VARCHAR(50) PRIMARY KEY NOT NULL,
	fName VARCHAR(45) NOT NULL,
    lName VARCHAR(45) NOT NULL,
    storeId INT NOT NULL,
    passW VARCHAR(30) NOT NULL,
    FOREIGN KEY (storeId) REFERENCES Store(storeId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Category(
	categoryName VARCHAR(45) PRIMARY KEY NOT NULL
);

CREATE TABLE Product(
	productId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    categoryName VARCHAR(45) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    descripe VARCHAR(300) NOT NULL,
    productName VARCHAR(45) NOT NULL,
    addedBy VARCHAR(50) NOT NULL,
    added_date DATETIME NOT NULL,
    FOREIGN KEY(addedBy) REFERENCES Employee(email) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(categoryName) REFERENCES Category(categoryName) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE ProductImage(
	productId INT NOT NULL,
    url VARCHAR(100) NOT NULL,
    PRIMARY KEY (productId,url),
    CONSTRAINT FK_productId FOREIGN KEY(productId) REFERENCES Product(productId) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE ProductInCart(
	cartId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY(productId, cartId),
    FOREIGN KEY(productId) REFERENCES Product(productId) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(cartId) REFERENCES Cart(cartId) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE ProductInStore(
	productId INT NOT NULL,
    storeId INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY(productId, storeId),
    FOREIGN KEY(productId) REFERENCES Product(productId) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (storeId) REFERENCES Store(storeId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Manager(
    email VARCHAR(50) PRIMARY KEY NOT NULL,
    passW VARCHAR(30) NOT NULL
);

INSERT INTO Manager VALUES('software@manager.com','-1113294952');


