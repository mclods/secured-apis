CREATE TABLE IF NOT EXISTS taco(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    creation_date TIMESTAMP NOT NULL
);


CREATE TABLE IF NOT EXISTS ingredient(
    id char(4) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(20) NOT NULL
);


CREATE TABLE IF NOT EXISTS taco_ingredient(
    taco_id INTEGER REFERENCES taco(id) ON DELETE CASCADE,
    ingredient_id char(4) REFERENCES ingredient(id) ON DELETE CASCADE,
    PRIMARY KEY(taco_id, ingredient_id)
);


CREATE TABLE IF NOT EXISTS users(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR NOT NULL
);


CREATE TABLE IF NOT EXISTS role(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT
);


CREATE TABLE IF NOT EXISTS permission(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT
);


CREATE TABLE IF NOT EXISTS users_role(
    users_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    role_id INTEGER REFERENCES role(id) ON DELETE CASCADE,
    PRIMARY KEY(users_id, role_id)
);


CREATE TABLE IF NOT EXISTS role_permission(
    role_id INTEGER REFERENCES role(id) ON DELETE CASCADE,
    permission_id INTEGER REFERENCES permission(id) ON DELETE CASCADE,
    PRIMARY KEY(role_id, permission_id)
);


ALTER TABLE role
ADD CONSTRAINT uq_role_name
UNIQUE (name);


ALTER TABLE permission
ADD CONSTRAINT uq_permission_name
UNIQUE (name);