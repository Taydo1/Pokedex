/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.trainer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pokedex.database.Pokemon;
import pokedex.database.Trainer;
import pokedex.gui.Action;
import pokedex.gui.MainPanel;
import pokedex.gui.widgets.InfoButton;

/**
 *
 * @author Leon
 */

// Les composants qui sont utilisés pour faire les modifications sont placés dans des Panels
// avec une bordure et un titre indiquant ce à quoi correspond le composant

public class TrainerModifInsertPanel extends JPanel implements ActionListener, ComponentListener {

    int idModif;
    MainPanel parent;
    JTextField name;
    JComboBox<InfoButton> equipe[];
    ArrayList<InfoButton> listPokemon;
    JButton save, discard;
    boolean isInsert;

    public TrainerModifInsertPanel(int id, MainPanel parent) { // Modification d'un dresseur
        
        // Permet de dire que l'on est en modification
        isInsert = false;
        
        // On enregistre l'id du dresseur que l'on modifie pour pouvoir le réutiliser
        this.idModif = id;
        
        // On enregistre la fenêtre qui contient l'onglet de modification/création pour pouvoir accéder à la database
        this.parent = parent;
        
        // On change les dimensions pour qu'elles correspondent à celles de la fenêtre
        setSize(new Dimension(parent.getWidth(), parent.getHeight()));
        
        // On récupère le dresseur que l'on veut modifier depuis la database
        Trainer currentTrainer = parent.db.getFromDB("SELECT * from trainer WHERE id = " + idModif, Trainer.class).get(0);
        
        // On crée et ajoute les composants nécessaires à la modification
        initComponentsModif(currentTrainer);
        
        setVisible(true);
        addComponentListener(this);
    }

    public TrainerModifInsertPanel(MainPanel parent) { // Création d'un dresseur
        
        // Permet de dire que l'on est en création
        isInsert = true;
        
        // On enregistre la fenêtre qui contient l'onglet de modification/création pour pouvoir accéder à la database
        this.parent = parent;
        
        // On change les dimensions pour qu'elles correspondent à celles de la fenêtre
        setSize(new Dimension(parent.getWidth(), parent.getHeight()));
        
        // On crée une base de dresseur que l'on va modifier
        Trainer currentTrainer = new Trainer(-1, "", -1, -1, -1, -1, -1, -1);
        
        // On crée et ajoute les composants nécessaires à la modification
        initComponentsModif(currentTrainer);
        
        setVisible(true);
        addComponentListener(this);
    }

    private void initComponentsModif(Trainer currentTrainer) {

        // Récupère tous les pokémons du dresseur
        ArrayList<Pokemon> listPkmn = currentTrainer.getPokemons(parent.db);
        
        // Passe les pokémons en une liste d'InfoButton (bouton avec un nom et un id)
        listPokemon = new ArrayList<>();
        for (int i = 0; i < listPkmn.size(); i++) {
            listPokemon.add(new InfoButton(listPkmn.get(i).name, listPkmn.get(i).id));
        }

        // Initialisation de l'équipe
        equipe = new JComboBox[6];

        // Création du Panel contenant un champ de texte pour le nom du pokémon
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.white);
        name = new JTextField(currentTrainer.name);
        name.setText(currentTrainer.name);
        namePanel.setBorder(BorderFactory.createTitledBorder("Nom du dresseur"));
        namePanel.add(name);

        // Création des Panel contenant une liste déroulante pour l'équipe
        JPanel[] team = new JPanel[6];
        for (int i = 0; i < 6; i++) {
            // Création et paramétrage de la liste déroulante
            equipe[i] = new JComboBox(listPokemon.toArray());
            equipe[i].setSelectedIndex(findSelectorId(currentTrainer.id_pokemon[i], i));
            equipe[i].addActionListener(this);
            equipe[i].setActionCommand(Action.CHANGE_TEAM.name());

            // Création et paramétrage du Panel
            team[i] = new JPanel();
            team[i].setBackground(Color.white);
            int num = i + 1;
            team[i].setBorder(BorderFactory.createTitledBorder("Pokémon n°" + num + " de l'équipe"));
            team[i].add(equipe[i]);
        }
        
        // Change les choix des listes déroulantes pour l'équipe
        updateEquipe();
        
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
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(namePanel, c);
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.gridy++;
        add(team[0], c);
        c.gridx++;
        add(team[1], c);
        c.gridx = 0;
        c.gridy++;
        add(team[2], c);
        c.gridx++;
        add(team[3], c);
        c.gridx = 0;
        c.gridy++;
        add(team[4], c);
        c.gridx++;
        add(team[5], c);
        c.gridx = 0;
        c.gridy++;
        add(savePanel, c);
        c.gridx++;
        add(discardPanel, c);
    }

    // Permet de définir les actions et fonctions du bouton sur lequel on appuie
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch (Action.valueOf(e.getActionCommand())) {

            // Ce qui est fait lorsque l'on change la sélection d'un membre de l'équipe
            case CHANGE_TEAM:
                // Change les choix des listes déroulantes pour l'équipe
                updateEquipe();
                break;

            // Ce qui est fait lorsque l'on appuie sur le bouton de modification
            case SAVE_MODIFICATION:

                // Création d'un tableau correspondant aux id des pokémons de l'équipe
                int[] equ = new int[6];
                for (int i = 0; i < 6; i++) {
                    InfoButton buttonSelect = (InfoButton) equipe[i].getSelectedItem();
                    if (buttonSelect == null || buttonSelect.getId() == 0) {
                        equ[i] = 0;
                    } else {
                        InfoButton pkmnSelect = (InfoButton) equipe[i].getSelectedItem();
                        equ[i] = pkmnSelect.getId();
                    }
                }

                // Création d'un dresseur avec les valeurs de la modification
                Trainer temp = new Trainer(idModif, name.getText(), equ[0], equ[1], equ[2], equ[3], equ[4], equ[5]);
                
                // Si l'on crée un dresseur, ajoute le nouveau dresseur dans la database
                if (isInsert) {
                    parent.db.executeUpdate("INSERT INTO trainer VALUES " + temp.getInsertSubRequest());
                    parent.trainerPanel.addLastTrainerSelector();
                // Sinon, on modifie applique les modifications dans la database
                } else {
                    temp.modifyInDB(parent.db);
                }

                // Sélectionne le dresseur créé/modifié dans l'onglet des dresseurs
                parent.trainerPanel.setId(parent.trainerPanel.currentId);
                
                // Affichage d'un message disant que les modifications/la création ont été effectuées
                JOptionPane.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);
                
                // Supprime l'onglet de modification/création et sélectionne l'onglet des dresseurs
                parent.removeTab(this, parent.trainerPanel, true);
                
                break;
                
            // Ce qui est fait lorsque l'on appuie sur le bouton de modification
            case DISCARD_MODIFICATION:
                // Supprime l'onglet de modification/création et sélectionne l'onglet des dresseurs
                parent.removeTab(this, parent.trainerPanel, false);
                break;
        }
    }

    // Change la taille des composants lorsque la taille de la fenêtre change
    @Override
    public void componentResized(ComponentEvent arg0) {
        updateDimension();
        revalidate();
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

    // Permet de savoir quel membre de l'équipe sélectionner
    private int findSelectorId(int id, int n) {
        for (int i = 0; i < equipe[n].getItemCount(); i++) {
            InfoButton item = equipe[n].getItemAt(i);
            if (item.getId() == id) {
                return i;
            }
        }
        return -1;
    }

    // Fonction pour adapter les composants à la fenêtre
    public void updateDimension() {
        // Calcul de valeurs de base pour les dimensions
        int dimx = (this.getWidth() / 2) - 30;
        int dimy = (this.getHeight() / 6) - 70;
        
        // Changements des dimensions des composants
        name.setPreferredSize(new Dimension(dimx, dimy));
        for (int i = 0; i < 6; i++) {
            equipe[i].setPreferredSize(new Dimension(dimx, dimy));
        }
        save.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
        discard.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
    }

    // Permet de mettre à jour les membres sélectionnables de l'équipe
    // pour qu'un même pokémon ne soit sélectionnable qu'une fois
    private void updateEquipe() {
        
        for (int i = 0; i < 6; i++) {
            equipe[i].removeActionListener(this);
            InfoButton selected = (InfoButton) equipe[i].getSelectedItem();
            equipe[i].removeAllItems();

            ArrayList<InfoButton> tmp = removeSelected(listPokemon, i);
            tmp.add(0, new InfoButton("", 0));
            for (InfoButton infoButton : tmp) {
                equipe[i].addItem(infoButton);
            }
            equipe[i].setSelectedItem(selected);
            equipe[i].addActionListener(this);
        }

        setEquipeAccess();
    }

    // Retire le pokémon sélectionné des autres listes
    private ArrayList<InfoButton> removeSelected(ArrayList<InfoButton> listDispo, int selectoToExcept) {
        ArrayList<InfoButton> returnList = new ArrayList<>(listDispo);
        for (int i = 0; i < 6; i++) {
            if (i != selectoToExcept) {
                InfoButton selected = (InfoButton) equipe[i].getSelectedItem();
                returnList.remove(selected);
            }
        }
        return returnList;
    }

    // Active/Désactive les listes déroulantes en fonction des sélections
    private void setEquipeAccess() {
        for (int i = 0; i < 5; i++) {
            InfoButton buttonSelect = (InfoButton) equipe[i].getSelectedItem();
            if (buttonSelect == null || buttonSelect.getId() == 0) {
                equipe[i + 1].setEnabled(false);
            } else {
                equipe[i + 1].setEnabled(true);
            }
        }
    }
}
