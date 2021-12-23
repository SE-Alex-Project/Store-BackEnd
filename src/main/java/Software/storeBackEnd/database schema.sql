drop SCHEMA storeDB;
CREATE SCHEMA storeDB;
use storeDB;

CREATE TABLE Customer(
	email VARCHAR(50),
	fName VARCHAR(30),
    lName VARCHAR(30),
    passW VARCHAR(30),
	PRIMARY KEY (email)
);


CREATE TABLE Cart(
	cartId INT auto_increment not null primary key, -- the value is got from frontEnd or auto increment here if they cannot do such
    customerMail VARCHAR(30),
    FOREIGN KEY(customerMail) REFERENCES Customer(email)
);


CREATE TABLE Product(
	productId INT auto_increment not null primary key,
    categoryName VARCHAR(40),
    price DECIMAL(8,2),
    descripe varchar(100),
    productName VARCHAR(50)
);


-- CREATE TABLE Images(
-- 	imageId INT auto_increment not null primary key,
--     URL varchar(100)
-- );

CREATE TABLE ProductImage(
	productId INT,
    url varchar(100),
    primary key (productId,url),
    FOREIGN KEY(productId) REFERENCES Product(productId)
);


CREATE TABLE ProductInCart(
	productId INT,
    cartId INT,
    countOfPieces INT,
    PRIMARY KEY(productId, cartId),
    FOREIGN KEY(productId) REFERENCES Product(productId),
    FOREIGN KEY(cartId) REFERENCES Cart(cartId)
);

CREATE TABLE Store(
	storeId INT auto_increment not null primary key,
    location VARCHAR(100),
    storeName VARCHAR(30)
);


CREATE TABLE ProductInStore(
	productId INT,
    storeId INT,
    quantity INT,
    PRIMARY KEY(productId, storeId),
    FOREIGN KEY(productId) REFERENCES Product(productId),
    FOREIGN KEY(storeId) REFERENCES Store(storeId)
);

-- CREATE TABLE Employee(
-- 	fName VARCHAR(30),
--     mName VARCHAR(30),
--     lName VARCHAR(30),
-- 	userName VARCHAR(30),
--     storeId INT, -- if he can work in multi store so we don't add it to primary key , else >> add it 
--     email VARCHAR(50),
--     passW VARCHAR(30),
--     address VARCHAR(70),
-- 	PRIMARY KEY (userName),
--     FOREIGN KEY (storeId) REFERENCES Store(storeId)
-- );





