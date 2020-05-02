/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pokedex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Pokedex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void importAll() {
        String ligne;
        try {
            Map<String, Integer> type2id = new HashMap<>();
            BufferedReader br = new BufferedReader(new FileReader("ressources/liste_types.csv"));
            br.readLine();
            String request = "INSERT INTO type VALUES ";
            while ((ligne = br.readLine()) != null) {
                request += new Type(ligne, type2id).getRequest() + ",";
            }
            executeUpdate(request.substring(0, request.length()-1));
            br.close();
            
            request = "INSERT INTO ability VALUES ";
            br = new BufferedReader(new FileReader("ressources/liste_abilities.csv"));
            br.readLine();
            while ((ligne = br.readLine()) != null) {
                request += new Ability(ligne).getRequest() + ",";
            }
            executeUpdate(request.substring(0, request.length()-1));
            br.close();
            
            request = "INSERT INTO move VALUES ";
            br = new BufferedReader(new FileReader("ressources/liste_moves.csv"));
            br.readLine();
            while ((ligne = br.readLine()) != null) {
                request += new Move(ligne, type2id).getRequest() + ",";
            }
            executeUpdate(request.substring(0, request.length()-1));
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pokedex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Pokedex.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println(request);
        try {
            st.executeUpdate(request);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
