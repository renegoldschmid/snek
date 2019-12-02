DROP TABLE IF EXISTS SNAKE_BODY_PART;

CREATE TABLE SNAKE_BODY_PART (
    id INTEGER auto_increment not NULL,
    red DOUBLE,
    green DOUBLE,
    blue DOUBLE,
    height DOUBLE,
    width DOUBLE,
    pos_x DOUBLE,
    pos_y DOUBLE,
    PRIMARY KEY (id)
);

