package race.team.race.service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
        @Autowired
        private DataSource dataSource;

        public void initBase() {
                try {
                        Connection c = dataSource.getConnection();
                        Statement stmt = c.createStatement();
                        stmt.executeUpdate("TRUNCATE TABLE admin CASCADE;");
                        stmt.executeUpdate("TRUNCATE TABLE equipe CASCADE;");
                        stmt.executeUpdate("TRUNCATE TABLE etape CASCADE;");
                        stmt.executeUpdate("TRUNCATE TABLE coureur CASCADE;");
                        stmt.executeUpdate("TRUNCATE TABLE categorie CASCADE;");
                        stmt.executeUpdate("TRUNCATE TABLE detail_coureur_etape CASCADE;");
                        stmt.executeUpdate("TRUNCATE TABLE point_classement CASCADE;");
                        stmt.executeUpdate("TRUNCATE TABLE coureur_categorie CASCADE;");
                        stmt.executeUpdate("TRUNCATE TABLE resultat_temp CASCADE;");
                        stmt.executeUpdate("TRUNCATE TABLE penalite CASCADE;");

                        stmt.executeUpdate("DROP SEQUENCE admin_seq;");
                        stmt.executeUpdate("DROP SEQUENCE equipe_seq;");
                        stmt.executeUpdate("DROP SEQUENCE etape_seq;");
                        stmt.executeUpdate("DROP SEQUENCE coureur_seq;");
                        stmt.executeUpdate("DROP SEQUENCE categorie_seq;");
                        stmt.executeUpdate("DROP SEQUENCE detail_coureur_etape_seq;");
                        stmt.executeUpdate("DROP SEQUENCE point_classement_seq;");
                        stmt.executeUpdate("DROP SEQUENCE penalite_seq;");

                        stmt.executeUpdate("CREATE SEQUENCE admin_seq;");
                        stmt.executeUpdate("CREATE SEQUENCE equipe_seq;");
                        stmt.executeUpdate("CREATE SEQUENCE etape_seq;");
                        stmt.executeUpdate("CREATE SEQUENCE coureur_seq;");
                        stmt.executeUpdate("CREATE SEQUENCE categorie_seq;");
                        stmt.executeUpdate("CREATE SEQUENCE detail_coureur_etape_seq;");
                        stmt.executeUpdate("CREATE SEQUENCE point_classement_seq;");
                        stmt.executeUpdate("CREATE SEQUENCE penalite_seq;");

                        stmt.executeUpdate(
                                        "INSERT INTO admin (id, email, mdp) VALUES ((SELECT CONCAT('ADM000', nextval('admin_seq'))), 'nykantorandri@icloud.com', '123');");

                        // stmt.executeUpdate(
                        //                 "INSERT INTO equipe (id, nom, email, mdp)\r\n" + //
                        //                                 "VALUES\r\n" + //
                        //                                 "((SELECT CONCAT('EQUI000', nextval('equipe_seq'))), 'equipe a', 'equipea@gmail.com', 'ea'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('EQUI000', nextval('equipe_seq'))), 'equipe b', 'equipeb@gmail.com', 'eb'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('EQUI000', nextval('equipe_seq'))), 'equipe c', 'equipec@gmail.com', 'ec');");

                        stmt.executeUpdate(
                                        "INSERT INTO categorie (id, nom) \r\n" + //
                                                        "VALUES \r\n" + //
                                                        "((SELECT CONCAT('CAT000', nextval('categorie_seq'))), 'homme'),\r\n"
                                                        + //
                                                        "((SELECT CONCAT('CAT000', nextval('categorie_seq'))), 'femme'),\r\n"
                                                        + //
                                                        "((SELECT CONCAT('CAT000', nextval('categorie_seq'))), 'junior');");

                        // stmt.executeUpdate(
                        //                 "INSERT INTO coureur (id, nom, numero_dossard, genre, date_naissance, id_equipe)\r\n"
                        //                                 + //
                        //                                 "VALUES\r\n" + //
                        //                                 "((SELECT CONCAT('COUR000', nextval('coureur_seq'))), 'Jean Dupont', 101, 1, '1990-01-01', 'EQUI0001'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('COUR000', nextval('coureur_seq'))), 'Marie Martin', 102, 2, '1991-02-02', 'EQUI0001'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('COUR000', nextval('coureur_seq'))), 'Luc Dubois', 103, 1, '1992-03-03', 'EQUI0001'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('COUR000', nextval('coureur_seq'))), 'Sophie Leroy', 104, 2, '1993-04-04', 'EQUI0001'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('COUR000', nextval('coureur_seq'))), 'Pierre Moreau', 105, 1, '1994-05-05', 'EQUI0001'),\r\n"
                        //                                 + //
                        //                                 "\r\n" + //
                        //                                 "((SELECT CONCAT('COUR000', nextval('coureur_seq'))), 'Paul Thomas', 201, 1, '1990-06-06', 'EQUI0002'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('COUR000', nextval('coureur_seq'))), 'Nathalie Petit', 202, 2, '1991-07-07', 'EQUI0002'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('COUR000', nextval('coureur_seq'))), 'Jacques Richard', 203, 1, '1992-08-08', 'EQUI0002'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('COUR000', nextval('coureur_seq'))), 'Isabelle Robert', 204, 2, '1993-09-09', 'EQUI0002'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('COUR00', nextval('coureur_seq'))), 'Alain Lefevre', 205, 1, '1994-10-10', 'EQUI0002'),\r\n"
                        //                                 + //
                        //                                 "\r\n" + //
                        //                                 "((SELECT CONCAT('COUR00', nextval('coureur_seq'))), 'Michel Gauthier', 301, 1, '1990-11-11', 'EQUI0003'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('COUR00', nextval('coureur_seq'))), 'Catherine Bernard', 302, 2, '1991-12-12', 'EQUI0003'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('COUR00', nextval('coureur_seq'))), 'Thierry François', 303, 1, '1992-01-13', 'EQUI0003'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('COUR00', nextval('coureur_seq'))), 'Valérie Hubert', 304, 2, '1993-02-14', 'EQUI0003'),\r\n"
                        //                                 + //
                        //                                 "((SELECT CONCAT('COUR00', nextval('coureur_seq'))), 'Henri Mathieu', 305, 1, '1994-03-15', 'EQUI0003');");

                        stmt.close();
                        c.close();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }
}
