-- ADMIN
INSERT INTO admin (id, email, mdp) VALUES
((SELECT CONCAT('ADM000', nextval('admin_seq'))), 'nykantorandri@icloud.com', '123');

-- EQUIPE
INSERT INTO equipe (id, nom, email, mdp)
VALUES
((SELECT CONCAT('EQUI000', nextval('equipe_seq'))), 'equipe a', 'equipea@gmail.com', 'ea'),
((SELECT CONCAT('EQUI000', nextval('equipe_seq'))), 'equipe b', 'equipeb@gmail.com', 'eb'),
((SELECT CONCAT('EQUI000', nextval('equipe_seq'))), 'equipe c', 'equipec@gmail.com', 'ec');

-- CATEGORIE
INSERT INTO categorie (id, nom) 
VALUES 
((SELECT CONCAT('CAT000', nextval('categorie_seq'))), 'homme'),
((SELECT CONCAT('CAT000', nextval('categorie_seq'))), 'femme'),
((SELECT CONCAT('CAT000', nextval('categorie_seq'))), 'junior'),
((SELECT CONCAT('CAT000', nextval('categorie_seq'))), 'senior');

-- COUREUR
INSERT INTO coureur (id, nom, numero_dossard, genre, date_naissance, id_equipe)
VALUES
((SELECT CONCAT('FINI000', nextval('finition_seq'))), 'Jean Dupont', 101, 1, '1990-01-01', 'EQUI0001'),
((SELECT CONCAT('FINI000', nextval('finition_seq'))), 'Marie Martin', 102, 2, '1991-02-02', 'EQUI0001'),
((SELECT CONCAT('FINI000', nextval('finition_seq'))), 'Luc Dubois', 103, 1, '1992-03-03', 'EQUI0001'),
((SELECT CONCAT('FINI000', nextval('finition_seq'))), 'Sophie Leroy', 104, 2, '1993-04-04', 'EQUI0001'),
((SELECT CONCAT('FINI000', nextval('finition_seq'))), 'Pierre Moreau', 105, 1, '1994-05-05', 'EQUI0001'),

((SELECT CONCAT('FINI000', nextval('finition_seq'))), 'Paul Thomas', 201, 1, '1990-06-06', 'EQUI0002'),
((SELECT CONCAT('FINI000', nextval('finition_seq'))), 'Nathalie Petit', 202, 2, '1991-07-07', 'EQUI0002'),
((SELECT CONCAT('FINI000', nextval('finition_seq'))), 'Jacques Richard', 203, 1, '1992-08-08', 'EQUI0002'),
((SELECT CONCAT('FINI000', nextval('finition_seq'))), 'Isabelle Robert', 204, 2, '1993-09-09', 'EQUI0002'),
((SELECT CONCAT('FINI00', nextval('finition_seq'))), 'Alain Lefevre', 205, 1, '1994-10-10', 'EQUI0002'),

((SELECT CONCAT('FINI00', nextval('finition_seq'))), 'Michel Gauthier', 301, 1, '1990-11-11', 'EQUI0003'),
((SELECT CONCAT('FINI00', nextval('finition_seq'))), 'Catherine Bernard', 302, 2, '1991-12-12', 'EQUI0003'),
((SELECT CONCAT('FINI00', nextval('finition_seq'))), 'Thierry François', 303, 1, '1992-01-13', 'EQUI0003'),
((SELECT CONCAT('FINI00', nextval('finition_seq'))), 'Valérie Hubert', 304, 2, '1993-02-14', 'EQUI0003'),
((SELECT CONCAT('FINI00', nextval('finition_seq'))), 'Henri Mathieu', 305, 1, '1994-03-15', 'EQUI0003');

-- POINT CLASSEMENT
INSERT INTO point_classement (id, point, rang)
VALUES
((SELECT CONCAT('POCLA000', nextval('point_classement_seq'))), 10, 1),
((SELECT CONCAT('POCLA000', nextval('point_classement_seq'))), 6, 2),
((SELECT CONCAT('POCLA000', nextval('point_classement_seq'))), 4, 3),
((SELECT CONCAT('POCLA000', nextval('point_classement_seq'))), 2, 4),
((SELECT CONCAT('POCLA000', nextval('point_classement_seq'))), 1, 5);
