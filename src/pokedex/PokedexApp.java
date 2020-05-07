/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Leon
 */
public class PokedexApp extends JFrame {

    Database db;
    JPanel pan;

    public PokedexApp() {
        this.setTitle("Pokedex 4.0");
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pan = new JPanel();
        this.setContentPane(pan);
        this.setVisible(true);
        start();
        testRequest();
    }

    public void start() {
        String dbName = "pokedex";
        String schemaName = "pokedex";

        db = new Database();
        db.createDB(dbName);
        db.executeFile("ressources/creation_tables.sql", schemaName);
        db.importAll();
    }

    private void testRequest() {
        Pokemon corona = new Pokemon("Coronavirus", 42, 1000, -1, 1, 3, 5, 8, 110, 188);
        db.executeUpdate("INSERT INTO Pokemon VALUES " + corona.getInsertSubRequest());

        ArrayList<Pokemon> listPokemon = db.getFromDB("SELECT * FROM pokemon", Pokemon.class);
        for (Pokemon pokemon : listPokemon) {
            System.out.println("" + pokemon);
        }
        ArrayList<Pokedex> listPokedex = db.getFromDB("SELECT * FROM pokedex WHERE id<=2", Pokedex.class);
        for (Pokedex pokedex : listPokedex) {
            System.out.println("" + pokedex);
        }
        ArrayList<pokedex.Type> listType = db.getFromDB("SELECT * FROM type WHERE id<=2", pokedex.Type.class);
        for (pokedex.Type type : listType) {
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

        ArrayList<Object> listPokemonPokedex = db.getFromDB("SELECT pokemon.name ,pokedex.name, ability.name  FROM pokemon JOIN pokedex ON pokemon.id_pokedex = pokedex.id JOIN ability ON pokemon.id_ability=ability.id");
        System.out.println("" + listPokemonPokedex);

        // Test de la fonction modification
        int[] tableau = {1, 1};
        String[] tableau2 = {"level", "name"};
        Object[] tableau3 = {4, "Coronovarus"};
        db.Modify("Pokemon", tableau, tableau2, tableau3);

        listPokemon = db.getFromDB("SELECT * FROM pokemon", Pokemon.class);
        for (Pokemon pokemon : listPokemon) {
            System.out.println("" + pokemon);
        }

        while (true) {
            String result = JOptionPane.showInputDialog(null, "Id du pokedex", "Pokedex Finder", JOptionPane.QUESTION_MESSAGE);
            if (result==null || result.length() == 0) {
                break;
            }
            listPokedex = db.getFromDB("SELECT * FROM pokedex WHERE id=" + Integer.parseInt(result), Pokedex.class);
            for (Pokedex pokedex : listPokedex) {
                System.out.println("" + pokedex);
            }
            listPokemonPokedex = db.getFromDB("SELECT * FROM pokedex WHERE id=" + Integer.parseInt(result));
            System.out.println("" + listPokemonPokedex);
            byte[] imageByte = (byte[]) listPokemonPokedex.get(17);

            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                BufferedImage image = ImageIO.read(bis);
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                pan.removeAll();
                pan.add(imageLabel);
                pan.repaint();
                this.revalidate();
                this.repaint();
            } catch (IOException ex) {
                Logger.getLogger(PokedexApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new PokedexApp();
    }

    /*
INSERT INTO type VALUES (default, "Electrik",1, 1, 1, 0.5, 1, 1, 1, 0.5, 1, 1, 2, 1, 1, 1, 1, 1, 0.5, 1);
INSERT INTO pokedex VALUES (172, 'Pichu', 'Pichu', 1, NULL, 'Pokémon Minisouris',  0.2, 2, NULL, 25),
						   (25, 'Pikachu', 'Pikachu', 1, NULL, 'Pokémon Souris',  0.4, 6, 172, 26),
						   (26, 'Raichu', 'Raichu', 1, NULL, 'Pokémon Souris',  0.8, 30, 25, NULL);*/
}
