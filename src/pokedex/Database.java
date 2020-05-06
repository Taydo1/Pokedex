/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.io.BufferedReader;
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
import net.efabrika.util.DBTablePrinter;

/**
 *
 * @author Leon
 */
public class Database {

    private Statement st;
    private Connection conn;

    public void connectDB(String dbName) {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbName, "postgres", "hugoquentinleon");
            st = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createDB(String dbName) {
        try {
            connectDB("");
            st.executeUpdate("DROP database IF EXISTS " + dbName);
            st.executeUpdate("CREATE database " + dbName);
            connectDB(dbName);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void executeFile(String fileName, String replace) {
        String fichier = "";
        String ligne;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((ligne = br.readLine()) != null) {
                fichier += " " + ligne;
            }
            br.close();

            String[] commands = fichier.split(";");
            for (int i = 0; i < commands.length; i++) {
                commands[i] = commands[i].replaceAll("\\$[^\\$]*\\$", replace);
                //System.out.println(commands[i]);
                st.executeUpdate(commands[i]);
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void importAll() {
        String ligne;
        try {
            Map<String, Integer> type2id = new HashMap<>();
            type2id.put(" ", -1);
            Map<String, Integer> ability2id = new HashMap<>();
            ability2id.put(" ", -1);
            BufferedReader br = new BufferedReader(new FileReader("ressources/liste_types.csv"));
            br.readLine();
            String request = "INSERT INTO type VALUES ";
            while ((ligne = br.readLine()) != null) {
                request += new Type(ligne, type2id).getInsertSubRequest() + ",";
            }
            executeUpdate(request.substring(0, request.length() - 1));
            br.close();

            request = "INSERT INTO ability VALUES ";
            br = new BufferedReader(new FileReader("ressources/liste_abilities.csv"));
            br.readLine();
            while ((ligne = br.readLine()) != null) {
                request += new Ability(ligne, ability2id).getInsertSubRequest() + ",";
            }
            executeUpdate(request.substring(0, request.length() - 1));
            br.close();

            request = "INSERT INTO move VALUES ";
            br = new BufferedReader(new FileReader("ressources/liste_moves.csv"));
            br.readLine();
            while ((ligne = br.readLine()) != null) {
                request += new Move(ligne, type2id).getInsertSubRequest() + ",";
            }
            executeUpdate(request.substring(0, request.length() - 1));
            br.close();

            request = "INSERT INTO pokedex VALUES ";
            br = new BufferedReader(new FileReader("ressources/liste_pokedex.csv"));
            br.readLine();
            while ((ligne = br.readLine()) != null) {
                request += new Pokedex(ligne, type2id, ability2id).getInsertSubRequest() + ",";
            }
            //System.out.println(request);
            executeUpdate(request.substring(0, request.length() - 1));
            br.close();

            int imageNb = 809;
            int shinyImageNb = 151;
            for (int i = 1; i <= imageNb; i++) {
                File file = new File(String.format("ressources/images/normal/%03d.png", i));
                FileInputStream fis = new FileInputStream(file);

                FileInputStream fisShiny = null;
                PreparedStatement ps;
                if (i <= shinyImageNb) {
                    File fileShiny = new File(String.format("ressources/images/shiny/%03d.png", i));
                    fisShiny = new FileInputStream(fileShiny);
                    ps = conn.prepareStatement("UPDATE pokedex SET image=?, image_shiny=? WHERE id=" + i);
                    ps.setBinaryStream(1, fis, file.length());
                    ps.setBinaryStream(2, fisShiny, fileShiny.length());
                } else {
                    ps = conn.prepareStatement("UPDATE pokedex SET image=? WHERE id=" + i);
                    ps.setBinaryStream(1, fis, file.length());
                }
                ps.executeUpdate();
                ps.close();
                fis.close();
                if (i <= shinyImageNb) {
                    fisShiny.close();
                }
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printTable(String tableName) {
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM " + tableName);
            DBTablePrinter.printResultSet(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void executeUpdate(String request) {
        //System.out.println(request);
        try {
            st.executeUpdate(request);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet executeQuery(String request) {
        //System.out.println(request);
        try {
            return st.executeQuery(request);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public <T extends DBElement> ArrayList<T> getFromDB(String request, Class<T> className) {
        ResultSet rs = executeQuery(request);

        ArrayList<T> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(className.getDeclaredConstructor(ResultSet.class).newInstance(rs));
            }
            return list;
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<Object> getFromDB(String request) {
        ResultSet rs = executeQuery(request);

        ArrayList<Object> list = new ArrayList();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnNumber; i++) {
                    switch (rsmd.getColumnType(i)) {
                        case Types.VARCHAR:
                            list.add(rs.getString(i));
                            break;
                        case Types.NUMERIC:
                            list.add(rs.getFloat(i));
                            break;
                        case Types.INTEGER:
                            list.add(rs.getInt(i));
                            break;
                        case Types.BOOLEAN:
                            list.add(rs.getBoolean(i));
                            break;
                        case Types.BINARY:
                            list.add("FAUT GERER LA RECUP DES IMAGES");
                            break;
                        default:
                            System.out.println("NON SUPPORTED TYPE !!! " + rsmd.getColumnType(i) + " " + rsmd.getColumnTypeName(i));
                            break;
                    }
                }
            }
            return list;
        } catch (IllegalArgumentException | SecurityException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void Modify(String liste_a_modif, int[] id_a_modifier, String[] colonnes_a_modifier, Object[] valeurs_modif) {
        for (int i = 0; i < colonnes_a_modifier.length; i++) {
            this.executeUpdate("UPDATE " + liste_a_modif + " SET " + colonnes_a_modifier[i].replace("'", "''") + " = '" + valeurs_modif[i].toString().replace("'", "''")
                    + "' WHERE id = " + String.valueOf(id_a_modifier[i]));
        }
    }
}
