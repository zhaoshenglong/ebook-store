use bookstore;
desc books;
insert into books values
("1","1","1","default","zsl",20.00,"什么也没有","史上最丑",20, "Literature"),
("2","2","2","default","hxc",52.00,"全部都是你","史上最美",20, "Story") 
;

insert into users values
("hxc","804799302@qq.com", "123456", "default", "dd","ddd","Activated"),
("zsl","G245078728@sjtu.edu.cn", "123456", "default", "dd","ddd","Activated")
;

insert into orders values
("hxc", "123458161", current_timestamp(), "paid"),
("zsl", "1234564452", current_timestamp(), "unpaid"),
("hxc", "123156151", current_timestamp(), "unpaid")
;

insert into order_item values
("1","123458161","1",2),
("2", "1234564452", "1", 2),
("3", "1234564452", "2", 3),
("4", "123156151", "2", 1);