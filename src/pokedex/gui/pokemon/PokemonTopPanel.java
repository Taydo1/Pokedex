/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokemon;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import pokedex.database.Database;
import pokedex.database.Pokemon;
import pokedex.gui.Action;
import pokedex.gui.InfoButton;
import pokedex.gui.Label;

/**
 *
 * @author Leon
 */
public class PokemonTopPanel extends JPanel {

    JComboBox<InfoButton> selector;
    InfoButton name, type1, type2, ability, trainer, move1, move2, move3, move4;
    Label level, health, type, move;
    Database db;

    public PokemonTopPanel(Database db, PokemonPanel parent) {
        super();
        this.db = db;
        type = new Label("", true);
        level = new Label("", true);
        health = new Label("", true);
        move = new Label("", true);

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

        InfoButton selectorButton;
        ArrayList<Object[]> pokemonList = db.getFromDB("SELECT id,name FROM pokemon");
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
        add(selector, c);
        c.gridy++;
        add(name, c);
        c.gridy++;
        add(type, c);
        c.gridy++;
        add(type1, c);
        c.gridy++;
        add(type2, c);
        c.gridy++;
        add(ability, c);
        c.gridy++;
        add(trainer, c);
        c.gridy++;
        add(move, c);
        c.gridy++;
        add(move1, c);
        c.gridy++;
        add(move2, c);
        c.gridy++;
        add(move3, c);
        c.gridy++;
        add(move4, c);
        c.gridy++;
        add(level, c);
        c.gridy++;
        add(health, c);
        setId(1);
    }

    public void setId(int id) {
        ArrayList<Pokemon> currentPokemonList = db.getFromDB("SELECT * FROM pokemon WHERE id=" + id, Pokemon.class);
        if (!currentPokemonList.isEmpty()) {
            Pokemon currentPokemon = currentPokemonList.get(0);
            String pokedexName = currentPokemon.getPokedexName(db);
            if (currentPokemon.name.equals(pokedexName)) {
                name.setText(currentPokemon.name);
            } else {
                name.setText(currentPokemon.name + " (" + pokedexName + ")");
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
            ability.setText("Talent : "+currentPokemon.getAbilityName(db));
            ability.setId(currentPokemon.id_ability);
            if (currentPokemon.id_trainer != 0) {
                trainer.setText(currentPokemon.getTrainerName(db));
                trainer.setId(currentPokemon.id_trainer);
                trainer.setVisible(true);
            } else {
                trainer.setVisible(false);
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
            move.setText("Attaque : ");
        }
        /*    
        JComboBox<InfoButton> selector;
        
        Label level, health, type, move;
        Database db;
         */
        //selector.setSelectedIndex(id - 1);
    }
}
