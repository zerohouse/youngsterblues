--------------------------------
-- Schema youngster
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `youngster` DEFAULT CHARACTER SET utf8 ;

USE `youngster` ;

-- -----------------------------------------------------
-- Table `youngster`.`user`
-- -----------------------------------------------------

CREATE TABLE `user` (
  `id` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


-- -----------------------------------------------------
-- Table `youngster`.`contents`
-- -----------------------------------------------------

CREATE TABLE `contents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `userid` varchar(45) NOT NULL,
  `head` varchar(45) NOT NULL,
  `content` text,
  `timestamp` datetime NOT NULL,
  PRIMARY KEY (`id`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8


