/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.trainer;

import java.awt.Color;
import java.awt.Dimension;
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

    JComboBox<InfoButton> selector, pokemonSelector;
    InfoButton team[], modification, delete;
    ArrayList<InfoButton> pokemons;
    Label name, teamLabel, pokemonLabel, blank, blank2;
    Database db;
    StyledButton add;
    MainPanel parent;
    int currentId;

    public TrainerPanel(Database db, MainPanel parent) {
        super();
        this.db = db;
        this.parent = parent;
        teamLabel = new Label("", true);

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

        name = new Label("", true);
        teamLabel = new Label("Equipe de pokemon", true);
        team = new InfoButton[6];
        for (int i = 0; i < 6; i++) {
            team[i] = new InfoButton("", 0, true);
            team[i].setActionCommand(Action.GET_POKEMON.name());
            team[i].addActionListener(parent);
        }

        blank = new Label();
        pokemonLabel = new Label("Pokemons", true);
        pokemonSelector = new JComboBox<>();
        pokemonSelector.setActionCommand(Action.GET_POKEMON.name());
        pokemonSelector.setPreferredSize(new Dimension(1,20));

        blank2 = new Label();
        add = new StyledButton("Ajouter un Pokemon", true);
        modification = new InfoButton("", 0, true);
        delete = new InfoButton("", 0, true);

        add.addActionListener(this);
        modification.addActionListener(this);
        delete.addActionListener(this);
        add.setActionCommand(Action.START_INSERTION.name());
        modification.setActionCommand(Action.START_MODIFICATION.name());
        delete.setActionCommand(Action.DELETE.name());

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.gridwidth = 2;
        add(selector, c);
        c.gridy++;
        add(name, c);
        c.gridy++;
        add(teamLabel, c);
        c.gridwidth = 1;
        for (int i = 0; i < team.length; i += 2) {
            c.gridx = 0;
            c.gridy++;
            add(team[i], c);
            c.gridx++;
            add(team[i + 1], c);
        }
        c.gridx=0;
        c.gridwidth = 2;
        c.gridy++;
        add(blank, c);
        c.gridy++;
        c.gridwidth = 1;
        add(pokemonLabel, c);
        c.gridx++;
        add(pokemonSelector, c);
        c.gridx = 0;
        c.gridy++;

        c.gridwidth = 2;
        add(blank2, c);
        c.gridy++;
        add(add, c);
        c.gridy++;
        add(modification, c);
        c.gridy++;
        add(delete, c);
        setId(1);
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
        currentId = id;
        for (int i = 0; i < 6; i++) {
            team[i].setVisible(false);
            team[i].setId(0);
            team[i].setText("");
        }
        name.setText("");
        teamLabel.setVisible(false);
        ArrayList<Trainer> currentTrainerList = db.getFromDB("SELECT * FROM trainer WHERE id=" + id, Trainer.class);
        if (!currentTrainerList.isEmpty()) {
            Trainer currentTrainer = currentTrainerList.get(0);

            selector.setSelectedIndex(findSelectorId(id));
            name.setText(currentTrainer.name);

            ArrayList<Pokemon> teamOfCurrentTrainer = currentTrainer.getTeam(db);
            if (!teamOfCurrentTrainer.isEmpty()) {
                teamLabel.setVisible(true);
            }
            for (int i = 0; i < teamOfCurrentTrainer.size(); i++) {
                team[i].setVisible(true);
                team[i].setId(teamOfCurrentTrainer.get(i).id);
                team[i].setText(teamOfCurrentTrainer.get(i).name);
            }

            pokemonSelector.removeActionListener(parent);
            ArrayList<Pokemon> pokemonsOfCurrentTrainer = currentTrainer.getPokemons(db);
            pokemonSelector.removeAllItems();
            for (Pokemon pokemon : pokemonsOfCurrentTrainer) {
                pokemonSelector.addItem(new InfoButton(pokemon.name, pokemon.id, true));
            }
            pokemonSelector.addActionListener(parent);

            modification.setText("Modifier le dresseur " + currentTrainer.name);
            modification.setId(id);
            delete.setText("Supprimer le dresseur " + currentTrainer.name);
            delete.setId(id);
            modification.setVisible(true);
            delete.setVisible(true);
        } else {
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
                modification.setEnabled(true);
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
    
    public void update(){
        setId(currentId);
    }

    public void addLastTrainerSelector() {
        ArrayList<Object[]> lastTrainerList = db.getFromDB("SELECT id,name FROM trainer ORDER BY id DESC LIMIT 1");
        InfoButton selectorButton = new InfoButton((String) lastTrainerList.get(0)[1], (Integer) lastTrainerList.get(0)[0]);
        selector.addItem(selectorButton);
    }
}
