CREATE TABLE IF NOT EXISTS positions(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    position_name TEXT
);


CREATE TABLE IF NOT EXISTS employees(
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        firts_name TEXT,
        last_name TEXT,
        phone TEXT,
        position_id BIGINT,
        FOREIGN KEY(position_id) REFERENCES positions(id)
    );