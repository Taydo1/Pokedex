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
import pokedex.gui.InfoButton;
import pokedex.gui.Label;
import pokedex.gui.MainPanel;

/**
 *
 * @author Leon
 */
public class TrainerPanel extends JPanel implements ActionListener {

    JComboBox<InfoButton> selector;
    InfoButton pokemons[];
    Label name, pokemonLabel;
    Database db;

    public TrainerPanel(Database db, MainPanel parent) {
        super();
        this.db = db;
        pokemonLabel = new Label("", true);

        name = new Label("", true);
        pokemonLabel = new Label("Pokemon", true);
        pokemons = new InfoButton[6];
        for (int i = 0; i < 6; i++) {
            pokemons[i] = new InfoButton("", 0, true);
            pokemons[i].setActionCommand(Action.GET_POKEMON.name());
            pokemons[i].addActionListener(parent);
        }

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
            }else if (pokemonOfCurrentTrainer.size() > 0) {
                pokemonLabel.setVisible(true);
                pokemonLabel.setText("Pokemon");
            }
            for (int i = 0; i < 6 && i < pokemonOfCurrentTrainer.size(); i++) {
                pokemons[i].setVisible(true);
                pokemons[i].setId(pokemonOfCurrentTrainer.get(i).id);
                pokemons[i].setText(pokemonOfCurrentTrainer.get(i).name);
            }

            /*selector.setSelectedIndex(findSelectorId(id));
            String pokedexName = currentTrainer.getPokedexName(db);
            if (currentTrainer.name.equals(pokedexName)) {
                name.setText(currentTrainer.name);
            } else {
                name.setText(currentTrainer.name + " (" + pokedexName + ")");
            }
            name.setId(currentTrainer.id_pokedex);

            type1.setText(currentTrainer.getTypeName(db, 1));
            type1.setId(currentTrainer.getTypeId(db, 1));
            int id_type2 = currentTrainer.getTypeId(db, 2);
            if (id_type2 != 0) {
                type2.setVisible(true);
                type2.setText(currentTrainer.getTypeName(db, 2));
                type2.setId(id_type2);
            } else {
                type2.setVisible(false);
            }
            ability.setText("Talent : " + currentTrainer.getAbilityName(db));
            ability.setId(currentTrainer.id_ability);
            if (currentTrainer.id_trainer != 0) {
                trainer.setText(currentTrainer.getTrainerName(db));
                trainer.setId(currentTrainer.id_trainer);
                trainer.setVisible(true);
            } else {
                trainer.setVisible(false);
            }

            move1.setText(currentTrainer.getMoveName(db, 1));
            move1.setId(currentTrainer.id_move1);
            if (currentTrainer.id_move2 != 0) {
                move2.setText(currentTrainer.getMoveName(db, 2));
                move2.setId(currentTrainer.id_move2);
                move2.setVisible(true);
            } else {
                move2.setVisible(false);
            }
            if (currentTrainer.id_move3 != 0) {
                move3.setText(currentTrainer.getMoveName(db, 3));
                move3.setId(currentTrainer.id_move3);
                move3.setVisible(true);
            } else {
                move3.setVisible(false);
            }
            if (currentTrainer.id_move4 != 0) {
                move4.setText(currentTrainer.getMoveName(db, 4));
                move4.setId(currentTrainer.id_move4);
                move4.setVisible(true);
            } else {
                move4.setVisible(false);
            }
            level.setText("Niveau : " + currentTrainer.level);
            health.setText("Vie : " + currentTrainer.health);
            type.setText("Type : ");
            move.setText("Attaque : ");*/
        }
    }

    public void setUser(String user) {
        /*switch (user.toLowerCase()) {
            case "professeur":
                modification.setEnabled(true);
                break;
            case "dresseur":
            case "visiteur":
                modification.setEnabled(false);
                break;
        }*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InfoButton source;
        switch (Action.valueOf(e.getActionCommand())) {
            case GET_TRAINER:
                source = (InfoButton) selector.getSelectedItem();
                setId(source.getId());
        }
    }

}
