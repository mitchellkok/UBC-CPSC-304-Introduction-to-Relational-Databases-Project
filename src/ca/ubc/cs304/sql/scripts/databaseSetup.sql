CREATE TABLE Sponsors
(
    spname  char(40),
    contact integer NOT NULL,
    PRIMARY KEY (spname),
    UNIQUE (contact)
);

CREATE TABLE Radio
(
    bname     char(40),
    country   char(40),
    contact   integer NOT NULL,
    frequency integer NOT NULL,
    PRIMARY KEY (bname, country),
    UNIQUE (contact)
);

CREATE TABLE TV
(
    bname         char(40),
    country       char(40),
    contact       integer NOT NULL,
    channelnumber integer NOT NULL,
    PRIMARY KEY (bname, country),
    UNIQUE (contact)
);

CREATE TABLE OnlineStreaming
(
    bname   char(40),
    country char(40),
    contact          NOT NULL,
    url     char(40) NOT NULL,
    PRIMARY KEY (bname, country)
);

CREATE TABLE Organizers
(
    oname char(40)
        PRIMARY KEY (oname),
    UNIQUE (contact, url)
);

CREATE TABLE Referees
(
    rlicensenumber integer,
    rname          char(40) NOT NULL,
    gender         char(10),
    age            integer,
    PRIMARY KEY (rlicensenumber)
);

CREATE TABLE Teams
(
    tname      char(40),
    city       char(40),
    winpercent integer,
    PRIMARY KEY (tname, city),
    FOREIGN KEY (city) REFERENCES Cities
);

CREATE TABLE Cities
(
    city    char(40),
    country char(40) NOT NULL,
    PRIMARY KEY (city)
);

CREATE TABLE Players
(
    jerseynumber   integer,
    tname          char(40),
    city           char(40),
    pname          char(40) NOT NULL,
    height         integer,
    weight         integer,
    age            integer,
    clicensenumber integer  NOT NULL,
    PRIMARY KEY (tname, city, jerseynumber),
    FOREIGN KEY (tname, city) REFERENCES Teams
        ON DELETE CASCADE
    FOREIGN KEY (clicensenumber) REFERENCES Coaches
);

CREATE TABLE Coaches
(
    clicensenumber integer,
    cname          char(40) NOT NULL,
    gender         char(10),
    age            integer,
    PRIMARY KEY (clicensenumber)
);

CREATE TABLE Stadiums
(
    stname   char(40),
    address  char(40) NOT NULL,
    capacity integer,
    PRIMARY KEY (stname),
    FOREIGN KEY (address) REFERENCES Locations,
    UNIQUE (address)
);

CREATE TABLE Locations
(
    address    char(40) NOT NULL,
    postalcode char(6)  NOT NULL,
    PRIMARY KEY (address)
);

CREATE TABLE Matches
(
    mid       char(10),
    oname     char(40) NOT NULL,
    stname    char(40) NOT NULL,
    rentalfee integer,
    teamA     char(40) NOT NULL,
    cityA     char(40) NOT NULL,
    teamB     char(40) NOT NULL,
    cityB     char(40) NOT NULL,
    date      date,
    result    char(10),
    PRIMARY KEY (mid),
    FOREIGN KEY (oname) REFERENCES Organizers,
    FOREIGN KEY (stname) REFERENCES Stadiums,
    FOREIGN KEY (teamA, cityA) REFERENCES Teams (tname, city),
    FOREIGN KEY (teamB, cityB) REFERENCES Teams (tname, city)
);

CREATE TABLE Finances
(
    spname char(40),
    mid    char(10),
    amount integer,
    FOREIGN KEY (spname) REFERENCES Sponsors
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (mid) REFERENCES Matches
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


CREATE TABLE Livestreams
(
    bname   char(40),
    country char(40),
    mid     char(10),
    FOREIGN KEY (bname, country) REFERENCES Broadcasters
        ON DELETE CASCADE
    FOREIGN KEY (mid) REFERENCES Matches
        ON DELETE CASCADE
);

CREATE TABLE Officiates
(
    rlicensenumber integer,
    mid            char(40),
    PRIMARY KEY (rlicensenumber, mid),
    FOREIGN KEY (rlicensenumber) REFERENCES Referees
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (mid) REFERENCES Matches
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO players (jerseynumber, tname, city, pname, height, weight, age, clicensenumber)
    VALUES (1, "Liverpool", "Liverpool", "John Blonde", 175, 75, 33, 12345);
INSERT INTO players (jerseynumber, tname, city, pname, height, weight, age, clicensenumber)
    VALUES (2, "Liverpool", "Liverpool", "Jack Black", 185, 85, 34, 12345);
INSERT INTO players (jerseynumber, tname, city, pname, height, weight, age, clicensenumber)
    VALUES (3, "Liverpool", "Liverpool", "Jay Gray", 165, 65, 35, 12345);
INSERT INTO players (jerseynumber, tname, city, pname, height, weight, age, clicensenumber)
    VALUES (11, "Manchester United", "Manchester", "Billy Klub", 167, 67, 36, 67890);
INSERT INTO players (jerseynumber, tname, city, pname, height, weight, age, clicensenumber)
    VALUES (22, "Manchester United", "Manchester", "Bobby Pynn", 177, 77, 37, 67890);
INSERT INTO players (jerseynumber, tname, city, pname, height, weight, age, clicensenumber)
    VALUES (33, "Manchester United", "Manchester", "Barry Caid", 187, 87, 38, 67890);

INSERT INTO teams (tname, city, winpercent)
    VALUES ("Liverpool", "Liverpool", 77);
INSERT INTO teams (tname, city, winpercent)
    VALUES ("Manchester United", "Manchester", 66);

INSERT INTO coaches (clicensenumber, cname, gender, age)
    VALUES (12345, "Jim Slim", "Male", 55);
INSERT INTO coaches (clicensenumber, cname, gender, age)
    VALUES (67890, "Ben Ten", "Male", 44);

INSERT INTO matches (mid, oname, stname, rentalfee, teamA, cityA, teamB, cityB, date, result)
    VALUES("ASD432", "FIFA", "Thunderbird Stadium", "Manchester", "Manchester United", "Liverpool", "Liverpool", 10000, 01-JAN-17, "5-6");
INSERT INTO matches (mid, oname, stname, rentalfee, teamA, cityA, teamB, cityB, date, result)
    VALUES("QWE765", "Junior Football League", "Tokyo Dome", "Madrid", "Real Madrid", "Barcelona", "FC Barcelona", 25000, 05-DEC-97, "6-7");
INSERT INTO matches (mid, oname, stname, rentalfee, teamA, cityA, teamB, cityB, date, result)
    VALUES("ZXC098", "Senior Football League", "BC Place", "Chelsea", "Chelsea", "Liverpool", "Liverpool", 5000, 25-MAR-07, "2-3");
INSERT INTO matches (mid, oname, stname, rentalfee, teamA, cityA, teamB, cityB, date)
    VALUES("FGH135", "FIFA", "Tokyo Dome", "Barcelona", "FC Barcelona", "Manchester", "Manchester United", 15000, 15-SEP-27);

INSERT INTO TV (bname, country, contact, channelnumber)
    VALUES ("ABC", "USA", 93461996, 101);
INSERT INTO TV (bname, country, contact, channelnumber)
    VALUES ("CNN", "USA", 94866588, 202);
INSERT INTO TV (bname, country, contact, channelnumber)
    VALUES ("Sportsnet", "Canada", 75054932, 303);

INSERT INTO Livestreams (bname, country, mid)
    VALUES ("ABC", "USA", "ABC432");
INSERT INTO Livestreams (bname, country, mid)
    VALUES ("ABC", "USA", "QWE765");
INSERT INTO Livestreams (bname, country, mid)
    VALUES ("ABC", "USA", "ZXC098");
INSERT INTO Livestreams (bname, country, mid)
    VALUES ("ABC", "USA", "FGH135");
INSERT INTO Livestreams (bname, country, mid)
    VALUES ("CNN", "USA", "QWE765");
INSERT INTO Livestreams (bname, country, mid)
    VALUES ("CNN", "USA", "ZXC098");
INSERT INTO Livestreams (bname, country, mid)
    VALUES ("CNN", "USA", "FGH135");
INSERT INTO Livestreams (bname, country, mid)
    VALUES ("Sportsnet", "Canada", "ZXC098");
INSERT INTO Livestreams (bname, country, mid)
    VALUES ("Sportsnet", "Canada", "FGH135");