      -- drop table activity,book,book_location,borrow_return_record,on_off_stock_record,score,user;
        CREATE TABLE IF NOT EXISTS user
             (
                          id                     BIGINT(20) UNSIGNED PRIMARY KEY auto_increment,
                          created_at             DATETIME,
                          updated_at             DATETIME,
                          user_name              VARCHAR(64),
                          nick_name              VARCHAR(64),
                          mobile                 VARCHAR(64),
                          email                  VARCHAR(64),
                          password               VARCHAR(128),
                          salt                   VARCHAR(64),
                          reset_password_token   VARCHAR(255),
                          reset_password_sent_at DATETIME,
                          remember_created_at    DATETIME,
                          signin_count           BIGINT(20) UNSIGNED,
                          current_sign_in_at     DATETIME,
                          last_sign_in_at        DATETIME,
                          area                   VARCHAR(512),
                          signature              VARCHAR(512),
                          img_id                 VARCHAR(128) ,
                          score INT
             );
             ALTER TABLE user ADD UNIQUE KEY `user_name` (`user_name`);
             ALTER TABLE user ADD UNIQUE KEY `email` (`email`);
             CREATE TABLE IF NOT EXISTS book
             (
                          id         BIGINT(20) UNSIGNED PRIMARY KEY auto_increment,
                          created_at DATETIME,
                          updated_at DATETIME,
                          isbn       VARCHAR(64),
                          title      VARCHAR(128),
                          img_url    VARCHAR(128),
                          author     VARCHAR(64),
                          summary    VARCHAR(4096),
                          price      VARCHAR(32),
                          publisher  VARCHAR(64)
             );
             CREATE TABLE IF NOT EXISTS book_location
             (
                          id         BIGINT(20) UNSIGNED PRIMARY KEY auto_increment,
                          created_at DATETIME,
                          updated_at DATETIME,
                          lat FLOAT(10, 6),
                          lng FLOAT(10, 6),
                          type     VARCHAR(64),
                          user_id      BIGINT(20),
                          province VARCHAR(128),
                          city     VARCHAR(128),
                          county   VARCHAR(128),
                          detail   VARCHAR(256)
             );
             CREATE TABLE IF NOT EXISTS on_off_stock_record
             (
                          id             BIGINT(20) UNSIGNED PRIMARY KEY auto_increment,
                          created_at     DATETIME,
                          updated_at     DATETIME,
                          book_id        BIGINT(20),
                          book_type      TINYINT,
                          qr_code_id     BIGINT(20),
                          owner_user_id  BIGINT(20),
                          owner_phone    VARCHAR(16),
                          location       BIGINT(20),
                          on_stock_date  DATETIME,
                          off_stock_date DATETIME,
                          borrow_id      BIGINT(20),
                          borrow_user_id BIGINT(20),
                          borrow_status  TINYINT
             );
             ALTER TABLE on_off_stock_record ADD UNIQUE KEY `qr_code_id` (`qr_code_id`);
             ALTER TABLE on_off_stock_record ADD INDEX `book_type` (`book_type`);
             ALTER TABLE on_off_stock_record ADD INDEX `owner_user_id` (`owner_user_id`);

             CREATE TABLE IF NOT EXISTS borrow_return_record
             (
                          id              BIGINT(20) UNSIGNED PRIMARY KEY auto_increment,
                          created_at      DATETIME,
                          updated_at      DATETIME,
                          owner_user_id   BIGINT(20),
                          borrow_user_id  BIGINT(20),
                          book_id         BIGINT(20),
                          on_off_stock_id BIGINT(20),
                          book_type       TINYINT,
                          borrow_date     DATETIME,
                          return_date     DATETIME,
                          borrow_status   TINYINT
             );
              ALTER TABLE borrow_return_record ADD INDEX `borrow_user_id` (`borrow_user_id`);
              ALTER TABLE borrow_return_record ADD INDEX `borrow_status` (`borrow_status`);
              ALTER TABLE borrow_return_record ADD INDEX `on_off_stock_id` (`on_off_stock_id`);

             CREATE TABLE IF NOT EXISTS score
             (
                          id           BIGINT(20) UNSIGNED PRIMARY KEY auto_increment,
                          created_at   DATETIME,
                          updated_at   DATETIME,
                          user_id      BIGINT(20),
                          fact_id      BIGINT(20),
                          score_reason INT,
                          score_value  INT
             );
             ALTER TABLE score ADD INDEX `user_id` (`user_id`);

             CREATE TABLE IF NOT EXISTS activity
             (
                          id           BIGINT(20) UNSIGNED PRIMARY KEY auto_increment,
                          created_at   DATETIME,
                          updated_at   DATETIME,
                          title        VARCHAR(128),
                          memo         VARCHAR(4096),
                          start_date   DATETIME,
                          end_date     DATETIME,
                          type         VARCHAR(16),
                          action       VARCHAR(16),
                          score        BIGINT(20),
                          status       INT,
                          img_id       VARCHAR(128)
             );
             ALTER TABLE book ADD UNIQUE KEY `isbn` (`isbn`);
             CREATE TABLE IF NOT EXISTS qrcode
             (
                          id           BIGINT(20) UNSIGNED PRIMARY KEY auto_increment,
                          created_at   DATETIME,
                          updated_at   DATETIME,
                          type         VARCHAR(64),
                          content      VARCHAR(255),
                          status       INT
             );
             ALTER TABLE qrcode ADD UNIQUE KEY `qrcode_content` (`content`);
