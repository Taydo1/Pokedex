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

// Les composants qui sont utilisés pour faire les modifications sont placés dans des Panels
// avec une bordure et un titre indiquant ce à quoi correspond le composant

public class PokemonModifInsertPanel extends JPanel implements ActionListener, ComponentListener {

    JTextField name;
    JComboBox level, trainer, move1, move2, move3, move4, pokedex, ability;
    JFormattedTextField health;
    JButton save, discard;
    MainPanel parent;
    String[] listTalent;
    int idModif;
    boolean isInsert;

    public PokemonModifInsertPanel(int id, MainPanel parent) { // Modification d'un pokémon
        
        // Permet de dire que l'on est en modification
        isInsert = false;
        
        // On enregistre l'id du pokémon que l'on modifie pour pouvoir le réutiliser
        this.idModif = id;
        
        // On enregistre la fenêtre qui contient l'onglet de modification/création pour pouvoir accéder à la database
        this.parent = parent;
        
        // On change les dimensions pour qu'elles correspondent à celles de la fenêtre
        setSize(new Dimension(parent.getWidth(), parent.getHeight()));
        
        // On récupère le pokémon que l'on veut modifier depuis la database
        Pokemon currentPokemon = parent.db.getFromDB("SELECT * from pokemon WHERE id = " + idModif, Pokemon.class).get(0);
        
        // On crée et ajoute les composants nécessaires à la modification
        initComponentsModif(currentPokemon);
        
        setVisible(true);
        addComponentListener(this);
    }

    public PokemonModifInsertPanel(MainPanel parent) { // Création d'un pokémon
        
        // Permet de dire que l'on est en création
        isInsert = true;
        
        // On enregistre la fenêtre qui contient l'onglet de modification/création pour pouvoir accéder à la database
        this.parent = parent;
        
        // On change les dimensions pour qu'elles correspondent à celles de la fenêtre
        setSize(new Dimension(parent.getWidth(), parent.getHeight()));
        
        // On crée une base de pokémon que l'on va modifier
        Pokemon currentPokemon = new Pokemon(-1, "", 0, 0, false, 0, 1, 0, 0, 0, 0, 1);
        
        // On crée et ajoute les composants nécessaires à la modification de la base créée
        initComponentsModif(currentPokemon);
        
        setVisible(true);
        addComponentListener(this);
    }

    private void initComponentsModif(Pokemon currentPokemon) {

        // On récupère la liste des noms des pokedex actuels de la database
        ArrayList<Object[]> list_pokedex = parent.db.getFromDB("SELECT name from pokedex ORDER BY id ASC");
        
        // Passe les noms des pokedex dans un tableau utilisable dans des JComboBox
        String[] listPokedex = new String[list_pokedex.size()];
        for (int i = 0; i < list_pokedex.size(); i++) {
            listPokedex[i] = (String) list_pokedex.get(i)[0];
        }

        // On récupère la liste des noms des dresseurs actuels de la database
        ArrayList<Trainer> list_trainer = parent.db.getFromDB("SELECT * from trainer ORDER BY id ASC", Trainer.class);
        
        // Passe les noms des dresseurs dans un tableau utilisable dans des JComboBox
        String[] listTrainer = new String[list_trainer.size()];
        for (int i = 0; i < list_trainer.size(); i++) {
            listTrainer[i] = list_trainer.get(i).name;
        }

        // On récupère la liste des noms des capacités actuelles de la database
        ArrayList<Move> list_move = parent.db.getFromDB("SELECT * from move ORDER BY id ASC", Move.class);
        
        // Passe les noms des capacités dans un tableau utilisable dans des JComboBox
        String[] listMove = new String[list_move.size()];
        for (int i = 0; i < list_move.size(); i++) {
            listMove[i] = list_move.get(i).name;
        }
        
        // crée le même tableau mais avec un élément vide en premier (permettra de sélectionner moins de 4 capacités)
        String[] listMoveNull = new String[list_move.size() + 1];
        listMoveNull[0] = "";
        for (int i = 1; i < list_move.size() + 1; i++) {
            listMoveNull[i] = list_move.get(i - 1).name;
        }

        // Création du Panel contenant un champ de texte pour le nom du pokémon
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.white);
        name = new JTextField(currentPokemon.name);
        name.setText(currentPokemon.name);
        namePanel.setBorder(BorderFactory.createTitledBorder("Nom du pokémon"));
        namePanel.add(name);

        // Création d'un tableau pour choisir le niveau du pokémon
        Object[] possibiliteLvl = new Object[100];
        for (int i = 0; i < 100; i++) {
            possibiliteLvl[i] = i + 1;
        }

        // Création du Panel contenant une liste déroulante pour le niveau du pokémon
        JPanel lvlPanel = new JPanel();
        lvlPanel.setBackground(Color.white);
        level = new JComboBox(possibiliteLvl);
        level.setSelectedItem(currentPokemon.level);
        lvlPanel.setBorder(BorderFactory.createTitledBorder("Niveau du pokémon"));
        lvlPanel.add(level);

        // Création du format de donnée entrable dans le champ de texte correspondant à la vie
        NumberFormat formatVie = NumberFormat.getInstance();
        formatVie.setMaximumFractionDigits(0); // Pas de chiffre après la virgule
        formatVie.setMaximumIntegerDigits(4); // Maximum 4 chiffres avant la virgule
        formatVie.setMinimumIntegerDigits(1); // Minimum 1 chiffre avant la virgule

        // Création du Panel contenant un champ de texte formatté pour la vie
        JPanel healthPanel = new JPanel();
        healthPanel.setBackground(Color.white);
        health = new JFormattedTextField(formatVie);
        health.setValue(currentPokemon.health);
        healthPanel.setBorder(BorderFactory.createTitledBorder("Points de vie du pokémon"));
        healthPanel.add(health);

        // Création du Panel contenant une liste déroulante pour le dresseur
        JPanel trainerPanel = new JPanel();
        trainerPanel.setBackground(Color.white);
        trainer = new JComboBox<>(listTrainer);
        trainer.setSelectedIndex(currentPokemon.id_trainer - 1);
        trainerPanel.setBorder(BorderFactory.createTitledBorder("Dresseur du pokémon"));
        trainerPanel.add(trainer);

        // Création du Panel contenant une liste déroulante pour la première capacité
        JPanel move1Panel = new JPanel();
        move1Panel.setBackground(Color.white);
        move1 = new JComboBox<>(listMove);
        move1.setSelectedIndex(currentPokemon.id_move1 - 1);
        move1Panel.setBorder(BorderFactory.createTitledBorder("Attaque n°1 du pokémon"));
        move1Panel.add(move1);

        // Création du Panel contenant une liste déroulante pour la deuxième capacité
        JPanel move2Panel = new JPanel();
        move2Panel.setBackground(Color.white);
        move2 = new JComboBox<>(listMoveNull);
        move2.setSelectedIndex(currentPokemon.id_move2);
        move2Panel.setBorder(BorderFactory.createTitledBorder("Attaque n°2 du pokémon"));
        move2Panel.add(move2);
        move2.setActionCommand(Action.MOVE_CHANGE.name());
        move2.addActionListener(this);

        // Création du Panel contenant une liste déroulante pour la troisième capacité
        JPanel move3Panel = new JPanel();
        move3Panel.setBackground(Color.white);
        move3 = new JComboBox<>(listMoveNull);
        move3.setSelectedIndex(currentPokemon.id_move3);
        move3Panel.setBorder(BorderFactory.createTitledBorder("Attaque n°3 du pokémon"));
        move3Panel.add(move3);
        move3.setActionCommand(Action.MOVE_CHANGE.name());
        move3.addActionListener(this);

        // Création du Panel contenant une liste déroulante pour la quatrième capacité
        JPanel move4Panel = new JPanel();
        move4Panel.setBackground(Color.white);
        move4 = new JComboBox<>(listMoveNull);
        move4.setSelectedIndex(currentPokemon.id_move4);
        move4Panel.setBorder(BorderFactory.createTitledBorder("Attaque n°4 du pokémon"));
        move4Panel.add(move4);

        // Création du Panel contenant une liste déroulante pour le pokedex correspondant
        JPanel pokedexPanel = new JPanel();
        pokedexPanel.setBackground(Color.white);
        pokedex = new JComboBox<>(listPokedex);
        pokedex.setSelectedIndex(currentPokemon.id_pokedex - 1);
        pokedexPanel.setBorder(BorderFactory.createTitledBorder("Race du pokémon"));
        pokedexPanel.add(pokedex);
        pokedex.setActionCommand(Action.POKEDEX_CHANGE.name());
        pokedex.addActionListener(this);

        // On récupère le pokedex correspondant au pokémon que l'on modifie depuis la database
        Pokedex currentPokedex = parent.db.getFromDB("SELECT * from pokedex WHERE id = " + currentPokemon.id_pokedex, Pokedex.class).get(0);

        // Crée et paramètre la liste déroulante correspondant au talent
        setTalent(currentPokedex);

        // Création du Panel contenant la liste déroulante pour le talent
        JPanel abilityPanel = new JPanel();
        abilityPanel.setBackground(Color.white);
        abilityPanel.setBorder(BorderFactory.createTitledBorder("Talent du pokémon"));
        abilityPanel.add(ability);

        // Création et configuration du bouton de sauvegarde
        save = new JButton("SAVE");
        save.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        save.addActionListener(this);
        save.setActionCommand(Action.SAVE_MODIFICATION.name());
        
        // Création et configuration du bouton d'annulation
        discard = new JButton("DISCARD");
        discard.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        discard.addActionListener(this);
        discard.setActionCommand(Action.DISCARD_MODIFICATION.name());

        // Placement du bouton de sauvergade dans un Panel
        JPanel savePanel = new JPanel();
        savePanel.add(save);
        savePanel.setBackground(Color.white);
        
        // Placement du bouton d'annulation dans un Panel
        JPanel discardPanel = new JPanel();
        discardPanel.add(discard);
        discardPanel.setBackground(Color.white);

        // Ajout des Panels suivant un GridBagLayout
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

        // Redimensionne les différents composants pour que les dimensions soient adaptées à la fenêtre
        updateDimensionModif();
        
        // Active/désactive les listes déroulantes pour les capacités en fonction des valeurs sélectionnées
        setMoveBox();

    }

    // Fonction pour adapter les composants à la fenêtre
    public void updateDimensionModif() {
        // Calcul de valeurs de base pour les dimensions
        int dimx = (this.getWidth() / 3) - 30;
        int dimy = (this.getHeight() / 5) - 30;
        
        // Changements des dimensions des composants
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

    // Permet de définir les actions et fonctions du bouton sur lequel on appuie
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Action.valueOf(e.getActionCommand())) {

            // Ce qui est fait lorsque l'on change la sélection d'une liste déroulante pour capacité
            case MOVE_CHANGE:
                
                setMoveBox();
                break;

            // Ce qui est fait lorsque l'on change le pokedex
            case POKEDEX_CHANGE:
                // Récupère le nouveau pokedex depuis la database
                int idpokedex = pokedex.getSelectedIndex() + 1;
                Pokedex currentPokedex = parent.db.getFromDB("SELECT * from pokedex WHERE id = " + idpokedex, Pokedex.class).get(0);
                
                // Change la liste déroulante des talents disponibles pour correspondre au pokedex sélectionné
                updateTalent(currentPokedex);
                break;

            // Ce qui est fait lorsque l'on appuie sur le bouton de modification
            case SAVE_MODIFICATION:
                // Aléatoire de la forme Shiny lors d'une création
                boolean isShiny;
                if (isInsert) {
                    isShiny = Math.random() < 0.1;
                }else{
                    isShiny = (boolean) parent.db.getFromDB("SELECT is_shiny from pokemon WHERE id = " + idModif).get(0)[0];
                }
                
                // Récupère le nom du pokedex correspondant
                String currentPokedexName = (String) parent.db.getFromDB("SELECT name from pokedex WHERE id = " + (pokedex.getSelectedIndex() + 1)).get(0)[0];
                
                // Si aucun nom n'est entré, donne le nom du pokedex à la place
                if(name.getText().equals(""))name.setText(currentPokedexName);
                
                // Création d'un pokémon avec les valeurs des modifications
                Pokemon temp = new Pokemon(idModif, name.getText(), level.getSelectedIndex() + 1, getHp(),
                        isShiny, trainer.getSelectedIndex() + 1,
                        move1.getSelectedIndex() + 1, move2.getSelectedIndex(),
                        move3.getSelectedIndex(), move4.getSelectedIndex(),
                        getAbility(pokedex.getSelectedIndex() + 1), pokedex.getSelectedIndex() + 1);
                
                // Si l'on crée un pokémon, ajoute le nouveau pokémon dans la database
                if (isInsert) {
                    parent.db.executeUpdate("INSERT INTO pokemon VALUES " + temp.getInsertSubRequest());
                    parent.pokemonPanel.topPanel.addLastPokemonSelector();
                // Sinon, on modifie applique les modifications dans la database
                } else {
                    temp.modifyInDB(parent.db);
                }
                
                // Update les pokémons disponibles dans l'onglet des pokémons
                parent.pokemonPanel.updatePokemonDispo();
                
                // Sélectionne le pokémon créé/modifié dans l'onglet des pokémons
                parent.pokemonPanel.setId(parent.pokemonPanel.currentId);
                
                // Affichage d'un message disant que les modifications/la création ont été effectuées
                JOptionPane.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);
                
                // Supprime l'onglet de modification/création et sélectionne l'onglet des pokémons
                parent.removeTab(this, parent.pokemonPanel, true);
                
                break;
                
            // Ce qui est fait lorsque l'on appuie sur le bouton de modification
            case DISCARD_MODIFICATION:
                // Supprime l'onglet de modification/création et sélectionne l'onglet des pokémons
                parent.removeTab(this, parent.pokemonPanel, false);
                break;
        }
    }

    // Change la taille des composants lorsque la taille de la fenêtre change
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

    // Permet de récupérer le talent du pokémon
    private int getAbility(int idpokedex) {

        switch (ability.getSelectedIndex()) {
            case 0:
                return parent.db.getFromDB("SELECT * from pokedex WHERE id = " + idpokedex, Pokedex.class).get(0).id_ability1;
            case 1:
                return parent.db.getFromDB("SELECT * from pokedex WHERE id = " + idpokedex, Pokedex.class).get(0).id_ability2;
            case 2:
                return parent.db.getFromDB("SELECT * from pokedex WHERE id = " + idpokedex, Pokedex.class).get(0).id_ability3;
            case 3:
                return parent.db.getFromDB("SELECT * from pokedex WHERE id = " + idpokedex, Pokedex.class).get(0).id_ability4;
            default:
                return parent.db.getFromDB("SELECT * from pokedex WHERE id = " + idpokedex, Pokedex.class).get(0).id_ability1;
        }
    }

    // Permet de donner le bon choix de talent initialement
    private void setTalent(Pokedex pokedex) {
        if (pokedex.id_ability4 != 0) {
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
        } else if (pokedex.id_ability1 != 0) {
            listTalent = new String[1];
            listTalent[0] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability1, Ability.class).get(0).name;
        }
        ability = new JComboBox<>(listTalent);
    }

    // Permet de changer le choix de talents pour correspondre à un nouveau pokedex
    private void updateTalent(Pokedex pokedex) {
        if (pokedex.id_ability4 != 0) {
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
        } else if (pokedex.id_ability1 != 0) {
            listTalent = new String[1];
            listTalent[0] = parent.db.getFromDB("SELECT * from ability WHERE id = " + pokedex.id_ability1, Ability.class).get(0).name;
        }
        ability.removeAllItems();
        for (int i = 0; i < listTalent.length; i++) {
            ability.addItem(listTalent[i]);
        }
        this.revalidate();
        this.repaint();
    }

    // Permet de récupérer la vie
    private int getHp() {
        double temp = Double.valueOf(health.getValue().toString());
        return (int) Math.abs(temp);
    }

    // Active/Désactive les listes déroulantes pour les capacités en fonction des valeurs sélectionnées
    private void setMoveBox() {
        if (move2.getSelectedIndex() == 0) {
            if (move3.getSelectedIndex() != 0) {
                move2.setSelectedIndex(move3.getSelectedIndex());
                move3.setSelectedIndex(0);
                move3.setEnabled(true);
            } else if (move4.getSelectedIndex() != 0) {
                move2.setSelectedIndex(move4.getSelectedIndex());
                move4.setSelectedIndex(0);
            } else {
                move3.setEnabled(false);
                move4.setEnabled(false);
            }
        } else {
            move3.setEnabled(true);
        }
        if (move3.getSelectedIndex() == 0) {
            if (move4.getSelectedIndex() != 0) {
                move3.setSelectedIndex(move4.getSelectedIndex());
                move4.setSelectedIndex(0);
                move4.setEnabled(true);
            } else {
                move4.setEnabled(false);
            }
        } else {
            move4.setEnabled(true);
        }
    }
}
