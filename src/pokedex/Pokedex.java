/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.sql.*;
import java.sql.DriverManager;

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
        String dbName="pokedex";
        String schemaName ="pokedex";
        String[] tables = {"test1"," test2"};
        Statement st;
        try {
            Class.forName("org.postgresql.Driver");
            st = connectDB("");
            st.executeUpdate("DROP database IF EXISTS "+dbName+";");
            st.executeUpdate("CREATE database "+dbName+";");
            st = connectDB(dbName);
            st.executeUpdate("CREATE SCHEMA "+schemaName+";");
            st.executeUpdate("SET search_path TO"+schemaName);
            st.executeUpdate("CREATE TABLE pokedex("
                +"id serial PRIMARY KEY"
                +"nom varchar(30));");
            
            // vvv POur lister toutes les tables/schema de la bdd vvv
            //SELECT table_schema, table_name FROM information_schema.tables WHERE table_schema NOT IN ('pg_catalog', 'information_schema');
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully NOOOOOOO");
    }
    
    public static Statement connectDB(String dbName) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbName, "postgres", "hugoquentinleon");
        return c.createStatement();
    }
} 