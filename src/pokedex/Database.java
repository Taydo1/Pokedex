/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                while ((ligne = br.readLine()) != null) {
                    fichier += " " + ligne;
                }
            }

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
        } catch (SQLException | IOException ex) {
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
                            //row[i]=("FAUT GERER LA RECUP DES IMAGES");
                            break;
                        default:
                            System.out.println("NON SUPPORTED TYPE !!! (" + i + ") " + rsmd.getColumnType(i) + " " + rsmd.getColumnTypeName(i));
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

    public void modify(String listeAModif, int idModif, String[] colonnesModifiees, Object[] valeursModif) {
        for (int i = 0; i < colonnesModifiees.length; i++) {
            this.executeUpdate("UPDATE " + listeAModif + " SET " + colonnesModifiees[i].replace("'", "''") + " = '" + valeursModif[i].toString().replace("'", "''")
                    + "' WHERE id = " + idModif);
        }
    }
    //methode de suppression de plusieurs lignes d'une table en renseignant l'id des ces lignes
   public void deleteFromID(String liste_del, int[] id_suppression){
        this.executeUpdate("DELETE FROM " +liste_del+ " WHERE id = " +Arrays.toString(id_suppression));
    }
   
/*Methode qui test si le string est un entier
ParseInt essaies de tranformer en entier, s'il n'y arrive pas, alors il y'a une 
erreur attrapée et le booléen passe à faux, sinon le booleen est vrai.*/
   public boolean TestInt(String chaine) {
		try {
			Integer.parseInt(chaine,10);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}
   //Methode permettant de supprimer des lignes selon une condition quelconque sur une colonne quelconque
   public void deleteFromCondition(String liste_del,String colonne, String condition  ){
       if (TestInt(condition)==true){
           this.executeUpdate("DELETE FROM "+ liste_del +" WHERE "+colonne+" = "+condition);
       }
       else{
           this.executeUpdate("DELETE FROM "+ liste_del +" WHERE "+colonne+" = '"+condition+"'");
       }
   }
   //Methode permettant de supprimer des lignes d'une table selon une condition d'égalité avec une autre table.
   public void deleteFromOther(String table_del, String table2,  String colonne){
       this.executeUpdate("DELETE FROM "+ table_del +" USING "+table2+ " WHERE "+table_del+"."+colonne+" = "+table2+"."+colonne);
   }

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
