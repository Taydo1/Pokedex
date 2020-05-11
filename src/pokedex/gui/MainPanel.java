/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import pokedex.gui.pokedex.*;
import pokedex.database.*;
import pokedex.gui.ability.AbilityPanel;
import pokedex.gui.type.TypePanel;

/**
 *
 * @author Leon
 */
public class MainPanel extends JPanel implements ActionListener {

    JComboBox choix;
    String utilisateur;
    public JTabbedPane tabbedPane;
    public Database db;
    public PokedexPanel pokedexPanel1;
    TypePanel typePanel;
    AbilityPanel abilityPanel;
    PokedexApp parent;
    public PokedexModificationPanel fenetreModification;

    MainPanel(Database db, PokedexApp p) {
        
        parent = p;
        this.db = db;
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        pokedexPanel1 = new PokedexPanel(db, this);
        typePanel = new TypePanel(db, this);
        abilityPanel = new AbilityPanel(db, this);
        tabbedPane.addTab("Pokedex", pokedexPanel1);
        tabbedPane.addTab("Type", typePanel);
        tabbedPane.addTab("Talent", abilityPanel);

        String[] personnes = new String[]{"Visiteur", "Dresseur", "Professeur"};
        choix = new JComboBox<>(personnes);
        choix.setActionCommand(Action.CHANGE_USER.name());
        choix.addActionListener(this);
        add(choix, BorderLayout.NORTH);
        
        add(tabbedPane, BorderLayout.CENTER);

        utilisateur = personnes[0];
    }

    private void changeUser(JComboBox comboBox) {
        String selection = (String) choix.getSelectedItem();
        String mdp;
        if (selection.equals("Visiteur")) {
            mdp = "visiteur";
        } else {
            mdp = getPassword();
        }

        if (mdp != null && mdp.toLowerCase().equals(selection.toLowerCase())) {
            if(!utilisateur.equals(selection)){
                while(tabbedPane.getTabCount() > 2){
                    tabbedPane.remove(2);
                }
                tabbedPane.setSelectedComponent(pokedexPanel1);
            }
            utilisateur = selection;
            pokedexPanel1.setUtilisateur(utilisateur);
        } else if (mdp != null) {
            comboBox.setSelectedItem(utilisateur);
            JOptionPane.showMessageDialog(null, "Mauvais mot de passe, sry !", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            comboBox.setSelectedItem(utilisateur);
        }
        
    }

    private String getPassword() {
        PasswordPanel passwordPanel = new PasswordPanel();
        JOptionPane op = new JOptionPane(passwordPanel);
        op.setMessageType(JOptionPane.QUESTION_MESSAGE);
        op.setOptionType(JOptionPane.OK_CANCEL_OPTION);

        JDialog dlg = op.createDialog("Mot de Passe");

        dlg.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                passwordPanel.gainedFocus();
            }
        });
        dlg.setVisible(true);

        if (op.getValue() != null && op.getValue().equals(JOptionPane.OK_OPTION)) {
            return new String(passwordPanel.getPassword());
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InfoButton source;
        switch (Action.valueOf(e.getActionCommand())) {
            case CHANGE_USER:
                changeUser((JComboBox) e.getSource());
                break;
            case GET_TYPE:
                source = (InfoButton)e.getSource();
                typePanel.setId(source.getId(), 0);
                tabbedPane.setSelectedComponent(typePanel);
                break;
            case GET_ABILITY:
                source = (InfoButton)e.getSource();
                abilityPanel.setId(source.getId());
                tabbedPane.setSelectedComponent(abilityPanel);
                break;
        }
    }
}
