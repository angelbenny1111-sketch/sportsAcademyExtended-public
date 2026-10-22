CREATE DATABASE IF NOT EXISTS sports_academy;
USE sports_academy;

-- 1. SPORT
CREATE TABLE IF NOT EXISTS Sport (
    sport_id INT PRIMARY KEY AUTO_INCREMENT,
    sport_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- 2. COACH
CREATE TABLE IF NOT EXISTS Coach (
    coach_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15)
);

-- 3. ATHLETE
CREATE TABLE IF NOT EXISTS Athlete (
    athlete_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    sport_name VARCHAR(100) NOT NULL
);

-- 4. TRAINING
CREATE TABLE IF NOT EXISTS Training (
    training_id INT PRIMARY KEY AUTO_INCREMENT,
    sport_id INT NOT NULL,
    coach_id INT NOT NULL,
    training_date DATE NOT NULL,
    training_time TIME NOT NULL,
    venue VARCHAR(150) NOT NULL,
    FOREIGN KEY (sport_id) REFERENCES Sport(sport_id),
    FOREIGN KEY (coach_id) REFERENCES Coach(coach_id)
);

-- 5. ATTENDANCE
CREATE TABLE IF NOT EXISTS Attendance (
    athlete_id INT NOT NULL,
    training_id INT NOT NULL,
    attendance_date DATE NOT NULL,
    status VARCHAR(10) NOT NULL,
    PRIMARY KEY (athlete_id, training_id),
    FOREIGN KEY (athlete_id)
        REFERENCES Athlete(athlete_id)
        ON DELETE CASCADE,
    FOREIGN KEY (training_id)
        REFERENCES Training(training_id)
        ON DELETE CASCADE
);

-- 6. FEE
CREATE TABLE IF NOT EXISTS Fee (
    fee_id INT PRIMARY KEY AUTO_INCREMENT,
    athlete_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'pending',
    FOREIGN KEY (athlete_id)
        REFERENCES Athlete(athlete_id)
);

-- 7. USER
CREATE TABLE IF NOT EXISTS User (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'COACH', 'ATHLETE') NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE IF NOT EXISTS Athlete_Sport (
    athlete_id INT NOT NULL,
    sport_id INT NOT NULL,
    coach_id INT NULL,
    enrollment_date DATE NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE')
        NOT NULL DEFAULT 'ACTIVE',

    PRIMARY KEY (athlete_id, sport_id),

    FOREIGN KEY (athlete_id)
        REFERENCES Athlete(athlete_id)
        ON DELETE CASCADE,

    FOREIGN KEY (sport_id)
        REFERENCES Sport(sport_id),

    FOREIGN KEY (coach_id)
        REFERENCES Coach(coach_id)
        ON DELETE SET NULL
);
CREATE TABLE DutyLeave (
    duty_leave_id INT PRIMARY KEY AUTO_INCREMENT,
    athlete_id INT NOT NULL,
    leave_date DATE NOT NULL,
    reason VARCHAR(255) NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED')
        NOT NULL DEFAULT 'PENDING'
);