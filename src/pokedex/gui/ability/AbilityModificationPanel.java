/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.ability;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import pokedex.database.Ability;
import pokedex.gui.Action;
import pokedex.gui.MainPanel;

/**
 *
 * @author Spectan
 */


// Les composants qui sont utilisés pour faire les modifications sont placés dans des Panels
// avec une bordure et un titre indiquant ce à quoi correspond le composant

public class AbilityModificationPanel extends JPanel implements ActionListener {

    JTextField name, enName;
    JTextArea descriptionFight, descriptionOutFight;
    JButton saveButton, discardButton;
    MainPanel parent;
    int idModif;

    public AbilityModificationPanel(int id, MainPanel p) {
        
        // Récupération du talent que l'on veut modifier
        Ability currentAbility = p.db.getFromDB("SELECT * FROM ability WHERE id=" + id, Ability.class).get(0);
        
        // On enregistre l'id du talent que l'on modifie pour pouvoir le réutiliser
        idModif = id;
        
        // On enregistre la fenêtre qui contient l'onglet de modification/création pour pouvoir accéder à la database
        parent = p;
        
        // Création des champs de texte pour les noms français et anglais
        name = new JTextField(currentAbility.name);
        enName = new JTextField(currentAbility.en_name);

        // Création de la zone de texte pour la description de l'effet en combat
        descriptionFight = new JTextArea();
        descriptionFight.append(currentAbility.description[0]);
        descriptionFight.setLineWrap(true);
        descriptionFight.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        
        // Création de la zone de texte pour la description de l'effet hors combat
        descriptionOutFight = new JTextArea();
        descriptionOutFight.append("" + currentAbility.description[1]);
        descriptionOutFight.setLineWrap(true);
        descriptionOutFight.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        
        // Création du Panel dans lequel il y aura le champ de texte du nom français
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.white);
        namePanel.setBorder(BorderFactory.createTitledBorder("Nom du talent"));
        namePanel.add(name);
        
        // Création du Panel dans lequel il y aura le champ de texte du nom anglais
        JPanel enNamePanel = new JPanel();
        enNamePanel.setBackground(Color.white);
        enNamePanel.setBorder(BorderFactory.createTitledBorder("Nom du talent anglais"));
        enNamePanel.add(enName);

        // Création du Panel dans lequel il y aura la zone de texte pour l'effet en combat
        JPanel descriptionFightPanel = new JPanel();
        descriptionFightPanel.setBackground(Color.white);
        descriptionFightPanel.setBorder(BorderFactory.createTitledBorder("Description de l'effet en combat"));
        descriptionFightPanel.add(descriptionFight);

        // Création du Panel dans lequel il y aura la zone de texte pour l'effet hors combat
        JPanel descriptionOutPanel = new JPanel();
        descriptionOutPanel.setBackground(Color.white);
        descriptionOutPanel.setBorder(BorderFactory.createTitledBorder("Description de l'effet hors combat"));
        descriptionOutPanel.add(descriptionOutFight);

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

        // Ajout des Panels et des 2 boutons suivant un GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        add(namePanel, c);
        c.gridx++;
        add(enNamePanel, c);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        c.gridheight = 2;
        add(descriptionFightPanel, c);
        c.gridy = c.gridy + 2;
        add(descriptionOutPanel, c);
        c.gridy = c.gridy + 2;
        c.gridwidth = 1;
        add(saveButton, c);
        c.gridx++;
        add(discardButton, c);

        // Redimensionne les différents composants pour que les dimensions soient adaptées à la fenêtre
        setDimension();
    }

    // Permet de définir les actions et fonctions du bouton sur lequel on appuie
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (Action.valueOf(e.getActionCommand())) {
            // Ce qui est fait lorsque l'on appuie sur le bouton de modification
            case SAVE_MODIFICATION:
                
                // Application des changements sur le talent
                new Ability(idModif, name.getText(), enName.getText(), descriptionFight.getText(),
                        descriptionOutFight.getText()).modifyInDB(parent.db);
                
                // Affichage d'un message disant que les modifications ont été effectuées
                JOptionPane.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);
                
                // Sélectionne le talent qui vient d'être modifié dans l'onglet des talents
                parent.abilityPanel.setId(idModif);
                
                // Supprime l'onglet de modification et se place sur l'onglet des talents
                parent.removeTab(this, parent.abilityPanel, true);
                
                break;
                
            // Ce qui est fait lorsque l'on appuie sur le bouton d'annulation  
            case DISCARD_MODIFICATION:
                // Supprime l'onglet de modification (oui, c'est tout)
                parent.removeTab(this, parent.abilityPanel, false);
                break;
        }
    }
    
    // Fonction pour adapter les composants à la fenêtre
    private void setDimension() {
        // Calcul de valeurs de base pour les dimensions
        int dimx = (parent.getWidth() / 2) - 40;
        int dimy = (parent.getHeight() / 6) - 60;
        
        // Changements des dimensions des composants
        name.setPreferredSize(new Dimension(dimx, dimy));
        enName.setPreferredSize(new Dimension(dimx, dimy));
        descriptionFight.setPreferredSize(new Dimension(dimx * 2, dimy * 2));
        descriptionOutFight.setPreferredSize(new Dimension(dimx * 2, dimy * 2));
    }
}
