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

------
-- 컨셉 차이
alter table pj_sns
add constraint fk_s_owner
foreign key(s_owner)
references pj_member(m_id)
on delete cascade;

create table pj_sns(
s_no number(5) primary key,
s_owner varchar2(20 char) not null,
s_txt varchar2(300 char) not null,
s_date date not null
)

create sequence pj_sns_seq;

create table pj_sns_reply(
r_no number(5) primary key,
r_s_no number(5) not null,
r_owner varchar2(10 char) not null,
r_txt varchar2(100 char) not null,
r_when date not null,
constraint s_r
foreign key(r_s_no)
references pj_sns(s_no)
on delete cascade
)
create sequence pj_sns_reply_seq;

insert into pj_sns values(pj_sns_seq.nextval, 'mz', '내용33', sysdate)

insert into pj_sns_reply values(pj_sns_reply_seq.nextval, 1, 'hg', '댓글 내용', sysdate)

select *
	from (-- rownum rn은 별칭 rownum으로 하면 1부터 시작되니까
	select rownum as rn, s_no, s_owner, s_txt, s_date, m_photo
		from(
			select * from pj_sns, pj_member
			where s_owner = m_id and s_txt like '%%'
			order by s_date desc
			)
	)
	where rn >= 1 and rn <= 3

select * from pj_sns
select * from pj_sns_reply