create table pj_member(
m_id varchar2(20 char) primary key,
m_pw varchar2(30 char) not null,
m_name varchar2(20 char) not null,
m_addr varchar2(100 char) not null,
m_photo varchar2(200 char) not null
)

insert into PJ_MEMBER values('mz','1004','엠지','종각','a.jpg');
insert into PJ_MEMBER values('hg','1004','형규','종각','d.jpg');

select * from pj_member

------
create table pj_dataroom(
	d_no number(5) primary key, 			
	d_owner varchar2(10 char) not null,
	d_title varchar2(25 char) not null, 
	d_file varchar2(200 char) not null,
	d_category char(6 char) not null
);

create sequence pj_dataroom_seq;
select * from pj_dataroom;

------

create table pj_gallery(
	g_no number(5) primary key, 			
	g_owner varchar2(10 char) not null,
	g_title varchar2(25 char) not null, 
	g_file varchar2(200 char) not null
);
create sequence pj_gallery_seq;
select * from pj_gallery;

------
create table pj_community(
	c_no number(5) primary key,
	c_from varchar2(10 char) not null,
	c_to varchar2(10 char) not null,
	c_txt varchar2(200 char) not null,
	c_when date not null
);
create sequence pj_community_seq;
select * from pj_community;

