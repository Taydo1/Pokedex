/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.trainer;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import pokedex.database.Database;
import pokedex.database.Pokemon;
import pokedex.database.Trainer;
import pokedex.gui.Action;
import pokedex.gui.widgets.InfoButton;
import pokedex.gui.widgets.Label;
import pokedex.gui.MainPanel;
import pokedex.gui.pokedex.StyledButton;

/**
 *
 * @author Leon
 */
public class TrainerPanel extends JPanel implements ActionListener {

    JComboBox<InfoButton> selector;
    InfoButton pokemons[], modification, delete;
    Label name, pokemonLabel, blank;
    Database db;
    StyledButton add;
    MainPanel parent;

    public TrainerPanel(Database db, MainPanel parent) {
        super();
        this.db = db;
        this.parent = parent;
        pokemonLabel = new Label("", true);

        name = new Label("", true);
        pokemonLabel = new Label("Pokemon", true);
        pokemons = new InfoButton[6];
        for (int i = 0; i < 6; i++) {
            pokemons[i] = new InfoButton("", 0, true);
            pokemons[i].setActionCommand(Action.GET_POKEMON.name());
            pokemons[i].addActionListener(parent);
        }

        blank = new Label();
        add = new StyledButton("Ajouter un Pokemon", true);
        modification = new InfoButton("", 0, true);
        delete = new InfoButton("", 0, true);

        add.addActionListener(this);
        modification.addActionListener(this);
        delete.addActionListener(this);
        add.setActionCommand(Action.START_INSERTION.name());
        modification.setActionCommand(Action.START_MODIFICATION.name());
        delete.setActionCommand(Action.DELETE.name());

        selector = new JComboBox<>();
        selector.setBackground(Color.GRAY);
        selector.setForeground(Color.WHITE);

        InfoButton selectorButton;
        ArrayList<Object[]> trainerList = db.getFromDB("SELECT id,name FROM trainer");
        trainerList.add(0, new Object[]{0, ""});
        for (int i = 0; i < trainerList.size(); i++) {
            selectorButton = new InfoButton((String) trainerList.get(i)[1], (Integer) trainerList.get(i)[0]);
            selector.addItem(selectorButton);
        }
        selector.setActionCommand(Action.GET_TRAINER.name());
        selector.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        add(selector, c);
        c.gridy++;
        add(name, c);
        c.gridy++;
        add(pokemonLabel, c);
        for (int i = 0; i < pokemons.length; i += 2) {
            c.gridx = 0;
            c.gridy++;
            add(pokemons[i], c);
            c.gridy++;
            add(pokemons[i + 1], c);
        }
        c.gridy++;
        add(blank, c);
        c.gridy++;
        add(add, c);
        c.gridy++;
        add(modification, c);
        c.gridy++;
        add(delete, c);
        setId(-1);
    }

    private int findSelectorId(int id) {
        for (int i = 0; i < selector.getItemCount(); i++) {
            InfoButton item = selector.getItemAt(i);
            if (item.getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public void setId(int id) {
        for (int i = 0; i < 6; i++) {
            pokemons[i].setVisible(false);
            pokemons[i].setId(0);
            pokemons[i].setText("");
        }
        name.setText("");
        pokemonLabel.setVisible(false);
        ArrayList<Trainer> currentTrainerList = db.getFromDB("SELECT * FROM trainer WHERE id=" + id, Trainer.class);
        if (!currentTrainerList.isEmpty()) {
            Trainer currentTrainer = currentTrainerList.get(0);

            name.setText(currentTrainer.name);

            ArrayList<Pokemon> pokemonOfCurrentTrainer = currentTrainer.getPokemons(db);
            if (pokemonOfCurrentTrainer.size() > 1) {
                pokemonLabel.setVisible(true);
                pokemonLabel.setText("Pokemons");
            } else if (pokemonOfCurrentTrainer.size() > 0) {
                pokemonLabel.setVisible(true);
                pokemonLabel.setText("Pokemon");
            }
            for (int i = 0; i < 6 && i < pokemonOfCurrentTrainer.size(); i++) {
                pokemons[i].setVisible(true);
                pokemons[i].setId(pokemonOfCurrentTrainer.get(i).id);
                pokemons[i].setText(pokemonOfCurrentTrainer.get(i).name);
            }
            selector.setSelectedIndex(findSelectorId(id));

            modification.setText("Modifier le dresseur " + currentTrainer.name);
            modification.setId(id);
            delete.setText("Supprimer le dresseur " + currentTrainer.name);
            delete.setId(id);
            modification.setVisible(true);
            delete.setVisible(true);
        }else{
            modification.setVisible(false);
            delete.setVisible(false);
        }
    }

    public void setUser(String user) {
        switch (user.toLowerCase()) {
            case "professeur":
                add.setEnabled(true);
                modification.setEnabled(true);
                delete.setEnabled(true);
                break;
            case "dresseur":
                add.setEnabled(true);
                modification.setEnabled(false);
                delete.setEnabled(true);
                break;
            case "visiteur":
                add.setEnabled(false);
                modification.setEnabled(false);
                delete.setEnabled(false);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InfoButton source;
        switch (Action.valueOf(e.getActionCommand())) {
            case GET_TRAINER:
                source = (InfoButton) selector.getSelectedItem();
                setId(source.getId());
                break;
            case START_MODIFICATION:
                parent.addTab(
                        new TrainerModifInsertPanel(modification.getId(), parent),
                        "Modification de " + db.getFromDB("SELECT name FROM trainer WHERE id=" + modification.getId()).get(0)[0],
                        MainPanel.PROFESSOR_TAB
                );
                break;
            case START_INSERTION:
                parent.addTab(
                        new TrainerModifInsertPanel(parent),
                        "Naissance d'un Dresseur",
                        MainPanel.PROFESSOR_TAB
                );
                break;
            case DELETE:
                System.err.println("PAS ENCORE IMPLEMENTE");
                break;
        }
    }

}
