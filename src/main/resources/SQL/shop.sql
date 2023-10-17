-- products 慶琳
-- 商品分類 --
create table product_category(
prodcat_id int primary key auto_increment,
prodcat_name varchar(10) not null
);

-- 商品 --
create table product(
prod_id int primary key auto_increment,
prodcat_id int not null,
prod_name varchar(20) not null,
prod_con varchar(1000),
prod_pri int not null,
prod_qty int,
prod_sta tinyint default 0 not null  not null comment '0:下架 1:上架',
prod_spec varchar(20)

-- constraint fk_prodcat_id
-- foreign key (prodcat_id) references product_category(prodcat_id)
);



-- 商品圖片庫 --
create table product_picture(
prodpic_id int primary key auto_increment,
prod_id int not null,
prod_pic mediumblob,
constraint fk_prod_id
foreign key(prod_id) references product (prod_id)
);

-- 購物車清單 --
create table cart_list(
cart_pri int not null,
cart_qty int not null,
mem_id int,
prod_id int,
primary key (mem_id, prod_id)
-- constraint fk_mem_id
-- foreign key (mem_id) references members (member_id),
-- constraint fk_prod_id
-- foreign key (prod_id) references product (prod_id)
);

select * from product_category;
insert into product_category(prodcat_id,prodcat_name)
values
(1, '戶外選品'),
(2, '在地美食'),
(3, '旅遊必備小物'),
(4, '流浪的意義');



select * from product_category;

select * from product;
insert into product(prod_id,prodcat_id,prod_name,prod_con,prod_pri,prod_qty,prod_sta,prod_spec)
values
(1,2,'日月潭紅茶 | 國際風味獎章 | 50g','台茶18號，茶湯呈現典雅金紅色，茶香中帶有淡淡薄荷肉桂香氣，此香氣更被世界紅茶專家譽為日月潭紅茶特有之「台灣香」',699,0,1,'3入'),
(2,3,'設計師聯名款太陽眼鏡','UandMe與國際設計師聯名太陽眼鏡，擷取時尚與文化融入設計，絕對是旅行中不可或缺的配件 ',2999,2,1,'1入'),
(3,4,'大自然的氣息','工作繁忙沒辦法安排假期旅行嗎? 把大自然的氣息帶回去，讓你在家也能有沉浸式的旅行體驗',11111,10,1,'1入'),
(4,2,'冠軍烘培咖啡豆','TCRC 烘焙冠軍特調精品配方豆｜醇厚度：輕盈｜酸度：低酸 風味描述： 焙茶、炒糖甜感、白花香、平衡乾淨',1080,10,1,'2入'),
(5,1,'原住民手工編織包','找回傳統記憶與技藝，手工編織',1680,10,1,'1入'),
(6,1,'Camping 戶外咖啡組','溫度計+濾杯+濾紙+旅行收納包+咖啡杓+單品咖啡豆，六件組',2980,10,0,'1組'),
(7,4,'質感手提行李箱','手提設計適用於各種情境，短程旅行、日常休閒，增添儀式感',3680,10,1,'1入'),
(8,1,'露營必備裝飾燈串','露營帳篷燈串,8 種閃爍模式',480,10,0,'1入'),
(9,3,'睡眠眼罩','遮光睡眠眼罩，改善睡眠品質降低疲勞感',480,10,1,'1入'),
(10,3,'多功能行動電源','雙孔輸出多功能無線充行動電源，22.5W大功率快速充電',1080,10,1,'1入'),
(11,3,'旅行萬用轉接頭','萬國萬用轉接頭，出國旅遊旅行必備',380,10,1,'1入');



select * from cart_list;
insert into cart_list(cart_pri,cart_qty,mem_id,prod_id)
value
(699, 1, 1, 1),
(2999, 1, 1, 2),
(699, 1, 2, 1),
(2999, 1, 2, 2),
(3680, 1, 2, 7),
(11111, 1, 3, 3),
(1680, 1, 4, 5),
(2980, 1, 4, 6),
(2980, 1, 5, 6),
(3680, 1, 5, 7);

select * from product_picture;
insert into product_picture values (
1, 1, null
);

-- 訂單 --
 create table orders (
    ord_id int primary key auto_increment,
    mem_id int not null,
    points int default 1,
    ord_fee int default 80,
    recipient_phone varchar(15) not null,
    ord_pay_sta tinyint default 0 not null, -- 0:未付款,1:已付款
    recipient_name varchar(10),
    recipient_addr varchar(30),
    ord_sta tinyint default 0 not null, -- 0:出貨中,1:訂單完成
    total int,
    checktotal int,
    ord_time timestamp not null default current_timestamp
--  constraint fk_mem_id
--  foreign key(mem_id) references table_name(mem_id)
);
select * from orders;
INSERT INTO orders (ord_id, mem_id, points, ord_fee, recipient_phone, ord_pay_sta, recipient_name, recipient_addr, ord_sta, total, checktotal, ord_time)
VALUES
  (101, 1, 100, 50, '09111111111', 1, '許彤彤', '台北市大安區復興南路一段123號', 0, 110, 1150, '2023-07-29 10:00:00'),
  (102, 2, 200, 80, '0922222222', 1, '陳安安', '新北市板橋區文化路二段456號', 1, 7378, 7458, '2023-07-29 11:30:00'),
  (103, 3, 150, 40, '0933333333', 1, '吳蓁蓁', '高雄市鳳山區中山西路789號', 0, 1190, 1230, '2023-07-29 12:45:00'),


select * from orders;

-- 訂單明細 --
create table order_details(
	ord_id int,
    prod_id int,
    prod_qty int not null,
	prod_review varchar(50),
    prod_price int not null,
    prod_com_score decimal(2,1),
    primary key(ord_id, prod_id)
--  constraint fk_ord_id
--  foreign key(ord_id) references orders(ord_id),
--  constraint fk_prod_id
--  foreign key(prod_id) references product(prod_id)
    );
select * from order_details;
INSERT INTO order_details (ord_id, prod_id, prod_qty, prod_review, prod_price, prod_com_score)
VALUES
  (101, 1, 1, '這個商品非常不錯，味道很好。', 699, 4.5),
  (101, 2, 1, '太陽眼鏡很時尚，很喜歡它的設計。', 2999, 4.0),
  (102, 1, 1, '溫暖又時尚，冬日必備!', 699, 4.5),
  (102, 2, 1, '這款太陽眼鏡的質量很好，我很滿意。', 2999, 4.0),
  (102, 7, 1, '質感手提行李箱的設計很不錯，質量也很好。', 3680, 3.5),
  (103, 3, 1, '大自然的氣息帶給我放鬆的感覺。', 11111, 4.7),
  (104, 2, 1, '太陽眼鏡很時尚，很喜歡它的設計。', 2999, 4.0),
  (104, 7, 1, '質感手提行李箱的設計很不錯，質量也很好。', 3680, 4.5),
  (105, 2, 1, '太陽眼鏡很時尚，很喜歡它的設計。', 2999, 4.0),
  (105, 3, 1, '大自然的氣息帶給我放鬆的感覺。', 11111, 4.7),
  (106, 2, 1, '太陽眼鏡很時尚，很喜歡它的設計。', 2999, 4.0),
  (107, 3, 1, '大自然的氣息帶給我放鬆的感覺。', 11111, 4.7),
  (108, 2, 1, '太陽眼鏡很時尚，很喜歡它的設計。', 2999, 4.0),
  (109, 1, 1, '這個商品非常不錯，味道很好。', 699, 4.5),
  (110, 1, 1, '這個商品非常不錯，味道很好。', 699, 4.5);
select * from order_details;
