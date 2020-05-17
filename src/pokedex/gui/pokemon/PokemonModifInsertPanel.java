/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokemon;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pokedex.database.Ability;
import pokedex.database.Move;
import pokedex.database.Pokedex;
import pokedex.database.Pokemon;
import pokedex.database.Trainer;
import pokedex.gui.Action;
import pokedex.gui.MainPanel;

/**
 *
 * @author Leon
 */
public class PokemonModifInsertPanel extends JPanel implements ActionListener, ComponentListener{
    
    JTextField name;
    JComboBox level, trainer, move1, move2, move3, move4, pokedex, ability;
    JFormattedTextField  health;
    JButton save, discard;
    MainPanel parent;
    String[] listTalent;
    int idModif;

    public PokemonModifInsertPanel(int id, MainPanel p) { //pokemon modification
        
        idModif = id;
        parent = p;
        this.setSize(new Dimension(parent.getWidth(), parent.getHeight()));
        this.initComponentsModif();
        this.setVisible(true);
        addComponentListener(this);
    }

    public PokemonModifInsertPanel(MainPanel parent) { //new pokemon
    }

    private void initComponentsModif() {
        
        //Liste des noms dans le pokédex
        ArrayList<Pokedex> list_pokedex = parent.db.getFromDB("SELECT * from pokedex ORDER BY id ASC", Pokedex.class);
        String[] listPokedex = new String[list_pokedex.size()];
        for (int i = 0; i < list_pokedex.size(); i++){
            listPokedex[i] = list_pokedex.get(i).name; 
        }
        
        //Liste des noms des dresseurs
        ArrayList<Trainer> list_trainer = parent.db.getFromDB("SELECT * from trainer ORDER BY id ASC", Trainer.class);
        String[] listTrainer = new String[list_trainer.size()];
        for (int i = 0; i < list_trainer.size(); i++){
            listTrainer[i] = list_trainer.get(i).name; 
        }
        
        //Liste des noms des moves
        ArrayList<Move> list_move = parent.db.getFromDB("SELECT * from move ORDER BY id ASC", Move.class);
        String[] listMove = new String[list_move.size()];
        for (int i = 0; i < list_move.size(); i++){
            listMove[i] = list_move.get(i).name; 
        }
        String[] listMoveNull = new String[list_move.size() + 1];
        listMoveNull[0] = "";
        for (int i = 1; i < list_move.size() + 1; i++){
            listMoveNull[i] = list_move.get(i - 1).name; 
        }
        
        Pokemon currentPokemon = parent.db.getFromDB("SELECT * from pokemon WHERE id = " + idModif, Pokemon.class).get(0);
        
        //Le nom
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.white);
        name = new JTextField(currentPokemon.name);
        name.setText(currentPokemon.name);
        namePanel.setBorder(BorderFactory.createTitledBorder("Nom du pokémon"));
        namePanel.add(name);
        
        //Le niveau
        Object[] possibiliteLvl = new Object[100];
        for (int i = 0; i < 100; i++){
            possibiliteLvl[i] = i + 1; 
        }
        
        JPanel lvlPanel = new JPanel();
        lvlPanel.setBackground(Color.white);
        level = new JComboBox(possibiliteLvl);
        level.setSelectedItem(currentPokemon.level);
        lvlPanel.setBorder(BorderFactory.createTitledBorder("Niveau du pokémon"));
        lvlPanel.add(level);
        
        //La vie
        NumberFormat formatVie = NumberFormat.getInstance();
        formatVie.setMaximumFractionDigits(0);
        formatVie.setMaximumIntegerDigits(3);
        formatVie.setMinimumIntegerDigits(1);
        
        JPanel healthPanel = new JPanel();
        healthPanel.setBackground(Color.white);
        health = new JFormattedTextField(formatVie);
        health.setValue(currentPokemon.health);
        healthPanel.setBorder(BorderFactory.createTitledBorder("Points de vie du pokémon"));
        healthPanel.add(health);
        
        //Le nom du dresseur
        JPanel trainerPanel = new JPanel();
        trainerPanel.setBackground(Color.white);
        trainer = new JComboBox<>(listTrainer);
        trainer.setSelectedIndex(currentPokemon.id_trainer - 1);
        trainerPanel.setBorder(BorderFactory.createTitledBorder("Dresseur du pokémon"));
        trainerPanel.add(trainer);
        
        //Les moves
        JPanel move1Panel = new JPanel();
        move1Panel.setBackground(Color.white);
        move1 = new JComboBox<>(listMove);
        move1.setSelectedIndex(currentPokemon.id_move1 - 1);
        move1Panel.setBorder(BorderFactory.createTitledBorder("Attaque n°1 du pokémon"));
        move1Panel.add(move1);
        
        JPanel move2Panel = new JPanel();
        move2Panel.setBackground(Color.white);
        move2 = new JComboBox<>(listMoveNull);
        move2.setSelectedIndex(currentPokemon.id_move2);
        move2Panel.setBorder(BorderFactory.createTitledBorder("Attaque n°2 du pokémon"));
        move2Panel.add(move2);
        move2.setActionCommand(Action.MOVE_CHANGE.name());
        move2.addActionListener(this);
        
        JPanel move3Panel = new JPanel();
        move3Panel.setBackground(Color.white);
        move3 = new JComboBox<>(listMoveNull);
        move3.setSelectedIndex(currentPokemon.id_move3);
        move3Panel.setBorder(BorderFactory.createTitledBorder("Attaque n°3 du pokémon"));
        move3Panel.add(move3);
        move3.setActionCommand(Action.MOVE_CHANGE.name());
        move3.addActionListener(this);
        
        JPanel move4Panel = new JPanel();
        move4Panel.setBackground(Color.white);
        move4 = new JComboBox<>(listMoveNull);
        move4.setSelectedIndex(currentPokemon.id_move4);
        move4Panel.setBorder(BorderFactory.createTitledBorder("Attaque n°4 du pokémon"));
        move4Panel.add(move4);
        
        //Le pokedex lié
        JPanel pokedexPanel = new JPanel();
        pokedexPanel.setBackground(Color.white);
        pokedex = new JComboBox<>(listPokedex);
        pokedex.setSelectedIndex(currentPokemon.id_pokedex - 1);
        pokedexPanel.setBorder(BorderFactory.createTitledBorder("Race du pokémon"));
        pokedexPanel.add(pokedex);
        pokedex.setActionCommand(Action.POKEDEX_CHANGE.name());
        pokedex.addActionListener(this);
        
        //Le talent
        Pokedex currentPokedex = parent.db.getFromDB("SELECT * from pokedex WHERE id = " + currentPokemon.id_pokedex, Pokedex.class).get(0);
        
        setTalent(currentPokedex);
        
        JPanel abilityPanel = new JPanel();
        abilityPanel.setBackground(Color.white);
        abilityPanel.setBorder(BorderFactory.createTitledBorder("Talent du pokémon"));
        abilityPanel.add(ability);
        
        save = new JButton("SAVE");
        save.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        save.addActionListener(this);
        save.setActionCommand(Action.SAVE_MODIFICATION.name());
        discard = new JButton("DISCARD");
        discard.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        discard.addActionListener(this);
        discard.setActionCommand(Action.DISCARD_MODIFICATION.name());

        JPanel savePanel = new JPanel();
        savePanel.add(save);
        savePanel.setBackground(Color.white);
        JPanel discardPanel = new JPanel();
        discardPanel.add(discard);
        discardPanel.setBackground(Color.white);
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setBackground(Color.white);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.33;
        c.weighty = 0.25;
        add(namePanel, c);
        c.gridx++;
        add(lvlPanel, c);
        c.gridx++;
        add(healthPanel, c);
        c.gridx = 0;
        c.gridy++;
        add(trainerPanel, c);
        c.gridx++;
        add(move1Panel, c);
        c.gridx++;
        add(move2Panel, c);
        c.gridx = 0;
        c.gridy++;
        add(move3Panel, c);
        c.gridx++;
        add(move4Panel, c);
        c.gridx++;
        add(pokedexPanel, c);
        c.gridx = 0;
        c.gridy++;
        add(savePanel, c);
        c.gridx++;
        add(abilityPanel, c);
        c.gridx++;
        add(discardPanel, c);
        
        updateDimensionModif();
        setMoveBox();
        
    }
    
    public void updateDimensionModif() {
        int dimx = (this.getWidth() / 3) - 30;
        int dimy = (this.getHeight() / 5) - 30;
        name.setPreferredSize(new Dimension(dimx, dimy));
        level.setPreferredSize(new Dimension(dimx, dimy));
        health.setPreferredSize(new Dimension(dimx, dimy));
        trainer.setPreferredSize(new Dimension(dimx, dimy));
        move1.setPreferredSize(new Dimension(dimx, dimy));
        move2.setPreferredSize(new Dimension(dimx, dimy));
        move3.setPreferredSize(new Dimension(dimx, dimy));
        move4.setPreferredSize(new Dimension(dimx, dimy));
        pokedex.setPreferredSize(new Dimension(dimx, dimy));
        ability.setPreferredSize(new Dimension(dimx, dimy));
        save.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
        discard.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Action.valueOf(e.getActionCommand())) {
            
            case MOVE_CHANGE : 
                setMoveBox();
                break;
                
            case POKEDEX_CHANGE :
                int idpokedex = pokedex.getSelectedIndex() + 1;
                System.out.println(idpokedex);
                Pokedex currentPokedex = parent.db.getFromDB("SELECT * from pokedex WHERE id = " + idpokedex, Pokedex.class).get(0);
                updateTalent(currentPokedex);
                break;
                
            case SAVE_MODIFICATION :
                
                //Vérifie que les capacités soient pas des null
                Object c2, c3, c4;
                if (move2.getSelectedIndex() == 0){
                    c2 = null;
                } else {
                    c2 = move2.getSelectedIndex();
                }
                if (move3.getSelectedIndex() == 0){
                    c3 = null;
                } else {
                    c3 = move3.getSelectedIndex();
                }
                if (move4.getSelectedIndex() == 0){
                    c4 = null;
                } else {
                    c4 = move4.getSelectedIndex();
                }
                String[] colonnes = new String[]{"name", "level", "health", "id_trainer", "id_move1", "id_move2", "id_move3", "id_move4", "id_pokedex", "id_ability"};
                Object [] valeurs = new Object[]{name.getText(), level.getSelectedIndex() + 1, getHp(), trainer.getSelectedIndex() + 1,
                                                move1.getSelectedIndex() + 1, c2, c3, c4, pokedex.getSelectedIndex() + 1, getAbility(pokedex.getSelectedIndex() + 1)};
                parent.db.modify("pokemon", idModif, colonnes, valeurs);

                parent.pokemonPanel.setId(parent.pokemonPanel.idActuel);
                JOptionPane.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);

            case DISCARD_MODIFICATION :
                parent.removeTab(this, parent.pokemonPanel);
                break;
        }
    }
    
    @Override
    public void componentResized(ComponentEvent arg0) {
        updateDimensionModif();
    }

    @Override
    public void componentMoved(ComponentEvent arg0) {
    }

    @Override
    public void componentShown(ComponentEvent arg0) {
    }

    @Override
    public void componentHidden(ComponentEvent arg0) {
    }
    
    private int getAbility(int idpokedex){
        
        switch (ability.getSelectedIndex()){
            case 0 :
                return parent.db.getFromDB("SELECT * from pokedex WHERE id = " + idpokedex, Pokedex.class).get(0).id_ability1;
            case 1 :
                return parent.db.getFromDB("SELECT * from pokedex WHERE id = " + idpokedex, Pokedex.class).get(0).id_ability2;    
            case 2 :
                return parent.db.getFromDB("SELECT * from pokedex WHERE id = " + idpokedex, Pokedex.class).get(0).id_ability3;
            case 3 :
                return parent.db.getFromDB("SELECT * from pokedex WHERE id = " + idpokedex, Pokedex.class).get(0).id_ability4;
            default :
                return parent.db.getFromDB("SELECT * from pokedex WHERE id = " + idpokedex, Pokedex.class).get(0).id_ability1;
        }
    }
    private void setTalent(Pokedex pokedex) {
        if (pokedex.id_ability4 != 0){
            listTalent = new String[4];
            listTalent[0] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability1, Ability.class).get(0).name;
            listTalent[1] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability2, Ability.class).get(0).name;
            listTalent[2] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability3, Ability.class).get(0).name;
            listTalent[3] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability4, Ability.class).get(0).name;
        } else if (pokedex.id_ability3 != 0) {
            listTalent = new String[3];
            listTalent[0] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability1, Ability.class).get(0).name;
            listTalent[1] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability2, Ability.class).get(0).name;
            listTalent[2] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability3, Ability.class).get(0).name;
        } else if (pokedex.id_ability2 != 0) {
            listTalent = new String[2];
            listTalent[0] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability1, Ability.class).get(0).name;
            listTalent[1] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability2, Ability.class).get(0).name;
        } else if (pokedex.id_ability1 != 0){
            listTalent = new String[1];
            listTalent[0] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability1, Ability.class).get(0).name;
        }
        ability = new JComboBox<String>(listTalent);
    }
    
    private void updateTalent(Pokedex pokedex){
        if (pokedex.id_ability4 != 0){
            listTalent = new String[4];
            listTalent[0] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability1, Ability.class).get(0).name;
            listTalent[1] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability2, Ability.class).get(0).name;
            listTalent[2] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability3, Ability.class).get(0).name;
            listTalent[3] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability4, Ability.class).get(0).name;
        } else if (pokedex.id_ability3 != 0) {
            listTalent = new String[3];
            listTalent[0] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability1, Ability.class).get(0).name;
            listTalent[1] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability2, Ability.class).get(0).name;
            listTalent[2] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability3, Ability.class).get(0).name;
        } else if (pokedex.id_ability2 != 0) {
            listTalent = new String[2];
            listTalent[0] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability1, Ability.class).get(0).name;
            listTalent[1] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability2, Ability.class).get(0).name;
        } else if (pokedex.id_ability1 != 0){
            listTalent = new String[1];
            listTalent[0] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability1, Ability.class).get(0).name;
        }
        ability.removeAllItems();
        for (int i = 0; i < listTalent.length; i++){
            ability.addItem(listTalent[i]);
        }
        this.revalidate();
        this.repaint();
    }

    private int getHp() {
        if (Integer.parseInt(health.getText()) != 0){
            return Integer.parseInt(health.getText());
        } else {
            return 1;
        }
    }

    private void setMoveBox() {
        
        if (move2.getSelectedIndex() == 0){
            if (move3.getSelectedIndex() != 0){
                move2.setSelectedIndex(move3.getSelectedIndex());
                move3.setSelectedIndex(0);
                move3.setEnabled(true);
            } else if (move4.getSelectedIndex() != 0){
                move2.setSelectedIndex(move4.getSelectedIndex());
                move4.setSelectedIndex(0);
            } else {
                move3.setEnabled(false);
                move4.setEnabled(false);
            }
        }
        if (move3.getSelectedIndex() == 0){
            if (move4.getSelectedIndex() != 0){
                move3.setSelectedIndex(move4.getSelectedIndex());
                move4.setSelectedIndex(0);
                move4.setEnabled(true);
            } else {
                move4.setEnabled(false);
            }
        } else {
            move4.setEnabled(true);
        }
        System.out.println("modif done");
    }
}
