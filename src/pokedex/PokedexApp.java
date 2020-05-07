/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Leon
 */
public class PokedexApp extends JFrame implements ActionListener {

    String dbName = "pokedex";
    String schemaName = "pokedex";

    Database db;
    JPanel mainPanel;
    ImagePanel imagePanel;
    SelectionPanel selectionPanel;
    
    int idActuel;

    public PokedexApp() {

        db = new Database();
        //createDB();
        db.connectDB(dbName);
        db.executeUpdate("SET search_path TO " + schemaName);
        setupWindow();
        //testRequest();
    }

    private void setupWindow() {
        setTitle("Pokedex 4.0");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        setContentPane(mainPanel);
        setLayout(new BorderLayout());

        imagePanel = new ImagePanel();
        Image image = db.getImage("SELECT image FROM pokedex WHERE id=001");
        imagePanel.setImage(image);
        mainPanel.add(imagePanel, BorderLayout.CENTER);

        selectionPanel = new SelectionPanel(001, "Bulbizarre", this, "dresseur", this);
        idActuel=1;
        mainPanel.add(selectionPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void createDB() {
        db.createDB(dbName);
        db.executeFile("ressources/creation_tables.sql", schemaName);
        db.importAll();
    }

    private void testRequest() {
        Pokemon corona = new Pokemon("Coronavirus", 42, 1000, -1, 1, 3, 5, 8, 110, 188);
        db.executeUpdate("INSERT INTO Pokemon VALUES " + corona.getInsertSubRequest());

        ArrayList<Pokemon> listPokemon = db.getFromDB("SELECT * FROM pokemon WHERE id<=2", Pokemon.class);
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
        db.modify("Pokemon", tableau, tableau2, tableau3);

        listPokemon = db.getFromDB("SELECT * FROM pokemon", Pokemon.class);
        for (Pokemon pokemon : listPokemon) {
            System.out.println("" + pokemon);
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        int goalId=-1;
        if (arg0.getSource() instanceof JButton) {
            JButton button = (JButton) (arg0.getSource());
            if("up".equals(button.getText())){
                goalId = idActuel + 1;
            }
            if("down".equals(button.getText())){
                goalId = idActuel - 1;
            }
        }else{
            goalId = selectionPanel.getGoId();
        }
        try {
            selectionPanel.setId(goalId, db);
            Image image = db.getImage("SELECT image FROM pokedex WHERE id=" + goalId);
            imagePanel.setImage(image);
            idActuel = goalId;
        } catch (NumberFormatException ex) {

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
