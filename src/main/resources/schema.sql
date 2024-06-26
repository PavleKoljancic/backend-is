-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema eTicket
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema eTicket
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `eTicket` ;
USE `eTicket` ;

-- -----------------------------------------------------
-- Table `eTicket`.`PIN_USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`PIN_USER` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Pin` CHAR(6) NOT NULL,
  `Firstname` VARCHAR(45) NOT NULL,
  `Lastname` VARCHAR(45) NOT NULL,
  `isActive` TINYINT NOT NULL DEFAULT 1,
  `JMB` CHAR(13) NOT NULL,
  `Password` CHAR(72) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Pin_UNIQUE` (`Pin` ASC) VISIBLE,
  UNIQUE INDEX `JMB_UNIQUE` (`JMB` ASC) VISIBLE,
  INDEX `NAME_INDEX` (`Firstname` ASC, `Lastname` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`TRANSPORTER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`TRANSPORTER` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(225) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`DRIVER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`DRIVER` (
  `PIN_USER_Id` INT NOT NULL AUTO_INCREMENT,
  `TRANSPORTER_Id` INT NOT NULL,
  INDEX `fk_DRIVER_PIN_USER_idx` (`PIN_USER_Id` ASC) VISIBLE,
  PRIMARY KEY (`PIN_USER_Id`),
  INDEX `fk_DRIVER_TRANSPORTER1_idx` (`TRANSPORTER_Id` ASC) VISIBLE,
  CONSTRAINT `fk_DRIVER_PIN_USER`
    FOREIGN KEY (`PIN_USER_Id`)
    REFERENCES `eTicket`.`PIN_USER` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DRIVER_TRANSPORTER1`
    FOREIGN KEY (`TRANSPORTER_Id`)
    REFERENCES `eTicket`.`TRANSPORTER` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`TICKET_CONTROLLER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`TICKET_CONTROLLER` (
  `PIN_USER_Id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`PIN_USER_Id`),
  CONSTRAINT `fk_TICKET_CONTROLLER_PIN_USER1`
    FOREIGN KEY (`PIN_USER_Id`)
    REFERENCES `eTicket`.`PIN_USER` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`TERMINAL ACTIVATION REQUEST`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`TERMINAL ACTIVATION REQUEST` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `SerialNumber` VARCHAR(200) NOT NULL,
  `TRANSPORTER_Id` INT NOT NULL,
  `Processed` TINYINT NOT NULL DEFAULT 0,
  INDEX `fk_TERMINAL ACTIVATION REQUEST_TRANSPORTER1_idx` (`TRANSPORTER_Id` ASC) VISIBLE,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC) VISIBLE,
  CONSTRAINT `fk_TERMINAL ACTIVATION REQUEST_TRANSPORTER1`
    FOREIGN KEY (`TRANSPORTER_Id`)
    REFERENCES `eTicket`.`TRANSPORTER` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`TERMINAL`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`TERMINAL` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `TRANSPORTER_Id` INT NOT NULL,
  `isActive` TINYINT NOT NULL DEFAULT 1,
  `TERMINAL ACTIVATION REQUEST_Id` INT NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_TERMINAL_TRANSPORTER1_idx` (`TRANSPORTER_Id` ASC) VISIBLE,
  INDEX `fk_TERMINAL_TERMINAL ACTIVATION REQUEST1_idx` (`TERMINAL ACTIVATION REQUEST_Id` ASC) VISIBLE,
  CONSTRAINT `fk_TERMINAL_TRANSPORTER1`
    FOREIGN KEY (`TRANSPORTER_Id`)
    REFERENCES `eTicket`.`TRANSPORTER` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TERMINAL_TERMINAL ACTIVATION REQUEST1`
    FOREIGN KEY (`TERMINAL ACTIVATION REQUEST_Id`)
    REFERENCES `eTicket`.`TERMINAL ACTIVATION REQUEST` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`ROUTE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`ROUTE` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `TRANSPORTER_Id` INT NOT NULL,
  `isActive` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`Id`),
  INDEX `fk_ROUTE_TRANSPORTER1_idx` (`TRANSPORTER_Id` ASC) VISIBLE,
  INDEX `NAME_INDEx` (`Name` ASC) VISIBLE,
  CONSTRAINT `fk_ROUTE_TRANSPORTER1`
    FOREIGN KEY (`TRANSPORTER_Id`)
    REFERENCES `eTicket`.`TRANSPORTER` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`ROUTE_HISTORY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`ROUTE_HISTORY` (
  `FromDateTime` DATETIME NOT NULL,
  `ToDateTime` TIMESTAMP NULL,
  `ROUTE_Id` INT NOT NULL,
  `TERMINAL_Id` INT NOT NULL,
  `DRIVER_PIN_USER_Id` INT NOT NULL,
  PRIMARY KEY (`FromDateTime`, `ROUTE_Id`, `TERMINAL_Id`),
  INDEX `fk_ROUTE_HISTORY_ROUTE1_idx` (`ROUTE_Id` ASC) VISIBLE,
  INDEX `fk_ROUTE_HISTORY_TERMINAL1_idx` (`TERMINAL_Id` ASC) VISIBLE,
  INDEX `fk_ROUTE_HISTORY_DRIVER1_idx` (`DRIVER_PIN_USER_Id` ASC) VISIBLE,
  CONSTRAINT `fk_ROUTE_HISTORY_ROUTE1`
    FOREIGN KEY (`ROUTE_Id`)
    REFERENCES `eTicket`.`ROUTE` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ROUTE_HISTORY_TERMINAL1`
    FOREIGN KEY (`TERMINAL_Id`)
    REFERENCES `eTicket`.`TERMINAL` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ROUTE_HISTORY_DRIVER1`
    FOREIGN KEY (`DRIVER_PIN_USER_Id`)
    REFERENCES `eTicket`.`DRIVER` (`PIN_USER_Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`USER` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `PictureHash` BINARY(64) NULL DEFAULT NULL,
  `Email` VARCHAR(50) NOT NULL,
  `Firstname` VARCHAR(20) NOT NULL,
  `Lastname` VARCHAR(20) NOT NULL,
  `PasswordHash` CHAR(72) NOT NULL,
  `Credit` DECIMAL(8,2) NOT NULL DEFAULT 0.0,
  `UserKey` CHAR(128) NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE,
  INDEX `NAME_INDEX` (`Firstname` ASC, `Lastname` ASC) VISIBLE,
  INDEX `USER_KEY_INDEX` (`Id` ASC, `UserKey` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`TRANSACTION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`TRANSACTION` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Amount` DECIMAL(7,2) NOT NULL,
  `DateAndTime` DATETIME NOT NULL,
  `USER_Id` INT NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_TRANSACTION_USER1_idx` (`USER_Id` ASC) VISIBLE,
  INDEX `DATE_TIME_INDEX` (`DateAndTime` ASC) VISIBLE,
  CONSTRAINT `fk_TRANSACTION_USER1`
    FOREIGN KEY (`USER_Id`)
    REFERENCES `eTicket`.`USER` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`SCAN_INTERACTION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`SCAN_INTERACTION` (
  `ROUTE_HISTORY_FromDateTime` DATETIME NOT NULL,
  `ROUTE_HISTORY_ROUTE_Id` INT NOT NULL,
  `ROUTE_HISTORY_TERMINAL_Id` INT NOT NULL,
  `USER_Id` INT NOT NULL,
  `Time` DATETIME NOT NULL,
  `TRANSACTION_Id` INT NOT NULL,
  PRIMARY KEY (`USER_Id`, `ROUTE_HISTORY_FromDateTime`, `ROUTE_HISTORY_ROUTE_Id`, `ROUTE_HISTORY_TERMINAL_Id`, `Time`),
  INDEX `fk_SCAN_INTERACTION_USER1_idx` (`USER_Id` ASC) VISIBLE,
  INDEX `fk_SCAN_INTERACTION_TRANSACTION1_idx` (`TRANSACTION_Id` ASC) VISIBLE,
  CONSTRAINT `fk_SCAN_INTERACTION_ROUTE_HISTORY1`
    FOREIGN KEY (`ROUTE_HISTORY_FromDateTime` , `ROUTE_HISTORY_ROUTE_Id` , `ROUTE_HISTORY_TERMINAL_Id`)
    REFERENCES `eTicket`.`ROUTE_HISTORY` (`FromDateTime` , `ROUTE_Id` , `TERMINAL_Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SCAN_INTERACTION_USER1`
    FOREIGN KEY (`USER_Id`)
    REFERENCES `eTicket`.`USER` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SCAN_INTERACTION_TRANSACTION1`
    FOREIGN KEY (`TRANSACTION_Id`)
    REFERENCES `eTicket`.`TRANSACTION` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`ADMIN`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`ADMIN` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Email` VARCHAR(50) NOT NULL,
  `Firstname` VARCHAR(20) NOT NULL,
  `Lastname` VARCHAR(20) NOT NULL,
  `PasswordHash` CHAR(72) NOT NULL,
  `isActive` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`SUPERVISOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`SUPERVISOR` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `TRANSPORTER_Id` INT NOT NULL,
  `Email` VARCHAR(50) NOT NULL,
  `Firstname` VARCHAR(20) NOT NULL,
  `Lastname` VARCHAR(20) NOT NULL,
  `PasswordHash` CHAR(72) NOT NULL,
  `isActive` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`Id`),
  INDEX `fk_SUPERVISOR_TRANSPORTER1_idx` (`TRANSPORTER_Id` ASC) VISIBLE,
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE,
  INDEX `NAME_INDEX` (`Firstname` ASC, `Lastname` ASC) VISIBLE,
  CONSTRAINT `fk_SUPERVISOR_TRANSPORTER1`
    FOREIGN KEY (`TRANSPORTER_Id`)
    REFERENCES `eTicket`.`TRANSPORTER` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`TICKET_TYPE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`TICKET_TYPE` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `NeedsDocumentaion` TINYINT NOT NULL,
  `Cost` DECIMAL(7,2) NOT NULL,
  `inUse` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`ACCEPTED`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`ACCEPTED` (
  `TRANSPORTER_Id` INT NOT NULL,
  `TICKET_TYPE_Id` INT NOT NULL,
  INDEX `fk_TRANSPORTER_has_TICKET_TYPE_TICKET_TYPE1_idx` (`TICKET_TYPE_Id` ASC) VISIBLE,
  INDEX `fk_TRANSPORTER_has_TICKET_TYPE_TRANSPORTER1_idx` (`TRANSPORTER_Id` ASC) VISIBLE,
  PRIMARY KEY (`TICKET_TYPE_Id`, `TRANSPORTER_Id`),
  CONSTRAINT `fk_TRANSPORTER_has_TICKET_TYPE_TRANSPORTER1`
    FOREIGN KEY (`TRANSPORTER_Id`)
    REFERENCES `eTicket`.`TRANSPORTER` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TRANSPORTER_has_TICKET_TYPE_TICKET_TYPE1`
    FOREIGN KEY (`TICKET_TYPE_Id`)
    REFERENCES `eTicket`.`TICKET_TYPE` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`AMOUNT_TICKET`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`AMOUNT_TICKET` (
  `TICKET_TYPE_Id` INT NOT NULL,
  `Amount` INT NOT NULL,
  INDEX `fk_AMOUNT_TICKET_TICKET_TYPE1_idx` (`TICKET_TYPE_Id` ASC) VISIBLE,
  PRIMARY KEY (`TICKET_TYPE_Id`),
  CONSTRAINT `fk_AMOUNT_TICKET_TICKET_TYPE1`
    FOREIGN KEY (`TICKET_TYPE_Id`)
    REFERENCES `eTicket`.`TICKET_TYPE` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`PERIODIC_TICKET`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`PERIODIC_TICKET` (
  `TICKET_TYPE_Id` INT NOT NULL,
  `ValidFor` INT NOT NULL,
  PRIMARY KEY (`TICKET_TYPE_Id`),
  CONSTRAINT `fk_PERIODIC_TICKET_TICKET_TYPE1`
    FOREIGN KEY (`TICKET_TYPE_Id`)
    REFERENCES `eTicket`.`TICKET_TYPE` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`DOCUMENT_TYPE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`DOCUMENT_TYPE` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `ValidFromDate` DATE NOT NULL,
  `ValidUntilDate` DATE NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `NAME_VALID_FROM` (`Name` ASC, `ValidFromDate` ASC) VISIBLE,
  INDEX `SEARCH_INDEX` (`Name` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`DOCUMENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`DOCUMENT` (
  `USER_Id` INT NOT NULL,
  `DOCUMENT_TYPE_Id` INT NOT NULL,
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Approved` TINYINT NULL,
  `Comment` VARCHAR(300) NULL,
  `SUPERVISOR_Id` INT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_DOCUMENT_DOCUMENT_TYPE1_idx` (`DOCUMENT_TYPE_Id` ASC) VISIBLE,
  INDEX `fk_USER_id` (`USER_Id` ASC) VISIBLE,
  INDEX `fk_DOCUMENT_SUPERVISOR1_idx` (`SUPERVISOR_Id` ASC) VISIBLE,
  CONSTRAINT `fk_DOCUMENT_USER1`
    FOREIGN KEY (`USER_Id`)
    REFERENCES `eTicket`.`USER` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DOCUMENT_DOCUMENT_TYPE1`
    FOREIGN KEY (`DOCUMENT_TYPE_Id`)
    REFERENCES `eTicket`.`DOCUMENT_TYPE` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DOCUMENT_SUPERVISOR1`
    FOREIGN KEY (`SUPERVISOR_Id`)
    REFERENCES `eTicket`.`SUPERVISOR` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`TICKET_REQUEST`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`TICKET_REQUEST` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `DateTime` DATETIME NULL,
  `USER_Id` INT NOT NULL,
  `TICKET_TYPE_Id` INT NOT NULL,
  `DOCUMENT_Id` INT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_TICKET_REQUEST_USER1_idx` (`USER_Id` ASC) VISIBLE,
  INDEX `fk_TICKET_REQUEST_TICKET_TYPE1_idx` (`TICKET_TYPE_Id` ASC) VISIBLE,
  INDEX `fk_TICKET_REQUEST_DOCUMENT1_idx` (`DOCUMENT_Id` ASC) VISIBLE,
  CONSTRAINT `fk_TICKET_REQUEST_USER1`
    FOREIGN KEY (`USER_Id`)
    REFERENCES `eTicket`.`USER` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TICKET_REQUEST_TICKET_TYPE1`
    FOREIGN KEY (`TICKET_TYPE_Id`)
    REFERENCES `eTicket`.`TICKET_TYPE` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TICKET_REQUEST_DOCUMENT1`
    FOREIGN KEY (`DOCUMENT_Id`)
    REFERENCES `eTicket`.`DOCUMENT` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`TICKET_REQUEST_RESPONSE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`TICKET_REQUEST_RESPONSE` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Comment` VARCHAR(500) NULL,
  `TICKET_REQUEST_Id` INT NOT NULL,
  `Approved` TINYINT NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC) VISIBLE,
  INDEX `fk_TICKET_REQUEST_RESPONSE_TICKET_REQUEST1_idx` (`TICKET_REQUEST_Id` ASC) VISIBLE,
  CONSTRAINT `fk_TICKET_REQUEST_RESPONSE_TICKET_REQUEST1`
    FOREIGN KEY (`TICKET_REQUEST_Id`)
    REFERENCES `eTicket`.`TICKET_REQUEST` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`TICKET_TRANSACTION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`TICKET_TRANSACTION` (
  `TRANSACTION_Id` INT NOT NULL,
  `TICKET_REQUEST_RESPONSE_Id` INT NOT NULL,
  PRIMARY KEY (`TRANSACTION_Id`),
  INDEX `fk_TICKET_TRANSACTION_TICKET_REQUEST_RESPONSE1_idx` (`TICKET_REQUEST_RESPONSE_Id` ASC) VISIBLE,
  CONSTRAINT `fk_TICKET_TRANSACTION_TRANSACTION1`
    FOREIGN KEY (`TRANSACTION_Id`)
    REFERENCES `eTicket`.`TRANSACTION` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TICKET_TRANSACTION_TICKET_REQUEST_RESPONSE1`
    FOREIGN KEY (`TICKET_REQUEST_RESPONSE_Id`)
    REFERENCES `eTicket`.`TICKET_REQUEST_RESPONSE` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`CREDIT_TRANSACTION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`CREDIT_TRANSACTION` (
  `TRANSACTION_Id` INT NOT NULL,
  `SUPERVISOR_Id` INT NOT NULL,
  PRIMARY KEY (`TRANSACTION_Id`),
  INDEX `fk_CREDIT TRANSACTION_SUPERVISOR1_idx` (`SUPERVISOR_Id` ASC) VISIBLE,
  CONSTRAINT `fk_CREDIT TRANSACTION_TRANSACTION1`
    FOREIGN KEY (`TRANSACTION_Id`)
    REFERENCES `eTicket`.`TRANSACTION` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CREDIT TRANSACTION_SUPERVISOR1`
    FOREIGN KEY (`SUPERVISOR_Id`)
    REFERENCES `eTicket`.`SUPERVISOR` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`SCAN_TRANSACTION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`SCAN_TRANSACTION` (
  `TRANSACTION_Id` INT NOT NULL,
  `TERMINAL_Id` INT NOT NULL,
  PRIMARY KEY (`TRANSACTION_Id`),
  INDEX `fk_SCAN_TRANSACTION_TERMINAL1_idx` (`TERMINAL_Id` ASC) VISIBLE,
  CONSTRAINT `fk_SCAN_TRANSACTION_TRANSACTION1`
    FOREIGN KEY (`TRANSACTION_Id`)
    REFERENCES `eTicket`.`TRANSACTION` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SCAN_TRANSACTION_TERMINAL1`
    FOREIGN KEY (`TERMINAL_Id`)
    REFERENCES `eTicket`.`TERMINAL` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`USER_TICKETS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`USER_TICKETS` (
  `USER_Id` INT NOT NULL,
  `TRANSACTION_Id` INT NOT NULL,
  `ValidUntilDate` DATETIME NULL,
  `CurrentAmount` MEDIUMINT NULL,
  `TICKET_TYPE_Id` INT NOT NULL,
  PRIMARY KEY (`TRANSACTION_Id`),
  INDEX `fk_USER_TICKETS_TICKET_TRANSACTION1_idx` (`TRANSACTION_Id` ASC) VISIBLE,
  INDEX `fk_USER_TICKETS_TICKET_TYPE1_idx` (`TICKET_TYPE_Id` ASC) VISIBLE,
  CONSTRAINT `fk_USER_TICKETS_USER1`
    FOREIGN KEY (`USER_Id`)
    REFERENCES `eTicket`.`USER` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_TICKETS_TICKET_TRANSACTION1`
    FOREIGN KEY (`TRANSACTION_Id`)
    REFERENCES `eTicket`.`TICKET_TRANSACTION` (`TRANSACTION_Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_TICKETS_TICKET_TYPE1`
    FOREIGN KEY (`TICKET_TYPE_Id`)
    REFERENCES `eTicket`.`TICKET_TYPE` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eTicket`.`TICKET_TYPE_ACCEPTS_DOCUMENT_TYPE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eTicket`.`TICKET_TYPE_ACCEPTS_DOCUMENT_TYPE` (
  `DOCUMENT_TYPE_Id` INT NOT NULL,
  `TICKET_TYPE_Id` INT NOT NULL,
  PRIMARY KEY (`DOCUMENT_TYPE_Id`, `TICKET_TYPE_Id`),
  INDEX `fk_DOCUMENT_TYPE_has_TICKET_TYPE_TICKET_TYPE1_idx` (`TICKET_TYPE_Id` ASC) VISIBLE,
  INDEX `fk_DOCUMENT_TYPE_has_TICKET_TYPE_DOCUMENT_TYPE1_idx` (`DOCUMENT_TYPE_Id` ASC) VISIBLE,
  CONSTRAINT `fk_DOCUMENT_TYPE_has_TICKET_TYPE_DOCUMENT_TYPE1`
    FOREIGN KEY (`DOCUMENT_TYPE_Id`)
    REFERENCES `eTicket`.`DOCUMENT_TYPE` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DOCUMENT_TYPE_has_TICKET_TYPE_TICKET_TYPE1`
    FOREIGN KEY (`TICKET_TYPE_Id`)
    REFERENCES `eTicket`.`TICKET_TYPE` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
