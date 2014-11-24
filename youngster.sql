-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

--  -----------------------------------------------------
-- Schema youngster
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema youngster
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `youngster` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`freeboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`freeboard` (
  `id` INT NOT NULL,
  `user` VARCHAR(45) NOT NULL,
  `head` VARCHAR(45) NOT NULL,
  `contents` VARCHAR(45) NOT NULL,
  `timestamp` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

USE `youngster` ;

-- -----------------------------------------------------
-- Table `youngster`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `youngster`.`user` (
  `id` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
