CREATE SEQUENCE admin_seq;

CREATE SEQUENCE equipe_seq;

CREATE SEQUENCE etape_seq;

CREATE SEQUENCE coureur_seq;

CREATE SEQUENCE categorie_seq;

CREATE SEQUENCE detail_coureur_etape_seq;

CREATE SEQUENCE point_classement_seq;

CREATE SEQUENCE penalite_seq;

CREATE TABLE coureur_categorie ( id_coureur VARCHAR(255) NOT NULL REFERENCES coureur, id_categorie VARCHAR(255) NOT NULL REFERENCES categorie );

ALTER TABLE penalite ADD COLUMN temps INTERVAL;

-- DUREE DES COUREURS PAR ETAPE
CREATE OR REPLACE VIEW v_coureur_duree AS
	SELECT
        dce.id,
        e.id as id_etape,
        e.rang,
        dce.id_coureur,
        c.nom as nom_coureur,
        eq.id as id_equipe,
        eq.nom as nom_equipe,
        (AGE(dce.date_heure_arrive, e.date_depart + e.heure_depart) + COALESCE(SUM(p.temps), INTERVAL '00:00:00')) AS duree
    FROM
        detail_coureur_etape dce
    JOIN
        etape e ON dce.id_etape = e.id
    JOIN
        coureur c ON dce.id_coureur = c.id
    JOIN
        equipe eq ON c.id_equipe = eq.id
    LEFT JOIN
        penalite p ON e.id = p.id_etape AND eq.id = p.id_equipe
    GROUP BY dce.id, e.id, c.nom, eq.id;


-- DUREE DES COUREURS PAR ETAPE J4
CREATE OR REPLACE VIEW v_coureur_duree_j AS
SELECT
    dce.id,
    e.id as id_etape,
    e.rang,
    dce.id_coureur,
    c.nom as nom_coureur,
    c.genre as genre_coureur,
    eq.id as id_equipe,
    eq.nom as nom_equipe,
    (AGE(dce.date_heure_arrive, e.date_depart + e.heure_depart)) AS chrono,
    (COALESCE(SUM(p.temps), INTERVAL '00:00:00')) AS penalite,
    (AGE(dce.date_heure_arrive, e.date_depart + e.heure_depart) + COALESCE(SUM(p.temps), INTERVAL '00:00:00')) AS temps_final
FROM
    detail_coureur_etape dce
JOIN
    etape e ON dce.id_etape = e.id
JOIN
    coureur c ON dce.id_coureur = c.id
JOIN
    equipe eq ON c.id_equipe = eq.id
LEFT JOIN
    penalite p ON e.id = p.id_etape AND eq.id = p.id_equipe
GROUP BY dce.id, e.id, c.nom, c.genre, eq.id;


-- RANG DES COUREURS PAR ETAPE
CREATE OR REPLACE VIEW v_rang_coureur_etape AS
    SELECT
        vcd.id,
        vcd.id_etape,
        vcd.rang,
        vcd.id_coureur,
        vcd.nom_coureur,
        vcd.id_equipe,
        vcd.nom_equipe,
        vcd.duree,
        (
            SELECT COUNT(DISTINCT vcd2.duree)
            FROM v_coureur_duree vcd2
            WHERE vcd2.rang = vcd.rang
            AND vcd2.duree <= vcd.duree
        ) AS rang_coureur
    FROM
        v_coureur_duree vcd
    ORDER BY
        vcd.rang,
        vcd.duree ASC;


-- RANG DES COUREURS PAR ETAPE
CREATE OR REPLACE VIEW v_rang_coureur_etape_j AS
    SELECT
        vcd.id,
        vcd.id_etape,
        vcd.rang,
        vcd.id_coureur,
        vcd.nom_coureur,
        vcd.genre_coureur,
        vcd.id_equipe,
        vcd.nom_equipe,
        vcd.chrono,
        vcd.penalite,
        vcd.temps_final,
        (
            SELECT COUNT(DISTINCT vcd2.temps_final)
            FROM v_coureur_duree_j vcd2
            WHERE vcd2.rang = vcd.rang
            AND vcd2.temps_final <= vcd.temps_final
        ) AS rang_coureur
    FROM
        v_coureur_duree_j vcd
    ORDER BY
        vcd.rang,
        vcd.temps_final ASC;


-- RANG DES COUREURS PAR ETAPE PAR CATEGORIE
CREATE OR REPLACE VIEW v_rang_coureur_etape_categorie AS
SELECT *
FROM (
	SELECT
        vcd.id,
        vcd.id_etape,
        vcd.rang,
        vcd.id_coureur,
        vcd.nom_coureur,
        vcd.id_equipe,
        vcd.nom_equipe,
        cc.id_categorie,
        vcd.duree,
        DENSE_RANK() OVER (PARTITION BY vcd.id_etape, cc.id_categorie ORDER BY vcd.duree ASC) AS rang_coureur
    FROM
        v_coureur_duree vcd
    JOIN coureur_categorie cc ON cc.id_coureur = vcd.id_coureur
) AS rankedCoureur
ORDER BY
    id_etape,
    rang_coureur;


-- POINTS DES COUREURS PAR ETAPE
CREATE OR REPLACE VIEW v_points_coureur_etape AS
SELECT
    vrce.id,
	vrce.id_etape,
    vrce.id_coureur,
    vrce.nom_coureur,
    vrce.id_equipe,
    vrce.nom_equipe,
    vrce.duree,
    COALESCE(pc.point, 0) AS point,
	DENSE_RANK() OVER (ORDER BY COALESCE(pc.point, 0) DESC) AS rang
FROM
    v_rang_coureur_etape vrce
LEFT JOIN
    point_classement pc ON vrce.rang_coureur = pc.rang;


-- POINTS DES COUREURS PAR ETAPE J-4
CREATE OR REPLACE VIEW v_points_coureur_etape_j AS
SELECT
    vrce.id,
	vrce.id_etape,
    vrce.id_coureur,
    vrce.nom_coureur,
    vrce.genre_coureur,
    vrce.id_equipe,
    vrce.nom_equipe,
    vrce.chrono,
    vrce.penalite,
    vrce.temps_final,
    COALESCE(pc.point, 0) AS point,
	DENSE_RANK() OVER (ORDER BY COALESCE(pc.point, 0) DESC) AS rang
FROM
    v_rang_coureur_etape_j vrce
LEFT JOIN
    point_classement pc ON vrce.rang_coureur = pc.rang;



-- POINTS DES COUREURS PAR ETAPE PAR CATEGORIE
CREATE OR REPLACE VIEW v_points_coureur_etape_categorie AS
SELECT
    vrcec.id,
	vrcec.id_etape,
    vrcec.id_coureur,
    vrcec.nom_coureur,
    vrcec.id_equipe,
    vrcec.nom_equipe,
    vrcec.id_categorie,
    vrcec.duree,
    COALESCE(pc.point, 0) AS point,
	DENSE_RANK() OVER (ORDER BY COALESCE(pc.point, 0) DESC) AS rang
FROM
    v_rang_coureur_etape_categorie vrcec
LEFT JOIN
    point_classement pc ON vrcec.rang_coureur = pc.rang;


-- CLASSEMENT GENERAL DES COUREURS SELON LEURS POINTS
CREATE OR REPLACE VIEW v_classement_general AS
SELECT
    vpce.id_coureur,
    vpce.nom_coureur,
    vpce.id_equipe,
    vpce.nom_equipe,
    SUM(vpce.point) AS total_points,
    DENSE_RANK() OVER (ORDER BY SUM(vpce.point) DESC) AS rang
FROM
    v_points_coureur_etape vpce
GROUP BY
    vpce.id_coureur, vpce.nom_coureur, vpce.id_equipe, vpce.nom_equipe
ORDER BY
    total_points DESC;


-- CLASSEMENT GENERAL PAR EQUIPE PAR CATEGORIE
-- CREATE OR REPLACE VIEW v_classement_general_equipe_categorie AS
-- SELECT
--     vpcec.id_equipe,
--     vpcec.nom_equipe,
--     vpcec.id_categorie,
--     SUM(vpcec.point) AS total_points,
--     DENSE_RANK() OVER (ORDER BY SUM(vpcec.point) DESC) AS rang
-- FROM
--     v_points_coureur_etape_categorie vpcec
-- GROUP BY
--     vpcec.id_equipe, vpcec.nom_equipe, vpcec.id_categorie
-- ORDER BY
--     total_points DESC;


CREATE OR REPLACE VIEW v_rang_equipe AS
SELECT
    vpce.id_equipe,
    vpce.nom_equipe,
    SUM(vpce.point) AS point,
    DENSE_RANK() OVER (ORDER BY SUM(vpce.point) DESC) AS rang
FROM
    v_points_coureur_etape vpce
GROUP BY
    vpce.id_equipe, vpce.nom_equipe
ORDER BY
    point DESC;


CREATE TABLE resultat_temp (
	etape_rang VARCHAR,
	numero_dossard VARCHAR,
	nom VARCHAR,
	genre VARCHAR,
	date_naissance VARCHAR,
	equipe VARCHAR,
	arrivee VARCHAR
);
