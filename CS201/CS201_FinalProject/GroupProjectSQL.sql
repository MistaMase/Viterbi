SET GLOBAL time_zone = '-7:00';

DROP DATABASE IF EXISTS UserProfile;
CREATE DATABASE UserProfile;
USE UserProfile;

CREATE TABLE Profile (
	UserID INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    Username VARCHAR(50) NOT NULL,
    Password VARCHAR(50) NOT NULL,
    Credits INT(11) NOT NULL
);