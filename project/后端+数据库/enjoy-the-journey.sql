/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : enjoy-the-journey

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 07/12/2020 16:03:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_collection
-- ----------------------------
DROP TABLE IF EXISTS `tbl_collection`;
CREATE TABLE `tbl_collection`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note_id` int(11) NULL DEFAULT NULL,
  `tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '�ղ�' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_collection
-- ----------------------------
INSERT INTO `tbl_collection` VALUES (75, 1, '1', '我的收藏夹');
INSERT INTO `tbl_collection` VALUES (78, 12, '1', '我的收藏夹');
INSERT INTO `tbl_collection` VALUES (81, 11, '1', '我的收藏夹');

-- ----------------------------
-- Table structure for tbl_comments
-- ----------------------------
DROP TABLE IF EXISTS `tbl_comments`;
CREATE TABLE `tbl_comments`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note_id` int(11) NULL DEFAULT NULL,
  `comments_content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `comments_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '���۱�' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_comments
-- ----------------------------
INSERT INTO `tbl_comments` VALUES (82, 1, 'aaa', '1', '2020-12-06-01:44');
INSERT INTO `tbl_comments` VALUES (83, 12, '正好看', '1', '2020-12-07-08:49');
INSERT INTO `tbl_comments` VALUES (84, 17, '发发发', '1', '2020-12-07-08:59');
INSERT INTO `tbl_comments` VALUES (85, 17, '啾啾啾', '1', '2020-12-07-08:59');
INSERT INTO `tbl_comments` VALUES (86, 10, '哈哈哈', '1', '2020-12-07-08:59');
INSERT INTO `tbl_comments` VALUES (87, 11, '啾啾啾', '1', '2020-12-07-08:59');
INSERT INTO `tbl_comments` VALUES (88, 12, '那你们', '1', '2020-12-07-09:21');
INSERT INTO `tbl_comments` VALUES (89, 12, '那奶猫', '1', '2020-12-07-09:21');
INSERT INTO `tbl_comments` VALUES (90, 12, '并不能', '1', '2020-12-07-09:21');
INSERT INTO `tbl_comments` VALUES (91, 12, '么么么', '1', '2020-12-07-09:21');
INSERT INTO `tbl_comments` VALUES (92, 17, '你你你', '1', '2020-12-07-09:24');
INSERT INTO `tbl_comments` VALUES (93, 12, '本宝宝', '1', '2020-12-07-09:24');
INSERT INTO `tbl_comments` VALUES (94, 1, '还好哈哈哈', '1', '2020-12-07-10:02');
INSERT INTO `tbl_comments` VALUES (95, 1, '1', '1', '2020-12-07-02:11');
INSERT INTO `tbl_comments` VALUES (96, 1, '11', '2', '2020-12-07-10:13');
INSERT INTO `tbl_comments` VALUES (97, 2, 'Jjj', '2', '2020-12-07-10:26');
INSERT INTO `tbl_comments` VALUES (98, 2, '6666', '2', '2020-12-07-10:29');
INSERT INTO `tbl_comments` VALUES (99, 2, '111', '2', '2020-12-07-10:59');
INSERT INTO `tbl_comments` VALUES (100, 1, '好看啊', '3', '2020-12-07-14:18');
INSERT INTO `tbl_comments` VALUES (101, 1, '加载完成', '3', '2020-12-07-14:52');
INSERT INTO `tbl_comments` VALUES (102, 1, '正好', '3', '2020-12-07-14:54');
INSERT INTO `tbl_comments` VALUES (103, 1, '真好的的', '3', '2020-12-07-03:05');
INSERT INTO `tbl_comments` VALUES (104, 1, '真的见得到毕竟55路不投补课', '3', '2020-12-07-15:06');
INSERT INTO `tbl_comments` VALUES (105, 12, '擦擦擦擦擦擦', '3', '2020-12-07-15:15');
INSERT INTO `tbl_comments` VALUES (106, 12, '婴儿你昨天', '3', '2020-12-07-15:17');
INSERT INTO `tbl_comments` VALUES (107, 12, '真的', '3', '2020-12-07-15:19');
INSERT INTO `tbl_comments` VALUES (108, 3, '你真好看，自己考虑追我', '1', '2020-12-07-15:28');

-- ----------------------------
-- Table structure for tbl_follow
-- ----------------------------
DROP TABLE IF EXISTS `tbl_follow`;
CREATE TABLE `tbl_follow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fans_tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `follow_tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_follow
-- ----------------------------
INSERT INTO `tbl_follow` VALUES (40, '3', '1');
INSERT INTO `tbl_follow` VALUES (42, '3', '2');
INSERT INTO `tbl_follow` VALUES (43, '3', '0');
INSERT INTO `tbl_follow` VALUES (45, '1', '2');

-- ----------------------------
-- Table structure for tbl_food
-- ----------------------------
DROP TABLE IF EXISTS `tbl_food`;
CREATE TABLE `tbl_food`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note_id` int(11) NULL DEFAULT NULL,
  `content` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_food
-- ----------------------------
INSERT INTO `tbl_food` VALUES (1, 2, 'a');

-- ----------------------------
-- Table structure for tbl_imgs
-- ----------------------------
DROP TABLE IF EXISTS `tbl_imgs`;
CREATE TABLE `tbl_imgs`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `node_id` int(11) NULL DEFAULT NULL,
  `img_src` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '��ͼ��' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_imgs
-- ----------------------------
INSERT INTO `tbl_imgs` VALUES (19, 2, '21.jpg');
INSERT INTO `tbl_imgs` VALUES (20, 2, '22.jpg');
INSERT INTO `tbl_imgs` VALUES (22, 3, '31.jpg');
INSERT INTO `tbl_imgs` VALUES (23, 3, '32.jpg');
INSERT INTO `tbl_imgs` VALUES (24, 3, '33.jpg');
INSERT INTO `tbl_imgs` VALUES (25, 4, '41.jpg');
INSERT INTO `tbl_imgs` VALUES (26, 4, '42.jpg');
INSERT INTO `tbl_imgs` VALUES (27, 5, '51.jpg');
INSERT INTO `tbl_imgs` VALUES (28, 6, '61.jpg');
INSERT INTO `tbl_imgs` VALUES (29, 6, '62.jpg');
INSERT INTO `tbl_imgs` VALUES (30, 8, '81.jpg');
INSERT INTO `tbl_imgs` VALUES (31, 8, '82.jpg');
INSERT INTO `tbl_imgs` VALUES (32, 10, '101.jpg');
INSERT INTO `tbl_imgs` VALUES (33, 10, '102.jpg');
INSERT INTO `tbl_imgs` VALUES (34, 10, '103.jpg');
INSERT INTO `tbl_imgs` VALUES (35, 11, '111.jgp');
INSERT INTO `tbl_imgs` VALUES (36, 11, '112.jpg');
INSERT INTO `tbl_imgs` VALUES (37, 11, '113.jpg');
INSERT INTO `tbl_imgs` VALUES (38, 13, '131.jpg');
INSERT INTO `tbl_imgs` VALUES (39, 13, '132.jpg');
INSERT INTO `tbl_imgs` VALUES (40, 15, '151.jpg');
INSERT INTO `tbl_imgs` VALUES (42, 15, '153.jpg');
INSERT INTO `tbl_imgs` VALUES (43, 16, '161.jpg');
INSERT INTO `tbl_imgs` VALUES (44, 16, '162.jg');

-- ----------------------------
-- Table structure for tbl_journeyonline
-- ----------------------------
DROP TABLE IF EXISTS `tbl_journeyonline`;
CREATE TABLE `tbl_journeyonline`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `image` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `introduce` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `image_one` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `image_two` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `image_three` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `introduce_one` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `introduce_two` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `introduce_three` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address_one` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address_two` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address_three` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_journeyonline
-- ----------------------------
INSERT INTO `tbl_journeyonline` VALUES (1, '黄浦江边行', 'huangpujiang.jpg', '黄浦江边行，上海外滩情。', '1-1.jpg', '1-2.jpg', '1-3.jpg', '沿着外滩漫步是上海游客必须做的事情之一。外滩位于黄浦江西侧，是上海最著名的旅游景点之一。外滩两侧是代表各种建筑风格的古老建筑，包括哥特式建筑，罗马式建筑和文艺复兴建筑。这与河的东侧形成鲜明对比，东侧充满了惊人的摩天大楼，可以达到令人眼花缭乱的高度。步行外滩的好时机是夜晚，那时摩天大楼会像圣诞树一样被灯光照亮。', '典型的苏州园林，很别致，院子不大，设计奇妙，绕来绕去的，又回到大门，有亭子、有湖、有长廊、有桥……园内有三穗堂、大假假山、铁狮子、快楼、得月楼等亭台楼阁以及假山、池塘等四十余处古代建筑。\r\n\r\n豫园有着江南园林的内秀，一步一景，在方圆间感受不同的错落之美\r\n\r\n层层叠叠，走几步，园林的格局便全不相同，花草山石郁郁葱葱，是静心休息的好地方。', '乐园里真是处处充满了童话的感觉，可以去看《人猿泰山》的表演，表演还是挺不错的，感觉演员都是练杂技的出身啊。', '上海外滩', '上海豫园', '上海迪士尼乐园');
INSERT INTO `tbl_journeyonline` VALUES (2, '首都北京', 'beijing.jpg', '首都北京，海纳百川 ', '2-1.jpg', '2-2.jpg', '2-3.jpg', '北京故宫是中国明清两代的皇家宫殿，旧称紫禁城，位于北京中轴线的中心。北京故宫以三大殿为中心，占地面积72万平方米，建筑面积约15万平方米，有大小宫殿七十多座，房屋九千余间。', '颐和园，中国清朝时期皇家园林，前身为清漪园，坐落在北京西郊，距城区15公里，占地约290公顷（2.9平方千米），与圆明园毗邻。它是以昆明湖、万寿山为基址，以杭州西湖为蓝本，汲取江南园林的设计手法而建成的一座大型山水园林，也是保存最完整的一座皇家行宫御苑，被誉为“皇家园林博物馆”。', '恭王府（Prince Kung\'s Mansion），位于北京市西城区柳荫街，为全国重点文物保护单位，中国国家一级博物馆，国家AAAAA级旅游景区，是清代规模最大的一座王府建筑群，曾先后作为清乾隆时期权臣和珅、清嘉庆时期庆僖亲王永璘的宅邸，（1851年）清廷赐封此宅邸于恭亲王奕訢，恭王府的名称也因此得来。', '北京故宫博物院', '北京颐和园', '北京恭王府');
INSERT INTO `tbl_journeyonline` VALUES (3, '羊城广州', 'guangzhou.jpg', '广州羊城，车水马龙', '3-1.jpg', '3-2.jpg', '3-3.jpg', '珠江新城，是广州天河CBD的主要组成部分。天河CBD是国务院批准的三大国家级中央商务区之一（另外两个为北京CBD与上海陆家嘴CBD） [1]  ，主要服务于珠三角经济圈，是华南地区最大的CBD、唯一的世界商务区联盟成员、粤港澳服务贸易自由化示范基地，已成为华南地区总部经济和金融、科技、商务等高端产业高度集聚区。天河CBD是中国300米以上摩天建筑最密集的地方，也是广州地区世界500强企业最密集的区', '广州塔（英语：Canton Tower）又称广州新电视塔，昵称小蛮腰，其位于中国广东省广州市海珠区（艺洲岛）赤岗塔附近，距离珠江南岸125米，与珠江新城、花城广场、海心沙岛隔江相望。广州塔塔身主体高454米，天线桅杆高146米，总高度600米 [1-2]  。是中国第二高塔，仅次于上海中心大厦 [3]  ，是国家AAAA级旅游景区。', '南沙天后宫，紧临珠江出海口伶仃洋，坐落于大角山东南麓，依山傍水，其建筑依山势层叠而上，殿宇辉煌，楼阁雄伟，在天后广场正中就是石雕天后圣像，是为纪念海上女神林默而建，建筑特点是集北京故宫的风格和南京中山陵的气势于一体，其规模是现今世界同类建筑之最，被誉为“天下天后第一宫”，也 [1]  是东南亚最大的妈祖庙。天后圣像高达14.5 米，屹立在天后宫广场正中。整座天后宫四周绿树婆娑，殿中香烟袅袅。', '珠江新城', '广州塔', '南沙天后宫');
INSERT INTO `tbl_journeyonline` VALUES (4, '临安杭州', 'hangzhou.jpg', '古之临安、秀水明山', '4-1.jpg', '4-2.jpg', '4-3.jpg', '西湖南、西、北三面环山，湖中白堤、苏堤、杨公堤、赵公堤将湖面分割成若干水面。西湖的湖体轮廓呈近椭圆形，湖底部较为平坦，湖泊平均水深为2.27米，最深约5米，最浅不到1米。湖泊天然地表水源是金沙涧、龙泓涧、赤山涧（慧因涧）、长桥溪四条溪流。西湖地处中国东南丘陵边缘和中亚热带北缘，年均太阳总辐射量在100—110千卡/平方厘米之间，日照时数1800—2100小时。', '灵隐寺，中国佛教古寺，又名云林寺，位于浙江省杭州市，背靠北高峰，面朝飞来峰，始建于东晋咸和元年（326年），占地面积约87000平方米。 [1]  灵隐寺开山祖师为西印度僧人慧理和尚。南朝梁武帝赐田并扩建。五代吴越王钱镠命请永明延寿大师重兴开拓，并赐名灵隐新寺。宋宁宗嘉定年间，灵隐寺被誉为江南禅宗“五山”之一。清顺治年间，禅宗巨匠具德和尚住持灵隐，筹资重建，仅建殿堂时间就前后历十八年之久，其规模之', '宋城以“建筑为形，文化为魂”为经营理念，仿宋代风格建造，主体建筑依据北宋画家张择端的长卷《清明上河图》而建，并按照宋书《营造法式》建造，还原了宋代都市风貌，是杭州第一个反映两宋文化内涵的主题公园，年接待游客超过1000万。大型歌舞《宋城千古情》是宋城的灵魂，与拉斯维加斯的O秀、巴黎红磨坊并称“世界三大名秀”', '杭州西湖白堤', '杭州灵隐寺', '宋城景区');
INSERT INTO `tbl_journeyonline` VALUES (5, '鹏城深圳', 'shenzhen.jpg', '鹏城深圳、旖旎风光 ', '5-1.jpg', '5-2.jpg', '5-3.jpg', '界之窗位于广东省深圳市深圳湾社区深南大道，是中国著名的缩微景区，以弘扬世界文化为宗旨，是一个把世界奇观、历史遗迹、古今名胜、民间歌舞表演融为一体的人造主题公园。公园中的各个景点都按不同的比例仿建。', '莲花山公园主峰建有 4000平方米的山顶广场，是深圳市内最高的室外广场。山顶广场中央矗立着改革开放总设计师邓小平同志的塑像，塑像高6米，基座高3.68米，重7吨，为青铜铸造。2000年11月14日，江泽民同志亲自为铜像题字和揭幕。山顶广场是深圳市中心区的最好去处。', '欢乐海岸汇聚全球大师智慧，以海洋文化为主题，以生态环保为理念，以创新型商业为主体，以创造都市滨海健康生活为梦想，开创性地将主题商业与滨海旅游、休闲娱乐和文化创意融为一体，整合零售、餐饮、娱乐、办公、公寓、酒店、湿地公园等多元业态，形成独一无二的商业+娱乐+文化+旅游+生态的全新商业模式，真正实现集主题商业、时尚娱乐、健康生活三位一体的价值组合，以实际行动推动中国主题商业的创新和发展。', '深圳世界之窗', '莲花山公园', '深圳欢乐海岸');
INSERT INTO `tbl_journeyonline` VALUES (6, '热带三亚', 'sanya.jpg', '热带风情之都,南海多彩明珠', '6-1.jpg', '6-2.jpg', '6-3.jpg', '蜈支洲岛坐落在海南省三亚市北部的海棠湾内，北面与南湾猴岛遥遥相对，南邻美誉天下第一湾的亚龙湾。蜈支洲岛距海岸线2.7公里，方圆1.48平方公里，呈不规则的蝴蝶状，东西长1400米，南北宽1100米。', '园区定位于国际一流的滨海山地生态观光兼生态度假型森林公园，植被类型为热带常绿性雨林和热带半落叶季雨林。其生物、地理、天象、水文、人文资源丰富多彩，景观建设极尽生态自然，可开展登山探险、野外拓展、休闲观光、养生度假、科普教育、民俗文化体验等多种旅游活动。海南省是中国第一个生态省，而“热带天堂”就是离城市最近的天然森林氧吧。\r\n园区定位于国际一流的滨海山地生态观光兼生态度假型森林公园，植被类型为热带常', '天涯海角游览区位于海南省三亚市西南方向23千米处，位于三亚湾和红塘湾之间的岬角上。总面积为16.4平方千米，其中陆域面积10.4平方千米，海域面积为6平方千米。 [1-3]  因景区两块巨石分别刻有“天涯”、“海角”及郭沫若先生题写的“天涯海角游览区”而得名。', '三亚蜈支洲岛', '三亚亚龙湾热带天堂森林公园', '三亚神象沐海');

-- ----------------------------
-- Table structure for tbl_love
-- ----------------------------
DROP TABLE IF EXISTS `tbl_love`;
CREATE TABLE `tbl_love`  (
  `note_id` int(11) NOT NULL,
  `tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`note_id`, `tel`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '���ޱ�' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_love
-- ----------------------------
INSERT INTO `tbl_love` VALUES (3, '1');
INSERT INTO `tbl_love` VALUES (4, '1');
INSERT INTO `tbl_love` VALUES (12, '1');
INSERT INTO `tbl_love` VALUES (15, '1');

-- ----------------------------
-- Table structure for tbl_modle
-- ----------------------------
DROP TABLE IF EXISTS `tbl_modle`;
CREATE TABLE `tbl_modle`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_modle
-- ----------------------------
INSERT INTO `tbl_modle` VALUES (1, 'route');
INSERT INTO `tbl_modle` VALUES (2, 'food');

-- ----------------------------
-- Table structure for tbl_note
-- ----------------------------
DROP TABLE IF EXISTS `tbl_note`;
CREATE TABLE `tbl_note`  (
  `note_id` int(11) NOT NULL AUTO_INCREMENT,
  `tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cover_src` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `like_num` int(11) NULL DEFAULT NULL,
  `collection_num` int(11) NULL DEFAULT NULL,
  `comments_num` int(11) NULL DEFAULT NULL,
  `titlle` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `upload_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `upload_position` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `res_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `modle_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tag_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`note_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '�ʼ�' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_note
-- ----------------------------
INSERT INTO `tbl_note` VALUES (1, '1', '1.jpg', 20, 1, 9, '到了10月，香格里拉就出了雨季，最美的秋天也要来了。', '到了10月，香格里拉就出了雨季，最美的秋天也要来了。\n草原:秋风吹过，把草原由绿渐渐吹黄，红色的狼毒花是草原上的点缀，牛羊们悠闲地在草原上踱步静享秋光\n湖泊:草原远处的湖水依旧清冽，晴朗天气阳光的映照下，湖面繁星★点点\n松林:环绕牧场连绵起伏的松林悄悄变了颜色，红橙橘黄的层次感在镜头下体现得淋漓尽致\n星空:秋季是香格里拉观星的好时节，晴朗的日子居多，没有了云层的遮挡，夜间漫天繁星肉眼可见，和秋日草原一起伴着你入睡\n', '2020-01-19-11:30', '香格里拉', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (2, '0', '2.jpg', 18, 0, 7, '大观湿地公园', '广州周末遛狗带娃看这里大观湿地公园个\n养狗跟带娃有个共同点，除了日常遛遛，偶尔还得带他们出去放放风!然而能带狗子的地点比适合带娃的地点更难找。\n最近铲屎官盆友们分享，广州新晋网红点大观湿地公园，狗子也能逛!那还等什么，走!!带上我的dodo子，走起!\n', '2020-02-20-14:45', '广州', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (3, '0', '3.jpg', 17, 0, 1, '哈尔冰雪景', '大东北哈尔滨雪乡的冬天太美了\n南方的孩子，整个冬天都在做一场关于大雪纷飞的梦@鑫燕旅行\n之前去过芬兰和挪威，也看过漫天大雪和堆积成半米高的雪地，其实讲真，抛开欧式建筑特有的欧洲风情，光从雪景来看，哈尔滨的雪景和芬兰并无大差别，特别是凤凰山(一定要去! !)从山脚坐巴士到达山顶的这一路，让我仿佛重新回到了芬兰，太像了，真的特别美。原来国内也有这样的冬天啊!\n', '2020-03-01-12:40', '哈尔滨', 'img', '2', 1);
INSERT INTO `tbl_note` VALUES (4, '0', '4.jpg', 15, 0, 0, '又美又野奢!住神仙别墅，过向往的生活\n', '今天推荐3家野奢住宿，把你藏在工作找不到的地方~\r\nNo.1腾冲康藤高黎贡帐篷营地\r\n奢华帐篷(Glamping=glamorous camping）这几年真的红遍社交网络!给大家安利云南高黎贡山区，它是印度和欧亚板块接缝处形成的纵谷区，物种丰富、文明交汇之处。\r\n平均海拔2300米，客房搭在树梢，呈线性分布，有那么点\"身在此山中，云深不知处\"的氛围。\r\n坐标:云南腾冲县五合乡小地方以北5公里玩法:看星星、野外徒步\r\n小贴士:一定要提前和营地预约，衣服带厚点，高海拔山区不是开玩笑\r\n瓷No.2腾冲喂研吾·石头纪温泉VILLA度假酒店腾冲的山林里藏着一家温泉别墅，还是著名设计大师限研吾的力作!喂研吾在腾冲云峰山，一片神秘又原始的乡野秘境里，再次展现了其\"负建筑\"的设计精髓。据说建筑材料只用武定砂岩、施甸米黄、云龙青石、腾冲本地火山石4种石头。\r\n作为一家全别墅度假酒店，石头纪的入门房型就有146平米，每种房型都有庭院和露天泡池。腾冲的温泉品质无需多言，真的很适合放松享受。\r\n', '2020-04-02-15:32', '云南', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (5, '0', '5.jpg', 12, 0, 0, '南京周边游～溧阳网红拍照地打卡\n', '从南京过去一个多小时，对于喜欢拍照的我来说真的是宝藏了\n①我们先导航了稻梦空间，很小的一块地方，真的能拍很久，有粉黛乱子草，草编大恐龙口大猩猩口，最喜欢的还是霍比特人小房子，相当出片，最重要的是人还不多，想怎么拍就怎么拍，旁边就是—号公路，对彩虹路感兴趣的小伙伴也可以拍拍拍，我是在南京拍过彩虹路和粉黛乱子草，所以不太感兴趣，天公作美，拍完才飘了几滴雨。②下一站马不停滴的赶往溧阳博物馆，到门口一看周一闭馆，还好我要拍的楼梯那边的景可以进去拍，不然真的白来了，虽然就拍了五分钟，成片出来感觉真的很值!\n③网红大飞机，擅长拍做旧工业风的伙伴可以去打卡，我修图全部失败，百度地图导航泳衣厂家直销就可以了\n', '2020-05-03-15:10', '南京', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (6, '0', '6.jpg', 10, 0, 0, '如诗如画杭州秘境嫩\n如果你来杭州，我绝不带你去西湖!\n', '九溪秋天算得上是杭州的代表~\n杭州的秋天短的不可思议，却也是美如诗如画其中九溪烟树最是诗意盎然，收获油画般的风景幽深如翡翠的墨绿打底,红与黄调整饱和度，溪涧漂流着染透了红叶窜\n(交通)交通攻略:\n自驾到九溪定位的位置\nР现新增了几个立体机械车库，10/h园区电瓶车8/人，往返停车场到九溪烟树建议步行前往，往返大约1.5小时，漫游漫拍\n品特色推荐:\n深秋的九溪十八涧是非常具有美感,\n层次丰富，渐冷红叶，泛黄冷杉高低错落有致，引得不少人驻足观赏～\n翠绿龙井茶园，铺满落叶d的溪流潺潺流淌夹杂着虫鸣鸟叫声，置身于大自然赋予我们的温柔里~\n', '2020-06-03-20:10', '杭州', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (7, '0', '7.jpg', 8, 0, 0, '穿着汉服游西安大唐不夜城|邂逅美丽的不倒翁小姐姐\n', '大唐不夜城已经赶超钟楼和回民街，成为西安最具有特色的一处旅行目的地，从大雁塔南广场开始，一路上灯光闪耀，富丽堂皇，感受到梦回大唐的感觉，仿佛来到了长安城。\n这里有好多表演:\n吓倒翁小姐姐，还有不倒翁小哥哥，注意时间表吗，别干里迢迢来了没看见，一定要提前去排队，展位在电音舞台对门，有三个不倒翁小姐姐，皮卡晨周五周六周日晚上表演，只牵女孩子的手，狐逗豆周日周一周二晚上表演，甜甜圈周二周三周四晚上表演。\n画中人，大唐不夜城的特色表演之一\n石头人，同为行为艺术表演，非常的受欢迎4李白等等\n位置:西安市大雁塔南广场往南1500米长的步行街\n如何到达:地铁大雁塔站下车就能到消费:全部免费的，不需要门票\n', '2020-07-03-10:10', '西安', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (8, '0', '8.jpg', 6, 0, 0, '石家庄citywalk |小众地旅行|周末短途游\n', '交通—石家庄往返太原的火车班次非常多，平均每1-2小时就有一趟，动车绿皮任意选，车程1-2\n小时车票30-60元不等,同时白豕)土1FT十去其他城市的最佳选择。\nDay1\n抱犊寨─市郊简单版登山，今地人4冰多中去处，爪吧到南天i门要是进山顶景区里电;次手4uen不进去免费，翻诚莲花山便完成二分之一，口十汉台阶类似泰山的十八盘，难度不大沿途补给很多，\n全程往返2-3小时。\n土门关驿道小镇一与抱犊寨距离不远，可安排在一起游玩，镇里有很多特色小吃店，古镇类的建筑街道。\n德明古镇—这两个小镇分别用1-2小时足够，德明位于西部长青旅游度假区内，非常大，另外有冰雪小镇等其他项目。古镇内也是以小吃十特色建筑为主，注意回市里的最后一班在下午5点，导航直接搜公车号可查到具体发车时间。\n', '2020-08-03-4:10', '石家庄', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (9, '0', '9.jpg', 4, 0, 0, '石家庄两天一夜完全攻略手册\n', '石家庄市内景点不多，硬说起来【河北省博物馆】算一个，隔着一条街的【河北省图书馆】还有大名鼎鼎的【呈明书店】\n【威尼斯】是在一个叫【海悦天地】的大商场里,实际是一条美食步行街\n至于【霍格沃兹魔法学院--河北美术学院】是在新乐县，需要坐公交车或者火车到【正定火车站】换乘石家庄至新乐的班车，每10分钟一趟车票价9元，当然赶时间也可以高铁坐到【正定机场站】打车前往，16公里40元即可。\nPS:河北美术学院保安管的不严，正门一卡一人进不去，但可以从停车场那边进去，如果被拦住了，门口有很多黑车司机，40块钱带到后门进去，我虽然知道位置但后门有锁,钥匙只有黑车司机有，直接过去也没用。\n同样【正定五大寺庙之隆兴大佛寺、开元寺、天宁寺、临济寺、广惠寺】都在正定，正定的位置在石家庄和新乐中间，从石家庄市内或正定火车站均可公交车到达为方便大家我在地图上标注出了位置,可以自行安排路线，在此我个人推荐两种路线:①从石家庄火车站出发，先去海悦天地吃顿饭，然后逛博物馆和书店，然后前往正定逛五大寺，住宿，第二天在正定火车站坐前往新乐的班车到河北美术学院，参观完毕后打车前往正定机场火车站坐车离开。\n', '2020-09-02-15:00', '石家庄', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (10, '0', '10.jpg', 6, 0, 1, '河北省博物院工石家庄不好玩?来这里!\n', '来石家庄读书一年了，终于去到了河北省博。上次去还是一年前，因为恰逢周一闭馆所以没能进去。\n这次中秋节专门去到省博参观。博物馆门前广场有很多鸽子，里面也有很多家长带着孩子去参观。实用tips:\n博物馆分为南北区，北区为近期展览(不是固定的，隔段时间就会换)，南区为展品。\n北区:\n冰河巨兽——猛犸象披毛犀动物群标本展不忘初心，牢记使命——主题教育档案文献展鼎立中原，豫地瑰宝——河南珍藏文物展\n南区分为三层，每层分为东西两边。每层3-4个展厅。每层展厅详细分布见P5今\n\n', '2020-10-02-15:00', '石家庄', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (11, '0', '11.jpg', 8, 1, 1, '石家庄最火日料(松本楼)\n', '2020啦，到石家庄看朋友，由于最近一直在减肥口￥所以好朋友带我来到了她们\"\"国际庄\"的一间日料店叼本来想着说，能有什么太多吃的啊，北京的日料店我也没吃过太多，随便吃点儿，饿不着就得了5结果什么减肥，甘油三酯，卡路里全都抛在了脑后切只恨自己狂有一颗吃货的心(桃心)压根儿没有一个匹配的胃啊宁说好的减肥先再见一天吧仓-—OO\n—-\n版推荐理由版\n其实推荐的理由很简单，就是两个很直白的中国文字~好吃有点苍白哈，那我多说两种\n日语打lvLl泰语asa3艺划就会这点了哈哈\n我本人在北京就不太吃日料（可能我也没吃对餐厅啊金）总觉得吃着特别贵不说，味道也差强人意。但是这里就不会啦，这200/人左右的价格还是自助餐卵，加上味道很好随便吃。我心里只有两个字那就是～漂亮◎\n', '2020-11-30-07:55', '石家庄', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (12, '2', '12.jpg', 11, 1, 9, '南京最仙的景点!仙到不得不去!\n', '或许是完全修复大报恩寺的传奇建筑在经费和技术上有相当的难度，因此，琉璃宝塔并未按照历史原样复建，而是以轻质钢架玻璃保护塔的形式重现，公园内部展厅也多以禅意、哲理、概念、艺术、科技的手法和表现方式，让游客能够自我联想，构建起心中理想的宝塔。所以在展厅内游览，你会惊叹于多个场景的美妙和华丽，当艺术与宗教结合，我们能够看到更为精彩的艺术呈现方式。\n可惜的是，景区内非常嘈杂，游客众多，却无人提醒保持安静，很多小朋友直接在佛像或人造景观上攀爬。我甚至还在馆内看到供儿童玩耍的塑料城堡，虽然给儿童提供娱乐空间是出于人性化的考量，但格格不入的塑料玩具是否破坏遗址公园原有的审美?此外，说到底也是佛教圣地，汉服体验在这里是否妥当?在展览馆内穿梭拍照的汉服少女，是否给予一个宗教领地应有的尊重。作为南京可能是最仙的景点，如何系统的维护和长久的发展，可能还有很多细节上的问题需要商榷。\n', '2020-12-06-08:38', '南京', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (13, '2', '13.jpg', 12, 0, 0, '石家庄的这所大学呦美爆了你的朋友圈\n', '—2HA一JI\n石家庄这次旅行对于我来说，号称史上最便宜的一次的旅行了哈哈抱着随遇而安的心态，没想到这是一个如此惊艳的地方\n河北美术学院，看图就觉得仿佛置身哈利波特\n魔法城堡，真的是超美喜欢拍照的小伙伴一定\n要来。口￥游玩tip:1学院一共六座城堡，我去的时候是可以随便进出的。刚进大门就可以看到标志性建筑(图1图2)，中间这座教学楼是可以进去的白外面有很好看的大楼梯，里面是错落有致的圆柱配上落地窗，有点像音乐盒里面转来转去的小天使哈哈口2出来之后再往后走会看到一条河周围全部是好看的建筑,这里还可以看到学校的大门，有一条连廊连接着两个建筑F非常有特色。然后可以看到食堂，微信最好是现金可以在食堂吃饭哈\n我们还发现了一个新大陆白一个像地下车库的位置，走下去墙上挂满了学生的作品。再往里面走是教室，走廊像是画展一样，配上暖色系的灯光，超级有感觉。\n\n', '2019-12-02-12:30', '石家庄', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (14, '2', '14.jpg', 14, 0, 0, '三亚后海-冲浪十吃喝玩乐超全攻略!\n', '美食\n在hanatoalice民宿的对面有一家海鲜餐厅，店里的海鲜都是当日新鲜打捞出来的，非常生猛，不收加工费，做法可以根据自己需求，手艺非常不错，成了我们在后海天天必去的食堂\n类后海村不大，在冲浪附近有一条街，有一些特色的酒吧，白天冲浪完就可以去酒吧，街上还有很多卖小椰子的水果的，超级甜\n米后海很休闲，白天冲浪晚上躺着看海，待在这里感觉时间过的特别慢，特别舒服，如果你也喜欢冲浪，喜欢原生态，可以考虑来这里度假哦S\n', '2019-10-02-12:30', '三亚', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (15, '2', '15.jpg', 17, 0, 0, '成都三日游成都-古尔沟温泉-毕棚沟-达古冰川\n', 'Day1.\n这一天基本耗在路上了短短五个小时车程开了一天，成都到都江堰一直堵车，临近毕棚沟景区在修路单边放行，到酒店已经精疲力尽了，去古尔沟华达美泡了温泉，因为很火爆临时没订到房间。我们泡完去吃了烧烤就回去休息啦~\nDay2.\n九点左右到毕棚沟景区门口已经排了一条长龙口一定要早点去!人暴多...检票处排队，观光车排队，电瓶车也要排队，场面堪比小长假，总之很绝望...不过看到美景都无所谓了，我们两点多准备坐车下山，无奈又是排队，于是我们决定徒步下山，速度还算快的，都要一个小时左右到景区门口。\n四点出发去达古冰川，米亚罗方向就我们一辆车，大家都是回成都的，一路上车少路况不太好，Day 3.\n早上在酒店吃了早餐驾车前往景区，十点左右坐景区观光车上山，人很少不用排队，我们提前准备了氧气瓶，在景区门口租了大衣，到索道处已经开始下雪了密冷!我俩从成都来穿的短袖到山顶十分钟左右【全世界最孤独的咖啡厅】里面是供氧的，在室外拍了照可以进去休息一下，我们两点过索道下山，高反头疼再加上温度低在山顶呆不了多久~下山就启程回成都啦回去吃火锅~\n必备品:墨镜围巾手套唇膏暖宝宝登山鞋厚袜子厚外套充电宝\n力\n浮云牧场和华达美酒店都要提前很久预定，临时去都没房间噢\n\n', '2019-11-02-12:30', '成都', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (16, '2', '16.jpg', 18, 0, 0, '鼓浪屿一日游巴来厦门必去之地|拍照攻略\n', '网红最美转角:需要排队拍照，旁边有个卖最美转角冰棍的大叔，干万不要买15RMB巨坑。类周杰伦晴天墙:人多的时候也是需要排队拍照，旁边有个网红猫，可以抱着拍照（图\n米大格树(图吓)\n类船屋:复古的代表在前两个附近。\n类八卦楼:鼓浪屿体积和高度最高的别墅，也叫风琴博物馆，门票20RMB，疫情期间好像没开放。类番婆楼:像教堂的一座别墅(图句\n类人民体育场骨图幻\n米日光岩:也叫岩仔山，鼓浪屿最高峰，站在山顶上可以看到鼓浪屿全景图\n类最美白转角:会审公堂旁边见图口\n类菽庄花园:海上花园，中西结合的贵族花园。类皓月园:为了纪念郑成功收复台湾的功绩，可以去看看郑成功的石雕，不枉此行图\n', '2019-12-01-12:30', '厦门', 'img', '1', 1);
INSERT INTO `tbl_note` VALUES (17, '2', '17.mp4', 20, 0, 3, '河北师范大学', '阿巴阿巴', '2020-12-01-12:30', '石家庄', 'video', '1', 1);

-- ----------------------------
-- Table structure for tbl_online_travel
-- ----------------------------
DROP TABLE IF EXISTS `tbl_online_travel`;
CREATE TABLE `tbl_online_travel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `latLong1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `latLong2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `latLong3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_online_travel
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_plan
-- ----------------------------
DROP TABLE IF EXISTS `tbl_plan`;
CREATE TABLE `tbl_plan`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `plan_content` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '�ƻ���' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_plan
-- ----------------------------
INSERT INTO `tbl_plan` VALUES (1, '11111111111', 'aaaaa', 'aaa');
INSERT INTO `tbl_plan` VALUES (2, '11111111111', 'bbbbbbbbb', 'bbb');
INSERT INTO `tbl_plan` VALUES (3, '22222222222', 'ccccccccccc', 'ccc');
INSERT INTO `tbl_plan` VALUES (4, '22222222222', 'sssssssss', 'sss');

-- ----------------------------
-- Table structure for tbl_route
-- ----------------------------
DROP TABLE IF EXISTS `tbl_route`;
CREATE TABLE `tbl_route`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note_id` int(11) NULL DEFAULT NULL,
  `content` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_route
-- ----------------------------
INSERT INTO `tbl_route` VALUES (1, 1, '石家庄');
INSERT INTO `tbl_route` VALUES (2, 1, '太原');
INSERT INTO `tbl_route` VALUES (3, 1, '阳泉');

-- ----------------------------
-- Table structure for tbl_son_comment
-- ----------------------------
DROP TABLE IF EXISTS `tbl_son_comment`;
CREATE TABLE `tbl_son_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) NULL DEFAULT NULL,
  `comments_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `comments_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_son_comment
-- ----------------------------
INSERT INTO `tbl_son_comment` VALUES (8, 0, 'qqq', '1', '2020-12-06 01:45:00');
INSERT INTO `tbl_son_comment` VALUES (9, 82, '突击', '1', '2020-12-07 10:02:00');
INSERT INTO `tbl_son_comment` VALUES (10, 82, '1', '1', '2020-12-07 02:11:00');
INSERT INTO `tbl_son_comment` VALUES (11, 0, '的', '2', '2020-12-07 10:13:00');
INSERT INTO `tbl_son_comment` VALUES (12, 0, '7777', '2', '2020-12-07 10:26:00');
INSERT INTO `tbl_son_comment` VALUES (13, 97, '888', '2', '2020-12-07 10:27:00');
INSERT INTO `tbl_son_comment` VALUES (14, 0, 'Ijsjsjjs', '2', '2020-12-07 10:29:00');
INSERT INTO `tbl_son_comment` VALUES (15, 97, '1', '2', '2020-12-07 10:59:00');

-- ----------------------------
-- Table structure for tbl_tag
-- ----------------------------
DROP TABLE IF EXISTS `tbl_tag`;
CREATE TABLE `tbl_tag`  (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_tag
-- ----------------------------
INSERT INTO `tbl_tag` VALUES (1, '热门地点');

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user`  (
  `tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pwd` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `headSrc` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `intro` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tel`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '�û���' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES ('0', '曾国藩', '0', '0.jpg', '男', '曾国藩（1811年11月26日－1872年3月12日），初名子城，字伯涵，号涤生，宗圣曾子七十世孙。中国晚清时期政治家、战略家、理学家、文学家、书法家 [1]  ，湘军的创立者和统帅。');
INSERT INTO `tbl_user` VALUES ('1', '左宗棠', '1', '1.jpg', '女', '左宗棠（1812年11月10日—1885年9月5日），汉族，字季高， [1]  一字朴存，号湘上农人。湖南湘阴人。晚清政治家、军事家、民族英雄 [2]  ，洋务派代表人物之一，与曾国藩等人并称“晚清中兴四大名臣”。');
INSERT INTO `tbl_user` VALUES ('2', '李鸿章 ', '2', '2.jpg', '男', '李鸿章（1823年2月15日—1901年11月7日），本名章铜，字渐甫、子黻[fú]，号少荃（一作少泉），晚年自号仪叟，别号省心，安徽合肥人，晚清名臣，洋务运动的主要领导人之一。世人多称“李中堂”，因行二，故民间又称“李二先生”。');
INSERT INTO `tbl_user` VALUES ('3', '糖果', '3', '3.jpg', '女', '可爱多');
INSERT INTO `tbl_user` VALUES ('319', '用户1607308895000', '319', 'original.jpg', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
