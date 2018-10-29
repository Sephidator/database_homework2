# 修改mysql全局时区为北京时间，即我们所在的东8区
# 修改当前会话时区
# 立即生效

SET GLOBAL time_zone = '+8:00';

SET time_zone = '+8:00';

FLUSH PRIVILEGES;

# 设置编码字符集

SET CHARACTER SET utf8;




INSERT INTO basic_price VALUES(1, 0.5, 0.1, 2, 5);

INSERT INTO plan VALUES
(1, '话费包月套餐', 20, 100, 50, 0, 0),
(2, '短信包月套餐', 10, 0, 200, 0, 0),
(3, '本地流量包月套餐', 20, 0, 0, 2000, 0),
(4, '国内流量包月套餐', 30, 0, 0, 0, 2000);

INSERT INTO user VALUES
('13218019068', '陈骁', 400, '江苏', 0, 0, 0, 0),
('13780946508', '刘嘉', 300, '江苏', 0, 0, 0, 0);
