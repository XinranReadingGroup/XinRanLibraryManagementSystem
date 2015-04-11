create table IF NOT EXISTS user (
  id bigint(20) unsigned primary key auto_increment,
  created_At datetime ,
  updated_At datetime ,
   user_Name  varchar(64),
    nick_Name  varchar(64),
     mobile  varchar(64),
      email  varchar(64),
       password  varchar(64),
        salt  varchar(64),
  reset_Password_Token varchar(255),

    reset_Password_Sent_At datetime ,
  remember_Created_At datetime ,
    signIn_Count bigint(20) unsigned ,

  current_Sign_In_At datetime ,
  last_Sign_In_At datetime ,
    area varchar(512),
  signature varchar(512),
  score int
  );



create table IF NOT EXISTS book (
  id bigint(20) unsigned primary key auto_increment,
  created_At datetime ,
  updated_At datetime ,
  isbn  varchar(64),
  title varchar(128),
  img_url varchar(128),
  author varchar(64),
  memo varchar(4096),
  price varchar(32),
  publisher varchar(64),
  book_Status tinyint default 0
);



create table IF NOT EXISTS Book_Location (
  id bigint(20) unsigned primary key auto_increment,
  created_At datetime ,
  updated_At datetime ,
  
    lat FLOAT( 10, 6 )     ,
     lng FLOAT( 10, 6 ) ,
  type varchar(64),
  province varchar(128),
  city varchar(128),
  country varchar(128),
  detail varchar(256)

   
);

create table IF NOT EXISTS Borrow_Return_Record (
  id bigint(20) unsigned primary key auto_increment,
  created_At datetime ,
  updated_At datetime ,
  
    owner_User_Id bigint(20),
  borrow_User_Id bigint(20),

  book_Id bigint(20),
  book_Status tinyint,

           borrow_Date datetime ,
           return_Date datetime ,
     borrow_Status tinyint

);
 
create table IF NOT EXISTS On_Off_Stock_Record (
  id bigint(20) unsigned primary key auto_increment,
  created_At datetime ,
  updated_At datetime ,
  
    owner_User_Id bigint(20),
 
  book_Id bigint(20),
  book_Status tinyint,


           on_Stock_Date datetime ,
           off_Stock_Date datetime ,
     borrow_Status tinyint

);