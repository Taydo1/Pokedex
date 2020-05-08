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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
    TopPanel topPanel;

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

        selectionPanel = new SelectionPanel(001, db, "Visiteur", this);
        idActuel = 1;
        mainPanel.add(selectionPanel, BorderLayout.SOUTH);

        topPanel = new TopPanel(this, 001, db);
        mainPanel.add(topPanel, BorderLayout.NORTH);

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

        ArrayList<Object[]> listPokemonPokedex = db.getFromDB("SELECT pokemon.name ,pokedex.name, ability.name  FROM pokemon JOIN pokedex ON pokemon.id_pokedex = pokedex.id JOIN ability ON pokemon.id_ability=ability.id");
        for (Object[] row : listPokemonPokedex) {
            System.out.println("" + row);
        }

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

    private void goToID(int id) {
        //Go to id
        try {
            selectionPanel.setId(id, db);
            Image image = db.getImage("SELECT image FROM pokedex WHERE id=" + id);
            imagePanel.setImage(image);
            idActuel = id;
        } catch (NumberFormatException ex) {

        }
    }

    private void changeUser(JComboBox comboBox) {
        String selection = (String) topPanel.choix.getSelectedItem();
        String mdp;
        if (selection.equals("Visiteur")) {
            mdp = "visiteur";
        } else {
            JPasswordField passwordField = new JPasswordField();
            int resultPassword = JOptionPane.showConfirmDialog(null, passwordField, "Saisissez le mot de passe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(resultPassword==JOptionPane.OK_OPTION){
                mdp = new String(passwordField.getPassword());
            }else{
                return;
            }
            //mdp = JOptionPane.showInputDialog(null, "Saisissez le mot de passe", "CAPTCHA", JOptionPane.QUESTION_MESSAGE);
        }
        if (mdp.toLowerCase().equals(selection.toLowerCase())) {
            selectionPanel.setUtilisateur(selection);
        } else {
            comboBox.setSelectedItem(selectionPanel.getUtilisateur());
            JOptionPane.showMessageDialog(null, "Mauvais mot de passe, sry !", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Action.valueOf(e.getActionCommand())) {
            case UP:
                goToID(idActuel + 1);
                break;
            case DOWN:
                goToID(idActuel - 1);
                break;
            case GO:
                goToID(selectionPanel.getGoId());
                break;
            case CHANGE_USER:
                changeUser((JComboBox) e.getSource());
        }
        /*if (e.getSource() instanceof JButton) {
            JButton button = (JButton) (e.getSource());
            if ("up".equals(button.getText())) {
                goalId++;
            } else if ("down".equals(button.getText())) {
                goalId--;
            } else if ("go".equals(button.getText())) {
                goalId = selectionPanel.getGoId();
            }
        } else if (e.getSource() instanceof JTextField){
            goalId = selectionPanel.getGoId();
        }else*/ if (e.getSource() instanceof JComboBox) {
            return;
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
