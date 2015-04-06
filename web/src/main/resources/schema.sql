create table IF NOT EXISTS user (
  id int primary key auto_increment,
  email varchar(255),
  userName varchar(255)
);


create table IF NOT EXISTS book (
  id bigint(20) unsigned primary key auto_increment,
  created_At datetime ,
  updated_At datetime ,
  ownerId bigint(20) unsigned,  
  isbn  varchar(64),
  title varchar(128),
  imgURL varchar(128),
  author varchar(64),
  memo varchar(1024),
  donateState tinyint,
  borrowStatus tinyint
);


