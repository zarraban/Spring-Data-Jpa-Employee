CREATE TABLE IF NOT EXISTS positions(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    position_name TEXT
);


CREATE TABLE IF NOT EXISTS employees(
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        first_name TEXT,
        last_name TEXT,
        phone TEXT,
        position_id BIGINT,
        FOREIGN KEY(position_id) REFERENCES positions(id)
    );

INSERT into positions (position_name)
values
    ('CEO'),
    ('Product Manager'),
    ('Software Developer'),
    ('QA Manual');
