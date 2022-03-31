CREATE TABLE Sponsors
(
    spname  char(40),
    contact integer NOT NULL,
    PRIMARY KEY (spname),
    UNIQUE (contact)
)

CREATE TABLE Radio
(
    bname     char(40),
    country   char(40),
    contact   integer NOT NULL,
    frequency integer NOT NULL,
    PRIMARY KEY (bname, country),
    UNIQUE (contact)
)

CREATE TABLE TV
(
    bname         char(40),
    country       char(40),
    contact       integer NOT NULL,
    channelnumber integer NOT NULL,
    PRIMARY KEY (bname, country),
    UNIQUE (contact)
)

CREATE TABLE OnlineStreaming
(
    bname   char(40),
    country char(40),
    contact          NOT NULL,
    url     char(40) NOT NULL,
    PRIMARY KEY (bname, country)
)

CREATE TABLE Organizers
(
    oname char(40)
        PRIMARY KEY (oname),
    UNIQUE (contact, url)
)

CREATE TABLE Referees
(
    rlicensenumber integer,
    rname          char(40) NOT NULL,
    gender         char(10),
    age            integer,
    PRIMARY KEY (rlicensenumber)
)


CREATE TABLE Teams
(
    tname      char(40),
    city       char(40),
    winpercent integer,
    PRIMARY KEY (tname, city),
    FOREIGN KEY (city) REFERENCES Cities
)

CREATE TABLE Cities
(
    city    char(40),
    country char(40) NOT NULL,
    PRIMARY KEY (city)
)


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
        ON UPDATE CASCADE,
    FOREIGN KEY (clicensenumber) REFERENCES Coaches
        ON DELETE NO ACTION
        ON UPDATE CASCADE
)

CREATE TABLE Coaches
(
    clicensenumber integer,
    cname          char(40) NOT NULL,
    gender         char(10),
    age            integer,
    PRIMARY KEY (clicensenumber)
)

CREATE TABLE Stadiums
(
    stname   char(40),
    address  char(40) NOT NULL,
    capacity integer,
    PRIMARY KEY (stname),
    FOREIGN KEY (address) REFERENCES Locations,
    UNIQUE (address)
)

CREATE TABLE Locations
(
    address    char(40) NOT NULL,
    postalcode char(6)  NOT NULL,
    PRIMARY KEY (address)
)

CREATE TABLE Matches
(
    mid       char(10),
    result    char(10),
    date      date,
    oname     char(40) NOT NULL,
    stname    char(40) NOT NULL,
    rentalfee integer,
    teamA     char(40) NOT NULL,
    cityA     char(40) NOT NULL,
    teamB     char(40) NOT NULL,
    cityB     char(40) NOT NULL,
    PRIMARY KEY (mid),
    FOREIGN KEY (oname) REFERENCES Organizers
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (stname) REFERENCES Stadiums
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (teamA, cityA) REFERENCES Teams (tname, city)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (teamB, cityB) REFERENCES Teams (tname, city)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
)

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
)


CREATE TABLE Livestreams
(
    bname   char(40),
    country char(40),
    mid     char(10),
    FOREIGN KEY (bname, country) REFERENCES Broadcasters
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (mid) REFERENCES Matches
        ON DELETE CASCADE
        ON UPDATE CASCADE
)

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
)

--
-- INSERT INTO branch VALUES (1, "ABC", "123 Charming Ave", "Vancouver", "6041234567");
-- INSERT INTO branch VALUES (2, "DEF", "123 Coco Ave", "Vancouver", "6044567890");