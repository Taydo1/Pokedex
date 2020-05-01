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
import net.efabrika.util.DBTablePrinter;

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
        
        
        Database db = new Database();
        db.createDB(dbName);
        //Class.forName("org.postgresql.Driver");
        db.executeFile("ressources/creation_tables.sql", schemaName);
        db.printTable("pokedex");
        db.printTable("pokemon");
        
        
        Pokemon pikachu = new Pokemon("Pikachu", 3, 25, 1, 3, 2, 5, 36, 25);
        pikachu.addToDB(db);
        
        db.printTable("pokemon");
    }

    public static void update(Statement st, String cmd) throws SQLException {
        st.executeUpdate(cmd);
    }
}
