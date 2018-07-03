-- MySQL dump 10.13  Distrib 5.6.21, for Linux (x86_64)
--
-- Host: localhost    Database: openbrm_test
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account_type`
--

DROP TABLE IF EXISTS `account_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_type` (
  `id` int(11) NOT NULL,
  `credit_limit` decimal(22,10) DEFAULT NULL,
  `invoice_design` varchar(100) DEFAULT NULL,
  `invoice_delivery_method_id` int(11) DEFAULT NULL,
  `date_created` timestamp NULL DEFAULT NULL,
  `credit_notification_limit1` decimal(22,10) DEFAULT NULL,
  `credit_notification_limit2` decimal(22,10) DEFAULT NULL,
  `language_id` int(11) DEFAULT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `currency_id` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `main_subscript_order_period_id` int(11) NOT NULL,
  `next_invoice_day_of_period` int(11) NOT NULL,
  `notification_ait_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `account_type_main_subscription_period_FK` (`main_subscript_order_period_id`),
  KEY `account_type_currency_id_FK` (`currency_id`),
  KEY `account_type_language_id_FK` (`language_id`),
  KEY `invoice_delivery_method_id_FK` (`invoice_delivery_method_id`),
  KEY `account_type_entity_id_FK` (`entity_id`),
  CONSTRAINT `account_type_currency_id_FK` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`),
  CONSTRAINT `account_type_entity_id_FK` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`),
  CONSTRAINT `account_type_language_id_FK` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `account_type_main_subscription_period_FK` FOREIGN KEY (`main_subscript_order_period_id`) REFERENCES `order_period` (`id`),
  CONSTRAINT `invoice_delivery_method_id_FK` FOREIGN KEY (`invoice_delivery_method_id`) REFERENCES `invoice_delivery_method` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `account_type_price`
--

DROP TABLE IF EXISTS `account_type_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_type_price` (
  `account_type_id` int(11) NOT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `price_expiry_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ach`
--

DROP TABLE IF EXISTS `ach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ach` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `aba_routing` varchar(40) NOT NULL,
  `bank_account` varchar(60) NOT NULL,
  `account_type` int(11) NOT NULL,
  `bank_name` varchar(50) NOT NULL,
  `account_name` varchar(100) NOT NULL,
  `optlock` int(11) NOT NULL,
  `gateway_key` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ach_fk_1` (`user_id`),
  CONSTRAINT `ach_fk_1` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ageing_entity_step`
--

DROP TABLE IF EXISTS `ageing_entity_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ageing_entity_step` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL,
  `days` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  `retry_payment` smallint(6) NOT NULL DEFAULT '0',
  `suspend` smallint(6) NOT NULL DEFAULT '0',
  `send_notification` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `entity_step_days` (`entity_id`,`days`),
  CONSTRAINT `ageing_entity_step_fk_2` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset` (
  `id` int(11) NOT NULL,
  `identifier` varchar(200) NOT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status_id` int(11) NOT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `deleted` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `notes` varchar(1000) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `group_id` int(11) DEFAULT NULL,
  `order_line_id` int(11) DEFAULT NULL,
  `global` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `asset_fk_4` (`entity_id`),
  KEY `asset_fk_3` (`status_id`),
  KEY `asset_fk_1` (`item_id`),
  KEY `asset_fk_2` (`order_line_id`),
  CONSTRAINT `asset_fk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `asset_fk_2` FOREIGN KEY (`order_line_id`) REFERENCES `order_line` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `asset_fk_3` FOREIGN KEY (`status_id`) REFERENCES `asset_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `asset_fk_4` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_assignment`
--

DROP TABLE IF EXISTS `asset_assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_assignment` (
  `id` int(11) NOT NULL,
  `asset_id` int(11) NOT NULL,
  `order_line_id` int(11) NOT NULL,
  `start_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_datetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `asset_assignment_fk_1` (`asset_id`),
  KEY `asset_assignment_fk_2` (`order_line_id`),
  CONSTRAINT `asset_assignment_fk_1` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `asset_assignment_fk_2` FOREIGN KEY (`order_line_id`) REFERENCES `order_line` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_entity_map`
--

DROP TABLE IF EXISTS `asset_entity_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_entity_map` (
  `asset_id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  UNIQUE KEY `asset_entity_map_uc_1` (`asset_id`,`entity_id`),
  KEY `asset_entity_map_fk1` (`entity_id`),
  CONSTRAINT `asset_entity_map_fk1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `asset_entity_map_fk2` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_meta_field_map`
--

DROP TABLE IF EXISTS `asset_meta_field_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_meta_field_map` (
  `asset_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL,
  KEY `asset_meta_field_map_fk_1` (`asset_id`),
  KEY `asset_meta_field_map_fk_2` (`meta_field_value_id`),
  CONSTRAINT `asset_meta_field_map_fk_1` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `asset_meta_field_map_fk_2` FOREIGN KEY (`meta_field_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_reservation`
--

DROP TABLE IF EXISTS `asset_reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_reservation` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `creator_user_id` int(11) NOT NULL,
  `asset_id` int(11) NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `asset_reservation_end_date_index` (`end_date`),
  KEY `asset_reservation_fk_1` (`creator_user_id`),
  KEY `asset_reservation_fk_2` (`user_id`),
  KEY `asset_reservation_fk_3` (`asset_id`),
  CONSTRAINT `asset_reservation_fk_1` FOREIGN KEY (`creator_user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `asset_reservation_fk_2` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `asset_reservation_fk_3` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_status`
--

DROP TABLE IF EXISTS `asset_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_status` (
  `id` int(11) NOT NULL,
  `item_type_id` int(11) DEFAULT NULL,
  `is_default` int(11) NOT NULL,
  `is_order_saved` int(11) NOT NULL,
  `is_available` int(11) NOT NULL,
  `deleted` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  `is_internal` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `asset_status_fk_1` (`item_type_id`),
  CONSTRAINT `asset_status_fk_1` FOREIGN KEY (`item_type_id`) REFERENCES `item_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_transition`
--

DROP TABLE IF EXISTS `asset_transition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_transition` (
  `id` int(11) NOT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `previous_status_id` int(11) DEFAULT NULL,
  `new_status_id` int(11) NOT NULL,
  `asset_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `assigned_to_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `asset_transition_fk_1` (`assigned_to_id`),
  KEY `asset_transition_fk_2` (`user_id`),
  KEY `asset_transition_fk_3` (`asset_id`),
  KEY `asset_transition_fk_4` (`new_status_id`),
  KEY `asset_transition_fk_5` (`previous_status_id`),
  CONSTRAINT `asset_transition_fk_1` FOREIGN KEY (`assigned_to_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `asset_transition_fk_2` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `asset_transition_fk_3` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `asset_transition_fk_4` FOREIGN KEY (`new_status_id`) REFERENCES `asset_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `asset_transition_fk_5` FOREIGN KEY (`previous_status_id`) REFERENCES `asset_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `base_user`
--

DROP TABLE IF EXISTS `base_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `base_user` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `password` varchar(1024) DEFAULT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `language_id` int(11) DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL,
  `subscriber_status` int(11) DEFAULT '1',
  `currency_id` int(11) DEFAULT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_status_change` timestamp NULL DEFAULT NULL,
  `last_login` timestamp NULL DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `failed_attempts` int(11) NOT NULL DEFAULT '0',
  `optlock` int(11) NOT NULL,
  `change_password_date` date DEFAULT NULL,
  `encryption_scheme` int(11) NOT NULL,
  `account_locked_time` timestamp NULL DEFAULT NULL,
  `account_disabled_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_base_user_un` (`entity_id`,`user_name`),
  KEY `base_user_fk_5` (`currency_id`),
  KEY `base_user_fk_4` (`language_id`),
  KEY `base_user_fk_6` (`status_id`),
  CONSTRAINT `base_user_fk_3` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `base_user_fk_4` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `base_user_fk_5` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `base_user_fk_6` FOREIGN KEY (`status_id`) REFERENCES `user_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batch_job_execution`
--

DROP TABLE IF EXISTS `batch_job_execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batch_job_execution` (
  `job_execution_id` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `job_instance_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `exit_code` varchar(100) DEFAULT NULL,
  `exit_message` varchar(2500) DEFAULT NULL,
  `last_updated` timestamp NULL DEFAULT NULL,
  `job_configuration_location` varchar(2500) DEFAULT NULL,
  PRIMARY KEY (`job_execution_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batch_job_execution_context`
--

DROP TABLE IF EXISTS `batch_job_execution_context`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batch_job_execution_context` (
  `job_execution_id` bigint(20) NOT NULL,
  `short_context` varchar(2500) NOT NULL,
  `serialized_context` longtext,
  PRIMARY KEY (`job_execution_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batch_job_execution_params`
--

DROP TABLE IF EXISTS `batch_job_execution_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batch_job_execution_params` (
  `job_execution_id` int(11) NOT NULL,
  `type_cd` varchar(6) NOT NULL,
  `key_name` varchar(100) NOT NULL,
  `string_val` varchar(250) DEFAULT NULL,
  `date_val` datetime NULL DEFAULT NULL,
  `long_val` int(11) DEFAULT NULL,
  `double_val` decimal(17,0) DEFAULT NULL,
  `identifying` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batch_job_execution_seq`
--

DROP TABLE IF EXISTS `batch_job_execution_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batch_job_execution_seq` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batch_job_instance`
--

DROP TABLE IF EXISTS `batch_job_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batch_job_instance` (
  `job_instance_id` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `job_name` varchar(100) NOT NULL,
  `job_key` varchar(32) NOT NULL,
  PRIMARY KEY (`job_instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batch_job_seq`
--

DROP TABLE IF EXISTS `batch_job_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batch_job_seq` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batch_process_info`
--

DROP TABLE IF EXISTS `batch_process_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batch_process_info` (
  `id` int(11) NOT NULL,
  `process_id` int(11) NOT NULL,
  `job_execution_id` int(11) NOT NULL,
  `total_failed_users` int(11) NOT NULL,
  `total_successful_users` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batch_step_execution`
--

DROP TABLE IF EXISTS `batch_step_execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batch_step_execution` (
  `step_execution_id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `step_name` varchar(100) NOT NULL,
  `job_execution_id` int(11) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NULL DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `commit_count` int(11) DEFAULT NULL,
  `read_count` int(11) DEFAULT NULL,
  `filter_count` int(11) DEFAULT NULL,
  `write_count` int(11) DEFAULT NULL,
  `read_skip_count` int(11) DEFAULT NULL,
  `write_skip_count` int(11) DEFAULT NULL,
  `process_skip_count` int(11) DEFAULT NULL,
  `rollback_count` int(11) DEFAULT NULL,
  `exit_code` varchar(100) DEFAULT NULL,
  `exit_message` varchar(2500) DEFAULT NULL,
  `last_updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`step_execution_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batch_step_execution_context`
--

DROP TABLE IF EXISTS `batch_step_execution_context`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batch_step_execution_context` (
  `step_execution_id` int(11) NOT NULL,
  `short_context` varchar(2500) NOT NULL,
  `serialized_context` longtext,
  PRIMARY KEY (`step_execution_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batch_step_execution_seq`
--

DROP TABLE IF EXISTS `batch_step_execution_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batch_step_execution_seq` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `billing_process`
--

DROP TABLE IF EXISTS `billing_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `billing_process` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `billing_date` date NOT NULL,
  `period_unit_id` int(11) NOT NULL,
  `period_value` int(11) NOT NULL,
  `is_review` int(11) NOT NULL,
  `paper_invoice_batch_id` int(11) DEFAULT NULL,
  `retries_to_do` int(11) NOT NULL DEFAULT '0',
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `billing_process_fk_2` (`entity_id`),
  KEY `billing_process_fk_3` (`paper_invoice_batch_id`),
  KEY `billing_process_fk_1` (`period_unit_id`),
  CONSTRAINT `billing_process_fk_1` FOREIGN KEY (`period_unit_id`) REFERENCES `period_unit` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `billing_process_fk_2` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `billing_process_fk_3` FOREIGN KEY (`paper_invoice_batch_id`) REFERENCES `paper_invoice_batch` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `billing_process_configuration`
--

DROP TABLE IF EXISTS `billing_process_configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `billing_process_configuration` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `next_run_date` date NOT NULL,
  `generate_report` int(11) NOT NULL,
  `retries` int(11) DEFAULT NULL,
  `days_for_retry` int(11) DEFAULT NULL,
  `days_for_report` int(11) DEFAULT NULL,
  `review_status` int(11) NOT NULL,
  `due_date_unit_id` int(11) NOT NULL,
  `due_date_value` int(11) NOT NULL,
  `df_fm` int(11) DEFAULT NULL,
  `only_recurring` int(11) NOT NULL DEFAULT '1',
  `invoice_date_process` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  `maximum_periods` int(11) NOT NULL DEFAULT '1',
  `auto_payment_application` int(11) NOT NULL DEFAULT '0',
  `period_unit_id` int(11) NOT NULL DEFAULT '1',
  `last_day_of_month` bit(1) NOT NULL DEFAULT b'0',
  `prorating_type` varchar(50) DEFAULT 'PRORATING_AUTO_OFF',
  PRIMARY KEY (`id`),
  KEY `billing_proc_configtn_fk_2` (`entity_id`),
  KEY `billing_proc_configtn_fk_1` (`period_unit_id`),
  CONSTRAINT `billing_proc_configtn_fk_1` FOREIGN KEY (`period_unit_id`) REFERENCES `period_unit` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `billing_proc_configtn_fk_2` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `billing_process_failed_user`
--

DROP TABLE IF EXISTS `billing_process_failed_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `billing_process_failed_user` (
  `id` int(11) NOT NULL,
  `batch_process_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `blacklist`
--

DROP TABLE IF EXISTS `blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blacklist` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type` int(11) NOT NULL,
  `source` int(11) NOT NULL,
  `credit_card` int(11) DEFAULT NULL,
  `credit_card_id` int(11) DEFAULT NULL,
  `contact_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `meta_field_value_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_blacklist_entity_type` (`entity_id`,`type`),
  KEY `ix_blacklist_user_type` (`user_id`,`type`),
  KEY `blacklist_fk_4` (`meta_field_value_id`),
  CONSTRAINT `blacklist_fk_1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `blacklist_fk_2` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `blacklist_fk_4` FOREIGN KEY (`meta_field_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `breadcrumb`
--

DROP TABLE IF EXISTS `breadcrumb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `breadcrumb` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `controller` varchar(255) NOT NULL,
  `action` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `object_id` int(11) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bundle_meta_field_map`
--

DROP TABLE IF EXISTS `bundle_meta_field_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bundle_meta_field_map` (
  `bundle_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL,
  KEY `meta_field_value_id` (`meta_field_value_id`),
  KEY `bundle_id` (`bundle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cdrentries`
--

DROP TABLE IF EXISTS `cdrentries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdrentries` (
  `id` int(11) NOT NULL,
  `accountcode` varchar(20) DEFAULT NULL,
  `src` varchar(20) DEFAULT NULL,
  `dst` varchar(20) DEFAULT NULL,
  `dcontext` varchar(20) DEFAULT NULL,
  `clid` varchar(20) DEFAULT NULL,
  `channel` varchar(20) DEFAULT NULL,
  `dstchannel` varchar(20) DEFAULT NULL,
  `lastapp` varchar(20) DEFAULT NULL,
  `lastdatat` varchar(20) DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `answer` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `billsec` int(11) DEFAULT NULL,
  `disposition` varchar(20) DEFAULT NULL,
  `amaflags` varchar(20) DEFAULT NULL,
  `userfield` varchar(100) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `charge_type`
--

DROP TABLE IF EXISTS `charge_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `charge_type` (
  `id` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `charge_sessions`
--

DROP TABLE IF EXISTS `charge_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `charge_sessions` (
  `id` int(11) NOT NULL DEFAULT '0',
  `user_id` int(11) DEFAULT NULL,
  `session_token` varchar(150) DEFAULT NULL,
  `ts_started` timestamp NULL DEFAULT NULL,
  `ts_last_access` timestamp NULL DEFAULT NULL,
  `carried_units` decimal(22,10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sessions_user` (`user_id`),
  CONSTRAINT `fk_sessions_user` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id` int(11) NOT NULL,
  `organization_name` varchar(200) DEFAULT NULL,
  `street_addres1` varchar(100) DEFAULT NULL,
  `street_addres2` varchar(100) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `state_province` varchar(30) DEFAULT NULL,
  `postal_code` varchar(15) DEFAULT NULL,
  `country_code` varchar(2) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `person_initial` varchar(5) DEFAULT NULL,
  `person_title` varchar(40) DEFAULT NULL,
  `phone_country_code` int(11) DEFAULT NULL,
  `phone_area_code` int(11) DEFAULT NULL,
  `phone_phone_number` varchar(20) DEFAULT NULL,
  `fax_country_code` int(11) DEFAULT NULL,
  `fax_area_code` int(11) DEFAULT NULL,
  `fax_phone_number` varchar(20) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `notification_include` int(11) DEFAULT '1',
  `user_id` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contact_i_del` (`deleted`),
  KEY `ix_contact_address` (`street_addres1`,`city`,`postal_code`,`street_addres2`,`state_province`,`country_code`),
  KEY `ix_contact_fname` (`first_name`),
  KEY `ix_contact_fname_lname` (`first_name`,`last_name`),
  KEY `ix_contact_lname` (`last_name`),
  KEY `ix_contact_orgname` (`organization_name`),
  KEY `ix_contact_phone` (`phone_phone_number`,`phone_area_code`,`phone_country_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact_map`
--

DROP TABLE IF EXISTS `contact_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_map` (
  `id` int(11) NOT NULL,
  `contact_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `table_id` int(11) NOT NULL,
  `foreign_id` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contact_map_fk_3` (`contact_id`),
  KEY `contact_map_fk_2` (`type_id`),
  KEY `contact_map_fk_1` (`table_id`),
  CONSTRAINT `contact_map_fk_1` FOREIGN KEY (`table_id`) REFERENCES `jbilling_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contact_map_fk_2` FOREIGN KEY (`type_id`) REFERENCES `contact_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contact_map_fk_3` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact_type`
--

DROP TABLE IF EXISTS `contact_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_type` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `is_primary` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contact_type_fk_1` (`entity_id`),
  CONSTRAINT `contact_type_fk_1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` int(11) NOT NULL,
  `code` varchar(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `credit_card`
--

DROP TABLE IF EXISTS `credit_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credit_card` (
  `id` int(11) NOT NULL,
  `cc_number` varchar(100) NOT NULL,
  `cc_number_plain` varchar(20) DEFAULT NULL,
  `cc_expiry` date NOT NULL,
  `name` varchar(150) DEFAULT NULL,
  `cc_type` int(11) NOT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `gateway_key` varchar(100) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_cc_number` (`cc_number_plain`),
  KEY `ix_cc_number_encrypted` (`cc_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS `currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currency` (
  `id` int(11) NOT NULL,
  `symbol` varchar(10) NOT NULL,
  `code` varchar(3) NOT NULL,
  `country_code` varchar(2) NOT NULL,
  `optlock` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `currency_entity_map`
--

DROP TABLE IF EXISTS `currency_entity_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currency_entity_map` (
  `currency_id` int(11) NOT NULL DEFAULT '0',
  `entity_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`currency_id`,`entity_id`),
  KEY `currency_entity_map_i_2` (`currency_id`,`entity_id`),
  KEY `currency_entity_map_fk_1` (`entity_id`),
  CONSTRAINT `currency_entity_map_fk_1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `currency_entity_map_fk_2` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `currency_exchange`
--

DROP TABLE IF EXISTS `currency_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currency_exchange` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `currency_id` int(11) DEFAULT NULL,
  `rate` decimal(22,10) NOT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `optlock` int(11) NOT NULL,
  `valid_since` date NOT NULL DEFAULT '1970-01-01',
  PRIMARY KEY (`id`),
  KEY `currency_exchange_fk_1` (`currency_id`),
  CONSTRAINT `currency_exchange_fk_1` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `partner_id` int(11) DEFAULT NULL,
  `referral_fee_paid` int(11) DEFAULT NULL,
  `invoice_delivery_method_id` int(11) NOT NULL,
  `auto_payment_type` int(11) DEFAULT NULL,
  `due_date_unit_id` int(11) DEFAULT NULL,
  `due_date_value` int(11) DEFAULT NULL,
  `df_fm` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `is_parent` int(11) DEFAULT NULL,
  `exclude_aging` int(11) NOT NULL DEFAULT '0',
  `invoice_child` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `dynamic_balance` decimal(22,10) DEFAULT NULL,
  `credit_limit` decimal(22,10) DEFAULT NULL,
  `auto_recharge` decimal(22,10) DEFAULT NULL,
  `use_parent_pricing` bit(1) NOT NULL,
  `main_subscript_order_period_id` int(11) DEFAULT NULL,
  `next_invoice_day_of_period` int(11) DEFAULT NULL,
  `next_inovice_date` date DEFAULT NULL,
  `account_type_id` int(11) DEFAULT NULL,
  `invoice_design` varchar(100) DEFAULT NULL,
  `credit_notification_limit1` decimal(22,10) DEFAULT NULL,
  `credit_notification_limit2` decimal(22,10) DEFAULT NULL,
  `recharge_threshold` decimal(22,10) DEFAULT NULL,
  `monthly_limit` decimal(22,10) DEFAULT NULL,
  `current_monthly_amount` decimal(22,10) DEFAULT NULL,
  `current_month` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_fk_1` (`invoice_delivery_method_id`),
  KEY `customer_fk_2` (`partner_id`),
  KEY `customer_fk_3` (`user_id`),
  KEY `customer_main_subscription_period_FK` (`main_subscript_order_period_id`),
  KEY `customer_account_type_fk` (`account_type_id`),
  KEY `ix_parent_customer_id` (`parent_id`),
  CONSTRAINT `customer_account_type_fk` FOREIGN KEY (`account_type_id`) REFERENCES `account_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `customer_fk_1` FOREIGN KEY (`invoice_delivery_method_id`) REFERENCES `invoice_delivery_method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `customer_fk_2` FOREIGN KEY (`partner_id`) REFERENCES `partner` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `customer_fk_3` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `customer_main_subscription_period_FK` FOREIGN KEY (`main_subscript_order_period_id`) REFERENCES `order_period` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_account_info_type_timeline`
--

DROP TABLE IF EXISTS `customer_account_info_type_timeline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_account_info_type_timeline` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `account_info_type_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL,
  `effective_date` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `customer_account_info_type_timeline_uk` (`customer_id`,`meta_field_value_id`,`account_info_type_id`),
  KEY `customer_account_info_type_timeline_account_info_type_id_fk` (`account_info_type_id`),
  KEY `customer_account_info_type_timeline_meta_field_value_id_fk` (`meta_field_value_id`),
  CONSTRAINT `customer_account_info_type_timeline_account_info_type_id_fk` FOREIGN KEY (`account_info_type_id`) REFERENCES `meta_field_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `customer_account_info_type_timeline_customer_id_fk` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `customer_account_info_type_timeline_meta_field_value_id_fk` FOREIGN KEY (`meta_field_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_meta_field_map`
--

DROP TABLE IF EXISTS `customer_meta_field_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_meta_field_map` (
  `customer_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL,
  PRIMARY KEY (`customer_id`,`meta_field_value_id`),
  KEY `customer_meta_field_map_fk_2` (`meta_field_value_id`),
  CONSTRAINT `customer_meta_field_map_fk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `customer_meta_field_map_fk_2` FOREIGN KEY (`meta_field_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_notes`
--

DROP TABLE IF EXISTS `customer_notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_notes` (
  `id` int(11) NOT NULL,
  `note_title` varchar(50) DEFAULT NULL,
  `note_content` varchar(1000) DEFAULT NULL,
  `creation_time` timestamp NULL DEFAULT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_notes_entity_id_FK` (`entity_id`),
  KEY `customer_notes_user_id_FK` (`user_id`),
  KEY `customer_notes_customer_id_FK` (`customer_id`),
  CONSTRAINT `customer_notes_customer_id_FK` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `customer_notes_entity_id_FK` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`),
  CONSTRAINT `customer_notes_user_id_FK` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `deferred_action`
--

DROP TABLE IF EXISTS `deferred_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deferred_action` (
  `id` int(11) NOT NULL,
  `enitity_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `when_date` datetime NOT NULL,
  `status_id` int(11) NOT NULL,
  `object` longblob,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` smallint(6) NOT NULL DEFAULT '0',
  `optlock` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entity_id` (`enitity_id`),
  KEY `when_date` (`when_date`),
  KEY `deferred_action_FK1` (`id`),
  KEY `deferred_action_FK2` (`status_id`),
  KEY `deferred_action_FK3` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_table_query`
--

DROP TABLE IF EXISTS `data_table_query`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_table_query` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `route_id` int(11) NOT NULL,
  `global` int(11) NOT NULL,
  `root_entry_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `data_table_query_next_FK` (`root_entry_id`),
  CONSTRAINT `data_table_query_next_FK` FOREIGN KEY (`root_entry_id`) REFERENCES `data_table_query_entry` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_table_query_entry`
--

DROP TABLE IF EXISTS `data_table_query_entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_table_query_entry` (
  `id` int(11) NOT NULL,
  `route_id` int(11) NOT NULL,
  `columns` varchar(250) NOT NULL,
  `next_entry_id` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `data_table_query_entry_next_FK` (`next_entry_id`),
  CONSTRAINT `data_table_query_entry_next_FK` FOREIGN KEY (`next_entry_id`) REFERENCES `data_table_query_entry` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `serial_num` varchar(100) DEFAULT NULL,
  `device_code` varchar(100) DEFAULT NULL,
  `vendor_code` varchar(100) DEFAULT NULL,
  `status_id` int(11) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated_date` timestamp NULL DEFAULT NULL,
  `deleted` smallint(6) NOT NULL DEFAULT '0',
  `OPTLOCK` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `icc` varchar(100) DEFAULT NULL,
  `imsi` varchar(100) DEFAULT NULL,
  `puk1` int(11) DEFAULT NULL,
  `puk2` int(11) DEFAULT NULL,
  `pin1` varchar(20) DEFAULT NULL,
  `pin2` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `icc` (`icc`),
  UNIQUE KEY `imsi` (`imsi`),
  KEY `device_FK1` (`status_id`),
  KEY `device_FK2` (`type_id`),
  KEY `device_FK3` (`entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_type`
--

DROP TABLE IF EXISTS `device_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_type` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discount` (
  `id` int(11) NOT NULL,
  `code` varchar(20) NOT NULL,
  `discount_type` varchar(25) NOT NULL,
  `rate` decimal(22,10) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `last_update_datetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `discount_entity_id_fk` (`entity_id`),
  CONSTRAINT `discount_entity_id_fk` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `discount_attribute`
--

DROP TABLE IF EXISTS `discount_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discount_attribute` (
  `discount_id` int(11) NOT NULL,
  `attribute_name` varchar(255) NOT NULL,
  `attribute_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`discount_id`,`attribute_name`),
  CONSTRAINT `discount_attr_id_fk` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `discount_line`
--

DROP TABLE IF EXISTS `discount_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discount_line` (
  `id` int(11) NOT NULL,
  `discount_id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL,
  `order_id` int(11) NOT NULL,
  `discount_order_line_id` int(11) DEFAULT NULL,
  `order_line_amount` decimal(22,10) DEFAULT NULL,
  `description` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `discount_line_discount_id_fk` (`discount_id`),
  KEY `discount_line_item_id_fk` (`item_id`),
  KEY `discount_line_order_id_fk` (`order_id`),
  KEY `discount_line_order_line_id_fk` (`discount_order_line_id`),
  CONSTRAINT `discount_line_discount_id_fk` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `discount_line_item_id_fk` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `discount_line_order_id_fk` FOREIGN KEY (`order_id`) REFERENCES `purchase_order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `discount_line_order_line_id_fk` FOREIGN KEY (`discount_order_line_id`) REFERENCES `order_line` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entity`
--

DROP TABLE IF EXISTS `entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entity` (
  `id` int(11) NOT NULL,
  `external_id` varchar(20) DEFAULT NULL,
  `description` varchar(100) NOT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `language_id` int(11) NOT NULL,
  `currency_id` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `invoice_as_reseller` bit(1) NOT NULL DEFAULT b'0',
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entity_fk_1` (`currency_id`),
  KEY `entity_fk_2` (`language_id`),
  KEY `entity_fk_3` (`parent_id`),
  CONSTRAINT `entity_fk_1` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `entity_fk_2` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `entity_fk_3` FOREIGN KEY (`parent_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entity_delivery_method_map`
--

DROP TABLE IF EXISTS `entity_delivery_method_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entity_delivery_method_map` (
  `method_id` int(11) NOT NULL DEFAULT '0',
  `entity_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`method_id`,`entity_id`),
  KEY `entity_delivry_methd_map_fk1` (`entity_id`),
  CONSTRAINT `entity_delivry_methd_map_fk1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `entity_delivry_methd_map_fk2` FOREIGN KEY (`method_id`) REFERENCES `invoice_delivery_method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entity_payment_method_map`
--

DROP TABLE IF EXISTS `entity_payment_method_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entity_payment_method_map` (
  `entity_id` int(11) NOT NULL DEFAULT '0',
  `payment_method_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`entity_id`,`payment_method_id`),
  KEY `entity_payment_method_map_fk_1` (`payment_method_id`),
  CONSTRAINT `entity_payment_method_map_fk_1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `entity_payment_method_map_fk_2` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entity_report_map`
--

DROP TABLE IF EXISTS `entity_report_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entity_report_map` (
  `report_id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  PRIMARY KEY (`report_id`,`entity_id`),
  KEY `report_map_entity_id_fk` (`entity_id`),
  CONSTRAINT `report_map_entity_id_fk` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `report_map_report_id_fk` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `enumeration`
--

DROP TABLE IF EXISTS `enumeration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enumeration` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `enumeration_values`
--

DROP TABLE IF EXISTS `enumeration_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enumeration_values` (
  `id` int(11) NOT NULL,
  `enumeration_id` int(11) NOT NULL,
  `value` varchar(50) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `enumeration_values_fk_1` (`enumeration_id`),
  CONSTRAINT `enumeration_values_fk_1` FOREIGN KEY (`enumeration_id`) REFERENCES `enumeration` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event_log`
--

DROP TABLE IF EXISTS `event_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_log` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `table_id` int(11) DEFAULT NULL,
  `foreign_id` int(11) NOT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `level_field` int(11) NOT NULL,
  `module_id` int(11) NOT NULL,
  `message_id` int(11) NOT NULL,
  `old_num` int(11) DEFAULT NULL,
  `old_str` varchar(1000) DEFAULT NULL,
  `old_date` timestamp NULL DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `affected_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_el_main` (`module_id`,`message_id`,`create_datetime`),
  KEY `event_log_fk_6` (`affected_user_id`),
  KEY `event_log_fk_2` (`entity_id`),
  KEY `event_log_fk_5` (`message_id`),
  KEY `event_log_fk_4` (`table_id`),
  KEY `event_log_fk_3` (`user_id`),
  CONSTRAINT `event_log_fk_1` FOREIGN KEY (`module_id`) REFERENCES `event_log_module` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `event_log_fk_2` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `event_log_fk_3` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `event_log_fk_4` FOREIGN KEY (`table_id`) REFERENCES `jbilling_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `event_log_fk_5` FOREIGN KEY (`message_id`) REFERENCES `event_log_message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `event_log_fk_6` FOREIGN KEY (`affected_user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event_log_message`
--

DROP TABLE IF EXISTS `event_log_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_log_message` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event_log_module`
--

DROP TABLE IF EXISTS `event_log_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_log_module` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `filter`
--

DROP TABLE IF EXISTS `filter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `filter` (
  `id` int(11) NOT NULL,
  `filter_set_id` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `constraint_type` varchar(255) NOT NULL,
  `field` varchar(255) NOT NULL,
  `template` varchar(255) NOT NULL,
  `visible` bit(1) NOT NULL,
  `integer_value` int(11) DEFAULT NULL,
  `string_value` varchar(255) DEFAULT NULL,
  `start_date_value` timestamp NULL DEFAULT NULL,
  `end_date_value` timestamp NULL DEFAULT NULL,
  `version` int(11) NOT NULL,
  `boolean_value` bit(1) DEFAULT NULL,
  `decimal_value` decimal(22,10) DEFAULT NULL,
  `decimal_high_value` decimal(22,10) DEFAULT NULL,
  `field_key_data` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `filter_set`
--

DROP TABLE IF EXISTS `filter_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `filter_set` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `filter_set_filter`
--

DROP TABLE IF EXISTS `filter_set_filter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `filter_set_filter` (
  `filter_set_filters_id` int(11) DEFAULT NULL,
  `filter_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `gotosolr_cdr_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `gotosolr_cdr_event` (
  `id` int(11) NOT NULL DEFAULT '0',
  `process_id` int(11) NOT NULL,
  `record_id` varchar(100) DEFAULT NULL,
  `order_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `invoice_id` int(11) DEFAULT NULL,
  `calling_number` varchar(40) DEFAULT NULL,
  `destination_number` varchar(40) DEFAULT NULL,
  `call_start_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `call_end_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `duration` int(40) DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `destination_descr` varchar(256) DEFAULT NULL,
  `rate_id` int(11) DEFAULT NULL,
  `call_type` varchar(20) DEFAULT NULL,
  `cdr_source` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id_idx` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mediation_cfg`
--

DROP TABLE IF EXISTS `mediation_cfg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mediation_cfg` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(50) NOT NULL,
  `order_value` int(11) NOT NULL,
  `pluggable_task_id` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mediation_cfg_fk_1` (`pluggable_task_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mediation_errors`
--

DROP TABLE IF EXISTS `mediation_errors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mediation_errors` (
  `accountcode` varchar(50) NOT NULL,
  `src` varchar(50) DEFAULT NULL,
  `dst` varchar(50) DEFAULT NULL,
  `dcontext` varchar(50) DEFAULT NULL,
  `clid` varchar(50) DEFAULT NULL,
  `channel` varchar(50) DEFAULT NULL,
  `dstchannel` varchar(50) DEFAULT NULL,
  `lastapp` varchar(50) DEFAULT NULL,
  `lastdata` varchar(50) DEFAULT NULL,
  `start` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `answer` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `duration` int(11) DEFAULT NULL,
  `billsec` int(11) DEFAULT NULL,
  `disposition` varchar(50) DEFAULT NULL,
  `amaflags` varchar(50) DEFAULT NULL,
  `userfield` varchar(50) DEFAULT NULL,
  `error_message` varchar(200) DEFAULT NULL,
  `should_retry` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`accountcode`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mediation_order_map`
--

DROP TABLE IF EXISTS `mediation_order_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mediation_order_map` (
  `mediation_process_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  KEY `mediation_order_map_fk_1` (`mediation_process_id`),
  KEY `mediation_order_map_fk_2` (`order_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mediation_process`
--

DROP TABLE IF EXISTS `mediation_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mediation_process` (
  `id` int(11) NOT NULL,
  `configuration_id` int(11) NOT NULL,
  `start_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_datetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orders_affected` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mediation_process_fk_1` (`configuration_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mediation_record`
--

DROP TABLE IF EXISTS `mediation_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mediation_record` (
  `id_key` varchar(100) NOT NULL,
  `start_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `mediation_process_id` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_key` (`id_key`,`status_id`),
  KEY `mediation_record_fk_1` (`mediation_process_id`),
  KEY `mediation_record_fk_2` (`status_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mediation_record_line`
--

DROP TABLE IF EXISTS `mediation_record_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mediation_record_line` (
  `id` int(11) NOT NULL,
  `order_line_id` int(11) NOT NULL,
  `event_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `amount` decimal(22,10) NOT NULL,
  `quantity` decimal(22,10) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `mediation_record_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_line_id` (`order_line_id`),
  KEY `mediation_record_line_fk_1` (`mediation_record_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `generic_status`
--

DROP TABLE IF EXISTS `generic_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `generic_status` (
  `id` int(11) NOT NULL,
  `dtype` varchar(50) NOT NULL,
  `status_value` int(11) NOT NULL,
  `can_login` int(11) DEFAULT NULL,
  `ordr` int(11) DEFAULT NULL,
  `attribute1` varchar(20) DEFAULT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `generic_status_fk_1` (`dtype`),
  KEY `generic_status_entity_id_fk` (`entity_id`),
  CONSTRAINT `generic_status_entity_id_fk` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `generic_status_fk_1` FOREIGN KEY (`dtype`) REFERENCES `generic_status_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `generic_status_type`
--

DROP TABLE IF EXISTS `generic_status_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `generic_status_type` (
  `id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `international_description`
--

DROP TABLE IF EXISTS `international_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `international_description` (
  `table_id` int(11) NOT NULL,
  `foreign_id` int(11) NOT NULL,
  `psudo_column` varchar(20) NOT NULL,
  `language_id` int(11) NOT NULL,
  `content` varchar(4000) NOT NULL,
  PRIMARY KEY (`table_id`,`foreign_id`,`psudo_column`,`language_id`),
  KEY `international_description_i_2` (`table_id`,`foreign_id`,`language_id`),
  KEY `international_description_fk_1` (`language_id`),
  CONSTRAINT `international_description_fk_1` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice` (
  `id` int(11) NOT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `billing_process_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `delegated_invoice_id` int(11) DEFAULT NULL,
  `due_date` date NOT NULL,
  `total` decimal(22,10) NOT NULL,
  `payment_attempts` int(11) NOT NULL DEFAULT '0',
  `status_id` int(11) NOT NULL DEFAULT '1',
  `balance` decimal(22,10) DEFAULT NULL,
  `carried_balance` decimal(22,10) NOT NULL,
  `in_process_payment` int(11) NOT NULL DEFAULT '1',
  `is_review` int(11) NOT NULL,
  `currency_id` int(11) NOT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `paper_invoice_batch_id` int(11) DEFAULT NULL,
  `customer_notes` varchar(1000) DEFAULT NULL,
  `public_number` varchar(40) DEFAULT NULL,
  `last_reminder` date DEFAULT NULL,
  `overdue_step` int(11) DEFAULT NULL,
  `create_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_invoice_date` (`create_datetime`),
  KEY `ix_invoice_due_date` (`user_id`,`due_date`),
  KEY `ix_invoice_number` (`user_id`,`public_number`),
  KEY `ix_invoice_ts` (`create_timestamp`,`user_id`),
  KEY `ix_invoice_user_id` (`user_id`,`deleted`),
  KEY `invoice_fk_1` (`billing_process_id`),
  KEY `invoice_fk_3` (`currency_id`),
  KEY `invoice_fk_4` (`delegated_invoice_id`),
  KEY `invoice_fk_2` (`paper_invoice_batch_id`),
  CONSTRAINT `invoice_fk_1` FOREIGN KEY (`billing_process_id`) REFERENCES `billing_process` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `invoice_fk_2` FOREIGN KEY (`paper_invoice_batch_id`) REFERENCES `paper_invoice_batch` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `invoice_fk_3` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `invoice_fk_4` FOREIGN KEY (`delegated_invoice_id`) REFERENCES `invoice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invoice_commission`
--

DROP TABLE IF EXISTS `invoice_commission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_commission` (
  `id` int(11) NOT NULL,
  `partner_id` int(11) DEFAULT NULL,
  `referral_partner_id` int(11) DEFAULT NULL,
  `commission_process_run_id` int(11) DEFAULT NULL,
  `invoice_id` int(11) DEFAULT NULL,
  `standard_amount` decimal(22,10) DEFAULT NULL,
  `master_amount` decimal(22,10) DEFAULT NULL,
  `exception_amount` decimal(22,10) DEFAULT NULL,
  `referral_amount` decimal(22,10) DEFAULT NULL,
  `commission_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invoice_delivery_method`
--

DROP TABLE IF EXISTS `invoice_delivery_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_delivery_method` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invoice_line`
--

DROP TABLE IF EXISTS `invoice_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_line` (
  `id` int(11) NOT NULL,
  `invoice_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `amount` decimal(22,10) NOT NULL,
  `quantity` decimal(22,10) DEFAULT NULL,
  `price` decimal(22,10) DEFAULT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `source_user_id` int(11) DEFAULT NULL,
  `is_percentage` int(11) NOT NULL DEFAULT '0',
  `optlock` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `invoice_line_fk_1` (`invoice_id`),
  KEY `invoice_line_fk_2` (`item_id`),
  KEY `invoice_line_fk_3` (`type_id`),
  KEY `invoice_line_fk_4` (`order_id`),
  CONSTRAINT `invoice_line_fk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `invoice_line_fk_2` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `invoice_line_fk_3` FOREIGN KEY (`type_id`) REFERENCES `invoice_line_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `invoice_line_fk_4` FOREIGN KEY (`order_id`) REFERENCES `purchase_order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invoice_line_type`
--

DROP TABLE IF EXISTS `invoice_line_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_line_type` (
  `id` int(11) NOT NULL,
  `description` varchar(50) NOT NULL,
  `order_position` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invoice_meta_field_map`
--

DROP TABLE IF EXISTS `invoice_meta_field_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_meta_field_map` (
  `invoice_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL,
  PRIMARY KEY (`invoice_id`,`meta_field_value_id`),
  KEY `invoice_meta_field_map_fk_2` (`meta_field_value_id`),
  CONSTRAINT `invoice_meta_field_map_fk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `invoice_meta_field_map_fk_2` FOREIGN KEY (`meta_field_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `internal_number` varchar(50) DEFAULT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `has_decimals` int(11) NOT NULL DEFAULT '0',
  `optlock` int(11) NOT NULL,
  `gl_code` varchar(50) DEFAULT NULL,
  `price_manual` int(11) DEFAULT NULL,
  `asset_management_enabled` int(11) NOT NULL DEFAULT '0',
  `standard_availability` bit(1) NOT NULL DEFAULT b'1',
  `global` bit(1) NOT NULL DEFAULT b'0',
  `standard_partner_percentage` decimal(22,10) DEFAULT NULL,
  `master_partner_percentage` decimal(22,10) DEFAULT NULL,
  `active_since` date DEFAULT NULL,
  `active_until` date DEFAULT NULL,
  `reservation_duration` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `ix_item_ent` (`entity_id`,`internal_number`),
  CONSTRAINT `item_fk_1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_account_type_availability`
--

DROP TABLE IF EXISTS `item_account_type_availability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_account_type_availability` (
  `item_id` int(11) DEFAULT NULL,
  `account_type_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_dependency`
--

DROP TABLE IF EXISTS `item_dependency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_dependency` (
  `id` int(11) NOT NULL,
  `dtype` varchar(10) NOT NULL,
  `item_id` int(11) NOT NULL,
  `min` int(11) NOT NULL,
  `max` int(11) DEFAULT NULL,
  `dependent_item_id` int(11) DEFAULT NULL,
  `dependent_item_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `item_dependency_fk1` (`item_id`),
  KEY `item_dependency_fk2` (`dependent_item_id`),
  KEY `item_dependency_fk3` (`dependent_item_type_id`),
  CONSTRAINT `item_dependency_fk1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_dependency_fk2` FOREIGN KEY (`dependent_item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_dependency_fk3` FOREIGN KEY (`dependent_item_type_id`) REFERENCES `item_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_entity_map`
--

DROP TABLE IF EXISTS `item_entity_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_entity_map` (
  `item_id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  UNIQUE KEY `item_entity_map_uc_1` (`entity_id`,`item_id`),
  KEY `item_entity_map_fk2` (`item_id`),
  CONSTRAINT `item_entity_map_fk1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_entity_map_fk2` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_meta_field_map`
--

DROP TABLE IF EXISTS `item_meta_field_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_meta_field_map` (
  `item_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL,
  PRIMARY KEY (`item_id`,`meta_field_value_id`),
  KEY `item_meta_field_map_fk_2` (`meta_field_value_id`),
  CONSTRAINT `item_meta_field_map_fk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_meta_field_map_fk_2` FOREIGN KEY (`meta_field_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_price`
--

DROP TABLE IF EXISTS `item_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_price` (
  `id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL,
  `currency_id` int(11) DEFAULT NULL,
  `price` decimal(22,10) NOT NULL,
  `OPTLOCK` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `item_price_fk_1` (`currency_id`),
  KEY `item_price_fk_2` (`item_id`),
  CONSTRAINT `item_price_fk_1` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_price_fk_2` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_type`
--

DROP TABLE IF EXISTS `item_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_type` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `order_line_type_id` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  `internal` bit(1) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `allow_asset_management` int(11) NOT NULL DEFAULT '0',
  `asset_identifier_label` varchar(50) DEFAULT NULL,
  `global` bit(1) NOT NULL DEFAULT b'0',
  `one_per_order` bit(1) NOT NULL DEFAULT b'0',
  `one_per_customer` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `item_type_fk_1` (`entity_id`),
  KEY `parent_id_fk` (`parent_id`),
  CONSTRAINT `item_type_fk_1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `parent_id_fk` FOREIGN KEY (`parent_id`) REFERENCES `item_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_type_entity_map`
--

DROP TABLE IF EXISTS `item_type_entity_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_type_entity_map` (
  `item_type_id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  KEY `item_type_entity_map_fk1` (`entity_id`),
  KEY `item_type_entity_map_fk2` (`item_type_id`),
  CONSTRAINT `item_type_entity_map_fk1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_type_entity_map_fk2` FOREIGN KEY (`item_type_id`) REFERENCES `item_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_type_exclude_map`
--

DROP TABLE IF EXISTS `item_type_exclude_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_type_exclude_map` (
  `item_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  PRIMARY KEY (`item_id`,`type_id`),
  KEY `item_type_exclude_type_id_fk` (`type_id`),
  CONSTRAINT `item_type_exclude_item_id_fk` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_type_exclude_type_id_fk` FOREIGN KEY (`type_id`) REFERENCES `item_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_type_map`
--

DROP TABLE IF EXISTS `item_type_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_type_map` (
  `item_id` int(11) NOT NULL DEFAULT '0',
  `type_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_id`,`type_id`),
  KEY `item_type_map_fk_2` (`type_id`),
  CONSTRAINT `item_type_map_fk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_type_map_fk_2` FOREIGN KEY (`type_id`) REFERENCES `item_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_type_meta_field_def_map`
--

DROP TABLE IF EXISTS `item_type_meta_field_def_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_type_meta_field_def_map` (
  `item_type_id` int(11) NOT NULL,
  `meta_field_id` int(11) NOT NULL,
  KEY `item_type_meta_field_def_map_fk_1` (`item_type_id`),
  KEY `item_type_meta_field_def_map_fk_2` (`meta_field_id`),
  CONSTRAINT `item_type_meta_field_def_map_fk_1` FOREIGN KEY (`item_type_id`) REFERENCES `item_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_type_meta_field_def_map_fk_2` FOREIGN KEY (`meta_field_id`) REFERENCES `meta_field_name` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_type_meta_field_map`
--

DROP TABLE IF EXISTS `item_type_meta_field_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_type_meta_field_map` (
  `item_type_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL,
  KEY `item_type_meta_field_type_fk` (`item_type_id`),
  KEY `item_type_meta_field_value_fk` (`meta_field_value_id`),
  CONSTRAINT `item_type_meta_field_type_fk` FOREIGN KEY (`item_type_id`) REFERENCES `item_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_type_meta_field_value_fk` FOREIGN KEY (`meta_field_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbilling_seqs`
--

DROP TABLE IF EXISTS `jbilling_seqs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jbilling_seqs` (
  `name` varchar(255) NOT NULL DEFAULT '',
  `next_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jbilling_table`
--

DROP TABLE IF EXISTS `jbilling_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jbilling_table` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `language` (
  `id` int(11) NOT NULL,
  `code` varchar(2) NOT NULL,
  `description` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `list_meta_field_values`
--

DROP TABLE IF EXISTS `list_meta_field_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `list_meta_field_values` (
  `meta_field_value_id` int(11) NOT NULL,
  `list_value` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `matching_field`
--

DROP TABLE IF EXISTS `matching_field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matching_field` (
  `id` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `required` bit(1) NOT NULL,
  `route_id` int(11) DEFAULT NULL,
  `matching_field` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `order_sequence` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  `longest_value` int(11) NOT NULL,
  `smallest_value` int(11) NOT NULL,
  `mandatory_fields_query` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `matching_field_route_id_FK` (`route_id`),
  CONSTRAINT `matching_field_route_id_FK` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meta_field_group`
--

DROP TABLE IF EXISTS `meta_field_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meta_field_group` (
  `id` int(11) NOT NULL,
  `date_created` date DEFAULT NULL,
  `date_updated` date DEFAULT NULL,
  `entity_id` int(11) NOT NULL,
  `display_order` int(11) DEFAULT NULL,
  `optlock` int(11) DEFAULT NULL,
  `entity_type` varchar(32) NOT NULL,
  `discriminator` varchar(30) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `account_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `meta_field_group_account_type_FK2` (`account_type_id`),
  KEY `meta_field_group_entity_FK1` (`entity_id`),
  CONSTRAINT `meta_field_group_account_type_FK2` FOREIGN KEY (`account_type_id`) REFERENCES `account_type` (`id`),
  CONSTRAINT `meta_field_group_entity_FK1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meta_field_name`
--

DROP TABLE IF EXISTS `meta_field_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meta_field_name` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `entity_type` varchar(25) NOT NULL,
  `data_type` varchar(25) NOT NULL,
  `is_disabled` bit(1) DEFAULT NULL,
  `is_mandatory` bit(1) DEFAULT NULL,
  `display_order` int(11) DEFAULT NULL,
  `default_value_id` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `entity_id` int(11) DEFAULT '1',
  `error_message` varchar(256) DEFAULT NULL,
  `is_primary` bit(1) DEFAULT b'1',
  `validation_rule_id` int(11) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
  `field_usage` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `meta_field_name_fk_1` (`default_value_id`),
  KEY `meta_field_entity_id_fk` (`entity_id`),
  KEY `validation_rule_fk_1` (`validation_rule_id`),
  CONSTRAINT `meta_field_entity_id_fk` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `meta_field_name_fk_1` FOREIGN KEY (`default_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `validation_rule_fk_1` FOREIGN KEY (`validation_rule_id`) REFERENCES `validation_rule` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meta_field_value`
--

DROP TABLE IF EXISTS `meta_field_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meta_field_value` (
  `id` int(11) NOT NULL,
  `meta_field_name_id` int(11) NOT NULL,
  `dtype` varchar(10) NOT NULL,
  `boolean_value` bit(1) DEFAULT NULL,
  `date_value` timestamp NULL DEFAULT NULL,
  `decimal_value` decimal(22,10) DEFAULT NULL,
  `integer_value` int(11) DEFAULT NULL,
  `string_value` varchar(1000) DEFAULT NULL,
  UNIQUE KEY `meta_field_value_id_key` (`id`),
  KEY `meta_field_value_fk_1` (`meta_field_name_id`),
  CONSTRAINT `meta_field_value_fk_1` FOREIGN KEY (`meta_field_name_id`) REFERENCES `meta_field_name` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `metafield_group_meta_field_map`
--

DROP TABLE IF EXISTS `metafield_group_meta_field_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `metafield_group_meta_field_map` (
  `metafield_group_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_category`
--

DROP TABLE IF EXISTS `notification_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_category` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_medium_type`
--

DROP TABLE IF EXISTS `notification_medium_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_medium_type` (
  `notification_id` int(11) NOT NULL,
  `medium_type` varchar(255) NOT NULL,
  PRIMARY KEY (`notification_id`,`medium_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_message`
--

DROP TABLE IF EXISTS `notification_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_message` (
  `id` int(11) NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  `entity_id` int(11) NOT NULL,
  `language_id` int(11) NOT NULL,
  `use_flag` int(11) NOT NULL DEFAULT '1',
  `optlock` int(11) NOT NULL,
  `attachment_type` varchar(20) DEFAULT NULL,
  `include_attachment` int(11) DEFAULT NULL,
  `attachment_design` varchar(100) DEFAULT NULL,
  `notify_admin` int(11) DEFAULT NULL,
  `notify_partner` int(11) DEFAULT NULL,
  `notify_parent` int(11) DEFAULT NULL,
  `notify_all_parents` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `notification_message_fk_3` (`entity_id`),
  KEY `notification_message_fk_1` (`language_id`),
  KEY `notification_message_fk_2` (`type_id`),
  CONSTRAINT `notification_message_fk_1` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `notification_message_fk_2` FOREIGN KEY (`type_id`) REFERENCES `notification_message_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `notification_message_fk_3` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_message_arch`
--

DROP TABLE IF EXISTS `notification_message_arch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_message_arch` (
  `id` int(11) NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT NULL,
  `result_message` varchar(200) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_message_arch_line`
--

DROP TABLE IF EXISTS `notification_message_arch_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_message_arch_line` (
  `id` int(11) NOT NULL,
  `message_archive_id` int(11) DEFAULT NULL,
  `section` int(11) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `notif_mess_arch_line_fk_1` (`message_archive_id`),
  CONSTRAINT `notif_mess_arch_line_fk_1` FOREIGN KEY (`message_archive_id`) REFERENCES `notification_message_arch` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_message_line`
--

DROP TABLE IF EXISTS `notification_message_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_message_line` (
  `id` int(11) NOT NULL,
  `message_section_id` int(11) DEFAULT NULL,
  `content` varchar(1000) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `notification_message_line_fk_1` (`message_section_id`),
  CONSTRAINT `notification_message_line_fk_1` FOREIGN KEY (`message_section_id`) REFERENCES `notification_message_section` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_message_section`
--

DROP TABLE IF EXISTS `notification_message_section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_message_section` (
  `id` int(11) NOT NULL,
  `message_id` int(11) DEFAULT NULL,
  `section` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `notification_msg_section_fk_1` (`message_id`),
  CONSTRAINT `notification_msg_section_fk_1` FOREIGN KEY (`message_id`) REFERENCES `notification_message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notification_message_type`
--

DROP TABLE IF EXISTS `notification_message_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_message_type` (
  `id` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id_fk_1` (`category_id`),
  CONSTRAINT `category_id_fk_1` FOREIGN KEY (`category_id`) REFERENCES `notification_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_billing_type`
--

DROP TABLE IF EXISTS `order_billing_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_billing_type` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_change`
--

DROP TABLE IF EXISTS `order_change`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_change` (
  `id` int(11) NOT NULL,
  `parent_order_change_id` int(11) DEFAULT NULL,
  `parent_order_line_id` int(11) DEFAULT NULL,
  `order_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `quantity` decimal(22,10) DEFAULT NULL,
  `price` decimal(22,10) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `use_item` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `start_date` date NOT NULL,
  `application_date` timestamp NULL DEFAULT NULL,
  `status_id` int(11) NOT NULL,
  `user_assigned_status_id` int(11) NOT NULL,
  `order_line_id` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `error_message` varchar(500) DEFAULT NULL,
  `error_codes` varchar(200) DEFAULT NULL,
  `applied_manually` int(11) DEFAULT NULL,
  `removal` int(11) DEFAULT NULL,
  `next_billable_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `order_change_type_id` int(11) NOT NULL,
  `order_status_id` int(11) DEFAULT NULL,
  `is_percentage` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `order_change_order_id_fk` (`order_id`),
  KEY `order_change_item_id_fk` (`item_id`),
  KEY `order_change_user_id_fk` (`user_id`),
  KEY `order_change_status_id_fk` (`status_id`),
  KEY `order_change_user_status_id_fk` (`user_assigned_status_id`),
  KEY `order_change_parent_order_line_id_fk` (`parent_order_line_id`),
  KEY `order_change_parent_order_change_fk` (`parent_order_change_id`),
  KEY `order_change_idx_order_line` (`order_line_id`),
  KEY `order_change_order_change_type_id_fk` (`order_change_type_id`),
  KEY `order_change_order_status_id_fk` (`order_status_id`),
  CONSTRAINT `order_change_item_id_fk` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_change_order_change_type_id_fk` FOREIGN KEY (`order_change_type_id`) REFERENCES `order_change_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_change_order_id_fk` FOREIGN KEY (`order_id`) REFERENCES `purchase_order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_change_order_line_id_fk` FOREIGN KEY (`order_line_id`) REFERENCES `order_line` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_change_order_status_id_fk` FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_change_parent_order_change_fk` FOREIGN KEY (`parent_order_change_id`) REFERENCES `order_change` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_change_parent_order_line_id_fk` FOREIGN KEY (`parent_order_line_id`) REFERENCES `order_line` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_change_status_id_fk` FOREIGN KEY (`status_id`) REFERENCES `generic_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_change_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_change_user_status_id_fk` FOREIGN KEY (`user_assigned_status_id`) REFERENCES `generic_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_change_asset_map`
--

DROP TABLE IF EXISTS `order_change_asset_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_change_asset_map` (
  `order_change_id` int(11) NOT NULL,
  `asset_id` int(11) NOT NULL,
  KEY `order_change_asset_map_change_id_fk` (`order_change_id`),
  KEY `order_change_asset_map_asset_id_fk` (`asset_id`),
  CONSTRAINT `order_change_asset_map_asset_id_fk` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_change_asset_map_change_id_fk` FOREIGN KEY (`order_change_id`) REFERENCES `order_change` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_change_meta_field_map`
--

DROP TABLE IF EXISTS `order_change_meta_field_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_change_meta_field_map` (
  `order_change_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL,
  KEY `order_change_meta_field_map_fk_1` (`order_change_id`),
  KEY `order_change_meta_field_map_fk_2` (`meta_field_value_id`),
  CONSTRAINT `order_change_meta_field_map_fk_1` FOREIGN KEY (`order_change_id`) REFERENCES `order_change` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_change_meta_field_map_fk_2` FOREIGN KEY (`meta_field_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_change_type`
--

DROP TABLE IF EXISTS `order_change_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_change_type` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `default_type` bit(1) NOT NULL DEFAULT b'0',
  `allow_order_status_change` bit(1) NOT NULL DEFAULT b'0',
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_change_type_entity_id_fk` (`entity_id`),
  CONSTRAINT `order_change_type_entity_id_fk` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_change_type_item_type_map`
--

DROP TABLE IF EXISTS `order_change_type_item_type_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_change_type_item_type_map` (
  `order_change_type_id` int(11) NOT NULL,
  `item_type_id` int(11) NOT NULL,
  KEY `order_change_type_item_type_map_change_type_id_fk` (`order_change_type_id`),
  KEY `order_change_type_item_type_map_item_type_id_fk` (`item_type_id`),
  CONSTRAINT `order_change_type_item_type_map_change_type_id_fk` FOREIGN KEY (`order_change_type_id`) REFERENCES `order_change_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_change_type_item_type_map_item_type_id_fk` FOREIGN KEY (`item_type_id`) REFERENCES `item_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_change_type_meta_field_map`
--

DROP TABLE IF EXISTS `order_change_type_meta_field_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_change_type_meta_field_map` (
  `order_change_type_id` int(11) NOT NULL,
  `meta_field_id` int(11) NOT NULL,
  KEY `order_change_type_meta_field_map_change_type_id_fk` (`order_change_type_id`),
  KEY `order_change_type_meta_field_map_meta_field_id_fk` (`meta_field_id`),
  CONSTRAINT `order_change_type_meta_field_map_change_type_id_fk` FOREIGN KEY (`order_change_type_id`) REFERENCES `order_change_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_change_type_meta_field_map_meta_field_id_fk` FOREIGN KEY (`meta_field_id`) REFERENCES `meta_field_name` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_line`
--

DROP TABLE IF EXISTS `order_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_line` (
  `id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `amount` decimal(22,10) NOT NULL,
  `quantity` decimal(22,10) DEFAULT NULL,
  `price` decimal(22,10) DEFAULT NULL,
  `item_price` int(11) DEFAULT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `description` varchar(1000) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `use_item` bit(1) NOT NULL,
  `parent_line_id` int(11) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `sip_uri` varchar(255) DEFAULT NULL,
  `is_percentage` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `order_line_fk_1` (`item_id`),
  KEY `order_line_fk_2` (`order_id`),
  KEY `order_line_fk_3` (`type_id`),
  KEY `order_line_parent_line_id_fk` (`parent_line_id`),
  CONSTRAINT `order_line_fk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_line_fk_2` FOREIGN KEY (`order_id`) REFERENCES `purchase_order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_line_fk_3` FOREIGN KEY (`type_id`) REFERENCES `order_line_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_line_parent_line_id_fk` FOREIGN KEY (`parent_line_id`) REFERENCES `order_line` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_line_meta_field_map`
--

DROP TABLE IF EXISTS `order_line_meta_field_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_line_meta_field_map` (
  `order_line_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL,
  KEY `ol_meta_field_map_fk_1` (`order_line_id`),
  KEY `ol_meta_field_map_fk_2` (`meta_field_value_id`),
  CONSTRAINT `ol_meta_field_map_fk_1` FOREIGN KEY (`order_line_id`) REFERENCES `order_line` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ol_meta_field_map_fk_2` FOREIGN KEY (`meta_field_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_line_meta_fields_map`
--

DROP TABLE IF EXISTS `order_line_meta_fields_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_line_meta_fields_map` (
  `item_id` int(11) NOT NULL,
  `meta_field_id` int(11) NOT NULL,
  KEY `ol_meta_fields_map_fk_1` (`item_id`),
  KEY `ol_meta_fields_map_fk_2` (`meta_field_id`),
  CONSTRAINT `ol_meta_fields_map_fk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ol_meta_fields_map_fk_2` FOREIGN KEY (`meta_field_id`) REFERENCES `meta_field_name` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_line_type`
--

DROP TABLE IF EXISTS `order_line_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_line_type` (
  `id` int(11) NOT NULL,
  `editable` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_meta_field_map`
--

DROP TABLE IF EXISTS `order_meta_field_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_meta_field_map` (
  `order_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL,
  PRIMARY KEY (`order_id`,`meta_field_value_id`),
  KEY `order_meta_field_map_fk_2` (`meta_field_value_id`),
  CONSTRAINT `order_meta_field_map_fk_1` FOREIGN KEY (`order_id`) REFERENCES `purchase_order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_meta_field_map_fk_2` FOREIGN KEY (`meta_field_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_period`
--

DROP TABLE IF EXISTS `order_period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_period` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `unit_id` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_period_fk_1` (`entity_id`),
  KEY `order_period_fk_2` (`unit_id`),
  CONSTRAINT `order_period_fk_1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order_period_fk_2` FOREIGN KEY (`unit_id`) REFERENCES `period_unit` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_process`
--

DROP TABLE IF EXISTS `order_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_process` (
  `id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `invoice_id` int(11) DEFAULT NULL,
  `billing_process_id` int(11) DEFAULT NULL,
  `periods_included` int(11) DEFAULT NULL,
  `period_start` date DEFAULT NULL,
  `period_end` date DEFAULT NULL,
  `is_review` int(11) NOT NULL,
  `origin` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_order_process_in` (`invoice_id`),
  KEY `ix_uq_order_process_or_bp` (`order_id`,`billing_process_id`),
  KEY `ix_uq_order_process_or_in` (`order_id`,`invoice_id`),
  CONSTRAINT `order_process_fk_1` FOREIGN KEY (`order_id`) REFERENCES `purchase_order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `package_price`
--

DROP TABLE IF EXISTS `package_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package_price` (
  `id` int(11) NOT NULL,
  `pkg_prod_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `amount` decimal(10,2) unsigned NOT NULL,
  `discount` decimal(10,2) unsigned NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_offset` int(11) unsigned NOT NULL,
  `start_offset_unit` int(11) unsigned NOT NULL,
  `end_offset` int(11) unsigned NOT NULL,
  `end_offset_unit` int(11) unsigned NOT NULL,
  `OPTLOCK` int(11) NOT NULL,
  `deleted` smallint(6) NOT NULL DEFAULT '0',
  `period_id` int(11) DEFAULT NULL,
  `billing_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1_pkg_prod_id` (`pkg_prod_id`),
  KEY `FK2_pkg_price_type_id` (`type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `package_price_type`
--

DROP TABLE IF EXISTS `package_price_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package_price_type` (
  `id` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `package_product`
--

DROP TABLE IF EXISTS `package_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package_product` (
  `id` int(11) NOT NULL,
  `package_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `deleted` smallint(6) NOT NULL DEFAULT '0',
  `OPTLOCK` int(11) DEFAULT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK1_package_id` (`package_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `price_package`
--

DROP TABLE IF EXISTS `price_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `price_package` (
  `id` int(11) NOT NULL,
  `package_code` varchar(40) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `active_until` datetime DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `OPTLOCK` int(11) NOT NULL,
  `deleted` smallint(6) DEFAULT NULL,
  `active_since` datetime DEFAULT NULL,
  `mbg_days` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `price_package_FK1` (`entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `product_charge`
--

DROP TABLE IF EXISTS `product_charge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_charge` (
  `id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `charge_type` int(11) NOT NULL,
  `OPTLOCK` int(11) NOT NULL,
  `deleted` smallint(6) NOT NULL DEFAULT '0',
  `tax_code` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_charge_FK1` (`item_id`),
  KEY `product_charge_FK2` (`charge_type`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_charge_rate`
--

DROP TABLE IF EXISTS `product_charge_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_charge_rate` (
  `id` int(11) NOT NULL,
  `charge_id` int(11) NOT NULL,
  `currency_id` int(11) NOT NULL,
  `fixed_amount` decimal(22,2) NOT NULL DEFAULT '0.00',
  `scaled_amount` decimal(22,2) NOT NULL DEFAULT '0.00',
  `unit_id` int(11) NOT NULL,
  `dependee_id` int(11) DEFAULT NULL,
  `rum_id` smallint(6) DEFAULT NULL,
  `active_since` datetime DEFAULT NULL,
  `active_until` datetime DEFAULT NULL,
  `OPTLOCK` int(11) NOT NULL,
  `deleted` smallint(6) DEFAULT NULL,
  `salience` int(11) NOT NULL,
  `destination_map_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_charge_rate_FK1` (`charge_id`),
  KEY `product_charge_rate_FK2` (`currency_id`),
  KEY `product_charge_rate_FK3` (`dependee_id`),
  KEY `product_charge_rate_FK4` (`rum_id`),
  KEY `product_charge_rate_FK5` (`unit_id`),
  KEY `product_charge_rate_FK6` (`destination_map_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `provisioning_tag`
--

DROP TABLE IF EXISTS `provisioning_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provisioning_tag` (
  `id` int(9) NOT NULL,
  `code` varchar(30) NOT NULL,
  `level` int(9) DEFAULT NULL,
  `parent_id` int(9) DEFAULT NULL,
  `deleted` int(9) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `provisioning_tag_map`
--

DROP TABLE IF EXISTS `provisioning_tag_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provisioning_tag_map` (
  `id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `tag_id` int(9) DEFAULT NULL,
  `level` int(9) NOT NULL DEFAULT '0',
  `parent_id` int(11) DEFAULT NULL,
  `deleted` int(9) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `item_id_index` (`item_id`),
  KEY `ptm_FK_2` (`tag_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `provisioning_tag_map_info`
--

DROP TABLE IF EXISTS `provisioning_tag_map_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provisioning_tag_map_info` (
  `id` int(11) NOT NULL,
  `map_id` int(11) NOT NULL,
  `parameter` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `map_id_index` (`map_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `order_status`
--

DROP TABLE IF EXISTS `order_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_status` (
  `id` int(11) NOT NULL,
  `order_status_flag` int(11) DEFAULT NULL,
  `entity_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `paper_invoice_batch`
--

DROP TABLE IF EXISTS `paper_invoice_batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paper_invoice_batch` (
  `id` int(11) NOT NULL,
  `total_invoices` int(11) NOT NULL,
  `delivery_date` date DEFAULT NULL,
  `is_self_managed` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partner`
--

DROP TABLE IF EXISTS `partner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partner` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `total_payments` decimal(22,10) NOT NULL,
  `total_refunds` decimal(22,10) NOT NULL,
  `total_payouts` decimal(22,10) NOT NULL,
  `due_payout` decimal(22,10) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `type` varchar(250) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `commission_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `partner_fk_4` (`user_id`),
  CONSTRAINT `partner_fk_4` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partner_commission`
--

DROP TABLE IF EXISTS `partner_commission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partner_commission` (
  `id` int(11) NOT NULL,
  `amount` decimal(22,10) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `partner_id` int(11) DEFAULT NULL,
  `commission_process_run_id` int(11) DEFAULT NULL,
  `currency_id` int(11) DEFAULT NULL,
  KEY `partner_commission_currency_id_FK` (`currency_id`),
  CONSTRAINT `partner_commission_currency_id_FK` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partner_commission_exception`
--

DROP TABLE IF EXISTS `partner_commission_exception`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partner_commission_exception` (
  `id` int(11) NOT NULL,
  `partner_id` int(11) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `percentage` decimal(22,10) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partner_commission_proc_config`
--

DROP TABLE IF EXISTS `partner_commission_proc_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partner_commission_proc_config` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `next_run_date` date DEFAULT NULL,
  `period_unit_id` int(11) DEFAULT NULL,
  `period_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partner_commission_process_run`
--

DROP TABLE IF EXISTS `partner_commission_process_run`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partner_commission_process_run` (
  `id` int(11) NOT NULL,
  `run_date` date DEFAULT NULL,
  `period_start` date DEFAULT NULL,
  `period_end` date DEFAULT NULL,
  `entity_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partner_meta_field_map`
--

DROP TABLE IF EXISTS `partner_meta_field_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partner_meta_field_map` (
  `partner_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL,
  PRIMARY KEY (`partner_id`,`meta_field_value_id`),
  KEY `partner_meta_field_map_fk_2` (`meta_field_value_id`),
  CONSTRAINT `partner_meta_field_map_fk_1` FOREIGN KEY (`partner_id`) REFERENCES `partner` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `partner_meta_field_map_fk_2` FOREIGN KEY (`meta_field_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partner_payout`
--

DROP TABLE IF EXISTS `partner_payout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partner_payout` (
  `id` int(11) NOT NULL,
  `starting_date` date NOT NULL,
  `ending_date` date NOT NULL,
  `payments_amount` decimal(22,10) NOT NULL,
  `refunds_amount` decimal(22,10) NOT NULL,
  `balance_left` decimal(22,10) NOT NULL,
  `payment_id` int(11) DEFAULT NULL,
  `partner_id` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `partner_payout_fk_1` (`partner_id`),
  CONSTRAINT `partner_payout_fk_1` FOREIGN KEY (`partner_id`) REFERENCES `partner` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partner_referral_commission`
--

DROP TABLE IF EXISTS `partner_referral_commission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partner_referral_commission` (
  `id` int(11) NOT NULL,
  `referral_id` int(11) DEFAULT NULL,
  `referrer_id` int(11) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `percentage` decimal(22,10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `attempt` int(11) DEFAULT NULL,
  `result_id` int(11) DEFAULT NULL,
  `amount` decimal(22,10) NOT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_datetime` timestamp NULL DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  `method_id` int(11) DEFAULT NULL,
  `credit_card_id` int(11) DEFAULT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `is_refund` int(11) NOT NULL DEFAULT '0',
  `is_preauth` int(11) NOT NULL DEFAULT '0',
  `payment_id` int(11) DEFAULT NULL,
  `currency_id` int(11) NOT NULL,
  `payout_id` int(11) DEFAULT NULL,
  `ach_id` int(11) DEFAULT NULL,
  `balance` decimal(22,10) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `payment_period` int(11) DEFAULT NULL,
  `payment_notes` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `payment_i_2` (`user_id`,`create_datetime`),
  KEY `payment_i_3` (`user_id`,`balance`),
  KEY `payment_fk_1` (`ach_id`),
  KEY `payment_fk_4` (`credit_card_id`),
  KEY `payment_fk_2` (`currency_id`),
  KEY `payment_fk_6` (`method_id`),
  KEY `payment_fk_3` (`payment_id`),
  KEY `payment_fk_5` (`result_id`),
  CONSTRAINT `payment_fk_1` FOREIGN KEY (`ach_id`) REFERENCES `ach` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_fk_2` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_fk_3` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_fk_4` FOREIGN KEY (`credit_card_id`) REFERENCES `credit_card` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_fk_5` FOREIGN KEY (`result_id`) REFERENCES `payment_result` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_fk_6` FOREIGN KEY (`method_id`) REFERENCES `payment_method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_authorization`
--

DROP TABLE IF EXISTS `payment_authorization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_authorization` (
  `id` int(11) NOT NULL,
  `payment_id` int(11) DEFAULT NULL,
  `processor` varchar(40) NOT NULL,
  `code1` varchar(40) NOT NULL,
  `code2` varchar(40) DEFAULT NULL,
  `code3` varchar(40) DEFAULT NULL,
  `approval_code` varchar(20) DEFAULT NULL,
  `avs` varchar(20) DEFAULT NULL,
  `transaction_id` varchar(40) DEFAULT NULL,
  `md5` varchar(100) DEFAULT NULL,
  `card_code` varchar(100) DEFAULT NULL,
  `create_datetime` date NOT NULL,
  `response_message` varchar(200) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `create_datetime` (`create_datetime`),
  KEY `transaction_id` (`transaction_id`),
  KEY `payment_authorization_fk_1` (`payment_id`),
  CONSTRAINT `payment_authorization_fk_1` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_commission`
--

DROP TABLE IF EXISTS `payment_commission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_commission` (
  `id` int(11) NOT NULL,
  `invoice_id` int(11) DEFAULT NULL,
  `payment_amount` decimal(22,10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_info_cheque`
--

DROP TABLE IF EXISTS `payment_info_cheque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_info_cheque` (
  `id` int(11) NOT NULL,
  `payment_id` int(11) DEFAULT NULL,
  `bank` varchar(50) DEFAULT NULL,
  `cheque_number` varchar(50) DEFAULT NULL,
  `cheque_date` date DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `payment_info_cheque_fk_1` (`payment_id`),
  CONSTRAINT `payment_info_cheque_fk_1` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_information`
--

DROP TABLE IF EXISTS `payment_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_information` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `payment_method_id` int(11) NOT NULL,
  `processing_order` int(11) DEFAULT NULL,
  `deleted` int(11) NOT NULL,
  `OPTLOCK` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `payment_information_FK1` (`user_id`),
  KEY `payment_information_FK2` (`payment_method_id`),
  CONSTRAINT `payment_information_FK1` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_information_FK2` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_information_meta_fields_map`
--

DROP TABLE IF EXISTS `payment_information_meta_fields_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_information_meta_fields_map` (
  `payment_information_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL,
  KEY `payment_information_meta_fields_map_FK1` (`payment_information_id`),
  KEY `payment_information_meta_fields_map_FK2` (`meta_field_value_id`),
  CONSTRAINT `payment_information_meta_fields_map_FK1` FOREIGN KEY (`payment_information_id`) REFERENCES `payment_information` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_information_meta_fields_map_FK2` FOREIGN KEY (`meta_field_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_instrument_info`
--

DROP TABLE IF EXISTS `payment_instrument_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_instrument_info` (
  `id` int(11) NOT NULL,
  `result_id` int(11) NOT NULL,
  `method_id` int(11) NOT NULL,
  `instrument_id` int(11) NOT NULL,
  `payment_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `payment_instrument_info_FK1` (`result_id`),
  KEY `payment_instrument_info_FK2` (`method_id`),
  KEY `payment_instrument_info_FK3` (`instrument_id`),
  KEY `payment_instrument_info_FK4` (`payment_id`),
  CONSTRAINT `payment_instrument_info_FK1` FOREIGN KEY (`result_id`) REFERENCES `payment_result` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_instrument_info_FK2` FOREIGN KEY (`method_id`) REFERENCES `payment_method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_instrument_info_FK3` FOREIGN KEY (`instrument_id`) REFERENCES `payment_information` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_instrument_info_FK4` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_invoice`
--

DROP TABLE IF EXISTS `payment_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_invoice` (
  `id` int(11) NOT NULL,
  `payment_id` int(11) DEFAULT NULL,
  `invoice_id` int(11) DEFAULT NULL,
  `amount` decimal(22,10) DEFAULT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_uq_payment_inv_map_pa_in` (`payment_id`,`invoice_id`),
  KEY `payment_invoice_fk_1` (`invoice_id`),
  CONSTRAINT `payment_invoice_fk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_invoice_fk_2` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_meta_field_map`
--

DROP TABLE IF EXISTS `payment_meta_field_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_meta_field_map` (
  `payment_id` int(11) NOT NULL,
  `meta_field_value_id` int(11) NOT NULL,
  PRIMARY KEY (`payment_id`,`meta_field_value_id`),
  KEY `payment_meta_field_map_fk_2` (`meta_field_value_id`),
  CONSTRAINT `payment_meta_field_map_fk_1` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_meta_field_map_fk_2` FOREIGN KEY (`meta_field_value_id`) REFERENCES `meta_field_value` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_method`
--

DROP TABLE IF EXISTS `payment_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_method` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_method_account_type_map`
--

DROP TABLE IF EXISTS `payment_method_account_type_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_method_account_type_map` (
  `payment_method_id` int(11) NOT NULL,
  `account_type_id` int(11) NOT NULL,
  KEY `payment_method_account_type_map_FK1` (`payment_method_id`),
  KEY `payment_method_account_type_map_FK2` (`account_type_id`),
  CONSTRAINT `payment_method_account_type_map_FK1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_method_account_type_map_FK2` FOREIGN KEY (`account_type_id`) REFERENCES `account_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_method_meta_fields_map`
--

DROP TABLE IF EXISTS `payment_method_meta_fields_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_method_meta_fields_map` (
  `payment_method_id` int(11) NOT NULL,
  `meta_field_id` int(11) NOT NULL,
  KEY `payment_method_meta_fields_map_FK1` (`payment_method_id`),
  KEY `payment_method_meta_fields_map_FK2` (`meta_field_id`),
  CONSTRAINT `payment_method_meta_fields_map_FK1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_method_meta_fields_map_FK2` FOREIGN KEY (`meta_field_id`) REFERENCES `meta_field_name` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_method_template`
--

DROP TABLE IF EXISTS `payment_method_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_method_template` (
  `id` int(11) NOT NULL,
  `template_name` varchar(20) NOT NULL,
  `OPTLOCK` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `template_name` (`template_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `foreign_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `permission_fk_1` (`type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permission_role_map`
--

DROP TABLE IF EXISTS `permission_role_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission_role_map` (
  `permission_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  KEY `permission_id` (`permission_id`,`role_id`),
  KEY `permission_role_map_fk_1` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permission_type`
--

DROP TABLE IF EXISTS `permission_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission_type` (
  `id` int(11) NOT NULL,
  `description` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permission_user`
--

DROP TABLE IF EXISTS `permission_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission_user` (
  `permission_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_grant` smallint(6) NOT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `permission_id` (`permission_id`,`user_id`),
  KEY `permission_user_fk_1` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rating_event_type`
--

DROP TABLE IF EXISTS `rating_event_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating_event_type` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `event_name` varchar(60) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `entity_id` (`entity_id`,`event_name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rate_dependee`
--

DROP TABLE IF EXISTS `rate_dependee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rate_dependee` (
  `id` int(11) NOT NULL,
  `currency_id` int(11) DEFAULT NULL,
  `min_balance` decimal(22,2) DEFAULT '-999999.00',
  `max_balance` decimal(22,2) DEFAULT '0.00',
  `dependency_type` smallint(6) DEFAULT NULL,
  `OPTLOCK` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rate_dependee_FK1` (`currency_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rate_dependency_type`
--

DROP TABLE IF EXISTS `rate_dependency_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rate_dependency_type` (
  `id` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rum_type`
--

DROP TABLE IF EXISTS `rum_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rum_type` (
  `id` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status_id` int(11) NOT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `order_id` int(11) NOT NULL,
  `order_line_id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `service_type` varchar(70) DEFAULT NULL,
  `deleted` smallint(6) NOT NULL DEFAULT '0',
  `OPTLOCK` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `service_FK1` (`user_id`),
  KEY `service_FK2` (`status_id`),
  KEY `service_FK3` (`order_id`),
  KEY `service_FK4` (`order_line_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_alias`
--

DROP TABLE IF EXISTS `service_alias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service_alias` (
  `id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  `alias_name` varchar(60) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated_date` timestamp NULL DEFAULT NULL,
  `deleted` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `service_feature`
--

DROP TABLE IF EXISTS `service_feature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service_feature` (
  `id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  `prov_tag_map_id` int(11) NOT NULL,
  `deleted` int(9) NOT NULL DEFAULT '0',
  `parent_id` int(11) DEFAULT NULL,
  `level` int(9) DEFAULT '0',
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `service_id_index` (`service_id`),
  KEY `service_feat_FK_2` (`prov_tag_map_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_feature_info`
--

DROP TABLE IF EXISTS `service_feature_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service_feature_info` (
  `id` int(11) NOT NULL,
  `service_feature_id` int(11) NOT NULL,
  `parameter` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `service_feature_id_index` (`service_feature_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `support_ticket`
--

DROP TABLE IF EXISTS `support_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `support_ticket` (
  `id` int(11) NOT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `assigned_user_id` int(11) NOT NULL,
  `status_id` smallint(6) NOT NULL,
  `created_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `payment_method_template_meta_fields_map`
--

DROP TABLE IF EXISTS `payment_method_template_meta_fields_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_method_template_meta_fields_map` (
  `method_template_id` int(11) NOT NULL,
  `meta_field_id` int(11) NOT NULL,
  KEY `payment_method_template_meta_fields_map_FK1` (`method_template_id`),
  KEY `payment_method_template_meta_fields_map_FK2` (`meta_field_id`),
  CONSTRAINT `payment_method_template_meta_fields_map_FK1` FOREIGN KEY (`method_template_id`) REFERENCES `payment_method_template` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_method_template_meta_fields_map_FK2` FOREIGN KEY (`meta_field_id`) REFERENCES `meta_field_name` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_method_type`
--

DROP TABLE IF EXISTS `payment_method_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_method_type` (
  `id` int(11) NOT NULL,
  `method_name` varchar(20) NOT NULL,
  `is_recurring` bit(1) NOT NULL DEFAULT b'0',
  `entity_id` int(11) NOT NULL,
  `template_id` int(11) NOT NULL,
  `OPTLOCK` int(11) NOT NULL,
  `all_account_type` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `payment_method_type_FK1` (`entity_id`),
  KEY `payment_method_type_FK2` (`template_id`),
  CONSTRAINT `payment_method_type_FK1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `payment_method_type_FK2` FOREIGN KEY (`template_id`) REFERENCES `payment_method_template` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_result`
--

DROP TABLE IF EXISTS `payment_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_result` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `period_unit`
--

DROP TABLE IF EXISTS `period_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `period_unit` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pluggable_task`
--

DROP TABLE IF EXISTS `pluggable_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pluggable_task` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  `processing_order` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  `notes` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pluggable_task_fk_2` (`entity_id`),
  CONSTRAINT `pluggable_task_fk_2` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pluggable_task_parameter`
--

DROP TABLE IF EXISTS `pluggable_task_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pluggable_task_parameter` (
  `id` int(11) NOT NULL,
  `task_id` int(11) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `int_value` int(11) DEFAULT NULL,
  `str_value` varchar(500) DEFAULT NULL,
  `float_value` decimal(22,10) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pluggable_task_parameter_fk_1` (`task_id`),
  CONSTRAINT `pluggable_task_parameter_fk_1` FOREIGN KEY (`task_id`) REFERENCES `pluggable_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pluggable_task_type`
--

DROP TABLE IF EXISTS `pluggable_task_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pluggable_task_type` (
  `id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `class_name` varchar(200) NOT NULL,
  `min_parameters` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pluggable_task_type_fk_1` (`category_id`),
  CONSTRAINT `pluggable_task_type_fk_1` FOREIGN KEY (`category_id`) REFERENCES `pluggable_task_type_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pluggable_task_type_category`
--

DROP TABLE IF EXISTS `pluggable_task_type_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pluggable_task_type_category` (
  `id` int(11) NOT NULL,
  `interface_name` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `preference`
--

DROP TABLE IF EXISTS `preference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preference` (
  `id` int(11) NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  `table_id` int(11) NOT NULL,
  `foreign_id` int(11) NOT NULL,
  `value` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `preference_fk_2` (`table_id`),
  KEY `preference_fk_1` (`type_id`),
  CONSTRAINT `preference_fk_1` FOREIGN KEY (`type_id`) REFERENCES `preference_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `preference_fk_2` FOREIGN KEY (`table_id`) REFERENCES `jbilling_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `preference_type`
--

DROP TABLE IF EXISTS `preference_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preference_type` (
  `id` int(11) NOT NULL,
  `def_value` varchar(200) DEFAULT NULL,
  `validation_rule_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `preference_type_vr_fk_1` (`validation_rule_id`),
  CONSTRAINT `preference_type_vr_fk_1` FOREIGN KEY (`validation_rule_id`) REFERENCES `validation_rule` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `process_run`
--

DROP TABLE IF EXISTS `process_run`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `process_run` (
  `id` int(11) NOT NULL,
  `process_id` int(11) DEFAULT NULL,
  `run_date` date NOT NULL,
  `started` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `finished` timestamp NULL DEFAULT NULL,
  `payment_finished` timestamp NULL DEFAULT NULL,
  `invoices_generated` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `process_run_fk_1` (`process_id`),
  KEY `process_run_fk_2` (`status_id`),
  CONSTRAINT `process_run_fk_1` FOREIGN KEY (`process_id`) REFERENCES `billing_process` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `process_run_fk_2` FOREIGN KEY (`status_id`) REFERENCES `generic_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `process_run_total`
--

DROP TABLE IF EXISTS `process_run_total`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `process_run_total` (
  `id` int(11) NOT NULL,
  `process_run_id` int(11) DEFAULT NULL,
  `currency_id` int(11) NOT NULL,
  `total_invoiced` decimal(22,10) DEFAULT NULL,
  `total_paid` decimal(22,10) DEFAULT NULL,
  `total_not_paid` decimal(22,10) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `process_run_total_fk_1` (`currency_id`),
  KEY `process_run_total_fk_2` (`process_run_id`),
  CONSTRAINT `process_run_total_fk_1` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `process_run_total_fk_2` FOREIGN KEY (`process_run_id`) REFERENCES `process_run` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `process_run_total_pm`
--

DROP TABLE IF EXISTS `process_run_total_pm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `process_run_total_pm` (
  `id` int(11) NOT NULL,
  `process_run_total_id` int(11) DEFAULT NULL,
  `payment_method_id` int(11) DEFAULT NULL,
  `total` decimal(22,10) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `bp_pm_index_total` (`process_run_total_id`),
  KEY `process_run_total_pm_fk_1` (`payment_method_id`),
  CONSTRAINT `process_run_total_pm_fk_1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `process_run_user`
--

DROP TABLE IF EXISTS `process_run_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `process_run_user` (
  `id` int(11) NOT NULL,
  `process_run_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `process_run_user_fk_1` (`process_run_id`),
  KEY `process_run_user_fk_2` (`user_id`),
  CONSTRAINT `process_run_user_fk_1` FOREIGN KEY (`process_run_id`) REFERENCES `process_run` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `process_run_user_fk_2` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promotion` (
  `id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL,
  `code` varchar(50) NOT NULL,
  `notes` varchar(200) DEFAULT NULL,
  `once` int(11) NOT NULL,
  `since` date DEFAULT NULL,
  `until` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_promotion_code` (`code`),
  KEY `promotion_fk_1` (`item_id`),
  CONSTRAINT `promotion_fk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `user_balance`
--

DROP TABLE IF EXISTS `user_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_balance` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `active_since` date NOT NULL,
  `active_until` date DEFAULT NULL,
  `create_datetime` date NOT NULL DEFAULT '0000-00-00',
  `currency_id` int(11) NOT NULL,
  `deleted` smallint(6) NOT NULL DEFAULT '0',
  `optlock` int(11) NOT NULL,
  `balance` decimal(22,10) DEFAULT NULL,
  `order_id` int(11) NOT NULL,
  `order_line_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_balance_fk_1` (`currency_id`),
  KEY `user_balance_fk_2` (`user_id`),
  KEY `user_balance_fk_3` (`order_line_id`),
  KEY `user_balance_fk_4` (`order_id`),
  KEY `idx_ub_user_id` (`user_id`),
  KEY `idx_ub_active_since` (`active_since`),
  KEY `idx_ub_order_id` (`order_id`),
  KEY `idx_ub_order_line_id` (`order_line_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `promotion_user_map`
--

DROP TABLE IF EXISTS `promotion_user_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promotion_user_map` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `promotion_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`,`promotion_id`),
  KEY `promotion_user_map_i_2` (`user_id`,`promotion_id`),
  KEY `promotion_user_map_fk_2` (`promotion_id`),
  CONSTRAINT `promotion_user_map_fk_1` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `promotion_user_map_fk_2` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `purchase_order`
--

DROP TABLE IF EXISTS `purchase_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_order` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `period_id` int(11) DEFAULT NULL,
  `billing_type_id` int(11) NOT NULL,
  `active_since` date DEFAULT NULL,
  `active_until` date DEFAULT NULL,
  `cycle_start` date DEFAULT NULL,
  `create_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `next_billable_day` date DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `status_id` int(11) NOT NULL,
  `currency_id` int(11) NOT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `notify` int(11) DEFAULT NULL,
  `last_notified` timestamp NULL DEFAULT NULL,
  `notification_step` int(11) DEFAULT NULL,
  `due_date_unit_id` int(11) DEFAULT NULL,
  `due_date_value` int(11) DEFAULT NULL,
  `df_fm` int(11) DEFAULT NULL,
  `anticipate_periods` int(11) DEFAULT NULL,
  `own_invoice` int(11) DEFAULT NULL,
  `notes` varchar(200) DEFAULT NULL,
  `notes_in_invoice` int(11) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  `primary_order_id` int(11) DEFAULT NULL,
  `prorate_flag` bit(1) NOT NULL DEFAULT b'0',
  `parent_order_id` int(11) DEFAULT NULL,
  `cancellation_fee_type` varchar(50) DEFAULT NULL,
  `cancellation_fee` int(11) DEFAULT NULL,
  `cancellation_fee_percentage` int(11) DEFAULT NULL,
  `cancellation_maximum_fee` int(11) DEFAULT NULL,
  `cancellation_minimum_period` int(11) DEFAULT NULL,
  `reseller_order` int(11) DEFAULT NULL,
  `free_usage_quantity` decimal(22,10) DEFAULT NULL,
  `deleted_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_purchase_order_date` (`user_id`,`create_datetime`),
  KEY `purchase_order_i_notif` (`active_until`,`notification_step`),
  KEY `purchase_order_i_user` (`user_id`,`deleted`),
  KEY `purchase_order_fk_2` (`billing_type_id`),
  KEY `purchase_order_fk_5` (`created_by`),
  KEY `purchase_order_fk_1` (`currency_id`),
  KEY `purchase_order_fk_3` (`period_id`),
  KEY `order_primary_order_fk_1` (`primary_order_id`),
  KEY `purchase_order_parent__order_id_fk` (`parent_order_id`),
  CONSTRAINT `order_primary_order_fk_1` FOREIGN KEY (`primary_order_id`) REFERENCES `purchase_order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `purchase_order_fk_1` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `purchase_order_fk_2` FOREIGN KEY (`billing_type_id`) REFERENCES `order_billing_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `purchase_order_fk_3` FOREIGN KEY (`period_id`) REFERENCES `order_period` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `purchase_order_fk_4` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `purchase_order_fk_5` FOREIGN KEY (`created_by`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `purchase_order_parent__order_id_fk` FOREIGN KEY (`parent_order_id`) REFERENCES `purchase_order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `purchased_bundle`
--

DROP TABLE IF EXISTS `purchased_bundle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchased_bundle` (
  `id` int(11) NOT NULL,
  `bundle_id` int(11) NOT NULL,
  `status_id` smallint(6) NOT NULL,
  `valid_from` date NOT NULL,
  `valid_to` date DEFAULT NULL,
  `update_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_datetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_purchase_bundle_1` (`bundle_id`),
  KEY `idx_purchase_bundle_2` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `purchased_bundle_product`
--

DROP TABLE IF EXISTS `purchased_bundle_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchased_bundle_product` (
  `id` int(11) NOT NULL,
  `pb_id` int(11) NOT NULL COMMENT 'purchased_bundle object  id',
  `product_id` int(11) NOT NULL,
  `recurring_charge` decimal(10,2) DEFAULT '0.00',
  `recurring_discount` decimal(10,2) DEFAULT '0.00',
  `recurring_start_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `recurring_end_time` timestamp NULL DEFAULT NULL,
  `oneoff_charge` decimal(10,2) DEFAULT '0.00',
  `oneoff_discount` decimal(10,2) DEFAULT '0.00',
  `oneoff_start_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `oneoff_end_time` timestamp NULL DEFAULT NULL,
  `usage_charge` decimal(10,2) DEFAULT '0.00',
  `usage_discount` decimal(10,2) DEFAULT '0.00',
  `usage_start_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `usage_end_time` timestamp NULL DEFAULT NULL,
  `cancel_charge` decimal(10,2) DEFAULT '0.00',
  `cancel_discount` decimal(10,2) DEFAULT '0.00',
  `cancel_start_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `cancel_end_time` timestamp NULL DEFAULT NULL,
  `oneoff_order_id` int(11) DEFAULT NULL,
  `recurring_order_id` int(11) DEFAULT NULL,
  `cancel_order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_purchased_bundle_prod_1` (`pb_id`),
  KEY `idx_purchased_bundle_prod_2` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `rating_unit`
--

DROP TABLE IF EXISTS `rating_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating_unit` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `price_unit_name` varchar(50) DEFAULT NULL,
  `increment_unit_name` varchar(50) DEFAULT NULL,
  `increment_unit_quantity` decimal(22,10) DEFAULT NULL,
  `can_be_deleted` bit(1) DEFAULT b'1',
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rating_unit_entity_id_FK` (`entity_id`),
  CONSTRAINT `rating_unit_entity_id_FK` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recent_item`
--

DROP TABLE IF EXISTS `recent_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recent_item` (
  `id` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `object_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `file_name` varchar(500) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report_parameter`
--

DROP TABLE IF EXISTS `report_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_parameter` (
  `id` int(11) NOT NULL,
  `report_id` int(11) NOT NULL,
  `dtype` varchar(10) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report_type`
--

DROP TABLE IF EXISTS `report_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_type` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reseller_entityid_map`
--

DROP TABLE IF EXISTS `reseller_entityid_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reseller_entityid_map` (
  `entity_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  KEY `reseller_entityid_map_fk_1` (`entity_id`),
  KEY `reseller_entityid_map_fk_2` (`user_id`),
  CONSTRAINT `reseller_entityid_map_fk_1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `reseller_entityid_map_fk_2` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reserved_amounts`
--

DROP TABLE IF EXISTS `reserved_amounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reserved_amounts` (
  `id` int(11) NOT NULL DEFAULT '0',
  `session_id` int(11) DEFAULT NULL,
  `ts_created` timestamp NULL DEFAULT NULL,
  `currency_id` int(11) DEFAULT NULL,
  `reserved_amount` decimal(22,10) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `data` text,
  `quantity` decimal(22,10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reservations_session` (`session_id`),
  CONSTRAINT `fk_reservations_session` FOREIGN KEY (`session_id`) REFERENCES `charge_sessions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reset_password_code`
--

DROP TABLE IF EXISTS `reset_password_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reset_password_code` (
  `base_user_id` int(11) NOT NULL,
  `date_created` datetime DEFAULT NULL,
  `token` varchar(32) NOT NULL,
  `new_password` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`token`),
  UNIQUE KEY `base_user_id` (`base_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `role_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_entity_id_fk` (`entity_id`),
  CONSTRAINT `role_entity_id_fk` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `table_name` varchar(50) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `optlock` int(11) NOT NULL,
  `root_table` bit(1) DEFAULT b'0',
  `output_field_name` varchar(150) DEFAULT NULL,
  `default_route` varchar(255) DEFAULT NULL,
  `route_table` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shortcut`
--

DROP TABLE IF EXISTS `shortcut`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shortcut` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `controller` varchar(255) NOT NULL,
  `action` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `object_id` int(11) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tax_code`
--

DROP TABLE IF EXISTS `tax_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tax_code` (
  `id` int(11) DEFAULT NULL,
  `tax_code` varchar(60) DEFAULT NULL,
  `country` varchar(60) DEFAULT NULL,
  `rate` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sure_tax_txn_log`
--

DROP TABLE IF EXISTS `sure_tax_txn_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sure_tax_txn_log` (
  `id` int(11) NOT NULL,
  `txn_id` varchar(20) NOT NULL,
  `txn_type` varchar(10) NOT NULL,
  `txn_data` longtext NOT NULL,
  `txn_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `resp_trans_id` int(11) DEFAULT NULL,
  `request_type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tab`
--

DROP TABLE IF EXISTS `tab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tab` (
  `id` int(11) NOT NULL,
  `message_code` varchar(50) DEFAULT NULL,
  `controller_name` varchar(50) DEFAULT NULL,
  `access_url` varchar(50) DEFAULT NULL,
  `required_role` varchar(50) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `default_order` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tab_configuration`
--

DROP TABLE IF EXISTS `tab_configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tab_configuration` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tab_configuration_fk_1` (`user_id`),
  CONSTRAINT `tab_configuration_fk_1` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tab_configuration_tab`
--

DROP TABLE IF EXISTS `tab_configuration_tab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tab_configuration_tab` (
  `id` int(11) NOT NULL,
  `tab_id` int(11) DEFAULT NULL,
  `tab_configuration_id` int(11) DEFAULT NULL,
  `display_order` int(11) DEFAULT NULL,
  `visible` bit(1) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tab_configuration_tab_fk_1` (`tab_id`),
  KEY `tab_configuration_tab_fk_2` (`tab_configuration_id`),
  CONSTRAINT `tab_configuration_tab_fk_1` FOREIGN KEY (`tab_id`) REFERENCES `tab` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tab_configuration_tab_fk_2` FOREIGN KEY (`tab_configuration_id`) REFERENCES `tab_configuration` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ticket_details`
--

DROP TABLE IF EXISTS `ticket_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket_details` (
  `id` int(11) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `created_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ticket_body` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ticket_status`
--

DROP TABLE IF EXISTS `ticket_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket_status` (
  `id` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_code`
--

DROP TABLE IF EXISTS `user_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_code` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `identifier` varchar(55) NOT NULL,
  `external_ref` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `type_desc` varchar(250) DEFAULT NULL,
  `valid_from` date DEFAULT NULL,
  `valid_to` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_code_link`
--

DROP TABLE IF EXISTS `user_code_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_code_link` (
  `id` int(11) NOT NULL,
  `user_code_id` int(11) NOT NULL,
  `object_type` varchar(50) NOT NULL,
  `object_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_credit_card_map`
--

DROP TABLE IF EXISTS `user_credit_card_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_credit_card_map` (
  `user_id` int(11) DEFAULT NULL,
  `credit_card_id` int(11) DEFAULT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `user_credit_card_map_i_2` (`user_id`,`credit_card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_password_map`
--

DROP TABLE IF EXISTS `user_password_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_password_map` (
  `id` int(11) NOT NULL,
  `base_user_id` int(11) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `new_password` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role_map`
--

DROP TABLE IF EXISTS `user_role_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role_map` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `role_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `user_role_map_i_2` (`user_id`,`role_id`),
  KEY `user_role_map_fk_1` (`role_id`),
  CONSTRAINT `user_role_map_fk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_role_map_fk_2` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_status`
--

DROP TABLE IF EXISTS `user_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_status` (
  `id` int(11) NOT NULL,
  `can_login` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_device`
--

DROP TABLE IF EXISTS `user_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_device` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `device_id` int(11) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated_date` timestamp NULL DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `order_line_id` int(11) DEFAULT NULL,
  `telephone_number` varchar(60) DEFAULT NULL,
  `ip` varchar(60) DEFAULT NULL,
  `deleted` smallint(6) NOT NULL DEFAULT '0',
  `OPTLOCK` int(11) NOT NULL,
  `ext_id1` varchar(60) DEFAULT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_device_FK1` (`user_id`),
  KEY `user_device_FK2` (`device_id`),
  KEY `order_id_FK4` (`order_id`),
  KEY `order_line_id_FK5` (`order_line_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `validation_rule`
--

DROP TABLE IF EXISTS `validation_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `validation_rule` (
  `id` int(11) NOT NULL,
  `rule_type` varchar(25) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `optlock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `validation_rule_attributes`
--

DROP TABLE IF EXISTS `validation_rule_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `validation_rule_attributes` (
  `validation_rule_id` int(11) NOT NULL,
  `attribute_name` varchar(255) NOT NULL,
  `attribute_value` varchar(255) DEFAULT NULL,
  KEY `validation_rule_fk_2` (`validation_rule_id`),
  CONSTRAINT `validation_rule_fk_2` FOREIGN KEY (`validation_rule_id`) REFERENCES `validation_rule` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


--
-- Table structure for table `c_rate`
--

DROP TABLE IF EXISTS `c_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c_rate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prefix` varchar(45) NOT NULL,
  `destination` varchar(45) NOT NULL,
  `version` smallint(6) NOT NULL DEFAULT '1',
  `deleted` smallint(6) NOT NULL DEFAULT '0',
  `flat_rate` decimal(22,10) DEFAULT NULL,
  `conn_charge` decimal(22,10) DEFAULT NULL,
  `scaled_rate` decimal(22,10) DEFAULT NULL,
  `rate_plan` int(11) DEFAULT NULL,
  `call_type` varchar(40) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `valid_from` date NOT NULL,
  `Valid_to` date DEFAULT NULL,
  `last_updated_date` date NOT NULL,
  `rate_type` varchar(100) DEFAULT NULL,
  `entity_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ir_prefix` (`prefix`),
  KEY `idx_ir_valid_from` (`valid_from`),
  KEY `idx_ir_destination` (`destination`)
) ENGINE=MyISAM AUTO_INCREMENT=740156 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ex_rate`
--

DROP TABLE IF EXISTS `ex_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_rate` (
  `id` int(11) NOT NULL,
  `prefix` varchar(45) NOT NULL,
  `destination` varchar(45) NOT NULL,
  `field1` varchar(45) NOT NULL,
  `field2` varchar(45) NOT NULL,
  `version` smallint(6) NOT NULL DEFAULT '1',
  `deleted` smallint(6) NOT NULL DEFAULT '0',
  `rate_plan` int(11) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `valid_from` date NOT NULL,
  `Valid_to` date DEFAULT NULL,
  `last_updated_date` date NOT NULL,
  `entity_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ir_prefix` (`prefix`),
  KEY `idx_ir_valid_from` (`valid_from`),
  KEY `idx_ir_destination` (`destination`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rate`
--

DROP TABLE IF EXISTS `rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rate` (
  `id` int(11) NOT NULL,
  `prefix` varchar(45) NOT NULL,
  `destination` varchar(45) NOT NULL,
  `version` smallint(6) NOT NULL DEFAULT '1',
  `deleted` smallint(6) NOT NULL DEFAULT '0',
  `flat_rate` decimal(22,10) DEFAULT NULL,
  `conn_charge` decimal(22,10) DEFAULT NULL,
  `scaled_rate` decimal(22,10) DEFAULT NULL,
  `rate_plan` int(11) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `valid_from` date NOT NULL,
  `Valid_to` date DEFAULT NULL,
  `last_updated_date` date NOT NULL,
  `rate_type` varchar(100) DEFAULT NULL,
  `entity_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ir_prefix` (`prefix`),
  KEY `idx_ir_valid_from` (`valid_from`),
  KEY `idx_ir_destination` (`destination`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `world_zone_map`
--

DROP TABLE IF EXISTS `world_zone_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `world_zone_map` (
  `MAP_GROUP` varchar(10) DEFAULT NULL,
  `TIER_CODE` varchar(10) DEFAULT NULL,
  `WORLD_ZONE` varchar(10) DEFAULT NULL,
  `id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


--
-- Table structure for table `destination_map`
--

DROP TABLE IF EXISTS `destination_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `destination_map` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MAP_GROUP` varchar(24) DEFAULT NULL,
  `PREFIX` varchar(24) DEFAULT NULL,
  `TIER_CODE` varchar(24) DEFAULT NULL,
  `DESCRIPTION` varchar(100) DEFAULT NULL,
  `CATEGORY` varchar(50) DEFAULT NULL,
  `RANK` int(11) NOT NULL DEFAULT '0',
  `deleted` int(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `IDX_DESTINATION_MAP` (`MAP_GROUP`,`PREFIX`)
) ENGINE=MyISAM AUTO_INCREMENT=97165 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `holiday_map`
--

DROP TABLE IF EXISTS `holiday_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `holiday_map` (
  `MAP_GROUP` varchar(24) DEFAULT NULL,
  `DAY` int(11) DEFAULT NULL,
  `MONTH` int(11) DEFAULT NULL,
  `YEAR` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(24) DEFAULT NULL,
  `id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `uploadcdr`
--

DROP TABLE IF EXISTS `uploadcdr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uploadcdr` (
  `Name` varchar(24) DEFAULT NULL,
  `id` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `Status` varchar(24) DEFAULT NULL,
  `Type` varchar(24) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET character_set_client = @saved_cs_client */;
-- Dump completed on 2016-05-22  8:02:37
