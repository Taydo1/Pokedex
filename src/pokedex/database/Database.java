/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.database;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pokedex.gui.*;

/**
 *
 * @author Leon
 */
public class Database {

    private Statement st;
    private Connection conn;

    public void setupDB(String dbName, String schemaName, boolean toBeCreated) {
        if (toBeCreated) { //pour forcer la creation de la bdd
            createDB(dbName);
        } else { //lorsqu'on veut simplement s'y connecter
            connectDB(dbName);
            executeUpdate("SET search_path TO " + schemaName);
        }
    }

    public void connectDB(String dbName) {
        try { //connection à la bdd
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbName, "postgres",
                    "hugoquentinleon");
            st = conn.createStatement();
        } catch (SQLException ex) { //si ca plante, la bdd n'existe pas et il faut la créer
            createDB(dbName);
        }
    }

    public void createDB(String dbName) {
        connectDB(""); //on se déconnecte de la bdd
        executeUpdate("DROP database IF EXISTS " + dbName);
        executeUpdate("CREATE database " + dbName);
        connectDB(dbName); //on s'y reconnecte apres création
        executeFile("ressources/creation_tables.sql"); //on la remplit avec des tables
        importAll(); //on ajoute les données dans les tables
    }

    public void executeFile(String fileName) { //permet l'execution d'un fichier sql
        String file = "";
        String lign;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((lign = br.readLine()) != null) {
                file += " " + lign;
            }

            String[] commands = file.split(";");
            for (int i = 0; i < commands.length; i++) {
                executeUpdate(commands[i]);
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void importAll() { //import les données dans les tables à la création
        String lign;
        try {
            Map<String, Integer> type2id = new HashMap<>(); //Associe un nom de type à son id
            type2id.put(" ", -1);
            Map<String, Integer> ability2id = new HashMap<>(); //Associe un nom de talent à son id
            ability2id.put(" ", -1);

            //lecture et insertion des types
            BufferedReader br = new BufferedReader(new FileReader("ressources/liste_types.csv"));
            br.readLine();
            String request = "INSERT INTO type VALUES ";
            while ((lign = br.readLine()) != null) {
                request += new Type(lign, type2id).getInsertSubRequest() + ",";
            }
            executeUpdate(request.substring(0, request.length() - 1));
            br.close();

            //lecture et insertion des talents
            request = "INSERT INTO ability VALUES ";
            br = new BufferedReader(new FileReader("ressources/liste_abilities.csv"));
            br.readLine();
            while ((lign = br.readLine()) != null) {
                request += new Ability(lign, ability2id).getInsertSubRequest() + ",";
            }
            executeUpdate(request.substring(0, request.length() - 1));
            br.close();

            //lecture et insertion des capacités
            request = "INSERT INTO move VALUES ";
            br = new BufferedReader(new FileReader("ressources/liste_moves.csv"));
            br.readLine();
            while ((lign = br.readLine()) != null) {
                request += new Move(lign, type2id).getInsertSubRequest() + ",";
            }
            executeUpdate(request.substring(0, request.length() - 1));
            br.close();

            //lecture et insertion des infos du pokedex
            request = "INSERT INTO pokedex VALUES ";
            br = new BufferedReader(new FileReader("ressources/liste_pokedex.csv"));
            br.readLine();
            while ((lign = br.readLine()) != null) {
                request += new Pokedex(lign, type2id, ability2id).getInsertSubRequest() + ",";
            }

            executeUpdate(request.substring(0, request.length() - 1));
            br.close();

            //lecture et insertion des images pour chaque pokemon (prend plusieurs secondes)
            int imageNb = 809;
            for (int i = 1; i <= imageNb; i++) {
                Pokedex pokeActuel = getFromDB("SELECT * FROM pokedex WHERE id=" + i, Pokedex.class).get(0);
                File file, fileShiny = null, fileMega = null;
                FileInputStream fis, fisShiny = null, fisMega = null;

                String requestImage = "UPDATE pokedex SET image=?";
                file = new File(String.format("ressources/images/normal/%03d.png", i));
                fis = new FileInputStream(file);
                if (pokeActuel.has_shiny) {
                    fileShiny = new File(String.format("ressources/images/shiny/%03d.png", i));
                    fisShiny = new FileInputStream(fileShiny);
                    requestImage += ", image_shiny=?";
                }
                if (pokeActuel.has_mega) {
                    fileMega = new File(String.format("ressources/images/mega/%03d.png", i));
                    fisMega = new FileInputStream(fileMega);
                    requestImage += ", image_mega=?";
                }
                requestImage += " WHERE id=" + i;

                PreparedStatement ps;
                ps = conn.prepareStatement(requestImage);
                ps.setBinaryStream(1, fis, file.length());
                if (pokeActuel.has_shiny) {
                    ps.setBinaryStream(2, fisShiny, fileShiny.length());
                }
                if (pokeActuel.has_mega) {
                    ps.setBinaryStream(3, fisMega, fileMega.length());
                }

                ps.executeUpdate();
                ps.close();

                fis.close();
                if (pokeActuel.has_shiny) {
                    fisShiny.close();
                }
                if (pokeActuel.has_mega) {
                    fisMega.close();
                }

            }

            executeUpdate("TRUNCATE TABLE trainer,pokemon RESTART IDENTITY");

            //creation de 3 dresseurs
            Trainer sacha = new Trainer("Sacha Ketchum", -1, -1, -1, -1, -1, -1);
            Trainer red = new Trainer("Red", -1, -1, -1, -1, -1, -1);
            Trainer ruiz = new Trainer("Ruiz (World Chmpn)", -1, -1, -1, -1, -1, -1);
            executeUpdate("INSERT INTO trainer VALUES " + sacha.getInsertSubRequest()
                    + "," + red.getInsertSubRequest()
                    + "," + ruiz.getInsertSubRequest()
            );

            //creation de plein de pokemons
            Pokemon pikachuRed = new Pokemon(-1, "Pikachu", 88, 270, false, 2, 344, 231, 98, 85, 209, 25);
            Pokemon lokhlassRed = new Pokemon(-1, "Lokhlass", 80, 390, false, 2, 59, 362, 94, 34, 51, 131);
            Pokemon ronflexRed = new Pokemon(-1, "Ronflex", 82, 550, false, 2, 247, 242, 59, 416, 124, 143);
            Pokemon florizarreRed = new Pokemon(-1, "Florizarre", 84, 450, false, 2, 188, 202, 79, 338, 79, 3);
            Pokemon dracaufeuRed = new Pokemon(-1, "Dracaufeu", 84, 320, false, 2, 394, 314, 307, 406, 35, 6);
            Pokemon tortankRed = new Pokemon(-1, "Tortank", 84, 500, false, 2, 411, 308, 59, 430, 228, 9);
            Pokemon mewtwoRed = new Pokemon(-1, "Mewtwo", 100, 600, false, 2, 540, 396, 377, -1, 228, 150);

            Pokemon tapu = new Pokemon(-1, "Tapu Koko", 50, 178, false, 3, 85, 605, 521, 435, 61, 785);
            Pokemon salamence = new Pokemon(-1, "Salamence", 50, 231, false, 3, 38, 350, 355, 182, 123, 373);
            Pokemon snorlex = new Pokemon(-1, "Snorlex", 50, 276, false, 3, 218, 187, 278, 182, 104, 143);
            Pokemon incineroar = new Pokemon(-1, "Incineroar", 50, 198, true, 3, 394, 555, 289, 252, 123, 727);
            Pokemon gastrodon = new Pokemon(-1, "Gastrodon", 50, 217, false, 3, 414, 58, 105, 182, 126, 423);
            Pokemon kartana = new Pokemon(-1, "Kartana", 50, 181, true, 3, 348, 533, 366, 182, 32, 798);

            Pokemon pikachuSacha = new Pokemon(-1, " Pikachu ", 100, 999, false, 1, 2, 3, 0, 0, 188, 25);
            Pokemon dracaufeuSacha = new Pokemon(-1, "Dracaufeu", 96, 402, false, 1, 53, 19, 69, 126, 35, 6);
            Pokemon ronflexSacha = new Pokemon(-1, "Ronflex", 75, 482, false, 1, 416, 156, 63, 34, 124, 143);
            Pokemon bulbizarre = new Pokemon(-1, "Bulbizarre", 24, 55, false, 1, 33, 22, 21, 43, 79, 1);
            Pokemon metamorph = new Pokemon(-1, "Métamorph", 48, 164, false, 1, 144, -1, -1, -1, 75, 132);
            Pokemon krabboss = new Pokemon(-1, "Krabboss", 36, 122, false, 1, 61, 152, 232, 11, 112, 99);
            Pokemon tauros = new Pokemon(-1, "Tauros", 51, 245, false, 1, 23, 36, 232, 33, 48, 128);
            Pokemon grotadmorv = new Pokemon(-1, "Grotadmorv", 48, 231, false, 1, 482, 92, 1, 29, 106, 89);
            Pokemon feurisson = new Pokemon(-1, "Feurisson", 31, 123, true, 1, 172, 98, 52, 108, 35, 156);
            Pokemon macronium = new Pokemon(-1, "Macronium", 28, 146, false, 1, 22, 34, 33, 37, 79, 153);
            Pokemon kaiminus = new Pokemon(-1, "Kaiminus", 46, 147, false, 1, 242, 422, 423, 401, 228, 158);
            Pokemon scarhino = new Pokemon(-1, "Scarhino", 57, 209, false, 1, 32, 31, 405, 406, 59, 214);
            Pokemon noarfang = new Pokemon(-1, "Noarfang", 71, 302, false, 1, 333, 428, 94, 355, 122, 164);
            Pokemon donphan = new Pokemon(-1, "Donphan", 81, 397, false, 1, 23, 31, 89, 205, 89, 232);
            Pokemon jungko = new Pokemon(-1, "Jungko", 91, 326, false, 1, 348, 400, 163, 14, 79, 254);
            Pokemon heledelle = new Pokemon(-1, "Hélédelle", 78, 287, false, 1, 333, 19, 413, 432, 59, 277);
            Pokemon chartor = new Pokemon(-1, "Chartor", 47, 200, false, 1, 436, 284, 334, 335, 76, 324);
            Pokemon scorvol = new Pokemon(-1, "Scorvol", 84, 306, false, 1, 12, 210, 185, 327, 235, 472);
            Pokemon simiabraz = new Pokemon(-1, "Simiabraz", 93, 318, false, 1, 394, 126, 307, 409, 35, 392);
            Pokemon torterra = new Pokemon(-1, "Torterra", 77, 425, false, 1, 89, 431, 202, 338, 79, 389);
            Pokemon griknot = new Pokemon(-1, "Griknot", 46, 142, false, 1, 242, 91, 89, 434, 235, 443);
            Pokemon mustebouee = new Pokemon(-1, "Mustébouée", 54, 196, false, 1, 453, 55, 98, 97, 103, 418);
            Pokemon etouraptor = new Pokemon(-1, "Étouraptor", 80, 334, false, 1, 332, 38, 413, 19, 123, 398);
            Pokemon oniglali = new Pokemon(-1, "Oniglali", 67, 358, false, 1, 58, 59, 419, 420, 54, 362);
            Pokemon vipelierre = new Pokemon(-1, "Vipélierre", 36, 112, false, 1, 22, 536, 437, 378, 79, 495);
            Pokemon moustillon = new Pokemon(-1, "Moustillon", 34, 104, false, 1, 534, 15, 453, 210, 228, 501);
            Pokemon grotichon = new Pokemon(-1, "Grotichon", 49, 186, false, 1, 488, 53, 67, 68, 35, 499);
            Pokemon manternel = new Pokemon(-1, "Manternel", 80, 334, false, 1, 238, 348, 210, 235, 43, 542);
            Pokemon geolithe = new Pokemon(-1, "Géolithe", 41, 199, false, 1, 350, 157, 222, 479, 89, 525);
            Pokemon crocorible = new Pokemon(-1, "Crocorible", 67, 306, false, 1, 90, 91, 399, 389, 119, 553);
            Pokemon deflaisan = new Pokemon(-1, "Déflaisan", 66, 259, false, 1, 332, 355, 143, 340, 45, 521);
            Pokemon baggiguane = new Pokemon(-1, "Baggiguane", 80, 334, false, 1, 136, 395, 411, 70, 151, 559);
            Pokemon batracne = new Pokemon(-1, "Batracné", 57, 268, false, 1, 57, 401, 482, 182, 123, 536);
            Pokemon zoroark = new Pokemon(-1, "Zoroark", 56, 245, false, 1, 185, 492, 389, 163, 116, 571);
            Pokemon amphinobi = new Pokemon(-1, "Amphinobi", 72, 289, false, 1, 594, 400, 127, 164, 214, 658);
            Pokemon flambusard = new Pokemon(-1, "Flambusard", 68, 304, false, 1, 332, 52, 413, 13, 52, 663);
            Pokemon muplodocus = new Pokemon(-1, "Muplodocus", 66, 417, false, 1, 406, 407, 525, 692, 111, 706);
            Pokemon bruyverne = new Pokemon(-1, "Bruyverne", 64, 387, false, 1, 332, 304, 542, 406, 121, 715);
            Pokemon brutalibre = new Pokemon(-1, "Brutalibré", 60, 241, false, 1, 507, 560, 25, 136, 75, 701);
            Pokemon brindibou = new Pokemon(-1, "Brindibou", 42, 146, false, 1, 669, 670, 365, 65, 79, 722);
            Pokemon matoufeu = new Pokemon(-1, "Matoufeu", 44, 184, false, 1, 306, 154, 53, 424, 35, 726);
            Pokemon lougaroc = new Pokemon(-1, "Lougaroc", 43, 153, false, 1, 444, 157, 317, 439, 193, 745);
            Pokemon melmetal = new Pokemon(-1, "Melmetal", 47, 191, false, 1, 742, 9, 430, 276, 174, 809);

            //insertion de tous les pokemons dans la bdd
            executeUpdate("INSERT INTO Pokemon VALUES " + pikachuSacha.getInsertSubRequest()
                    + "," + pikachuRed.getInsertSubRequest()
                    + "," + lokhlassRed.getInsertSubRequest()
                    + "," + ronflexRed.getInsertSubRequest()
                    + "," + florizarreRed.getInsertSubRequest()
                    + "," + dracaufeuRed.getInsertSubRequest()
                    + "," + tortankRed.getInsertSubRequest()
                    + "," + mewtwoRed.getInsertSubRequest()
                    + "," + tapu.getInsertSubRequest()
                    + "," + salamence.getInsertSubRequest()
                    + "," + snorlex.getInsertSubRequest()
                    + "," + incineroar.getInsertSubRequest()
                    + "," + gastrodon.getInsertSubRequest()
                    + "," + kartana.getInsertSubRequest()
                    + "," + dracaufeuSacha.getInsertSubRequest()
                    + "," + ronflexSacha.getInsertSubRequest()
                    + "," + bulbizarre.getInsertSubRequest()
                    + "," + metamorph.getInsertSubRequest()
                    + "," + krabboss.getInsertSubRequest()
                    + "," + tauros.getInsertSubRequest()
                    + "," + grotadmorv.getInsertSubRequest()
                    + "," + feurisson.getInsertSubRequest()
                    + "," + macronium.getInsertSubRequest()
                    + "," + kaiminus.getInsertSubRequest()
                    + "," + scarhino.getInsertSubRequest()
                    + "," + noarfang.getInsertSubRequest()
                    + "," + donphan.getInsertSubRequest()
                    + "," + jungko.getInsertSubRequest()
                    + "," + heledelle.getInsertSubRequest()
                    + "," + chartor.getInsertSubRequest()
                    + "," + scorvol.getInsertSubRequest()
                    + "," + simiabraz.getInsertSubRequest()
                    + "," + torterra.getInsertSubRequest()
                    + "," + griknot.getInsertSubRequest()
                    + "," + mustebouee.getInsertSubRequest()
                    + "," + etouraptor.getInsertSubRequest()
                    + "," + oniglali.getInsertSubRequest()
                    + "," + vipelierre.getInsertSubRequest()
                    + "," + moustillon.getInsertSubRequest()
                    + "," + grotichon.getInsertSubRequest()
                    + "," + manternel.getInsertSubRequest()
                    + "," + geolithe.getInsertSubRequest()
                    + "," + crocorible.getInsertSubRequest()
                    + "," + deflaisan.getInsertSubRequest()
                    + "," + baggiguane.getInsertSubRequest()
                    + "," + batracne.getInsertSubRequest()
                    + "," + zoroark.getInsertSubRequest()
                    + "," + amphinobi.getInsertSubRequest()
                    + "," + flambusard.getInsertSubRequest()
                    + "," + muplodocus.getInsertSubRequest()
                    + "," + bruyverne.getInsertSubRequest()
                    + "," + brutalibre.getInsertSubRequest()
                    + "," + brindibou.getInsertSubRequest()
                    + "," + matoufeu.getInsertSubRequest()
                    + "," + lougaroc.getInsertSubRequest()
                    + "," + melmetal.getInsertSubRequest()
            );

            //modification des equipe de chaque dresseur
            executeUpdate("UPDATE trainer SET "
                    + "id_pokemon1=1, "
                    + "id_pokemon2=15, "
                    + "id_pokemon3=16, "
                    + "id_pokemon4=32, "
                    + "id_pokemon5=48, "
                    + "id_pokemon6=56 "
                    + " WHERE id=1");
            executeUpdate("UPDATE trainer SET "
                    + "id_pokemon1=2, "
                    + "id_pokemon2=3, "
                    + "id_pokemon3=4, "
                    + "id_pokemon4=5, "
                    + "id_pokemon5=6, "
                    + "id_pokemon6=7 "
                    + "WHERE id=2");
            executeUpdate("UPDATE trainer SET "
                    + "id_pokemon1=9, "
                    + "id_pokemon2=10, "
                    + "id_pokemon3=11, "
                    + "id_pokemon4=12, "
                    + "id_pokemon5=13, "
                    + "id_pokemon6=14 "
                    + "WHERE id=3");

        } catch (SQLException | IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void executeUpdate(String request) {//permet la modification de la bdd (INSERT, CREATE, DROP, UPDATE, ...)
        //System.out.println(request);
        try {
            st.executeUpdate(request);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet executeQuery(String request) { //permet de lire ce qu'il y a dans la table (SELECT)
        // System.out.println(request);
        try {
            return st.executeQuery(request);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //Simplifie la selection de donnée das la bdd
    //renvoie une arrayList contenant des instances de classe hérités de DBElement 
    //Chaque element de l'ArrayList correspond à une ligne de la projection
    //Ne permet pas de faire des jointures ou de selectionner que certaines collones de la table
    public <T extends DBElement> ArrayList<T> getFromDB(String request, Class<T> className) {
        ResultSet rs = executeQuery(request);

        ArrayList<T> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(className.getDeclaredConstructor(ResultSet.class).newInstance(rs));
            }
            return list;
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException
                | SecurityException | InvocationTargetException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //Simplifie la selection de donnée das la bdd
    //renvoie une arrayList de tableaux d'Object contenant des Integer/Boolean/Float/...
    //Chaque element de l'ArrayList correspond à une ligne de la projection
    public ArrayList<Object[]> getFromDB(String request) {
        ResultSet rs = executeQuery(request);

        ArrayList<Object[]> list = new ArrayList();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnNumber = rsmd.getColumnCount();
            while (rs.next()) {
                Object row[] = new Object[columnNumber];
                for (int i = 1; i <= columnNumber; i++) {
                    switch (rsmd.getColumnType(i)) {
                        case Types.VARCHAR:
                            row[i - 1] = rs.getString(i);
                            break;
                        case Types.NUMERIC:
                            row[i - 1] = (rs.getFloat(i));
                            break;
                        case Types.INTEGER:
                        case Types.BIGINT:
                            row[i - 1] = (rs.getInt(i));
                            break;
                        case Types.BOOLEAN:
                        case Types.BIT:
                            row[i - 1] = (rs.getBoolean(i));
                            break;
                        case Types.BINARY:
                            row[i - 1] = (rs.getBytes(i));
                            // row[i]=("FAUT GERER LA RECUP DES IMAGES");
                            break;
                        default:
                            System.out.println("NON SUPPORTED TYPE !!! (" + i + ") " + rsmd.getColumnType(i) + " "
                                    + rsmd.getColumnTypeName(i));
                            break;
                    }
                }
                list.add(row);
            }
            return list;
        } catch (IllegalArgumentException | SecurityException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //simplifie la modification de plusieurs colonnes d'une ligne
    public void modify(String listeAModif, int idModif, String[] colonnesModifiees, Object[] valeursModif) {
        for (int i = 0; i < colonnesModifiees.length; i++) {
            if (valeursModif[i] == null) {
                this.executeUpdate("UPDATE " + listeAModif + " SET " + colonnesModifiees[i].replace("'", "''")
                        + " = NULL " + "WHERE id = " + idModif);
            } else {
                this.executeUpdate("UPDATE " + listeAModif + " SET " + colonnesModifiees[i].replace("'", "''") + " = '"
                        + valeursModif[i].toString().replace("'", "''").replace(",", ".") + "' WHERE id = " + idModif);
            }
        }
    }

    // methode de suppression de plusieurs lignes d'une table en renseignant l'id
    // des ces lignes
    public void deleteFromID(String liste_del, int[] id_suppression) {
        for (int i = 0; i < id_suppression.length; i++) {
            this.executeUpdate("DELETE FROM " + liste_del + " WHERE id = " + id_suppression[i]);
        }
    }

    /*
     * Methode qui test si le string est un entier ParseInt essaies de tranformer en
     * entier, s'il n'y arrive pas, alors il y'a une erreur attrapée et le booléen
     * passe à faux, sinon le booleen est vrai.
     */
    public boolean TestInt(String chaine) {
        try {
            Integer.parseInt(chaine, 10);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // Methode permettant de supprimer des lignes selon une condition quelconque sur
    // une colonne quelconque
    public void deleteFromCondition(String liste_del, String colonne, String condition) {
        if (TestInt(condition) == true) {
            this.executeUpdate("DELETE FROM " + liste_del + " WHERE " + colonne + " = " + condition);
        } else {
            this.executeUpdate("DELETE FROM " + liste_del + " WHERE " + colonne + " = '" + condition + "'");
        }
    }

    // Methode permettant de supprimer des lignes d'une table selon une condition
    // d'égalité avec une autre table.
    public void deleteFromOther(String table_del, String table2, String colonne) {
        this.executeUpdate("DELETE FROM " + table_del + " USING " + table2 + " WHERE " + table_del + "." + colonne
                + " = " + table2 + "." + colonne);
    }

    // Methode qui récupère et convertit une image de la bdd enn une image utilisable
    public Image getImage(String request) {
        byte[] imageByte = (byte[]) getFromDB(request).get(0)[0];
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            return ImageIO.read(bis);
        } catch (IOException ex) {
            Logger.getLogger(PokedexApp.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
