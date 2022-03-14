# 부동산 커뮤니티
## H2 DATABASE
url : jdbc:h2:tcp://localhost/~/test
username : sa

### DATABASE setup
유저관련 정보 셋업을 위한 Rest API 구현

    create table account_type (
		account_type_eng varchar(100) not null,
        account_type_kor varchar(100) not null,
        primary key (account_type_eng)
    );
    /* 계정 데이터 초기화 */
	insert into account_type values('REALTOR', '공인중개사'); 
	insert into account_type values('LESSOR', '임대인');
	insert into account_type values('LESSEE', '임차인');
    

    create table member (
		id bigint auto_increment,
		account_id varchar(255) not null,
		account_type varchar(100),        
        nick_name varchar(255) not null,
        quit integer default 0,
        primary key (id),
		foreign key (account_type) references account_type(account_type_eng)
    );
    /* 멤버 데이터 초기화 */
	insert into member values(1, 'realtorId', 'REALTOR', 'realtorNickname', 0);
	insert into member values(2, 'lessorId', 'LESSOR', 'lessorNickname', 0);
	insert into member values(3, 'lesseeId', 'LESSEE', 'lesseeNickname', 0);


	create table likes (
		id bigint auto_increment,
		likes_count bigint,
        primary key (id)
    );



    create table board (
		id bigint auto_increment,
		user_id bigint not null,		
		title varchar(4000) not null,
        content varchar(4000) not null,
		likes_id bigint,
        created_at timestamp,
        updated_at timestamp,		
        deleted_at timestamp,        
		is_deleted integer default 0,
        primary key (id),
		foreign key (user_id) references member(id),
		foreign key (likes_id) references likes(id)
    );
 
 
    create table reply (
		id bigint auto_increment,
		board_id bigint,
		user_id bigint,
        content varchar(4000),
		likes_id bigint,
        created_at timestamp,
		updated_at timestamp,
        deleted_at timestamp,
		is_deleted integer,
        primary key (id),
		foreign key (user_id) references member(id),
		foreign key (likes_id) references likes(id)		
    );


	create table likes_user (
		id bigint auto_increment,
        likes_id bigint,
		user_id bigint,
        primary key (id),
		foreign key (user_id) references member(id),
		foreign key (likes_id) references likes(id)			
    );

## 검증


## 구현방식
각 케이스 별 DTO를 더 다양하게 구현한다면 요청 타입에 맞춘 데이터를 깔끔하게 전달 할 수 있었지만
시간이 촉박했던 관계로 모든 데이터를 가져오게 하였다. 
