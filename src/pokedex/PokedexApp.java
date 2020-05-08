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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Leon
 */
public class PokedexApp extends JFrame implements ActionListener {

    String dbName = "pokedex";
    String schemaName = "pokedex";
    String imageName = "image";

    Database db;
    JPanel mainPanel;
    ImagePanel imagePanel;
    SelectionPanel selectionPanel;
    TopPanel topPanel;

    int idActuel;

    public PokedexApp() {

        setupDB(true);
        setupWindow();
        //testRequest();
    }

    private void setupDB(boolean toBeCreated) {
        db = new Database();
        if (toBeCreated) {
            createDB();
        } else {
            db.connectDB(dbName);
            db.executeUpdate("SET search_path TO " + schemaName);
        }
    }

    private void setupWindow() {
        setTitle("Pokedex 4.0");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        setContentPane(mainPanel);
        setLayout(new BorderLayout());

        idActuel = 2;
        imagePanel = new ImagePanel();
        Image image = db.getImage("SELECT image FROM pokedex WHERE id="+idActuel);
        imagePanel.setImage(image);
        mainPanel.add(imagePanel, BorderLayout.CENTER);

        selectionPanel = new SelectionPanel(idActuel, db, "Visiteur", this);
        mainPanel.add(selectionPanel, BorderLayout.SOUTH);

        topPanel = new TopPanel(this, idActuel, db);
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

        //Test de la fonction modification
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
            topPanel.setId(id, db);
            String imageRequest;
            //switch
            imageName = "image";
            Image image = db.getImage("SELECT " + imageName + " FROM pokedex WHERE id=" + id);
            imagePanel.setImage(image);
            idActuel = id;
        } catch (NumberFormatException ex) {

        }
    }

    private String getPassword() {
        PasswordPanel passwordPanel = new PasswordPanel();
        JOptionPane op = new JOptionPane(passwordPanel);
        op.setMessageType(JOptionPane.QUESTION_MESSAGE);
        op.setOptionType(JOptionPane.OK_CANCEL_OPTION);

        JDialog dlg = op.createDialog("Mot de Passe");

        dlg.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                passwordPanel.gainedFocus();
            }
        });
        dlg.setVisible(true);

        if (op.getValue() != null && op.getValue().equals(JOptionPane.OK_OPTION)) {
            return new String(passwordPanel.getPassword());
        }
        return null;
    }

    private void changeUser(JComboBox comboBox) {
        String selection = (String) topPanel.choix.getSelectedItem();
        String mdp;
        if (selection.equals("Visiteur")) {
            mdp = "visiteur";
        } else {
            mdp = getPassword();
        }

        if (mdp != null && mdp.toLowerCase().equals(selection.toLowerCase())) {
            selectionPanel.setUtilisateur(selection);
        } else if (mdp != null) {
            comboBox.setSelectedItem(selectionPanel.getUtilisateur());
            JOptionPane.showMessageDialog(null, "Mauvais mot de passe, sry !", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            comboBox.setSelectedItem(selectionPanel.getUtilisateur());

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
            case GO: {
                try {
                    goToID(selectionPanel.getGoId());
                    selectionPanel.clearGoId();
                } catch (NumberFormatException ex) {
                }
                break;
            }
            case IMAGE_NORMAL:
                imageName = "image";
                goToID(idActuel);
                break;
            case IMAGE_SHINY:
                imageName = "image_shiny";
                goToID(idActuel);
                break;
            case IMAGE_MEGA:
                imageName = "image_mega";
                goToID(idActuel);
                break;
            case CHANGE_USER:
                changeUser((JComboBox) e.getSource());
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
