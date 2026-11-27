-- Reference data (Product Specs, 3.4): 5 stations, 30 e-bikes (24 normal, 6 cargo).
INSERT INTO stations (name, latitude, longitude, capacity) VALUES
    ('DETI (edifício 4)',       40.63331, -8.65954, 10),
    ('Biblioteca',              40.63106, -8.65928, 10),
    ('Cantina do Crasto',       40.62420, -8.65667,  8),
    ('Residências de Santiago', 40.62998, -8.66147, 12),
    ('Reitoria',                40.63128, -8.65744,  6);

INSERT INTO ebikes (code, type, status, battery, station_id)
SELECT b.code, b.type, b.status, b.battery, s.id
FROM (VALUES
    ('EB-01', 'NORMAL', 'AVAILABLE',   92, 'DETI (edifício 4)'),
    ('EB-02', 'NORMAL', 'AVAILABLE',   81, 'DETI (edifício 4)'),
    ('EB-03', 'NORMAL', 'AVAILABLE',   67, 'DETI (edifício 4)'),
    ('EB-04', 'NORMAL', 'AVAILABLE',   18, 'DETI (edifício 4)'),
    ('EB-05', 'NORMAL', 'MAINTENANCE', 55, 'DETI (edifício 4)'),
    ('EB-06', 'NORMAL', 'AVAILABLE',   74, 'DETI (edifício 4)'),
    ('EB-07', 'NORMAL', 'AVAILABLE',   88, 'Cantina do Crasto'),
    ('EB-08', 'NORMAL', 'AVAILABLE',   45, 'Cantina do Crasto'),
    ('EB-09', 'NORMAL', 'AVAILABLE',   97, 'Cantina do Crasto'),
    ('EB-10', 'NORMAL', 'AVAILABLE',   23, 'Cantina do Crasto'),
    ('EB-11', 'CARGO',  'AVAILABLE',   79, 'Cantina do Crasto'),
    ('EB-12', 'CARGO',  'AVAILABLE',   61, 'Cantina do Crasto'),
    ('EB-13', 'NORMAL', 'AVAILABLE',   90, 'Biblioteca'),
    ('EB-14', 'NORMAL', 'AVAILABLE',   34, 'Biblioteca'),
    ('EB-15', 'NORMAL', 'AVAILABLE',   12, 'Biblioteca'),
    ('EB-16', 'NORMAL', 'AVAILABLE',   70, 'Biblioteca'),
    ('EB-17', 'NORMAL', 'AVAILABLE',   85, 'Biblioteca'),
    ('EB-18', 'NORMAL', 'MAINTENANCE', 40, 'Biblioteca'),
    ('EB-19', 'NORMAL', 'AVAILABLE',   99, 'Residências de Santiago'),
    ('EB-20', 'NORMAL', 'AVAILABLE',   58, 'Residências de Santiago'),
    ('EB-21', 'NORMAL', 'AVAILABLE',   63, 'Residências de Santiago'),
    ('EB-22', 'NORMAL', 'AVAILABLE',   20, 'Residências de Santiago'),
    ('EB-23', 'NORMAL', 'AVAILABLE',   19, 'Residências de Santiago'),
    ('EB-24', 'NORMAL', 'AVAILABLE',   77, 'Residências de Santiago'),
    ('EB-25', 'CARGO',  'AVAILABLE',   83, 'Residências de Santiago'),
    ('EB-26', 'CARGO',  'AVAILABLE',   50, 'Residências de Santiago'),
    ('EB-27', 'NORMAL', 'AVAILABLE',   66, 'Reitoria'),
    ('EB-28', 'NORMAL', 'AVAILABLE',   72, 'Reitoria'),
    ('EB-29', 'CARGO',  'AVAILABLE',   94, 'Reitoria'),
    ('EB-30', 'CARGO',  'MAINTENANCE', 30, 'Reitoria')
) AS b(code, type, status, battery, station_name)
JOIN stations s ON s.name = b.station_name;
