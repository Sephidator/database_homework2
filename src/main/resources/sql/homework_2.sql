# 修改mysql全局时区为北京时间，即我们所在的东8区
# 修改当前会话时区
# 立即生效

SET GLOBAL time_zone = '+8:00';

SET time_zone = '+8:00';

FLUSH PRIVILEGES;



# 设置编码字符集

SET CHARACTER SET utf8;


# DROP TABLE user;
#
# DROP TABLE basic_price;
#
# DROP TABLE plan;
#
# DROP TABLE orders;
#
# DROP TABLE call_record;
#
# DROP TABLE message_record;
#
# DROP TABLE local_data_record;
#
# DROP TABLE domestic_data_record;
#
# DROP TABLE plan_record;
#
#
# CREATE TABLE user (
#   phone VARCHAR(20) NOT NULL,
#   uname VARCHAR(50),
#   balance FLOAT(2),
#   province VARCHAR(20),
#   freeCallMinutes BIGINT,
#   freeMessageNum BIGINT,
#   freeLocalData FLOAT(2),
#   freeDomesticData FLOAT(2),
#   PRIMARY KEY (phone)
# )ENGINE = InnoDB;
#
#
# CREATE TABLE basic_price (
#   pid BIGINT AUTO_INCREMENT,
#   callPrice FLOAT(2),
#   messagePrice FLOAT(2),
#   localDataPrice FLOAT(2),
#   domesticDataPrice FLOAT(2),
#   PRIMARY KEY (pid)
# )ENGINE = InnoDB AUTO_INCREMENT=1;
#
#
# CREATE TABLE plan (
#   pid BIGINT AUTO_INCREMENT,
#   pname VARCHAR(50),
#   price DOUBLE,
#   freeCall BIGINT,
#   freeMessage BIGINT,
#   freeLocalData FLOAT(2),
#   freeDomesticData FLOAT(2),
#   PRIMARY KEY (pid)
# )ENGINE = InnoDB AUTO_INCREMENT=1;
#
#
# CREATE TABLE orders (
#   orderno BIGINT AUTO_INCREMENT,
#   user_phone VARCHAR(20),
#   pid BIGINT,
#   beginTime DATETIME,
#   endTime DATETIME,
#   renewal BOOL,
#   applicable BOOL,
#   PRIMARY KEY (orderno)
# )ENGINE = InnoDB AUTO_INCREMENT=1;
#
#
# CREATE TABLE call_record (
#   rid BIGINT AUTO_INCREMENT,
#   beginTime DATETIME,
#   minutes BIGINT,
#   free_minutes BIGINT,
#   paid_minutes BIGINT,
#   expense FLOAT(2),
#   caller_phone VARCHAR(20),
#   callee_phone VARCHAR(20),
#   PRIMARY KEY (rid)
# )ENGINE = InnoDB AUTO_INCREMENT=1;
#
#
# CREATE TABLE message_record (
#   rid BIGINT AUTO_INCREMENT,
#   sendTime DATETIME,
#   number BIGINT,
#   free_num BIGINT,
#   paid_num BIGINT,
#   expense FLOAT(2),
#   sender_phone VARCHAR(20),
#   receiver_phone VARCHAR(20),
#   PRIMARY KEY (rid)
# )ENGINE = InnoDB AUTO_INCREMENT=1;
#
#
# CREATE TABLE local_data_record (
#   rid BIGINT AUTO_INCREMENT,
#   sendTime DATETIME,
#   amount FLOAT(2),
#   free_amount FLOAT(2),
#   paid_amount FLOAT(2),
#   expense FLOAT(2),
#   user_phone VARCHAR(20),
#   PRIMARY KEY (rid)
# )ENGINE = InnoDB AUTO_INCREMENT=1;
#
#
# CREATE TABLE domestic_data_record (
#   rid BIGINT AUTO_INCREMENT,
#   sendTime DATETIME,
#   amount FLOAT(2),
#   free_amount FLOAT(2),
#   paid_amount FLOAT(2),
#   expense FLOAT(2),
#   user_phone VARCHAR(20),
#   PRIMARY KEY (rid)
# )ENGINE = InnoDB AUTO_INCREMENT=1;
#
#
# CREATE TABLE plan_record (
#   rid BIGINT AUTO_INCREMENT,
#   time DATETIME,
#   expense FLOAT(2),
#   refund FLOAT(2),
#   user_phone VARCHAR(20),
#   pid BIGINT,
#   PRIMARY KEY (rid)
# )ENGINE = InnoDB AUTO_INCREMENT=1;


INSERT INTO basic_price VALUES(0, 0.5, 0.1, 2, 5);

INSERT INTO plan VALUES
(1, '话费包月套餐', 20, 100, 0, 0, 0),
(2, '短信包月套餐', 10, 0, 200, 0, 0),
(3, '本地流量包月套餐', 20, 0, 0, 2000, 0),
(4, '国内流量包月套餐', 30, 0, 0, 0, 2000);

INSERT INTO user VALUES
('13218019068', '陈骁', 400, '江苏', 0, 0, 0, 0),
('13780946508', '刘嘉', 300, '江苏', 0, 0, 0, 0);