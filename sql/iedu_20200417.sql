-- --------------------------------------------------------
-- 主机:                           47.112.227.13
-- 服务器版本:                        5.7.29 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 iedu 的数据库结构
CREATE DATABASE IF NOT EXISTS `iedu` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `iedu`;

-- 导出  表 iedu.annex 结构
CREATE TABLE IF NOT EXISTS `annex` (
  `aid` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '附件id',
  `filename` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '附件名称',
  `url` text CHARACTER SET utf8 NOT NULL COMMENT '附件地址',
  `lid` int(8) unsigned zerofill NOT NULL COMMENT '所属课目',
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 正在导出表  iedu.annex 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `annex` DISABLE KEYS */;
/*!40000 ALTER TABLE `annex` ENABLE KEYS */;

-- 导出  表 iedu.chapter 结构
CREATE TABLE IF NOT EXISTS `chapter` (
  `chid` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '章节id',
  `title` varchar(30) CHARACTER SET utf8 NOT NULL COMMENT '章节标题',
  `description` text CHARACTER SET utf8 NOT NULL COMMENT '章节概要',
  `cid` int(8) unsigned zerofill NOT NULL COMMENT '所属课程',
  PRIMARY KEY (`chid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- 正在导出表  iedu.chapter 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
INSERT INTO `chapter` (`chid`, `title`, `description`, `cid`) VALUES
	(00000001, 'Spring体系介绍', '主要讲述Spring框架体系的发展史及其主要成员', 00000001),
	(00000002, 'Spring体系基础入门', '快速学会Spring框架体系组件的基本应用。', 00000001),
	(00000003, 'Spring体系高级教程', '深入介绍Spring框架体系组件的底层原理及调优算法。', 00000001);
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;

-- 导出  表 iedu.comment 结构
CREATE TABLE IF NOT EXISTS `comment` (
  `cmid` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `target_type` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '评论对象类型（课程、课目）',
  `target_id` int(8) unsigned zerofill NOT NULL COMMENT '评论对象id',
  `content` text CHARACTER SET utf8 NOT NULL COMMENT '评论内容',
  `creator` int(8) unsigned zerofill NOT NULL COMMENT '评论者',
  `reply_to` int(8) unsigned zerofill DEFAULT NULL COMMENT '回复对象（另一评论的id）',
  `create_date` datetime NOT NULL COMMENT '评论日期',
  PRIMARY KEY (`cmid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- 正在导出表  iedu.comment 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` (`cmid`, `target_type`, `target_id`, `content`, `creator`, `reply_to`, `create_date`) VALUES
	(00000001, 'lession', 00000001, '不错，支持支持。', 00000004, NULL, '2020-03-20 15:36:51'),
	(00000002, 'lession', 00000001, '日常打卡', 00000003, NULL, '2020-03-20 15:37:34'),
	(00000003, 'lession', 00000001, '感谢支持', 00000002, 00000001, '2020-03-20 15:38:05'),
	(00000004, 'lession', 00000001, 'iEdu源码仓库地址：<a href="https://github.com/shadL-17/iedu" target="_blank">github.com/shadL-17/iedu</a>', 00000002, NULL, '2020-04-15 17:35:39');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;

-- 导出  表 iedu.course 结构
CREATE TABLE IF NOT EXISTS `course` (
  `cid` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '课程id',
  `name` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '课程名称',
  `description` text CHARACTER SET utf8 NOT NULL COMMENT '课程介绍',
  `image` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '封面图地址',
  `type` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '标签（逗号分隔）',
  `status` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '更新中' COMMENT '课程状态（更新中/已完结）',
  `creator` int(8) unsigned zerofill NOT NULL COMMENT '创建者',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- 正在导出表  iedu.course 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` (`cid`, `name`, `description`, `image`, `type`, `status`, `creator`) VALUES
	(00000001, 'Spring框架 - 史上最全教程', 'Spring是Java EE的一种框架体系。其核心组件有Spring, SpringMVC, SpringDataJpa, SpringBoot, SpringCloud, SpringSecure等。', '/image/course-thumbnail/1.jpg', 'java,spring,计算机', '已完结', 00000002),
	(00000002, 'Python：从入门到精通', 'Python是一种跨平台的计算机程序设计语言。是一种面向对象的动态类型语言，最初被设计用于编写自动化脚本(shell)，随着版本的不断更新和语言新功能的添加，越多被用于独立的、大型项目的开发。', '/image/course-thumbnail/2.jpg', 'python,计算机', '更新中', 00000002),
	(00000003, 'Linux操作系统：零基础入门', 'Linux是一套免费开源和自由传播的类Unix操作系统，其以高性能、高安全性获得了全球用户的喜爱。', '/image/course-thumbnail/3.jpg', '计算机,操作系统,linux', '更新中', 00000002),
	(00000004, 'Java SE核心技术：零基础到精通', 'Java是一门面向对象编程语言，具有功能强大和简单易用两个特征。Java语言作为静态面向对象编程语言的代表，极好地实现了面向对象理论，允许程序员以优雅的思维方式进行复杂的编程。', '/image/course-thumbnail/4.jpg', 'java,计算机', '更新中', 00000002),
	(00000005, 'Unity游戏引擎 - 实战游戏项目', 'Unity是由Unity Technologies开发的一个让玩家轻松创建诸如三维视频游戏、建筑可视化、实时三维动画等类型互动内容的多平台的综合型游戏开发工具，是一个全面整合的专业游戏引擎。', '/image/course-thumbnail/5.jpg', 'Unity,计算机,游戏开发', '更新中', 00000002);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;

-- 导出  表 iedu.exam 结构
CREATE TABLE IF NOT EXISTS `exam` (
  `eid` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '测验id',
  `title` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '章节测验' COMMENT '测验标题',
  `chid` int(8) unsigned zerofill NOT NULL COMMENT '所属章节',
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- 正在导出表  iedu.exam 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
INSERT INTO `exam` (`eid`, `title`, `chid`) VALUES
	(00000001, '章节测验：Spring体系概念', 00000001),
	(00000002, '章节测验：Spring入门使用', 00000002),
	(00000003, '章节测验：Spring深层理论', 00000003);
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;

-- 导出  表 iedu.lession 结构
CREATE TABLE IF NOT EXISTS `lession` (
  `lid` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '课目id',
  `title` varchar(30) CHARACTER SET utf8 NOT NULL COMMENT '课目标题',
  `description` text CHARACTER SET utf8 NOT NULL COMMENT '课目概述',
  `vid` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '课目视频',
  `chid` int(8) unsigned zerofill NOT NULL COMMENT '所属章节',
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- 正在导出表  iedu.lession 的数据：~10 rows (大约)
/*!40000 ALTER TABLE `lession` DISABLE KEYS */;
INSERT INTO `lession` (`lid`, `title`, `description`, `vid`, `chid`) VALUES
	(00000001, '第1课：Spring概述', '概述：什么是Spring、为什么要用Spring、如何使用Spring', '/video/spring001.mp4', 00000001),
	(00000002, '第2课：Spring发展历程', '介绍Spring的发展历程', '/video/spring001.mp4', 00000001),
	(00000003, '第3课：Spring体系结构', '介绍Spring的体系结构', '/video/spring001.mp4', 00000001),
	(00000004, '第4课：Spring注解使用', '入门：服务层框架Spring中@Component, @Bean, @Autowared等常用注解的作用和使用情形。', '/video/spring001.mp4', 00000002),
	(00000005, '第5课：SpringMVC入门', '入门：应用层框架SpringMVC的使用', '/video/spring001.mp4', 00000002),
	(00000006, '第6课：SpringDataJpa使用', '入门：持久层框架SpringDataJpa的使用', '/video/spring001.mp4', 00000002),
	(00000007, '第7课：SpringBoot实战', '入门：第一个SpringBoot项目', '/video/spring001.mp4', 00000002),
	(00000008, '第8课：Spring的设计模式概念', '讲述耦合、解耦、工厂模式、IOC、AOP等概念', '/video/spring001.mp4', 00000003),
	(00000009, '第9课：Spring的底层原理', '讲述Spring容器的初始化、Bean的作用范围和声明周期', '/video/spring001.mp4', 00000003),
	(00000010, '第10课：Spring调优算法', '讲述Spring如何优化程序的处理能力和机器性能利用率', '/video/spring001.mp4', 00000003);
/*!40000 ALTER TABLE `lession` ENABLE KEYS */;

-- 导出  表 iedu.question 结构
CREATE TABLE IF NOT EXISTS `question` (
  `qid` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '题目id',
  `question` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '题目问题',
  `answer` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '题目答案',
  `option` text CHARACTER SET utf8 COMMENT '题目选项（分号分隔，空则为填空题）',
  `value` int(11) NOT NULL DEFAULT '10' COMMENT '题目分值',
  `eid` int(8) unsigned zerofill NOT NULL COMMENT '所属测验',
  PRIMARY KEY (`qid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- 正在导出表  iedu.question 的数据：~13 rows (大约)
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` (`qid`, `question`, `answer`, `option`, `value`, `eid`) VALUES
	(00000001, '下列Spring组件中，属于应用层框架的是：', 'Spring MVC', 'Spring AOP|Spring Data Jpa|Spring MVC|Spring Cloud', 10, 00000001),
	(00000002, '下列不属于Spring框架核心思想的是：', 'ORM(对象关系映射)', 'IOC(反向控制)|DI(依赖注入|AOP(面向切面编程)|ORM(对象关系映射)', 10, 00000001),
	(00000003, 'Spring框架体系中专注于持久层的组件是：', 'Spring Data Jpa', 'Spring Boot|Spring Data Jpa|Spring Security|Spring Admin', 10, 00000001),
	(00000004, '下列哪个注解用于注入Spring容器中的对象：', '@Autowired', '@Autowired|@GetMapping|@Component|@Value', 10, 00000002),
	(00000005, '注解@RequestParam 的作用是：', '声明请求参数以便于接收', '声明请求参数以便于接收|从配置文件获取数据为该变量赋值|将对象加入到Spring的IOC容器中|声明方法所需的参数列表', 10, 00000002),
	(00000006, '以下关于SpringMVC的使用，不正确的是：', 'SpringMVC是基于Action类处理的进阶设计', '可通过引入spring-boot-starter-web依赖使用springmvc|@PostMapping注解表示接口只能通过Post请求访问|SpringMVC是基于Action类处理的进阶设计|SpringMVC需要用到依赖注入的原理', 10, 00000002),
	(00000007, '使用SpringDataJpa，需在自定义的Repository类中继承什么接口：', 'JpaRepositroy', 'JpaRepositroy|JpaDataReader|JpaDataWriter|JpaDataHandler', 10, 00000002),
	(00000008, '以下哪些注解可将类对象加入IOC容器中：', '@Component&@Service&@Controller&@Repository', '@Component|@Service|@Controller|@Repository', 10, 00000002),
	(00000009, 'Spring声明式事务的特点是：', '原子性', '原子性|封装性|抽象性|可复用性', 10, 00000003),
	(00000010, '以下哪一项不是Bean的生命周期：', 'response', 'request|session|global-session|response', 10, 00000003),
	(00000011, '以下哪一项不是SpringCloud体系的组件：', 'Structs2', 'Structs2|Eureka|Feign|Zuul', 10, 00000003),
	(00000012, 'AOP的实现方式有：', 'CGLIB&SpringAOP', 'CGLIB|SpringAOP|RESTController|RestTemplate', 10, 00000003),
	(00000013, '有关Eureka组件正确的说法是：', 'Eureka主要作用是作为分布式项目中的服务注册中心&在分布式项目中必须首先启动Eureka服务，否则其他服务将无法互相访问', 'Eureka主要作用是作为分布式项目中的服务注册中心|Eureka主要作用是实现分布式项目中负载均衡的问题|在分布式项目中必须首先启动Eureka服务，否则其他服务将无法访问|在分布式项目中必须首先启动Eureka服务，否则其他服务将无法互相访问', 10, 00000003);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;

-- 导出  表 iedu.student_course 结构
CREATE TABLE IF NOT EXISTS `student_course` (
  `ucid` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `uid` int(8) unsigned zerofill NOT NULL COMMENT '用户id（学生）',
  `cid` int(8) unsigned zerofill NOT NULL COMMENT '课程id',
  `progress` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '进度',
  `score` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '成绩',
  PRIMARY KEY (`ucid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- 正在导出表  iedu.student_course 的数据：~6 rows (大约)
/*!40000 ALTER TABLE `student_course` DISABLE KEYS */;
INSERT INTO `student_course` (`ucid`, `uid`, `cid`, `progress`, `score`) VALUES
	(00000001, 00000003, 00000001, 2, 0),
	(00000002, 00000004, 00000001, 3, 0),
	(00000003, 00000005, 00000001, 0, 0),
	(00000004, 00000006, 00000001, 0, 0),
	(00000005, 00000013, 00000001, 0, 0),
	(00000006, 00000015, 00000001, 1, 0);
/*!40000 ALTER TABLE `student_course` ENABLE KEYS */;

-- 导出  表 iedu.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `uid` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET big5 NOT NULL,
  `password` varchar(20) NOT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'guest',
  `gender` varchar(5) CHARACTER SET big5 DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `contact` varchar(50) CHARACTER SET big5 DEFAULT NULL,
  `avatar` varchar(50) CHARACTER SET big5 NOT NULL DEFAULT '/image/user-avatar/default.jpg',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- 正在导出表  iedu.user 的数据：~11 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`uid`, `username`, `password`, `role`, `gender`, `birthday`, `contact`, `avatar`) VALUES
	(00000001, 'admin', 'asdasd', 'admin', NULL, NULL, NULL, '/image/user-avatar/default.jpg'),
	(00000002, 'shadow', 'qweqwe', 'teacher', '男', '1997-11-23', 'overthetop', '/image/user-avatar/default.jpg'),
	(00000003, 'moon', 'asdasd', 'student', '女', '2002-02-26', 'guangzhou', '/image/user-avatar/default.jpg'),
	(00000004, 'ryan', 'qqw', 'student', '男', '2000-05-01', '020-8356112', '/image/user-avatar/default.jpg'),
	(00000005, 'mike', '1234', 'student', NULL, NULL, NULL, '/image/user-avatar/default.jpg'),
	(00000006, 'john', '1234', 'student', NULL, NULL, NULL, '/image/user-avatar/default.jpg'),
	(00000007, 'sheldon', 'sed', 'teacher', '男', '1980-02-26', 'caltech', '/image/user-avatar/default.jpg'),
	(00000008, 'amy', 'zxczxc', 'teacher', NULL, NULL, NULL, '/image/user-avatar/default.jpg'),
	(00000013, 'morty', 'asdasd', 'student', NULL, NULL, NULL, '/image/user-avatar/default.jpg'),
	(00000014, 'rick', 'asdasd', 'teacher', NULL, NULL, NULL, '/image/user-avatar/default.jpg'),
	(00000015, 'jack', 'asdasd', 'student', NULL, NULL, NULL, '/image/user-avatar/default.jpg');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
