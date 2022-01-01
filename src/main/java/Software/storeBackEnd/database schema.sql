drop SCHEMA storeDB;
CREATE SCHEMA storeDB;
use storeDB;

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
	FOREIGN KEY(cartId) REFERENCES Cart(cartId)
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
    FOREIGN KEY (storeId) REFERENCES Store(storeId)
);


CREATE TABLE Product(
	productId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    categoryName VARCHAR(45) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    descripe VARCHAR(300) NOT NULL,
    productName VARCHAR(45) NOT NULL,
    addedBy VARCHAR(50) NOT NULL,
    added_date DATETIME DEFAULT NOW(),
    FOREIGN KEY(addedBy) REFERENCES Employee(email)
);


CREATE TABLE CartProducts(
	cartId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (cartId,productId),
    FOREIGN KEY(productId) REFERENCES Product(productId)
);


CREATE TABLE ProductImage(
	productId INT NOT NULL,
    url VARCHAR(100) NOT NULL,
    primary key (productId,url),
    FOREIGN KEY(productId) REFERENCES Product(productId)
);


CREATE TABLE ProductInCart(
	productId INT NOT NULL,
    cartId INT NOT NULL,
    countOfPieces INT NOT NULL,
    PRIMARY KEY(productId, cartId),
    FOREIGN KEY(productId) REFERENCES Product(productId),
    FOREIGN KEY(cartId) REFERENCES Cart(cartId)
);


CREATE TABLE ProductInStore(
	productId INT NOT NULL,
    storeId INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY(productId, storeId),
    FOREIGN KEY(productId) REFERENCES Product(productId),
    FOREIGN KEY(storeId) REFERENCES Store(storeId)
);





