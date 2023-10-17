-- attractions景點相關表格建立 劉力辰
create table attraction_type(
attr_type_id int primary key,
attr_type_name varchar(10)
);

insert into attraction_type(attr_type_id, attr_type_name)
values
(1,"第一類"),
(2,"第二類"),
(3,"第三類");
-- select * from attraction_type;
-- drop table attraction_type;

create table attractions (
attr_id int primary key auto_increment,
attr_veri_sta tinyint,-- deprecated
attr_sta int default 1,-- 預設上架
attr_name varchar(20) not null,
attr_addr varchar(100) not null,
attr_lon float,
attr_lat float,
attr_illa varchar(500),
attr_type varchar(100),
attr_buss_time varchar(200) default '09:00-18:00|09:00-21:00',
attr_cost_range tinyint,
attr_rep varchar(500) -- deprecated
);

INSERT INTO attractions (attr_id, attr_veri_sta, attr_sta, attr_name, attr_addr, attr_lon, attr_lat, attr_illa, attr_type_id, attr_buss_time, attr_cost_range, attr_rep)
VALUES
  (1, 1, 1, '中壢緯育', '台灣桃園市中壢區復興路緯育TibaMe附設中壢職訓中心', 121.225, 24.9576, '暫無描述', 1, '0830~1800|0830~2200', 2, 'no report record'),
  (2, 1, 1, '台北101', '台灣台北市信義區市府路台北101購物中心', 121.564, 25.0341, '暫無描述', 2, '1100~2130|1100~2130', 2, 'no report record'),
  (3, 1, 1, '基隆廟口夜市', '台灣基隆市玉田里仁三路基隆廟口夜市', 121.744, 25.1282, '暫無描述', 2, '0800~0000|1200~0000|1200~0000|1200~0000|1200~0000|1200~0000|1200~0000', 2, 'no report record'),
  (4, 1, 1, '清華大學', '台灣新竹市光復路二段清華大學', 120.997, 24.7961, '暫無描述', 2, '暫無資料', 2, 'no report record'),
  (5, 1, 1, '宮原眼科', '台灣台中市中區中山路宮原眼科', 120.684, 24.1378, '暫無描述', 2, '1000~2100|1000~2100|1000~2100|1000~2100|1000~2100|1000~2100|1000~2100', 2, 'no report record'),
  (6, 1, 1, '鳳山高中', '台灣高雄市鳳山區光復路二段鳳山高中', 120.346, 22.6298, '暫無描述', 2, '暫無資料', 2, 'no report record'),
  (7, 1, 1, '阿里山國家森林遊樂區', '台灣嘉義縣阿里山國家森林遊樂區', 120.803, 23.511, '暫無描述', 2, '暫無資料', 2, 'no report record'),
  (8, 1, 1, '合歡山南峰', '台灣南投縣合歡山南峰', 121.269, 24.1243, '暫無描述', 2, '暫無資料', 2, 'no report record'),
  (9, 1, 1, '中央大學', '台灣桃園市中壢區中大路中央大學', 121.195, 24.9682, '暫無描述', 2, '0800~1700|0800~1700|0800~1700|0800~1700|0800~1700', 2, 'no report record'),
  (10, 1, 1, '中壢火車站', '台灣桃園市中壢區中正路中壢火車站', 121.226, 24.9537, '暫無描述', 2, '暫無資料', 2, 'no report record');

select * from attractions;
-- drop table attractions;


-- create table members(
-- mem_id int primary key,
-- mem_name varchar(20)
-- );

-- insert into members(mem_id, mem_name)
-- values
-- (1,"Andy");

-- drop table member;
-- select * from member;

create table attraction_collections (
attr_id int,
mem_id int

-- primary key (attr_id, mem_id),
-- foreign key(mem_id) references members(mem_id),
-- foreign key(attr_id) references attractions(attr_id)

);

insert into attraction_collections(attr_id, mem_id)
values
(1,1),(2,1),(3,1),
(2,2),(3,2),(4,2),
(3,3),(4,3),(5,3),
(4,4),(5,4),(6,4),
(5,5),(6,5),(7,5),
(6,6),(7,6),(8,6),
(7,7),(8,7),(9,7),
(8,8),(9,8),(10,8),
(9,9),(10,9),(1,9),
(10,10),(1,10),(2,10);

-- select * from attraction_collections;
-- drop table attraction_collections;

-- drop table attraction_pictures;
create table attraction_pictures (
attr_pic_id int unsigned primary key auto_increment,
attr_id int,
-- constraint fk_attr_id
-- foreign key(attr_id) references attractions(attr_id),
attr_pic_data longtext
);

--insert into attraction_pictures(attr_pic_id, attr_id, attr_pic_data)
--values
--(1,1,LOAD_FILE('/tomcat.jpg'));
-- select attr_pic_id from attraction_pictures where fk_attr_id = 1;
-- delete from attraction_pictures where attr_pic_id = 1;
-- alter table attraction_pictures drop foreign key fk_attr_id;

create table attraction_comments(
attr_id int,
mem_id int,
attr_com_score decimal(2,1),
attr_com varchar(500)
-- foreign key(attr_id) references attractions(attr_id),
-- foreign key(mem_id) references members(mem_id)
);

insert into attraction_comments(attr_id, mem_id, attr_com_score, attr_com)
values
(1,1,0.1,"景點沒廁所");

-- select * from attraction_comments;
