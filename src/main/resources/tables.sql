INSERT INTO person (first_name, last_name)
VALUES ('Stepan', 'Sokolov'),
       ('Petr', 'Orlov'),
       ('Alex', 'Voronin'),
       ('Nikita', 'Sinitsyn'),
       ('Mihail', 'Vorobyev'),
       ('Vladimir', 'Snegirev'),
       ('Denis', 'Solovyev');

INSERT INTO vehicle (type, model, person_id)
 VALUES ('Car', 'Lada', 3),
        ('Truck', 'Volvo', 1),
        ('Bus', 'Mercedes', 3),
        ('Van', 'Dodge', 4),
        ('Bike', 'Honda', 5),
        ('Suv', 'Toyota', 6),
        ('Car', 'Nissan', 4),
        ('Truck', 'Tatra', 7),
        ('Car', 'Audi', 2),
        ('Van', 'Volkswagen', 2);

INSERT INTO tyre (name, season)
 VALUES ('Michelin', 'Winter'),
        ('Michelin', 'Summer'),
        ('Michelin', 'All-season'),
        ('Goodyear', 'Winter'),
        ('Goodyear', 'Summer'),
        ('Goodyear', 'All-season'),
        ('Bridgestone', 'Winter'),
        ('Bridgestone', 'Summer'),
        ('Bridgestone', 'All-season');

INSERT INTO vehicle_tyre (vehicle_id, tyre_id)
VALUES (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 6),
       (3, 4),
       (4, 4),
       (4, 5),
       (5, 1),
       (5, 3),
       (6, 2),
       (6, 3),
       (6, 4),
       (7, 6),
       (7, 7),
       (8, 7),
       (8, 8),
       (9, 8),
       (10, 7),
       (10, 9);