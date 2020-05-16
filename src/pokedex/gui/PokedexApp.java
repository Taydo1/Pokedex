/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui;

import java.util.ArrayList;
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

        db = new Database();
        db.setupDB(dbName, schemaName, false);
        testRequest();
        setupWindow();
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

    private void testRequest() {
        //db.executeUpdate("TRUNCATE TABLE ");
        db.executeUpdate("TRUNCATE TABLE trainer,pokemon RESTART IDENTITY");
        Trainer boss = new Trainer("Giga BOSS");
        db.executeUpdate("INSERT INTO trainer VALUES " + boss.getInsertSubRequest());

        Pokemon corona = new Pokemon("Coronavirus", 42, 1000, false, 1, 1, 3, 5, 8, 188, 110);
        Pokemon corona2 = new Pokemon("Coronavirus d'août", 69, 10000, true, 1, 1, 3, -1, -1, 188, 110);
        db.executeUpdate("INSERT INTO Pokemon VALUES " + corona.getInsertSubRequest() + "," + corona2.getInsertSubRequest());

        db.printTable("trainer");

        boss = db.getFromDB("SELECT * FROM trainer WHERE id=1", Trainer.class).get(0);
        ArrayList<Pokemon> pokeDeSamet = boss.getPokemons(db);
        for (Pokemon pokemon : pokeDeSamet) {
            System.out.println("" + pokemon);
        }
        System.out.println("fini");

        /*ArrayList<Pokemon> listPokemon = db.getFromDB("SELECT * FROM pokemon WHERE id<=2", Pokemon.class);
        for (Pokemon pokemon : listPokemon) {
            System.out.println("" + pokemon);
        }
        ArrayList<Pokedex> listPokedex = db.getFromDB("SELECT * FROM pokedex WHERE id<=2", Pokedex.class);
        for (Pokedex pokedex : listPokedex) {
            System.out.println("" + pokedex);
        }
        ArrayList<pokedex.database.Type> listType = db.getFromDB("SELECT * FROM type WHERE id<=2", pokedex.database.Type.class);
        for (pokedex.database.Type type : listType) {
            System.out.println("" + type);
        }
        ArrayList<Ability> listTalent = db.getFromDB("SELECT * FROM ability WHERE id<=2", Ability.class);
        for (Ability talent : listTalent) {
            System.out.println("" + talent);
        }
        ArrayList<Move> listAttaque = db.getFromDB("SELECT * FROM move WHERE id<=2", Move.class);
        for (Move attaque : listAttaque) {
            System.out.println("" + attaque);
        }

        ArrayList<Object[]> listPokemonPokedex = db.getFromDB("SELECT pokemon.name ,pokedex.name, ability.name  FROM pokemon JOIN pokedex ON pokemon.id_pokedex = pokedex.id JOIN ability ON pokemon.id_ability=ability.id");
        for (Object[] row : listPokemonPokedex) {
            System.out.println("" + row);
        }*/

 /*//Test de la fonction modification
        int[] tableau = {1, 1};
        String[] tableau2 = {"level", "name"};
        Object[] tableau3 = {4, "Coronovarus"};
        db.modify("Pokemon", 1, tableau2, tableau3);
        
        //test delete
        db.printTable("Pokemon");
        int[] suppression = {2,3};
        db.deleteFromID("Pokemon",suppression);
        db.printTable("Pokemon");
        db.deleteFromCondition("Pokemon", "name", "Coronavirus");
        db.printTable("Pokemon");
        
        listPokemon = db.getFromDB("SELECT * FROM pokemon", Pokemon.class);
        for (Pokemon pokemon : listPokemon) {
            System.out.println("" + pokemon);
        }*/
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new PokedexApp();
    }
}
