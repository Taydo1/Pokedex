/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokemon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import pokedex.database.Database;
import pokedex.database.Pokemon;
import pokedex.gui.Action;
import pokedex.gui.widgets.InfoButton;
import pokedex.gui.widgets.Label;

/**
 *
 * @author Leon
 */
public class PokemonTopPanel extends JPanel {

    JComboBox<InfoButton> selector;
    InfoButton name, type1, type2, ability, trainer, move1, move2, move3, move4;
    Label title, level, health, type, moveLabel, abilityLabel, trainerLabel;
    Database db;

    public PokemonTopPanel(Database db, PokemonPanel parent) {
        super();
        this.db = db;
        title = new Label("Pokemon", true);
        type = new Label("", true);
        level = new Label("", true);
        health = new Label("", true);
        moveLabel = new Label("", true);
        abilityLabel = new Label("", true);
        trainerLabel = new Label("", true);

        name = new InfoButton("", 0, true);
        type1 = new InfoButton("", 0, true);
        type2 = new InfoButton("", 0, true);
        ability = new InfoButton("", 0, true);
        trainer = new InfoButton("", 0, true);
        move1 = new InfoButton("", 0, true);
        move2 = new InfoButton("", 0, true);
        move3 = new InfoButton("", 0, true);
        move4 = new InfoButton("", 0, true);

        selector = new JComboBox<>();
        selector.setBackground(Color.GRAY);
        selector.setForeground(Color.WHITE);
        selector.setPreferredSize(new Dimension(1, 20));

        InfoButton selectorButton;
        ArrayList<Object[]> pokemonList = db.getFromDB("SELECT id,name FROM pokemon ORDER BY id ASC");
        pokemonList.add(0, new Object[]{0, ""});
        for (int i = 0; i < pokemonList.size(); i++) {
            selectorButton = new InfoButton((String) pokemonList.get(i)[1], (Integer) pokemonList.get(i)[0]);
            selector.addItem(selectorButton);
        }
        selector.setActionCommand(Action.GET_POKEMON.name());
        selector.addActionListener(parent);
        name.setActionCommand(Action.GET_POKEDEX.name());
        name.addActionListener(parent.parent);
        type1.setActionCommand(Action.GET_TYPE.name());
        type1.addActionListener(parent.parent);
        type2.setActionCommand(Action.GET_TYPE.name());
        type2.addActionListener(parent.parent);
        ability.setActionCommand(Action.GET_ABILITY.name());
        ability.addActionListener(parent.parent);
        trainer.setActionCommand(Action.GET_TRAINER.name());
        trainer.addActionListener(parent.parent);
        move1.setActionCommand(Action.GET_MOVE.name());
        move1.addActionListener(parent.parent);
        move2.setActionCommand(Action.GET_MOVE.name());
        move2.addActionListener(parent.parent);
        move3.setActionCommand(Action.GET_MOVE.name());
        move3.addActionListener(parent.parent);
        move4.setActionCommand(Action.GET_MOVE.name());
        move4.addActionListener(parent.parent);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        add(title, c);
        c.gridx++;
        add(selector, c);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        add(name, c);
        c.gridy++;
        c.gridwidth = 1;
        c.gridheight = 2;
        add(type, c);
        c.gridheight = 1;
        c.gridx++;
        add(type1, c);
        c.gridy++;
        add(type2, c);
        c.gridx = 0;
        c.gridy++;
        add(abilityLabel, c);
        c.gridx++;
        add(ability, c);
        c.gridx = 0;
        c.gridy++;
        add(trainerLabel, c);
        c.gridx++;
        add(trainer, c);
        c.gridx=0;
        c.gridy++;
        c.gridheight=4;
        add(moveLabel, c);
        c.gridheight=1;
        c.gridx++;
        add(move1, c);
        c.gridy++;
        add(move2, c);
        c.gridy++;
        add(move3, c);
        c.gridy++;
        add(move4, c);
        c.gridx=0;
        c.gridy++;
        c.gridwidth=2;
        add(level, c);
        c.gridy++;
        add(health, c);
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
        ArrayList<Pokemon> currentPokemonList = db.getFromDB("SELECT * FROM pokemon WHERE id=" + id, Pokemon.class);
        if (!currentPokemonList.isEmpty()) {
            move1.setEnabled(true);
            move2.setEnabled(true);
            move3.setEnabled(true);
            move4.setEnabled(true);
            name.setEnabled(true);
            type1.setEnabled(true);
            type2.setEnabled(true);
            ability.setEnabled(true);
            trainer.setEnabled(true);

            selector.setSelectedIndex(findSelectorId(id));
            Pokemon currentPokemon = currentPokemonList.get(0);
            String pokedexName = currentPokemon.getPokedexName(db);
            if (currentPokemon.name.equals(pokedexName)) {
                if (currentPokemon.is_shiny) {
                    name.setText(currentPokemon.name + " (Chromatique)");
                } else {
                    name.setText(currentPokemon.name);
                }
            } else {
                if (currentPokemon.is_shiny) {
                    name.setText(currentPokemon.name + " (" + pokedexName + " Chromatique)");
                } else {
                    name.setText(currentPokemon.name + " (" + pokedexName + ")");
                }
            }
            name.setId(currentPokemon.id_pokedex);

            type1.setText(currentPokemon.getTypeName(db, 1));
            type1.setId(currentPokemon.getTypeId(db, 1));
            int id_type2 = currentPokemon.getTypeId(db, 2);
            if (id_type2 != 0) {
                type2.setVisible(true);
                type2.setText(currentPokemon.getTypeName(db, 2));
                type2.setId(id_type2);
            } else {
                type2.setVisible(false);
            }
            abilityLabel.setText("Talent : ");
            ability.setText(currentPokemon.getAbilityName(db));
            ability.setId(currentPokemon.id_ability);
            if (currentPokemon.id_trainer != 0) {
                trainer.setText(currentPokemon.getTrainerName(db));
                trainer.setId(currentPokemon.id_trainer);
                trainer.setVisible(true);
                trainerLabel.setText("Dresseur : ");
                trainerLabel.setVisible(true);
            } else {
                trainer.setVisible(false);
                trainerLabel.setVisible(false);
            }

            move1.setText(currentPokemon.getMoveName(db, 1));
            move1.setId(currentPokemon.id_move1);
            if (currentPokemon.id_move2 != 0) {
                move2.setText(currentPokemon.getMoveName(db, 2));
                move2.setId(currentPokemon.id_move2);
                move2.setVisible(true);
            } else {
                move2.setVisible(false);
            }
            if (currentPokemon.id_move3 != 0) {
                move3.setText(currentPokemon.getMoveName(db, 3));
                move3.setId(currentPokemon.id_move3);
                move3.setVisible(true);
            } else {
                move3.setVisible(false);
            }
            if (currentPokemon.id_move4 != 0) {
                move4.setText(currentPokemon.getMoveName(db, 4));
                move4.setId(currentPokemon.id_move4);
                move4.setVisible(true);
            } else {
                move4.setVisible(false);
            }
            level.setText("Niveau : " + currentPokemon.level);
            health.setText("Vie : " + currentPokemon.health);
            type.setText("Type : ");
            moveLabel.setText("Attaque : ");
        } else {
            level.setText("");
            health.setText("");
            type.setText("");
            moveLabel.setText("");
            move1.setText("");
            move2.setText("");
            move3.setText("");
            move4.setText("");
            ability.setText("");
            abilityLabel.setText("");
            name.setText("");
            type1.setText("");
            type2.setText("");
            trainer.setText("");
            trainerLabel.setText("");

            move1.setEnabled(false);
            move2.setEnabled(false);
            move3.setEnabled(false);
            move4.setEnabled(false);
            name.setEnabled(false);
            type1.setEnabled(false);
            type2.setEnabled(false);
            ability.setEnabled(false);
            trainer.setEnabled(false);

        }
    }

    public void addLastPokemonSelector() {
        ArrayList<Object[]> lastPokemonList = db.getFromDB("SELECT id,name FROM pokemon ORDER BY id DESC LIMIT 1");
        InfoButton selectorButton = new InfoButton((String) lastPokemonList.get(0)[1], (Integer) lastPokemonList.get(0)[0]);
        selector.addItem(selectorButton);
    }
}
