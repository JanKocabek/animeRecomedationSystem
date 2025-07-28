LOAD DATA LOCAL INFILE 'C:\\Users\\sehes\\programming\\repository\\finalproject data\\datasets\\users-details-2023.csv'
    INTO TABLE users
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;

