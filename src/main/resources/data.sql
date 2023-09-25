INSERT INTO Genre (id, name)
SELECT * FROM (VALUES
                   (1, 'Комедия'),
                   (2, 'Драма'),
                   (3, 'Мультфильм'),
                   (4, 'Триллер'),
                   (5, 'Документальный'),
                   (6, 'Боевик')
              ) AS tmp
WHERE NOT EXISTS (
        SELECT 1 FROM Genre WHERE id = tmp.C1
    );
INSERT INTO Mpa(id, name)
SELECT * FROM (VALUES
                   (1, 'G' ),
                   (2, 'PG'),
                   (3, 'PG-13'),
                   (4, 'R'),
                   (5, 'NC-17')) AS tmp
WHERE NOT EXISTS(
        SELECT 1 FROM MPA WHERE  id = tmp.C1
    );