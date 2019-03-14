DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS specialty;
DROP TABLE IF EXISTS report;
DROP SEQUENCE IF EXISTS seq_employee;
DROP SEQUENCE IF EXISTS seq_specialty;
DROP SEQUENCE IF EXISTS seq_report;

CREATE SEQUENCE seq_employee;
CREATE TABLE EMPLOYEE(
ID_EMPLOYEE INTEGER NOT NULL PRIMARY KEY,
NAME_EMPLOYEE VARCHAR(255)
);

CREATE SEQUENCE seq_specialty;
CREATE TABLE SPECIALTY(
ID_SPECIALTY INTEGER NOT NULL PRIMARY KEY,
NAME_SPECIALTY VARCHAR(255)
);

CREATE SEQUENCE seq_report;
CREATE TABLE REPORT(
ID_REPORT INTEGER NOT NULL PRIMARY KEY,
DESCRIPTION VARCHAR(255),
DATE DATE,
TIME_START TIME,
TIME_END TIME,
ID_EMPLOYEE INTEGER NOT NULL,
ID_SPECIALTY INTEGER NOT NULL,
CONSTRAINT fk_specialty FOREIGN KEY (id_specialty) REFERENCES SPECIALTY (id_specialty),
CONSTRAINT fk_employee FOREIGN KEY (id_employee) REFERENCES EMPLOYEE (id_employee),
);

INSERT INTO EMPLOYEE VALUES
(nextval('seq_employee'), 'Блажевич Игорь Юрьевич'),
(nextval('seq_employee'), 'Кузнецов Иван Анатольевич'),
(nextval('seq_employee'), 'Шергин Иван Андреевич'),
(nextval('seq_employee'), 'Сафонов Георгий Владимирович'),
(nextval('seq_employee'), 'Носиков Павел Николаевич'),
(nextval('seq_employee'), 'Цветков Людвиг Николаевич'),
(nextval('seq_employee'), 'Игнатьев Эрнест Тимофеевич'),
(nextval('seq_employee'), 'Бобылёв Велор Наумович'),
(nextval('seq_employee'), 'Орлов Лев Степанович');


INSERT INTO SPECIALTY VALUES
(nextval('seq_specialty'), 'Главный бухгалтер'),
(nextval('seq_specialty'), 'Начальник отдела кадров'),
(nextval('seq_specialty'), 'Механик'),
(nextval('seq_specialty'), 'Начальник отдела сбыта'),
(nextval('seq_specialty'), 'Инженер по качеству');

INSERT INTO REPORT VALUES
(nextval('seq_report'), 'Попал в ДТП', CAST('2019-03-10' AS DATE), CAST('09:00:00' AS TIME), CAST('12:00:00' AS TIME), 1, 5),
(nextval('seq_report'), 'Прием у врача', CAST('2019-03-09' AS DATE), CAST('10:15:00' AS TIME), CAST('11:00:00' AS TIME), 2, 4),
(nextval('seq_report'), 'Отпросился', CAST('2019-03-05' AS DATE), CAST('12:00:00' AS TIME), CAST('17:00:00' AS TIME), 3, 3),
(nextval('seq_report'), 'Отгул', CAST('2019-03-10' AS DATE), CAST('09:00:00' AS TIME), CAST('18:00:00' AS TIME), 4, 2),
(nextval('seq_report'), 'Прием у врача', CAST('2019-03-09' AS DATE), CAST('10:00:00' AS TIME), CAST('13:00:00' AS TIME), 5, 1),
(nextval('seq_report'), 'Прием у врача', CAST('2019-03-09' AS DATE), CAST('10:15:00' AS TIME), CAST('11:00:00' AS TIME), 6, 4),
(nextval('seq_report'), 'Отпросился', CAST('2019-03-05' AS DATE), CAST('12:00:00' AS TIME), CAST('17:00:00' AS TIME), 7, 3),
(nextval('seq_report'), 'Отгул', CAST('2019-03-10' AS DATE), CAST('09:00:00' AS TIME), CAST('18:00:00' AS TIME), 8, 2),
(nextval('seq_report'), 'Прием у врача', CAST('2019-03-09' AS DATE), CAST('10:00:00' AS TIME), CAST('13:00:00' AS TIME), 9, 1);
