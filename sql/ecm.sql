/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : ecm

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 21/10/2026 21:36:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_address
-- ----------------------------
DROP TABLE IF EXISTS `t_address`;
CREATE TABLE `t_address`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '地址ID',
  `user_id` int(20) NOT NULL COMMENT '用户ID，关联t_user表',
  `receiver_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人联系电话',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '城市',
  `district` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区县',
  `detailed_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `is_default` tinyint(1) NULL DEFAULT 0 COMMENT '是否默认地址：0-否，1-是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_address_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收货地址表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_address
-- ----------------------------
INSERT INTO `t_address` VALUES (1, 1, '时雨', '18923452345', '山东省', '东营市', '东营区', '幸福小区', 1);
INSERT INTO `t_address` VALUES (2, 1, '时雨', '18923452345', '山东省', '东营市', '东营区', '美丽小区', 0);
INSERT INTO `t_address` VALUES (4, 2, '夜', '13822223333', '山东省', '东营市', '东营区', '金龙大厦4层405', 0);
INSERT INTO `t_address` VALUES (5, 2, '雨', '13212344321', '山东省', '东营市', '东营区', '幸福小区33号楼1门301', 1);
INSERT INTO `t_address` VALUES (7, 2, '雨', '13411112222', '山东省', '德州市', '乐陵市', '时代天街101号', 0);
INSERT INTO `t_address` VALUES (8, 8, 'sb', '13145678956', 'df', 'kl', 'ui', 'ui', 1);

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES (1, 'admin', '123123');

-- ----------------------------
-- Table structure for t_favorite
-- ----------------------------
DROP TABLE IF EXISTS `t_favorite`;
CREATE TABLE `t_favorite`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `product_id` int(11) NOT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_product`(`user_id`, `product_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE,
  CONSTRAINT `t_favorite_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `t_favorite_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `t_product` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_favorite
-- ----------------------------
INSERT INTO `t_favorite` VALUES (98, 2, 1);
INSERT INTO `t_favorite` VALUES (99, 2, 2);
INSERT INTO `t_favorite` VALUES (100, 2, 7);

-- ----------------------------
-- Table structure for t_like
-- ----------------------------
DROP TABLE IF EXISTS `t_like`;
CREATE TABLE `t_like`  (
  `user_id` int(20) NOT NULL COMMENT '用户ID',
  `news_id` int(20) NOT NULL COMMENT '资讯ID',
  PRIMARY KEY (`user_id`, `news_id`) USING BTREE,
  INDEX `fk_like_news`(`news_id`) USING BTREE,
  CONSTRAINT `fk_like_news` FOREIGN KEY (`news_id`) REFERENCES `t_news` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_like_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户资讯点赞表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_like
-- ----------------------------
INSERT INTO `t_like` VALUES (7, 1);
INSERT INTO `t_like` VALUES (7, 3);
INSERT INTO `t_like` VALUES (7, 5);

-- ----------------------------
-- Table structure for t_news
-- ----------------------------
DROP TABLE IF EXISTS `t_news`;
CREATE TABLE `t_news`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资讯ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `author` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'admin' COMMENT '发布人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '汽车资讯表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_news
-- ----------------------------
INSERT INTO `t_news` VALUES (1, '高端车“线控转向”黑科技！没有机械连接，开车真的更稳？', '最近发布的蔚来ES9，还有即将发布的理想L9，都配备了线控转向技术，那作为消费者，首先更关心的就是，少了机械连接，线控转向真的靠谱么？\n先给核心结论：线控转向，本质就是“用电子信号代替机械传动轴”，像用手机遥控玩具车一样，让方向盘和车轮“无线牵手”，去掉多余机械束缚，驾驶体验和安全性直接升级。\n先对比大家熟悉的传统机械转向：你开车转方向盘，就像“拉绳子”——方向盘通过一根粗壮的机械传动轴，直接连到车轮转向机构，你转多少，车轮就转多少，中间是“硬连接”。路面的震动、石子的颠簸，都会通过传动轴传到方向盘上，开车时手容易麻，还可能有转向“虚位”。\n而线控转向，相当于把这根“机械绳子”换成了“电子指令”：方向盘不直接连车轮，只负责“发号施令”，车轮负责“精准执行”。\n生活化比喻：就像微信发消息，不接触也能精准传指令\n传统机械转向 = 你直接用手推朋友，你用多大劲、推多少角度，朋友就动多少；\n线控转向 = 你给朋友发微信，说“向左走5步”，朋友收到指令后，精准执行，中间没有任何直接接触。\n具体来说，方向盘上装了“指令传感器”，你转动方向盘时，它会把你的操作（转了多少角度、用了多大劲）转换成电子信号，通过电线传给车载电脑；电脑再根据车速、路况（高速/低速）微调指令，传给车轮上的“转向执行器”，最终带动车轮转向，全程几乎没有延迟。\n普通车主最关心的3个优势，不玩专业术语\n1. 转向更丝滑，没有“虚位”：没有机械传动轴的摩擦和间隙，低速挪车时，打半圈方向盘，车轮就精准转半圈，不用反复调整；高速行驶时，方向盘会自动变“沉”，避免误操作，开起来更稳。\n2. 驾驶更舒适、更安静：机械连接会传递路面震动，线控转向去掉了传动轴，压到石子、过坑洼时，震动不会传到方向盘上，手不麻，车内也更安静。\n3. 安全性拉满，有“保命备用”：线控转向有“冗余设计”（简单说就是备用系统），一个电子信号出问题，备用系统立刻接管，不会出现方向盘失灵；而且车载电脑反应比人快10倍，紧急变道时会微调转向角度，避免侧滑。\n最关键疑问：没有机械连接，会不会失控？\n答案很明确：比传统机械转向更安全！\n除了双重/三重备用系统（像飞机备用发动机），线控转向去掉了传动轴，碰撞时不会出现传动轴断裂戳进驾驶舱的风险；再加上电脑实时干预，能提前规避很多人为操作的风险，安全感直接拉满。\n现在线控转向主要搭载在宝马、奔驰、蔚来等高端车型上，未来会逐渐下放。以后看车，只要问一句“线控转向有几重冗余”，就能瞬间从“小白”变“懂车人”～', 'admin', '2026-04-13 23:42:16');
INSERT INTO `t_news` VALUES (3, '电芯隔热/缓冲/轻量化：MPP 发泡材料备受新能源电池行业青睐', '新能源汽车对电池材料的核心诉求\n新能源汽车产业的快速迭代，对动力电池提出了越来越严苛的性能要求。在动力电池的Pack系统中，电芯工作环境的稳定性直接决定了整包的安全性能、循环寿命与能量密度，三大核心需求始终是行业研发的方向：电芯隔热、结构缓冲、系统轻量化。\n首先是电芯隔热需求。动力电池在充放电过程中会持续产生热量，若热管理不畅，局部温度过高不仅会加速电芯老化，更可能引发热失控，威胁整车安全。尤其是在单块电芯发生热失控时，需要隔热材料阻断热量向相邻电芯传递，为乘客预留足够的逃生时间，这对材料的隔热性能提出了极高要求。\n其次是结构缓冲需求。动力电池整车行驶过程中，会持续承受路面颠簸带来的振动与冲击，电芯与电芯之间、电芯与Pack箱体之间若缺乏有效的缓冲结构，长期应力作用会导致极片错位、壳体变形，最终影响电芯性能与安全。缓冲材料需要能够均匀分散应力，吸收振动能量，保护电芯结构完整。\n最后是系统轻量化需求。续航里程是新能源汽车核心竞争力指标之一，整包重量每降低1kg，不仅可以直接提升续航，还能降低整车簧下质量，改善操控性能。在保证安全与结构强度的前提下，电池系统集成过程中对各类辅材的轻量化要求越来越高，传统材料密度大，已经难以满足当前高能量密度电池包的设计需求。\nMPP发泡材料的特性优势\nMPP发泡材料全称为发泡改性聚丙烯，是通过对聚丙烯基体进行化学交联或物理发泡得到的微孔发泡材料，凭借独特的结构特性，恰好匹配了动力电池三大核心需求：\n优异的隔热性能，阻断热失控蔓延\nMPP发泡材料内部存在大量闭孔微孔结构，空气被封闭在微小孔穴中，热传导效率极低，其热导率通常可以低至0.03~0.04 W/，远低于传统的聚丙烯、橡胶等材料，甚至优于很多传统隔热材料。在电芯发生热失控时，MPP发泡材料可以长时间阻挡高温热传导，试验数据显示，10mm厚度的MPP发泡板材，可以在1000℃高温火焰下保持30分钟以上不被击穿，有效延缓热失控在整包内的蔓延速度，大幅提升动力电池系统的安全阈值。\n同时，MPP基体本身就是阻燃改性材料，氧指数可以达到30以上，本身不助燃，也不会在高温下产生大量有毒有害烟气，符合动力电池Pack的环保与安全要求。\n良好的缓冲吸能，保护电芯结构\nMPP发泡材料属于半硬质发泡材料，具备优异的回弹性与缓冲性能，压缩模量可以根据发泡倍率调整，能够适应不同电芯间隙的缓冲需求。当受到外部冲击或者振动时，材料内部的微孔结构可以通过形变吸收能量，随后快速恢复原状，不会产生永久变形，长期使用过程中性能衰减极小。\n对比传统的EVA泡棉、硅胶缓冲垫，MPP发泡材料的压缩永久变形率可以控制在5%以内，在-40℃到120℃的宽温度范围内，力学性能保持稳定，不会因为低温变脆、高温变软而丧失缓冲性能，完美适配动力电池全场景工作环境。\n极低密度满足轻量化要求\nMPP发泡材料的发泡倍率可以达到3~20倍，密度最低可以降至0.05g/cm³，远低于传统的隔热缓冲材料，比如传统的石棉隔热板密度在0.5g/cm³以上，EPDM泡棉密度通常在0.15~0.3g/cm³，相同体积下，MPP发泡材料的重量可以降低60%以上，对电池包整体减重的贡献十分显著。\n同时，低密度并没有牺牲材料的力学强度，MPP发泡材料的比强度远高于传统发泡材料，在满足隔热缓冲性能要求的前提下，可以做到更薄的厚度，帮助电池包节省出更多空间布置电芯，间接提升了整包的能量密度。\n除了三大核心优势，MPP发泡材料还具备很多适配动力电池行业的附加特性：一是耐化学腐蚀性好，对电解液、冷却油等物质具备良好抗性，即使发生轻微漏液也不会快速腐蚀失效；二是环保可回收，聚丙烯基体属于可回收高分子材料，符合动力电池全生命周期回收的行业趋势；三是易加工，可以根据需求切割、模压成不同形状，适配不同型号电池包的结构设计，生产成本可控。\n当前行业应用情况与发展前景\n近年来，随着动力电池向高能量密度、大电芯方向发展，MPP发泡材料的应用渗透率正在快速提升，已经广泛应用在车企与动力电池厂商的新型动力电池包中，主要应用场景包括：电芯之间的隔热缓冲垫、电芯与箱体之间的隔热缓冲层、电池包上盖的隔热层、模组与Pack之间的缓冲垫等。\n尤其是在当前4680大圆柱电芯、刀片电池等新型结构设计中，MPP发泡材料的优势更加明显：大圆柱电芯之间需要填充隔热缓冲材料，MPP发泡材料可以既满足隔热要求，又控制整体重量与空间占用；刀片电池包的整包集成度高，对辅材的厚度、重量要求严苛，MPP发泡材料低密度、低厚度的特性完美匹配设计需求。\nMPP产业整体布局具备鲜明本土化发展特色，作为国内独创新型材料，产品生产加工、市场销售推广高度集中于国内市场，已搭建完成从聚丙烯原料改性优化、发泡生产工艺升级、成品深加工制造到终端行业场景适配应用的完整成熟产业链条，牢牢掌控全球MPP产业核心发展主导权。\n未来，随着新能源汽车对安全与续航要求进一步提升，以及储能领域对动力电池安全性能要求越来越高，MPP发泡材料的市场空间还会持续扩大，预计未来三年，新能源领域对MPP发泡材料的年需求量会达到数十万吨级，成为新能源电池材料领域的重要细分赛道。\n宝益科技是国内知名的功能性高分子发泡材料供应商，深耕行业多年。宝益MPP产品采用原材料专利配方和超临界流体技术，实现从发泡过程清洁无污染到发泡成品安全环保，兼具刚性和韧性的特性，微米尺度的泡孔赋予了产品优异的减震、缓冲、隔热和吸声等性能，并且宝益MPP产品已达到UL94评价体系中的最高等级HF-1级别阻燃，广泛应用于汽车和新能源领域。', 'admin', '2026-04-14 00:04:15');
INSERT INTO `t_news` VALUES (5, '三分钟读懂固态电池，一篇讲明白', '相信现在很多人对电动汽车不感冒，主要原因是现在的液态锂电池不安全、续航难涨、充电慢。而固态电池，就是解决这三大痛点的下一代“神仙电池”。今天就来讲一讲它是怎么走过来的、好在哪里、什么时候能用上。\n\n一、固态电池，到底是个啥？\n先对比你现在用的电池，迅速看懂：\n\n液态电池：正极+负极+易燃液体电解液+塑料隔膜。把正极、隔膜和负极卷在一起后，整个浸入在液态电解液里，正极、隔膜和负极之间的间隙被液态电解液填满，锂离子靠游泳在正负极之间穿梭，当前的液态电解液易燃、易发生副反应，离子传导速率较慢而且受应力后发生漏液引发短路。\n固态电池：直接把液体+隔膜换成一整块固体材料（氧化物/硫化物/聚合物）。锂离子在固体里走专用通道，固体电解质够硬，不怕锂枝晶，在正负极材料选型时就可以更大胆，不燃、不漏、更稳更快。\n半固态、准固态：在液态电池的原理上，降低液态电解液的含量占比，而且隔膜还在，现在业内普遍定义含量＜10wt%叫半固态，＜5wt%叫准固态。但和固态电池仍然是两种完全不同的东西，别被骗了。\n极简总结：固态电池= 全固体结构，没有易燃液体和隔膜（只要有就不是固态电池），安全拉满、续航翻倍、充电超快。\n\n二、固态电池发展历程（一路进化）\n它不是新概念，是一步步熬到今天的：\n\n1. 萌芽期（1960s–1990s）：从心脏起搏器开始\n\n1969 年：第一块薄膜固态电池问世，用于起搏器，不可充电\n1980s：可充电薄膜固态电池出现，只够小设备用\n1991 年：锂电池之父古德纳夫提出固态替代液态大方向\n2. 突破期（2000s–2010s）：材料大爆发（又是鬼子突破，硫化物相关专利可能会被卡）\n2001 年：LLZO氧化物固态电解质出现，奠定氧化物路线\n2007 年：硫化物电解质突破，电导率接近液态\n2006 年起：丰田全力入场，成为行业领跑者\n2011 年：LGPS硫化物再升级，全固态看到量产希望\n3. 竞速期（2010s–2025s）：全球扎堆干\n2017 年：丰田喊出2025量产（现在推迟到2030年），固态彻底破圈\n2020 年后：中、日、韩、美全面加码，宁德、比亚迪、大众纷纷入局\n2025年：中国发布两项突破技术。\n4. 由实验室样品转产线（2026–2030）：落地难度极大\n2026-2027：中试线建立，各大厂均宣布建立中试线并开始小批量\n2027–2030（本人预测）：需要有技术重大突破，建立批产线开始进行小批量生成\n三、它到底强在哪？4个核心优势，直白说\n1. 安全：从根上解决起火\n液态电解液一点就着；固态电解质不可燃、不挥发、不泄漏。针扎、挤压、高温都很难热失控，电动车自燃风险直接大幅降低。\n\n2. 续航：直接干到1000km+\n液态电池能量密度基本摸到天花板（250–300Wh/kg）。固态电池轻松做到400–700Wh/kg，续航直接翻倍。\n\n3. 充电：10分钟满电上路\n固态离子传导更快，能扛大电流快充，以后电动车充电跟加油差不多快。\n\n4. 更耐用、更抗冻耐热\n不怕“锂枝晶”（短路元凶），循环寿命更长；-20℃~60℃稳定干活，冬天不掉电、夏天不发烫。\n\n四、三条技术路线，轻松分清\n固态电池不是只有一种，目前就三条主流路线，很好记：\n\n1. 氧化物路线（陶瓷派）\n类比：像陶瓷片，硬、稳、但是脆\n\n亮点：耐水氧、不产气、安全度拉满，界面稳定，中国专利多，不被卡\n\n短板：太脆、太硬导致贴合接触难、导电速度较差，加工需要1000℃烧结，成本高，循环差\n\n定位：当下半固态/准固态首选\n\n2. 硫化物路线\n类比：橡皮泥，离子跑得飞快、界面贴得紧\n\n亮点：室温电导率逼近液态，可快充、适配锂金属负极\n\n短板：怕水怕氧（遇水会产生剧毒物，生产环境要求比沙漠还要干百倍）、原材料成本高、在10Mpa环境下才能稳定循环，80%专利被鬼子卡\n\n定位：全固态终极方向，巨头重仓\n\n3. 聚合物路线（高端塑料）\n类比：像塑料薄膜，好弯好做、但怕冷\n\n亮点：柔性好、易加工、产线兼容高\n\n短板：室温无法工作要加热到60℃、能量密度一般\n\n定位：多用于薄型/ 柔性电子（手机、手表等）\n\n五、为啥迟迟不能量产\n固 - 固接触难：众所周知，两个再光滑平整的固体，贴在一起在微观角度都是有缝隙的，离子传递受阻。虽说中国新发布的技术（技术1：添加碘化物，可自适应填充这些缝隙；技术2：将固态电解质和负极材料合成一种材料）可有效改善，但技术落地困难。\n成本偏高：材料与产线投资大，硫化物材料比当前电解液贵6倍以上，产线含水量要求极低；氧化物需要高温烧结，需要投入新产线设备，而且干电极工艺良率较差。技术和生产工艺需要时间迭代成熟。\n简单说：固态电池一落地，锂电池时代就正式进入下半场。根据欧阳老爷子的预测，固态电池还得等到2030年，上车时间更难预计，想买固态电池汽车的大佬们，估计得等着下一辆车再选啦。', 'admin', '2026-04-24 22:24:51');
INSERT INTO `t_news` VALUES (6, '汽车燃油车与新能源车悬挂系统到底差在哪', '很多人买车时喜欢看发动机、电池、零百加速、座舱大屏，却容易忽略一个更“老派”的东西：底盘悬挂。可真正决定一辆车开起来稳不稳、坐起来晃不晃、过弯有没有信心、刹车点头严不严重的，往往不是屏幕大小，而是车轮和车身之间那套复杂的机械系统。\n\n悬挂的任务看起来很简单：把车轮连到车身上。但它真正要解决的是一个矛盾：车轮要尽可能贴住地面，车身又要尽可能少被路面冲击打扰。如果悬挂太软，坐着舒服，但高速变线、过弯、刹车时车身会晃；如果悬挂太硬，操控直接，但过坑洼路面会颠，轮胎甚至可能跳起来，反而失去抓地力。\n\n所以，悬挂不是“软就是好”或“硬就是运动”，而是一套关于几何结构、材料强度、簧下质量、阻尼调校、轮胎接地姿态和电子控制的综合工程。\n\n一、悬挂系统到底由什么组成？\n一套典型悬挂系统包括几类东西。\n\n第一是弹性元件，常见为螺旋弹簧、空气弹簧、扭杆弹簧、钢板弹簧。它负责承受车身重量，把路面冲击“存起来”。\n\n第二是减振器，也叫避震器。弹簧被压缩后会反复弹跳，减振器负责消耗这种振动能量，让车身尽快稳定下来。没有减振器，汽车过一个坑之后会像蹦床一样上下摇很久。\n\n第三是导向机构，也就是各种控制臂、连杆、摆臂、转向节、副车架。它决定车轮上下运动时，外倾角、前束角、主销角等几何参数如何变化。\n\n第四是横向稳定杆。它连接左右车轮，主要用于抑制过弯时的车身侧倾。稳定杆越强，车身侧倾越小，但左右轮独立性会下降，舒适性可能受影响。\n\n这些零件合在一起，才构成我们常说的“底盘质感”。\n\n\n二、前悬挂：为什么大多数车都用麦弗逊？\n现代乘用车最常见的前悬挂是麦弗逊悬挂。它的核心结构是：减振器本身兼作上部导向支柱，下方用一个控制臂连接车轮，结构紧凑，占空间小，成本低，特别适合前横置发动机、前驱平台。麦弗逊式悬挂通常被认为由美国工程师 Earle S. MacPherson 在 20 世纪中期发展成熟，后来成为现代乘用车前悬挂的主流方案。\n\n麦弗逊的优点很现实：结构简单、重量轻、机舱占用小、制造成本低、维修方便。所以从十万元家用车到不少二三十万元车型，都可能使用麦弗逊前悬挂。它不是“低级悬挂”，而是一个非常成熟的工程折中。\n\n但麦弗逊也有弱点。因为它只有下控制臂，上方主要靠减振支柱定位，车轮上下跳动时外倾角控制能力有限。激烈过弯时，轮胎接地姿态不如双叉臂稳定。因此，很多运动型车、高端车会使用双叉臂前悬挂或多连杆前悬挂。\n\n双叉臂可以理解成上下两根“A字形摆臂”夹住车轮。它的好处是可以更精确地控制车轮姿态，过弯时让外侧轮胎更好贴地，操控极限更高。缺点是占空间大、零件多、成本高。对于燃油车来说，双叉臂前悬挂还会和发动机舱布置抢空间；对于新能源车来说，因为没有传统大体积发动机，前舱空间自由度更高，反而更容易布置复杂前悬挂。\n\n多连杆前悬挂则进一步把控制臂拆成多根独立连杆，让工程师分别调节纵向力、横向力、外倾角变化和转向响应。它的理论上限很高，但调校难度也高。多连杆不是简单“连杆越多越高级”，关键在于几何设计和衬套调校。\n\n\n三、后悬挂：扭力梁、多连杆、双叉臂各有分工\n后悬挂的类型比前悬挂更丰富。\n\n最常见的低成本方案是扭力梁后悬挂。它不是完全独立悬挂，左右后轮通过一根可扭转横梁相连。它的优点是结构简单、成本低、占空间小，后备箱和油箱布置更容易。很多小型车、紧凑级家用车使用扭力梁，并不是因为车企完全不懂操控，而是为了在成本、空间、可靠性之间取得平衡。\n\n扭力梁的缺点是左右轮会相互影响。一侧车轮压过坑洼时，另一侧也会被横梁牵动，所以在连续颠簸、弯中起伏路面上，舒适性和贴地性不如独立悬挂。\n\n更高级也更常见于中高端车型的是多连杆后悬挂。它通过三根、四根或五根连杆控制后轮运动轨迹。多连杆后悬挂最大的价值，是可以在舒适性和操控之间做细腻平衡：直线行驶时保证稳定，过弯时控制后轮外倾和前束，刹车和加速时抑制车轮乱动。多连杆悬挂通常由每个车轮三个或更多控制连杆组成，不同连杆可以承担不同方向的力学约束。\n\n还有一种常见于高性能车或高级平台的方案是双叉臂/五连杆后悬挂。它能更精确地控制轮胎姿态，尤其适合后驱、大马力、重车身平台。新能源车因为电池重、扭矩瞬间释放强，后悬挂承受的纵向力和横向力都很大，因此高端纯电车往往重视后悬挂结构。\n\n\n四、材质：钢、铝合金、铸造件、冲压件，差别在哪里？\n悬挂材料不是简单“铝合金一定高级，钢一定低端”。真正要看零件位置、受力方式、制造工艺和成本目标。\n\n冲压钢板是最常见的材料之一。它成本低、强度高、工艺成熟，适合大规模生产。很多控制臂、副车架、扭力梁都使用钢制结构。钢的缺点是重量较大，防腐和疲劳设计也要做好。\n\n铸铁/铸钢件过去常用于转向节、羊角、下摆臂等高受力部位。它强度高、成本可控，但重量较大。对于普通燃油车来说，钢铁件非常实用；对于追求轻量化和操控响应的车型来说，过重的悬挂零件会增加簧下质量。\n\n所谓簧下质量，就是弹簧下面的重量，包括轮胎、轮毂、刹车盘、卡钳、转向节、部分控制臂等。簧下质量越大，车轮越像“重锤”，遇到坑洼时越不容易快速贴回地面；簧下质量越小，轮胎跟随路面的能力越好，舒适性、抓地力和制动表现都有机会改善。铝合金铸件和锻件常被用于控制臂、转向节、副车架等部位，目的之一就是减轻簧下质量。行业资料也指出，铝合金铸件和挤压件可用于悬挂臂、副车架、转向节等底盘零件，并有助于降低簧下质量、改善乘坐和制动表现。\n\n但是铝合金也有代价。它材料成本高，对铸造、锻造、热处理、连接工艺要求高，碰撞或严重磕碰后的修复成本也可能更高。豪华车、性能车、新能源高端车喜欢用铝合金悬挂，不仅是为了“看起来高级”，更是为了在重车身、高扭矩、大轮胎的条件下尽量减轻运动部件负担。\n\n复合材料和碳纤维也可以用于底盘悬挂，但目前大多集中在赛车、超跑或局部零件上。它们轻、强度高，但成本、耐冲击、批量制造和维修问题仍然限制了普及。\n\n所以，判断底盘材质不能只看“是不是铝合金”。一根设计不好的铝合金摆臂，不一定比一根设计优秀的钢制摆臂更好。真正要看强度、重量、衬套、几何、耐久和整车调校。\n\n\n五、燃油车底盘：围绕发动机、变速箱和油箱布置\n燃油车底盘的传统约束很明显：前面要放发动机和变速箱，中间有排气管、传动轴，后面有油箱、消声器，有些车还要布置四驱系统。\n\n因此，很多燃油车前悬挂要为发动机舱让空间。前横置家用车常用麦弗逊，就是因为它结构紧凑。后悬挂也会受油箱、排气管、备胎坑影响。小型车用扭力梁，很大程度上是因为它便宜、可靠、空间利用率高。\n\n燃油车还有一个特点：发动机和变速箱通常集中在车头，前轴负荷较大。前驱燃油车既要前轮驱动，又要前轮转向，还要承担制动和大部分车头重量，所以前悬挂调校常常需要在转向响应、舒适性和轮胎磨耗之间折中。\n\n后驱燃油车则不同。发动机纵置、变速箱纵置、传动轴通向后桥，前后配重更容易做得均衡。因此很多运动型燃油车喜欢前双叉臂、后多连杆，或者前麦弗逊优化版本、后五连杆结构。\n\n燃油车底盘的发展长期围绕一个核心：如何在发动机和变速箱占据大量空间的前提下，让车轮尽可能稳定地工作。\n\n\n六、新能源车底盘：电池改变了整个车身力学\n新能源车尤其是纯电车，底盘逻辑发生了很大变化。\n\n第一，电池通常铺在地板下面，整车重心更低。低重心有利于减少侧倾，提高高速稳定性。很多纯电车即使车重很大，日常开起来也不容易“飘”，和电池位置有直接关系。\n\n第二，新能源车普遍更重。同级别纯电车往往比燃油车重几百公斤。重量增加后，弹簧、减振器、控制臂、衬套、轮胎、刹车系统都要承受更大负担。纯电车不是简单把燃油车底盘加硬就行，因为重车如果调得太硬会颠，调得太软又会晃。\n\n第三，电机扭矩来得很快。燃油车发动机扭矩要经过转速爬升、变速箱换挡、涡轮建立压力；电机则可以瞬间输出大扭矩。这会给后悬挂纵向衬套、控制臂、轮胎和副车架带来更强冲击。因此新能源车底盘不仅要考虑过弯，还要考虑加速和能量回收时的前后载荷转移。\n\n第四，电池包开始参与车身结构。比如比亚迪在海豹上使用 CTB 技术，把刀片电池作为车身结构的一部分；比亚迪欧洲官网介绍称，CTB 技术下刀片电池不只是能源来源，也成为能承受显著力的结构部件。 宁德时代也在推进 CIIC 一体化智能底盘，官方信息称其与北汽合作开发下一代 CIIC 滑板底盘，并围绕架构、系统、技术、成本和性能平衡进行联合研发。\n\n这意味着新能源底盘不只是“悬挂换成多连杆”这么简单，而是从车身、电池、热管理、电驱、制动、转向到悬挂控制的一体化工程。\n\n\n七、可变阻尼：为什么现在越来越多车强调 CDC？\n传统减振器的阻尼特性基本固定。工程师出厂前调好之后，车主只能接受这个性格：偏舒适，还是偏运动。\n\n可变阻尼减振器改变了这一点。它通过电磁阀、可变阀门或磁流变液，在不同工况下改变减振器阻尼力。高速变线时阻尼变硬，抑制侧倾和俯仰；低速过坑时阻尼变软，提高舒适性。\n\nZF 的 CDC 连续阻尼控制系统就是典型代表。ZF 官方介绍称，CDC 系统由中央控制单元根据传感器采集的车辆数据，为每个车轮计算理想阻尼力，并在压缩和回弹方向上调节阻尼，从偏安全、偏硬到偏舒适、偏软的特性曲线之间变化。\n\n还有一种更高级的路线是磁流变减振器。它的减振器油液中含有可被磁场控制的微粒，通电后流体特性迅速变化，从而改变阻尼力。BWI/MagneRide 官方资料称，MagneRide 使用磁流变液实现可变阻尼，相比被动减振器只有单一特性，它可以在很宽阻尼力窗口内产生大量力学特性。\n\n可变阻尼的意义不是简单“高级”，而是让一台车拥有更多性格。它可以低速软、高速稳；直线舒适、过弯支撑；满载不塌、空载不颠。尤其对新能源车来说，因为车重大、扭矩强、车身姿态变化明显，可变阻尼能明显改善整车动态。\n\n\n八、空气悬挂：不只是“软”，更重要是高度和姿态控制\n空气悬挂用空气弹簧代替传统螺旋弹簧。它的优势不是单纯更软，而是可以调节车身高度和弹簧刚度。\n\n高速时车身降低，可以减少风阻、提升稳定性；通过烂路时车身升高，可以增加通过性；满载时空气悬挂可以自动补偿高度，避免车尾下沉。Continental 关于空气悬挂的资料也提到，空气悬挂可在不同驾驶和负载条件下保证动态性能与舒适性，并在重载车辆中提升安全性。\n\n高端新能源车喜欢空气悬挂，原因很直接：车重大，电池重，乘坐舒适性要求高，用户又希望有运动、舒适、越野、高速等多种模式。空气悬挂加 CDC，便可以同时调车高和阻尼。\n\n但空气悬挂不是没有缺点。它结构复杂，有空气弹簧、气泵、储气罐、阀体、高度传感器、控制单元。时间久了可能出现气囊老化、漏气、气泵故障，维修成本比普通螺旋弹簧高。买车时看到“空气悬挂”不能只兴奋，也要考虑长期维护。\n\n\n九、主动悬挂：从机械底盘走向软件底盘\n更进一步是主动悬挂。普通悬挂是被动响应路面，可变阻尼只是改变“抑制振动”的力度；主动悬挂则可以主动对车轮或车身施加力。\n\n奔驰 E-ACTIVE BODY CONTROL 是典型例子。奔驰官方用户手册介绍其为带有可变阻尼的电液悬挂系统，用于改善乘坐舒适性，并且具有全车高度控制功能。 这类系统可以在过弯、刹车、加速、颠簸路面中主动控制车身姿态，理论效果很好，但成本和复杂度都高。\n\n中国新能源车也在快速推进“智能底盘”。比如蔚来 ET7 官方信息提到，其智能底盘控制器集成冗余驻车制动和空气悬挂控制，并支持底盘舒适性与操控性的整体调校和 FOTA 升级。 这说明底盘正在从传统机械系统变成“机械 + 传感器 + 控制器 + 软件算法”的系统。\n\n未来底盘调校不再只是工程师换弹簧、换减振器、调衬套，也会包括软件版本、传感器融合、路面预瞄、悬挂域控制、制动与转向协同。\n\n\n十、燃油车和新能源车悬挂的核心区别\n可以把差异概括成几句话。\n\n燃油车底盘更多是在传统机械平台中做平衡：发动机、变速箱、油箱、排气系统决定空间布局，悬挂结构要在成本、可靠性和驾驶感之间折中。它的优势是技术成熟，调校经验丰富，重量相对可控。\n\n新能源车底盘则是围绕电池、电机和电子控制重新设计。它重心低、扭矩大、重量高、空间布局更自由，也更依赖软件控制。它有机会用更复杂的多连杆、铝合金件、空气悬挂、可变阻尼和主动控制来提升表现；但如果调校不好，也容易出现“低速硬、高速晃”“车重压不住”“滤振生硬”“衬套负担大”等问题。\n\n所以新能源车并不是天生底盘好，燃油车也不是必然落后。新能源车给了工程师更大的架构自由度，但也提出了更高的重量管理和控制要求。\n\n\n十一、如何判断一辆车的底盘悬挂是否高级？\n不要只看宣传词，要看几个关键点。\n\n第一，看前后悬挂结构。前麦弗逊、后扭力梁不一定差，但上限有限；前双叉臂、后多连杆通常有更高动态潜力，但也要看调校。\n\n第二，看材料。铝合金控制臂、铝合金转向节、铝合金副车架有助于减轻重量，特别是簧下质量。但材料只是基础，不等于最终体验。\n\n第三，看衬套和副车架。很多车宣传多连杆，却在衬套上省成本，结果滤振和稳定性一般。真正好的底盘，细碎震动不会硬传进车身，急加速和刹车时后桥也不会松散。\n\n第四，看减振器类型。普通被动减振器靠一次性标定；CDC 可变阻尼能适应更多工况；空气悬挂加 CDC 更容易兼顾舒适和姿态；主动悬挂则进一步进入高成本智能控制领域。\n\n第五，看整车重量与轮胎匹配。新能源车很重，如果轮胎、弹簧、减振器、控制臂强度和制动系统不匹配，就会出现“参数很好，开起来一般”的情况。\n\n第六，看实际道路表现。好底盘不是试驾场绕桩漂亮，而是在高速接缝、连续起伏、弯中破损路面、紧急变线、满载刹车时仍然稳定。\n\n结语：好底盘不是堆料，而是系统工程\n底盘悬挂的发展，从早期的钢制结构、机械弹簧、固定阻尼，逐渐走向铝合金轻量化、多连杆几何控制、空气弹簧、可变阻尼和主动悬挂。燃油车时代，底盘更多依赖机械结构和工程师经验；新能源时代，电池、电机和软件控制让底盘进入新的阶段。\n\n但不管技术怎么变，悬挂的根本目标没有变：让轮胎稳稳贴地，让车身少受打扰，让驾驶员有信心，让乘客不难受。\n\n所以，一辆车的底盘好不好，不能只看“是不是多连杆”“是不是铝合金”“有没有空气悬挂”。真正高级的底盘，是材料、结构、几何、阻尼、衬套、车身刚度、轮胎和软件控制共同完成的一套平衡。燃油车有燃油车的成熟机械功底，新能源车有新能源车的架构自由和智能控制潜力。谁更好，最终不取决于宣传词，而取决于工程完成度。', 'admin', '2026-05-29 22:52:25');
INSERT INTO `t_news` VALUES (7, '为什么奥迪四驱那么强？原理一文解释清楚！', '大家仔细看一下，如今汽车使用四驱的概率是越来越高了，甚至连一些10万级别的新车上也都采用了四驱设计。严格意义上来说，这类四驱其实就是前后两个电机，同时提供动力系统，不就是四驱吗？事实上，说到四驱我们必须要提到奥迪。今天我们就来介绍一下奥迪的四驱为什么这么强。\n\n\n我们印象中的奥迪应该搭载的就是quattro，也就是我们熟知的传统全时四驱。这套系统为什么这么强呢？因为采用的是纯机械的中央差速器，比如托森差速器。这套四驱的逻辑就是不需要任何传感器或者电脑介入，因为动力始终是分配在四个轮子上的，随时随地都是四驱，所以响应速度比较快，而且在过弯以及湿滑路面的稳定性更高。当然了，这样设计带来的不足就是油耗始终偏高。但是奥迪也有其聪明的做法，就是这类传统的全时四驱一般只搭载在大排量或者高价格的新车上，而这类新车的用户更多考虑的是四驱操控性，而非油耗，因为这类高价值的用户对燃油经济性并不是特别的敏感。\n\n\n其实奥迪还有一款叫做智能四驱，也就是我们并不太熟知的Quattro ultra，翻译过来就是智能四驱（但是之前奥迪宣传不会这么详细，一般来说就是告诉用户这是quattro），这类四驱往往使用在一些中低档的奥迪车型上。为什么叫做智能四驱？因为是带系统模块的，在日常行驶过程中就是前驱，但如果进入了坡道或者加速，或者是雨天，还有就是车主自动切换回四驱模式，那么可以在30毫秒内迅速变成四轮驱动，不仅安全性不错，而且也有着不错的燃油经济性。不过就硬核程度，显然不如全时四驱，但是也有自己的优势，那就是成本方面更加可控。\n\n\n进入电动汽车时代，奥迪也在研发全新一代的电动quattro，这套全新的四驱主要使用在电动汽车上，所以这类新车一般会有两个或者三个电机同时驱动，每个轴可以进行驱动，这也是我们自主品牌的新车最喜欢的配置！其实这套电子四驱是双电机诞生的衍生物，所以很多电车搭载了，甚至不需要专门去研发就自带了。\n\n简单来说，奥迪的四驱为什么能够这么强，其实主要还是响应速度快，而且能够自动将动力尽可能输送到车轮上，在雪天或者湿滑路面，起步更快，也更加安全。当然了，随着电动汽车的电子四驱快速的发展，其实愿意聊奥迪四驱的网友数量变少了，这也说明原先高门槛的设计正在慢慢变成了平价车上的标配！而奥迪之前的核心优势也在慢慢瓦解了！', 'admin', '2026-05-29 22:53:32');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
  `user_id` int(20) NOT NULL COMMENT '用户ID，关联t_user表',
  `address_id` int(20) NOT NULL COMMENT '收货地址ID，关联t_address表',
  `total_amount` decimal(10, 2) NOT NULL COMMENT '订单总金额',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '订单状态：1-已支付，2-已取消',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_order_no`(`order_no`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_address_id`(`address_id`) USING BTREE,
  CONSTRAINT `fk_order_address` FOREIGN KEY (`address_id`) REFERENCES `t_address` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (1, '2026051202261260A50F', 2, 5, 33.90, 2, '');
INSERT INTO `t_order` VALUES (2, '2026051202301176B41D', 2, 5, 33.90, 2, '');
INSERT INTO `t_order` VALUES (3, '20260512023021516622', 2, 5, 33.90, 2, '');
INSERT INTO `t_order` VALUES (4, '202605120247466F302A', 2, 5, 11.30, 2, '');
INSERT INTO `t_order` VALUES (5, '202605120248094605AF', 2, 5, 100.70, 1, '');
INSERT INTO `t_order` VALUES (6, '20260512084142BB0BAF', 2, 7, 33.90, 1, '');
INSERT INTO `t_order` VALUES (7, '2026051217095185BBF7', 8, 8, 150000.00, 1, '');
INSERT INTO `t_order` VALUES (8, '20260512171007D2F680', 8, 8, 113.00, 2, '');
INSERT INTO `t_order` VALUES (9, '20260514170638082125', 2, 5, 100.00, 1, '');
INSERT INTO `t_order` VALUES (10, '2026051417064213E9A2', 2, 5, 100.00, 1, '');
INSERT INTO `t_order` VALUES (11, '202605141706513A8162', 2, 7, 10.00, 1, '');
INSERT INTO `t_order` VALUES (12, '20260514220547B5DD47', 2, 5, 0.10, 1, '');
INSERT INTO `t_order` VALUES (15, '20260516031259B05FCB', 2, 5, 10.17, 1, '');
INSERT INTO `t_order` VALUES (16, '20260530141918FAA597', 2, 5, 45.20, 2, '123');
INSERT INTO `t_order` VALUES (17, '202605301419220230E4', 2, 5, 20.14, 1, '');

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '订单详情ID',
  `order_id` int(20) NOT NULL COMMENT '订单ID，关联t_order表',
  `product_id` int(20) NOT NULL COMMENT '商品ID，关联t_product表',
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称（冗余字段）',
  `product_price` decimal(10, 2) NOT NULL COMMENT '商品单价（冗余字段）',
  `quantity` int(11) NOT NULL COMMENT '购买数量',
  `subtotal` decimal(10, 2) NOT NULL COMMENT '小计金额',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE,
  CONSTRAINT `fk_order_item_order` FOREIGN KEY (`order_id`) REFERENCES `t_order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_order_item_product` FOREIGN KEY (`product_id`) REFERENCES `t_product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单详情表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_order_item
-- ----------------------------
INSERT INTO `t_order_item` VALUES (1, 1, 1, '玻璃水', 11.30, 3, 33.90);
INSERT INTO `t_order_item` VALUES (2, 2, 1, '玻璃水', 11.30, 3, 33.90);
INSERT INTO `t_order_item` VALUES (3, 3, 1, '玻璃水', 11.30, 3, 33.90);
INSERT INTO `t_order_item` VALUES (4, 4, 1, '玻璃水', 11.30, 1, 11.30);
INSERT INTO `t_order_item` VALUES (5, 5, 5, 'WRC汽车车贴', 10.07, 10, 100.70);
INSERT INTO `t_order_item` VALUES (6, 6, 1, '玻璃水', 11.30, 3, 33.90);
INSERT INTO `t_order_item` VALUES (7, 7, 4, 'EA888发动机', 150000.00, 1, 150000.00);
INSERT INTO `t_order_item` VALUES (8, 8, 1, '玻璃水', 11.30, 10, 113.00);
INSERT INTO `t_order_item` VALUES (9, 9, 7, '保时捷911(992 gt3rs)汽车模型', 100.00, 1, 100.00);
INSERT INTO `t_order_item` VALUES (10, 10, 7, '保时捷911(992 gt3rs)汽车模型', 100.00, 1, 100.00);
INSERT INTO `t_order_item` VALUES (11, 11, 6, '行车记录仪', 10.00, 1, 10.00);
INSERT INTO `t_order_item` VALUES (12, 12, 8, '1232123', 0.05, 2, 0.10);
INSERT INTO `t_order_item` VALUES (15, 15, 2, '全包围汽车脚垫', 10.17, 1, 10.17);
INSERT INTO `t_order_item` VALUES (16, 16, 1, '玻璃水', 11.30, 4, 45.20);
INSERT INTO `t_order_item` VALUES (17, 17, 5, 'WRC汽车车贴', 10.07, 2, 20.14);

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品详情描述',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `stock` int(11) NOT NULL DEFAULT 0 COMMENT '库存，默认0',
  `category_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属分类',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品封面图片',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：1上架 0下架，默认1',
  `sales` int(11) NULL DEFAULT 0 COMMENT '销量，默认0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '汽车用品商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES (1, '玻璃水', '强力去油，耐冷ccc', 11.30, 93, '汽车养护', '玻璃水.jpg', 1, 7);
INSERT INTO `t_product` VALUES (2, '全包围汽车脚垫', '防滑、耐磨、结实', 10.17, 96, '汽车养护', '全包围汽车脚垫.jpg', 1, 4);
INSERT INTO `t_product` VALUES (4, 'EA888发动机', 'EA888发动机是大众集团旗下中高级车型的主力机型，包括1.8L和2.0L两种排量，集缸内直喷、涡轮增压、可变气门正时等一系列先进技术于一身，凭借充足的低速扭矩，良好的燃油经济性以及一流的可靠性，得到市场的广泛认可。', 150000.00, 8, '汽车改装', 'EA888发动机.png', 1, 2);
INSERT INTO `t_product` VALUES (5, 'WRC汽车车贴', '帅气的拉花\n', 10.07, 0, '汽车饰品', 'WRC汽车车贴.jpeg', 0, 12);
INSERT INTO `t_product` VALUES (6, '行车记录仪', '111111111111', 10.00, 109, '汽车电子', '行车记录仪.jpeg', 1, 1);
INSERT INTO `t_product` VALUES (7, '保时捷911(992 gt3rs)汽车模型', '车型:保时捷 911 992 GT3 RS 厂商:迷你切 比例:1/64', 100.00, 138, '汽车文化', '保时捷911(992 gt3rs)汽车模型.jpeg', 1, 2);
INSERT INTO `t_product` VALUES (8, '倍耐力SCORPION™ ALL TERRAIN PLUS', '全地形胎、专为高性能电动及插电式混合动力车型而研制开发\n规格：LT245/70R16\n载重指数和速度级别：113T', 319.00, 9, '汽车改装', '倍耐力全地形胎.png', 1, 2);
INSERT INTO `t_product` VALUES (9, '倍耐力P ZERO™ CORSA (PZC4)', '规格：295/35R22 XL\n载重指数和速度级别：108Y', 1089.00, 20, '汽车改装', '倍耐力P ZERO™ CORSA (PZC4).png', 1, 0);
INSERT INTO `t_product` VALUES (10, '米其林PRIMACY 5 浩悦五代', '规格：245/45R18\n载重指数和速度级别：100W', 2234.00, 129, '汽车改装', '浩悦五代.png', 1, 0);
INSERT INTO `t_product` VALUES (11, '米其林CROSSCLIMATE 3 跨悦 3', '规格：225/50R17\n载重指数和速度级别：94W', 560.00, 33, '汽车改装', '跨越三.png', 1, 0);
INSERT INTO `t_product` VALUES (12, '德国马牌（Continental）汽车轮胎', '规格：205/60R16\n载重指数和速度级别：96V', 349.02, 10, '汽车改装', '德国马牌.png', 1, 0);
INSERT INTO `t_product` VALUES (13, '汽车玻璃清洗剂', '汽车玻璃去油膜清洁乳去除剂驱水光亮洁净防污油膜清洗剂汽车通用', 10.00, 10000, '汽车养护', '洗油膜.jpg', 1, 0);
INSERT INTO `t_product` VALUES (14, '龟牌（Turtle Wax）冰蜡', '白车黑车蜡新车水晶打蜡保护上光新能源去油膜镀膜划痕', 60.00, 100, '汽车养护', '打蜡试剂.png', 1, 0);
INSERT INTO `t_product` VALUES (15, '情侣鹿汽车摆件', '一路平安车内饰品个性创意可爱车头车载高档树脂鹿', 20.00, 20, '汽车饰品', '一路平安.png', 1, 0);
INSERT INTO `t_product` VALUES (16, '车载国旗党旗', '车载国旗五星红旗党旗汽车国旗摆件装饰用品金属国旗小红旗', 30.00, 100, '汽车饰品', '党旗国旗.png', 1, 0);
INSERT INTO `t_product` VALUES (17, '车载电子钟', '车载电子钟时间表汽车用多功能电子时钟夜光车内中控台温度计摆件', 5.00, 120, '汽车电子', '车载电子钟.png', 1, 0);
INSERT INTO `t_product` VALUES (18, '手机支架', '车载手机支架', 10.00, 30, '汽车饰品', '手机支架.png', 1, 0);
INSERT INTO `t_product` VALUES (19, '汽车大百科全书', '汽车大百科全书儿童全套注音小百科男孩酷车揭秘经典汽车视觉绘本幼儿科学认知科普读物3-6-8-9-12岁小学生书籍', 10.00, 34, '汽车文化', '汽车百科书.png', 1, 0);
INSERT INTO `t_product` VALUES (20, '纽曼汽车载电瓶应急启动电源', '纽曼汽车载电瓶应急启动电源12V充电宝启动器车辆打火搭电宝神器 [新手防反接] S400L旗舰版', 100.00, 10, '汽车电子', '汽车电瓶启动器.png', 1, 0);
INSERT INTO `t_product` VALUES (21, 'AMG汽车模型', '车型：梅赛德斯奔驰AMG GT 202', 100.00, 10, '汽车文化', 'amggt.png', 1, 0);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'www', 'www123', '17789892034', 'www@outlook.com');
INSERT INTO `t_user` VALUES (2, 'sy', 'sy123', '12344457789', 'sy@163.com');
INSERT INTO `t_user` VALUES (3, 'qwe', 'qwe123', '12345678900', 'qwe@qwe.com');
INSERT INTO `t_user` VALUES (7, 'xy', 'xy123', '11123454678', '123@123.com');
INSERT INTO `t_user` VALUES (8, 'lfz', '123123', '13145678956', '');

-- ----------------------------
-- Table structure for t_user_balance
-- ----------------------------
DROP TABLE IF EXISTS `t_user_balance`;
CREATE TABLE `t_user_balance`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '余额记录ID',
  `user_id` int(20) NOT NULL COMMENT '用户ID，关联t_user表',
  `balance` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '用户余额',
  `frozen_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '冻结金额',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_balance_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户余额表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user_balance
-- ----------------------------
INSERT INTO `t_user_balance` VALUES (1, 2, 1009816.48, 375.01);
INSERT INTO `t_user_balance` VALUES (2, 8, 851499.99, 150000.00);
INSERT INTO `t_user_balance` VALUES (3, 7, 0.00, 0.00);

SET FOREIGN_KEY_CHECKS = 1;
