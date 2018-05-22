CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `test`;
DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
    `id` int(11) NOT NULL auto_increment,
    `name` varchar(255) NOT NULL,
    `description` varchar(1000) default NULL,
    `price` decimal(10,0) NOT NULL,
    PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
