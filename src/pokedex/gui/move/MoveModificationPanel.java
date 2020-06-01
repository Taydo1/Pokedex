/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.move;

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
import pokedex.database.Move;
import pokedex.gui.Action;
import pokedex.gui.MainPanel;

/**
 *
 * @author Leon
 */

// Les composants qui sont utilisés pour faire les modifications sont placés dans des Panels
// avec une bordure et un titre indiquant ce à quoi correspond le composant

public class MoveModificationPanel extends JPanel implements ActionListener, ComponentListener {
    
    
    JTextField name, enName;
    JComboBox<String> type, category;
    JComboBox pp, accuracy;
    JFormattedTextField power;
    MainPanel parent;
    JButton saveButton, discardButton;
    int idModif;
    
    public MoveModificationPanel(int id, MainPanel p) {
        
        // On enregistre l'id de la capacité que l'on modifie pour pouvoir le réutiliser
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
        
        // On récupère la liste des types actuels de la database
        ArrayList<pokedex.database.Type> list = parent.db.getFromDB("SELECT * from type ORDER BY id ASC", pokedex.database.Type.class);
        
        // Passe les noms des types dans un tableau utilisable dans des JComboBox
        String[] listType = new String[list.size()];
        for (int i = 0; i < list.size(); i++){
            listType[i] = list.get(i).name; 
        }
        
        // On récupère la capacité que l'on veut modifier depuis la database
        Move currentMove = parent.db.getFromDB("SELECT * from move WHERE id = " + idModif, Move.class).get(0);
        
        // Création du Panel contenant un champ de texte pour le nom français
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.white);
        name = new JTextField(currentMove.name);
        namePanel.setBorder(BorderFactory.createTitledBorder("Nom de la capacité"));
        namePanel.add(name);
        
        // Création du Panel contenant un champ de texte pour le nom anglais
        JPanel enNamePanel = new JPanel();
        enNamePanel.setBackground(Color.white);
        enName = new JTextField(currentMove.en_name);
        enNamePanel.setBorder(BorderFactory.createTitledBorder("Nom anglais de la capacité"));
        enNamePanel.add(enName);
        
        // Création du Panel contenant une liste déroulante pour le type de la capacité
        JPanel typePanel = new JPanel();
        typePanel.setBackground(Color.white);
        type = new JComboBox<>(listType);
        type.setSelectedItem(listType[currentMove.id_type - 1]);
        typePanel.setBorder(BorderFactory.createTitledBorder("Type de la capacité"));
        typePanel.add(type);
        
        // Création d'un tableau pour choisir la catégorie de la capacité
        String[] listCategory = new String[]{"Physique", "Spéciale", "Statut"};
        
        // Création du Panel contenant une liste déroulante pour la catégorie de la capacité
        JPanel categoryPanel = new JPanel();
        categoryPanel.setBackground(Color.white);
        category = new JComboBox<>(listCategory);
        category.setSelectedItem(currentMove.category);
        categoryPanel.setBorder(BorderFactory.createTitledBorder("Catégorie de la capacité"));
        categoryPanel.add(category);
        
        // Création d'un tableau pour choisir le nombre de pp de la capacité
        Object[] possibilitePP = new Object[]{0, 1, 5, 10, 15, 20, 25, 30, 35, 40};
        
        // Création du Panel contenant une liste déroulante pour le nombre de pp de la capacité (nombre d''utilisations possibles)
        JPanel ppPanel = new JPanel();
        ppPanel.setBackground(Color.white);
        pp = new JComboBox<>(possibilitePP);
        pp.setSelectedItem(currentMove.pp);
        ppPanel.setBorder(BorderFactory.createTitledBorder("PP de base de la capacité"));
        ppPanel.add(pp);
        
        // Création du format de donnée entrable dans le champ de texte correspondant à la puissance
        NumberFormat formatPuissance = NumberFormat.getInstance();
        formatPuissance.setMaximumFractionDigits(0);
        
        // Création du Panel contenant un champ de texte formatté pour la puissance de la capacité
        JPanel powerPanel = new JPanel();
        powerPanel.setBackground(Color.white);
        power = new JFormattedTextField(formatPuissance);
        if (currentMove.power == -1){
            power.setValue(0);
        } else {
            power.setValue(currentMove.power);
        }
        powerPanel.setBorder(BorderFactory.createTitledBorder("Puissance de la capacité"));
        powerPanel.add(power);
        
        // Création d'un tableau pour les possiblités de précision de la capacité (multiple de 5 ou ne rate jamais)
        Object[] possibiliteAccuracy = new Object[]{"Ne rate jamais", 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
        
        // Création du Panel contenant une liste déroulante pour la précision de la capacité
        JPanel accuracyPanel = new JPanel();
        accuracyPanel.setBackground(Color.white);
        accuracy = new JComboBox<>(possibiliteAccuracy);
        setSelectedAccuracy((int)(currentMove.accuracy*100));
        accuracyPanel.setBorder(BorderFactory.createTitledBorder("Précision de la capacité"));
        accuracyPanel.add(accuracy);
        
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

        // Placement du bouton de sauvergade dans un Panel
        JPanel savePanel = new JPanel();
        savePanel.add(saveButton);
        savePanel.setBackground(Color.white);
        
        // Placement du bouton d'annulation dans un Panel
        JPanel discardPanel = new JPanel();
        discardPanel.add(discardButton);
        discardPanel.setBackground(Color.white);
        
        // Ajout des Panels suivant un GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setBackground(Color.white);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.33;
        c.weighty = 0.33;
        add(namePanel, c);
        c.gridx++;
        add(enNamePanel, c);
        c.gridx++;
        add(typePanel, c);
        c.gridx = 0;
        c.gridy++;
        add(categoryPanel, c);
        c.gridx++;
        add(ppPanel, c);
        c.gridx++;
        add(powerPanel, c);
        c.gridx = 0;
        c.gridy++;
        add(savePanel, c);
        c.gridx++;
        add(accuracyPanel, c);
        c.gridx++;
        add(discardPanel, c);
        
        // Redimensionne les différents composants pour que les dimensions soient adaptées à la fenêtre
        updateDimension();
    }
    
    // Fonction pour adapter les composants à la fenêtre
    public void updateDimension() {
        // Calcul de valeurs de base pour les dimensions
        int dimx = (this.getWidth() / 3) - 30;
        int dimy = (this.getHeight() / 4) - 30;
        
        // Changements des dimensions des composants
        name.setPreferredSize(new Dimension(dimx, dimy));
        enName.setPreferredSize(new Dimension(dimx, dimy));
        type.setPreferredSize(new Dimension(dimx, dimy));
        category.setPreferredSize(new Dimension(dimx, dimy));
        pp.setPreferredSize(new Dimension(dimx, dimy));
        power.setPreferredSize(new Dimension(dimx, dimy));
        accuracy.setPreferredSize(new Dimension(dimx, dimy));
        saveButton.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
        discardButton.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
    }
    
    // Permet de définir les actions et fonctions du bouton sur lequel on appuie
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch (Action.valueOf(e.getActionCommand())) {
            // Ce qui est fait lorsque l'on appuie sur le bouton de modification
            case SAVE_MODIFICATION:
                
                // Traduit la valeur de puissance rentrée
                int p;
                if (Integer.parseInt(power.getText()) == 0){
                    p = -1;
                } else {
                    p = Integer.parseInt(power.getText());
                }
                
                // Application des changements sur la capacité
                new Move(idModif, name.getText(), enName.getText(), type.getSelectedIndex() + 1,
                        category.getSelectedItem().toString(), getSelectedPP(),
                        p, getSelectedAccuracy()).modifyInDB(parent.db);

                // Sélectionne la capacité qui vient d'être modifiée dans l'onglet des capacités
                parent.movePanel.setId(parent.movePanel.currentId);
                
                // Affichage d'un message disant que les modifications ont été effectuées
                JOptionPane.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);

                // Supprime l'onglet de modification et se place sur l'onglet des capacités
                parent.removeTab(this, parent.movePanel, true);
                
                break;
                
            // Ce qui est fait lorsque l'on appuie sur le bouton d'annulation  
            case DISCARD_MODIFICATION:
                // Supprime l'onglet de modification et se place sur l'onglet des talents
                parent.removeTab(this, parent.movePanel, false);
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

    // Permet de sélectionner la précision
    private void setSelectedAccuracy(int a) {
        switch (a) {
            case -100 :
                accuracy.setSelectedIndex(0);
                break;
            case 5 :
                accuracy.setSelectedIndex(1);
                break;
            case 10 :
                accuracy.setSelectedIndex(2);
                break;
            case 15 :
                accuracy.setSelectedIndex(3);
                break;
            case 20 :
                accuracy.setSelectedIndex(4);
                break;
            case 25 :
                accuracy.setSelectedIndex(5);
                break;
            case 30 :
                accuracy.setSelectedIndex(6);
                break;
            case 35 :
                accuracy.setSelectedIndex(7);
                break;
            case 40 :
                accuracy.setSelectedIndex(8);
                break;
            case 45 :
                accuracy.setSelectedIndex(9);
                break;
            case 50 :
                accuracy.setSelectedIndex(10);
                break;
            case 55 :
                accuracy.setSelectedIndex(11);
                break;
            case 60 :
                accuracy.setSelectedIndex(12);
                break;
            case 65 :
                accuracy.setSelectedIndex(13);
                break;
            case 70 :
                accuracy.setSelectedIndex(14);
                break;
            case 75 :
                accuracy.setSelectedIndex(15);
                break;
            case 80 :
                accuracy.setSelectedIndex(16);
                break;
            case 85 :
                accuracy.setSelectedIndex(17);
                break;
            case 90 :
                accuracy.setSelectedIndex(18);
                break;
            case 95 :
                accuracy.setSelectedIndex(19);
                break;
            case 100 :
                accuracy.setSelectedIndex(20);
                break;
            default :
                break;
        }
    }
    
    // Permet de récupérer la précision sélectionnée
    private float getSelectedAccuracy() {
        int a = accuracy.getSelectedIndex();
        switch (a) {
            case 0 :
                return -1;
            case 1 :
                return (float)0.05;
            case 2 :
                return (float)0.10;
            case 3 :
                return (float)0.15;
            case 4 :
                return (float)0.20;
            case 5 :
                return (float)0.25;
            case 6 :
                return (float)0.30;
            case 7 :
                return (float)0.35;
            case 8 :
                return (float)0.40;
            case 9 :
                return (float)0.45;
            case 10 :
                return (float)0.50;
            case 11 :
                return (float)0.55;
            case 12 :
                return (float)0.60;
            case 13 :
                return (float)0.65;
            case 14 :
                return (float)0.70;
            case 15 :
                return (float)0.75;
            case 16 :
                return (float)0.80;
            case 17 :
                return (float)0.85;
            case 18 :
                return (float)0.90;
            case 19 :
                return (float)0.95;
            case 20 :
                return 1;
            default :
                return -1;
        }
    }
    
    // Permet de récupérer le nombre de pp sélectionné
    private int getSelectedPP() {
        int a = pp.getSelectedIndex();
        switch (a) {
            case 0 :
                return 0;
            case 1 :
                return 1;
            case 2 :
                return 5;
            case 3 :
                return 10;
            case 4 :
                return 15;
            case 5 :
                return 20;
            case 6 :
                return 25;
            case 7 :
                return 30;
            case 8 :
                return 35;
            case 9 :
                return 40;
            default :
                return 0;
        }
    }
}
