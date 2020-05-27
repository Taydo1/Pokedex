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
        db.executeUpdate("TRUNCATE TABLE trainer,pokemon RESTART IDENTITY");
        Trainer sacha = new Trainer("Sacha", -1, -1, -1, -1, -1, -1);
        Trainer red = new Trainer("Red", -1, -1, -1, -1, -1, -1);
        Trainer ruiz = new Trainer("Ruiz (World Chmpn)", -1, -1, -1, -1, -1, -1);
        db.executeUpdate("INSERT INTO trainer VALUES " + sacha.getInsertSubRequest()
                + "," + red.getInsertSubRequest()
                + "," + ruiz.getInsertSubRequest()
        );

        Pokemon pikachuSacha = new Pokemon(-1, "Pikachu", 42, 1000, false, 1, 2, 3, 0, 0, 188, 25);
        Pokemon evoliSacha = new Pokemon(-1, "Evoli de Sacha", 69, 10000, true, 1, 1, 3, -1, -1, 188, 133);

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
        
        db.executeUpdate("INSERT INTO Pokemon VALUES " + pikachuSacha.getInsertSubRequest()
                + "," + evoliSacha.getInsertSubRequest()
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
        );

        db.executeUpdate("UPDATE trainer SET id_pokemon1=1, id_pokemon2=2 WHERE id=1");
        db.executeUpdate("UPDATE trainer SET "
                + "id_pokemon1=3, "
                + "id_pokemon2=4, "
                + "id_pokemon3=5, "
                + "id_pokemon4=6, "
                + "id_pokemon5=7, "
                + "id_pokemon6=8 "
                + "WHERE id=2");
        db.executeUpdate("UPDATE trainer SET "
                + "id_pokemon1=10, "
                + "id_pokemon2=11, "
                + "id_pokemon3=12, "
                + "id_pokemon4=13, "
                + "id_pokemon5=14, "
                + "id_pokemon6=15 "
                + "WHERE id=3");

        /*db.printTable("trainer");

        boss = db.getFromDB("SELECT * FROM trainer WHERE id=1", Trainer.class).get(0);
        ArrayList<Pokemon> pokeDeSamet = boss.getPokemons(db);
        for (Pokemon pokemon : pokeDeSamet) {
            System.out.println("" + pokemon);
        }
        System.out.println("fini");*/

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
