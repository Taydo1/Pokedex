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
        Trainer sacha = new Trainer("Sacha Ketchum", -1, -1, -1, -1, -1, -1);
        Trainer red = new Trainer("Red", -1, -1, -1, -1, -1, -1);
        Trainer ruiz = new Trainer("Ruiz (World Chmpn)", -1, -1, -1, -1, -1, -1);
        db.executeUpdate("INSERT INTO trainer VALUES " + sacha.getInsertSubRequest()
                + "," + red.getInsertSubRequest()
                + "," + ruiz.getInsertSubRequest()
        );

        Pokemon pikachuSacha = new Pokemon(-1, " Pikachu ", 100, 999, false, 1, 2, 3, 0, 0, 188, 25);

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
        
        db.executeUpdate("INSERT INTO Pokemon VALUES " + pikachuSacha.getInsertSubRequest()
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

        db.executeUpdate("UPDATE trainer SET "
                + "id_pokemon1=1, "
                + "id_pokemon2=15, "
                + "id_pokemon3=16, "
                + "id_pokemon4=32, "
                + "id_pokemon5=48, "
                + "id_pokemon6=56 "
                + " WHERE id=1");
        db.executeUpdate("UPDATE trainer SET "
                + "id_pokemon1=2, "
                + "id_pokemon2=3, "
                + "id_pokemon3=4, "
                + "id_pokemon4=5, "
                + "id_pokemon5=6, "
                + "id_pokemon6=7 "
                + "WHERE id=2");
        db.executeUpdate("UPDATE trainer SET "
                + "id_pokemon1=9, "
                + "id_pokemon2=10, "
                + "id_pokemon3=11, "
                + "id_pokemon4=12, "
                + "id_pokemon5=13, "
                + "id_pokemon6=14 "
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
