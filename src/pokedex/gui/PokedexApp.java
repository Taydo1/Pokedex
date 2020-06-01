/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import pokedex.database.*;

/**
 *
 * @author Leon
 */
public class PokedexApp extends JFrame {

    String dbName = "pokedex";
    String schemaName = "pokedex";

    Database db;
    MainPanel mainPanel;
    JTabbedPane tabbedPane;

    public PokedexApp() {
        db = new Database(); //creation du lien avec postgre
        db.setupDB(dbName, schemaName, true); // on se connecte à la bdd (on la crée si elle n'existe pas ou si on lui envoie true)
        setupWindow(); //on initialise la fenetre
    }
    
    private void setupWindow() {
        setTitle("Pokedex 4.0");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new MainPanel(db, this);
        setContentPane(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PokedexApp();
    }
}
