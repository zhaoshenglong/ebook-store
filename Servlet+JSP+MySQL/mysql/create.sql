use bookstore;

/**
 * buys - relation between user and order_id
 */
DROP TABLE IF EXISTS order_item;
/**
 * orders - relation between order_id and book 
 */
DROP TABLE IF EXISTS orders;

/**
 * likes - relation between user_id and book
 */
DROP TABLE IF EXISTS book_category;
DROP TABLE IF EXISTS category;

DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS book_category;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS category;

CREATE TABLE books(
	id VARCHAR(40),
	book_isbn 	VARCHAR(20),
	book_name 	VARCHAR(100) NOT NULL,
    img		VARCHAR(100) DEFAULT "default",
    author 	VARCHAR(100) DEFAULT "Unknown",
    price	DECIMAL(6,2) NOT NULL DEFAULT 10.00,
    content 	TEXT,
    author_info		TEXT,
    stock INT DEFAULT 10,
    tag VARCHAR(30) DEFAULT "Novel",
    liked INT DEFAULT 0,
    create_date	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	modify_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
	CHECK (price > 0)
);

CREATE TABLE users(
	user_name 	VARCHAR(100) NOT NULL,
    email 	VARCHAR(50)  NOT NULL,
    passwd 	VARCHAR(100) NOT NULL,
    avatar	VARCHAR(100) DEFAULT "default",
    state BOOLEAN, # True -> Activiated False -> Forbidden
    create_date	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    modify_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_name),
    UNIQUE (email)
);

# province, city, district stored in Mongodb
CREATE TABLE address(
	id VARCHAR(40),
	user_name VARCHAR(40),
    province VARCHAR(40) DEFAULT "",
    city VARCHAR(40) DEFAULT "",
    district VARCHAR(40) DEFAULT "",
    detail VARCHAR(100) DEFAULT "",
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    modify_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_name) REFERENCES users(user_name)
	ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE orders(
	order_id VARCHAR(40),
	user_name VARCHAR(100),
	create_date	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    state SMALLINT DEFAULT 0, # 0 -> unpaid, 1 -> paid, 2 -> deleted
    FOREIGN KEY (user_name) REFERENCES users(user_name)
    ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY(order_id)
);


CREATE TABLE order_item(
	id VARCHAR(40),
	order_id VARCHAR(40),
    book_id  VARCHAR(40),
    book_price DECIMAL(6,2) DEFAULT 10.00,
	quantity INT DEFAULT 1,
    FOREIGN KEY(book_id) REFERENCES books(id)
    ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (order_id) REFERENCES orders(order_id)
    ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (id),
	CHECK (quantity > 0)
);

/*
 * likes - Many To Many, relationship sets
 */
CREATE TABLE likes(
	user_name VARCHAR(100),
    book_id VARCHAR(40),
    FOREIGN KEY(user_name) REFERENCES users(user_name)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(book_id) REFERENCES books(id)
    ON UPDATE CASCADE ON DELETE CASCADE
);
