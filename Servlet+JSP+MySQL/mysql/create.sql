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
DROP TABLE IF EXISTS likes;

/**
 * remarks - relation between book and remark
 */ 
DROP TABLE IF EXISTS remarks;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS remark;

CREATE TABLE books(
	id VARCHAR(40),
	book_isbn 	VARCHAR(20),
	book_name 	VARCHAR(100) NOT NULL,
    img		VARCHAR(100) DEFAULT "default",
    author 	VARCHAR(100) DEFAULT "Unknown",
    price	DECIMAL(6,2) NOT NULL DEFAULT 10.00,
    content 	TEXT,
    authorInfo		TEXT,
    stock INT DEFAULT 10,
    tag VARCHAR(20),
    create_date	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	CHECK (price > 0)
);

CREATE TABLE users(
	user_name 	VARCHAR(100) NOT NULL,
    email 	VARCHAR(50)  NOT NULL,
    passwd 	VARCHAR(100) NOT NULL,
    avatar	VARCHAR(100) DEFAULT "default",
    brief_addr 	VARCHAR(100) DEFAULT "",
    detail_addr 	VARCHAR(100) DEFAULT "",
    state varchar(20) DEFAULT "Activiate",
    create_date	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_name),
    UNIQUE (email)
);

CREATE TABLE orders(
	user_name VARCHAR(100),
    order_id VARCHAR(40),
	create_date	 TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    state VARCHAR(20) DEFAULT "paid",
    FOREIGN KEY (user_name) REFERENCES users(user_name)
    ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY(order_id)
);
CREATE TABLE order_item(
	id VARCHAR(40),
	order_id VARCHAR(40),
    book_id  VARCHAR(40),
	quantity INT DEFAULT 1,
    FOREIGN KEY(book_id) REFERENCES books(id)
    ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (order_id) REFERENCES orders(order_id)
    ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (id),
	CHECK (book_num > 0)
);

CREATE TABLE likes(
	user_name VARCHAR(100),
    book_id VARCHAR(40),
    FOREIGN KEY(user_name) REFERENCES users(user_name)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(book_id) REFERENCES books(id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE remark(
	id VARCHAR(10),
    remark_date DATE,
    agrees INT,
    remark_content VARCHAR(200)
);

CREATE TABLE remarks(
	book_id VARCHAR(40),
    user_name VARCHAR(100),
    remark_id VARCHAR(10),
    FOREIGN KEY(user_name) REFERENCES users(user_name)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(book_id) REFERENCES books(id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

