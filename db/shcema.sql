CREATE TABLE if not exists pasports(
                      id SERIAL PRIMARY KEY NOT NULL,
                      name VARCHAR(50),
                      surname VARCHAR(50),
                      patronymic VARCHAR(50),
                      birthday DATE,
                      dateofissue DATE,
                      series INT,
                      number INT,
                      registration VARCHAR(200)
);