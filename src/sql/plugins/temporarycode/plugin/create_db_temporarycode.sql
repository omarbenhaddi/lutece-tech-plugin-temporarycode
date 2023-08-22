
--
-- Structure for table temporary_code_generated
--

DROP TABLE IF EXISTS temporary_code_generated;
CREATE TABLE temporary_code_generated (
id_temporary_code int AUTO_INCREMENT,
user_id varchar(255) default '' NOT NULL,
code varchar(255) default '' NOT NULL,
action_name varchar(255) default '' NOT NULL,
created_date timestamp NOT NULL,
used_date timestamp,
validity_date timestamp NOT NULL,
used SMALLINT,
PRIMARY KEY (id_temporary_code)
);

--
-- Structure for table temporary_code_config
--

DROP TABLE IF EXISTS temporary_code_config;
CREATE TABLE temporary_code_config (
id_temporary_code_config int AUTO_INCREMENT,
code_length int default '0' NOT NULL,
validity_time int default '0' NOT NULL,
character_type varchar(255) default '' NOT NULL,
default_temporary_code int default '0' NOT NULL,
PRIMARY KEY (id_temporary_code_config)
);

INSERT INTO temporary_code_config VALUES (1,4,10,'NUMERIC',1);
