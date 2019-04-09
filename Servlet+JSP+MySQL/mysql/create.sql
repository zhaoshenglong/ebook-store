use bookstore;


/**
 * buys - relation between user and order_id
 */
DROP TABLE IF EXISTS buys;
/**
 * carts - relation between user and book
 */
DROP TABLE IF EXISTS carts;
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

DROP TABLE IF EXISTS description;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS remark;

CREATE TABLE books(
	book_isbn 	VARCHAR(20),
	book_name 	VARCHAR(100) NOT NULL,
    author 	VARCHAR(100) DEFAULT "Unknown",
    price	DECIMAL(6,2) NOT NULL DEFAULT 10.00,
	PRIMARY KEY (book_isbn),
	CHECK (price > 0)
);
CREATE TABLE tags(
	book_isbn 	VARCHAR(20),
    tag			VARCHAR(20),
    PRIMARY KEY(book_isbn, tag),
    FOREIGN KEY (book_isbn) REFERENCES books(book_isbn)
);
CREATE TABLE description(
	book_isbn 	VARCHAR(20),
    content 	TEXT,
    author		TEXT,
	FOREIGN KEY(book_isbn) REFERENCES books(book_isbn)
);
CREATE TABLE users(
	u_name 	VARCHAR(100) NOT NULL,
    email 	VARCHAR(50)  NOT NULL,
    passwd 	VARCHAR(100) NOT NULL,
    avatar	VARCHAR(100) DEFAULT "_blank",
    brief_addr 	VARCHAR(100) DEFAULT "",
    detail_addr 	VARCHAR(100) DEFAULT "",
    PRIMARY KEY (u_name),
    UNIQUE (email)
);

CREATE TABLE orders(
	order_id VARCHAR(20),
    book_isbn  VARCHAR(20),
    book_num INT DEFAULT 1,
	o_date	 DATE,
    PRIMARY KEY (order_id),
    FOREIGN KEY(book_isbn) REFERENCES books(book_isbn)
    ON UPDATE CASCADE ON DELETE CASCADE,
	CHECK (book_num > 0)
);

CREATE TABLE buys(
	u_name VARCHAR(100),
    order_id VARCHAR(20),
    FOREIGN KEY (u_name) REFERENCES users(u_name)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE carts(
	u_name VARCHAR(100),
    book_isbn VARCHAR(20),
    book_num INT DEFAULT 1,
    FOREIGN KEY (u_name) REFERENCES users(u_name)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(book_isbn) REFERENCES books(book_isbn)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE likes(
	u_name VARCHAR(100),
    book_isbn VARCHAR(20),
    FOREIGN KEY(u_name) REFERENCES users(u_name)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(book_isbn) REFERENCES books(book_isbn)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE remark(
	re_id VARCHAR(10),
    re_date DATE,
    agrees INT,
    re_info VARCHAR(200)
);

CREATE TABLE remarks(
	book_isbn VARCHAR(20),
    u_name VARCHAR(100),
    re_id VARCHAR(10),
    FOREIGN KEY(u_name) REFERENCES users(u_name)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(book_isbn) REFERENCES books(book_isbn)
    ON UPDATE CASCADE ON DELETE CASCADE
);

