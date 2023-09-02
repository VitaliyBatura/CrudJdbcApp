CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS vehicle (
    id SERIAL PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    person_id INT NOT NULL,
    FOREIGN KEY(person_id) REFERENCES person(id)
);

CREATE TABLE IF NOT EXISTS tyre (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    season VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS vehicle_tyre (
    vehicle_id INT NOT NULL,
    tyre_id BIGINT NOT NULL,
    PRIMARY KEY (vehicle_id, tyre_id),
    FOREIGN KEY(vehicle_id) REFERENCES vehicle(id),
    FOREIGN KEY(tyre_id) REFERENCES tyre(id)
);

