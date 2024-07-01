DROP DATABASE IF EXISTS sweater;

CREATE DATABASE sweater;

USE sweater;

CREATE TABLE user (
	user_id VARCHAR(20) PRIMARY KEY,
    password VARCHAR(20) NOT NULL,
    nickname VARCHAR(20) UNIQUE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    picture TEXT,
    introduce TEXT
);

CREATE TABLE mountain (
	name VARCHAR(30) NOT NULL UNIQUE,
    code VARCHAR(30) NOT NULL PRIMARY KEY,
    region VARCHAR(20) NOT NULL
);

CREATE TABLE mtdetail (
	mntn_code VARCHAR(30) NOT NULL,
    fid INT NOT NULL,
    region VARCHAR(20),
    pmntn_lt  DECIMAL NOT NULL,
    pmntn_dffl VARCHAR(20) NOT NULL,
    pmntn_uppl INT NOT NULL,
    pmntn_godn INT NOT NULL,
    paths LONGTEXT NOT NULL,
    
    PRIMARY KEY(MNTN_CODE, FID),
    
    FOREIGN KEY (MNTN_CODE)
    REFERENCES mountain (code)
);

CREATE TABLE board (
	board_id INT AUTO_INCREMENT PRIMARY KEY,
    region VARCHAR(20) NOT NULL,
    tag VARCHAR(20) NOT NULL,
    writer VARCHAR(20) NOT NULL,
    title VARCHAR(30) NOT NULL,
    header TEXT NOT NULL,
    content LONGTEXT NOT NULL,
    start_date DATE,
    end_date DATE,
    heart INT default 0,
    media TEXT NOT NULL,
    trail TEXT NOT NULL,
    hiking_time INT NOT NULL,
    hiking_dist INT NOT NULL,
    
    FOREIGN KEY (writer)
    REFERENCES user (nickname)
    ON DELETE CASCADE
);

CREATE TABLE board_heart (
	user_id VARCHAR(20),
    board_id INT,
    
    PRIMARY KEY(user_id, board_id),
    
    FOREIGN KEY(user_id)
    REFERENCES user(user_id),
    
    FOREIGN KEY(board_id)
    REFERENCES board(board_id)
    ON DELETE CASCADE
);

CREATE TABLE review (
	board_id INT NOT NULL,
    review_id INT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(30) NOT NULL,
    writer VARCHAR(20) NOT NULL,
    content TEXT NOT NULL,
    
    FOREIGN KEY (board_id)
    REFERENCES board(board_id)
    ON DELETE CASCADE,
    
    FOREIGN KEY(writer)
    REFERENCES user (nickname)
    ON DELETE CASCADE
);

CREATE TABLE thunder (
	thunder_id INT AUTO_INCREMENT PRIMARY KEY,
    region VARCHAR(20) NOT NULL,
    tag VARCHAR(20) NOT NULL,
    writer VARCHAR(20) NOT NULL,
    title VARCHAR(30) NOT NULL,
    header TEXT NOT NULL,
    content LONGTEXT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
	heart int default 0,
    media TEXT NOT NULL,
    trail TEXT NOT NULL,
    hiking_time INT NOT NULL,
    hiking_dist INT NOT NULL,
    
    FOREIGN KEY (writer)
    REFERENCES user (nickname)
    ON DELETE CASCADE
);

CREATE TABLE thunder_heart (
	user_id VARCHAR(20),
    thunder_id INT,
    
    PRIMARY KEY(user_id, thunder_id),
    
	FOREIGN KEY(user_id)
    REFERENCES user(user_id),
    
    FOREIGN KEY(thunder_id)
    REFERENCES thunder(thunder_id)
    ON DELETE CASCADE
);

CREATE TABLE comment (
	thunder_id INT NOT NULL,
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(30) NOT NULL,
    writer VARCHAR(20) NOT NULL,
    content TEXT NOT NULL,
    
    FOREIGN KEY (thunder_id)
    REFERENCES thunder(thunder_id)
    ON DELETE CASCADE,
    
    FOREIGN KEY(writer)
    REFERENCES user (nickname)
    ON DELETE CASCADE
);

CREATE TABLE message (
	message_id INT AUTO_INCREMENT PRIMARY KEY,
	sender VARCHAR(20) NOT NULL,
    receiver VARCHAR(20) NOT NULL,
    content TEXT NOT NULL,
	sent_date DATE default (CURRENT_DATE),
    
    FOREIGN KEY (sender)
    REFERENCES user (nickname),
    
    FOREIGN KEY (receiver)
    REFERENCES user (nickname)
);

CREATE TABLE certification (
	user_id VARCHAR(30) PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    certification_number VARCHAR(4) NOT NULL
);



