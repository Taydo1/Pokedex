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
import java.sql.*;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leon
 */
public class Pokedex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String dbName = "pokedex";
        String schemaName = "pokedex";
        String[] tables = {"test1", " test2"};
        Statement st;
        try {
            Class.forName("org.postgresql.Driver");
            st = connectDB("");
            update(st, "DROP database IF EXISTS " + dbName);
            update(st, "CREATE database " + dbName);
            st = connectDB(dbName);
            executeFile(st, "ressources/creation_tables.sql", schemaName);

            ResultSet rs = st.executeQuery("SELECT * FROM pokedex");
            while (rs.next()) {
                System.out.print("Row : ");
                System.out.print(rs.getString("name") + " ");
                System.out.print(rs.getDouble("poids") + " ");
                System.out.println(rs.getInt("id"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Fini");
    }

    public static void update(Statement st, String cmd) throws SQLException {
        st.executeUpdate(cmd);
    }

    public static Statement connectDB(String dbName) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbName, "postgres", "hugoquentinleon");
        return c.createStatement();
    }

    public static void executeFile(Statement st, String fileName, String replace) throws SQLException {
        String fichier = "";
        String ligne;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((ligne = br.readLine()) != null) {
                fichier += " " + ligne;
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pokedex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Pokedex.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(fichier + "\n");

        String[] commands = fichier.split(";");
        for (int i = 0; i < commands.length; i++) {
            commands[i] = commands[i].replaceAll("\\$[^\\$]*\\$", replace);
            System.out.println(commands[i]);
            st.executeUpdate(commands[i]);
        }
    }
}
