/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.type;

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
import pokedex.database.Type;
import pokedex.gui.Action;
import pokedex.gui.MainPanel;

/**
 *
 * @author Spectan
 */

// Les composants qui sont utilisés pour faire les modifications sont placés dans des Panels
// avec une bordure et un titre indiquant ce à quoi correspond le composant

public class TypeModificationPanel extends JPanel implements ActionListener, ComponentListener {

    JTextField name, enName;
    MainPanel parent;
    int idModif;
    JButton saveButton, discardButton;
    JComboBox<String>[] vsSelectors;
    JPanel panVs[];

    public TypeModificationPanel(int id, MainPanel p) {

        // On enregistre l'id du type que l'on modifie pour pouvoir le réutiliser
        idModif = id;
        
        // On enregistre la fenêtre qui contient l'onglet de modification/création pour pouvoir accéder à la database
        parent = p;
        
        // On change les dimensions pour qu'elles correspondent à celles de la fenêtre
        this.setSize(new Dimension(parent.getWidth(), parent.getHeight()));
        
        // On crée et ajoute les composants nécessaires à la modification
        this.initComponents();
        
        this.setVisible(true);
        addComponentListener(this);
    }

    private void initComponents() {

        // Récupère la liste de tous les types de la database
        ArrayList<pokedex.database.Type> list = parent.db.getFromDB("SELECT * from type ORDER BY id ASC", pokedex.database.Type.class);
        
        // Récupère le type que l'on modifie
        pokedex.database.Type currentType = parent.db.getFromDB("SELECT * from type WHERE id = " + idModif, pokedex.database.Type.class).get(0);

        // Création du Panel contenant un champ de texte pour le nom français
        JPanel panName = new JPanel();
        panName.setBackground(Color.white);
        name = new JTextField(currentType.name);
        panName.setBorder(BorderFactory.createTitledBorder("Nom du type"));
        panName.add(name);

        // Création du Panel contenant un champ de texte pour le nom anglais
        JPanel panEnName = new JPanel();
        panEnName.setBackground(Color.white);
        enName = new JTextField(currentType.en_name);
        panEnName.setBorder(BorderFactory.createTitledBorder("Nom anglais du type"));
        panEnName.add(enName);

        // Création d'un tableau pour le choix de la faiblesse face aux autres types
        String[] listFaiblesse = new String[]{"Immunisé", "Résistant", "Efficace", "Vulnérable"};

        // Création des Panels contenants une liste déroulante pour les faiblesses
        vsSelectors = new JComboBox[18];
        panVs = new JPanel[18];
        for (int i = 0; i < 18; i++) {
            panVs[i] = new JPanel();
            panVs[i].setBackground(Color.white);
            vsSelectors[i] = new JComboBox<>(listFaiblesse);
            vsSelectors[i].setSelectedItem(faiblesseToString(currentType.vs[i]));
            panVs[i].setBorder(BorderFactory.createTitledBorder("Contre " + list.get(i).name));
            panVs[i].add(vsSelectors[i]);
        }

        // Création et configuration du bouton de sauvegarde
        saveButton = new JButton("SAVE");
        saveButton.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        saveButton.addActionListener(this);
        saveButton.setActionCommand(Action.SAVE_MODIFICATION.name());
        
        // Création et configuration du bouton d'annulation
        discardButton = new JButton("DISCARD");
        discardButton.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        discardButton.addActionListener(this);
        discardButton.setActionCommand(Action.DISCARD_MODIFICATION.name());

        // Ajout des Panels suivant un GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.25;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(panName, c);
        c.gridx++;
        add(panEnName, c);
        c.gridx++;
        add(panVs[0], c);
        c.gridx++;
        add(panVs[1], c);
        c.gridx = 0;
        c.gridy++;
        add(panVs[2], c);
        c.gridx++;
        add(panVs[3], c);
        c.gridx++;
        add(panVs[4], c);
        c.gridx++;
        add(panVs[5], c);
        c.gridx = 0;
        c.gridy++;
        add(panVs[6], c);
        c.gridx++;
        add(panVs[7], c);
        c.gridx++;
        add(panVs[8], c);
        c.gridx++;
        add(panVs[9], c);
        c.gridx = 0;
        c.gridy++;
        add(panVs[10], c);
        c.gridx++;
        add(panVs[11], c);
        c.gridx++;
        add(panVs[12], c);
        c.gridx++;
        add(panVs[13], c);
        c.gridx = 0;
        c.gridy++;
        add(panVs[14], c);
        c.gridx++;
        add(panVs[15], c);
        c.gridx++;
        add(panVs[16], c);
        c.gridx++;
        add(panVs[17], c);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy++;
        add(saveButton, c);
        c.gridx = 2;
        add(discardButton, c);

        // Redimensionne les différents composants pour que les dimensions soient adaptées à la fenêtre
        updateDimension();
    }

    // Fonction pour adapter les composants à la fenêtre
    public void updateDimension() {
        // Calcul de valeurs de base pour les dimensions
        int dimx = (this.getWidth() / 4) - 60;
        int dimy = (this.getHeight() / 7) - 70;
        
        // Changements des dimensions des composants
        name.setPreferredSize(new Dimension(dimx, dimy));
        enName.setPreferredSize(new Dimension(dimx, dimy));
        for (int i = 0; i < 18; i++) {
            vsSelectors[i].setPreferredSize(new Dimension(dimx, dimy));
        }
    }

    // Convertit la valeur de la faiblesse en texte
    public String faiblesseToString(float valeurFaiblesse) {
        if (valeurFaiblesse == 2) {
            return "Vulnérable";
        } else if (valeurFaiblesse == 1) {
            return "Efficace";
        } else if (valeurFaiblesse == 0.5) {
            return "Résistant";
        } else if (valeurFaiblesse == 0) {
            return "Immunisé";
        }
        return "Efficace";
    }

    // Convertit le texte d'une faiblesse en réel
    public float stringToFaiblesse(String value) {
        if (value.equals("Vulnérable")) {
            return 2;
        } else if (value.equals("Efficace")) {
            return 1;
        } else if (value.equals("Résistant")) {
            return 0.5f;
        } else if (value.equals("Immunisé")) {
            return 0;
        }
        return 1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch (Action.valueOf(e.getActionCommand())) {
            
            // Ce qui est fait lorsque l'on appuie sur le bouton de modification
            case SAVE_MODIFICATION:
                
                // Crée un tableau de réel avec les valeurs de chaque faiblesse
                float[] vs = new float[18];
                for (int i = 0; i < 18; i++) {
                    vs[i] = stringToFaiblesse(vsSelectors[i].getSelectedItem().toString());
                }
                
                // Application des changements sur le type
                new Type(idModif, name.getText(), enName.getText(), vs).modifyInDB(parent.db);

                // Affichage d'un message disant que les modifications/la création ont été effectuées
                JOptionPane.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);
                
                // Supprime l'onglet de modification et se place sur l'onglet des types
                parent.removeTab(this, parent.typePanel, true);
                
                break;
                
            // Ce qui est fait lorsque l'on appuie sur le bouton d'annulation
            case DISCARD_MODIFICATION:
                // Supprime l'onglet de modification et se place sur l'onglet des types
                parent.removeTab(this, parent.typePanel, false);
                break;
        }
    }

    // Change la taille des composants lorsque la taille de la fenêtre change
    @Override
    public void componentResized(ComponentEvent arg0) {
        updateDimension();
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

}
