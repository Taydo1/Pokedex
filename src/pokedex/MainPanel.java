/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

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

/**
 *
 * @author Leon
 */
public class MainPanel extends JPanel implements ActionListener {

    JComboBox choix;
    String utilisateur;
    JTabbedPane tabbedPane;
    Database db;
    PokedexPanel pokedexPanel1, pokedexPanel2;
    PokedexApp parent;

    MainPanel(Database db, PokedexApp p) {
        
        parent = p;
        this.db = db;
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        pokedexPanel1 = new PokedexPanel(db, this);
        pokedexPanel2 = new PokedexPanel(db, this);
        tabbedPane.addTab("Pokedex", pokedexPanel1);
        tabbedPane.addTab("Pokedex 2", pokedexPanel2);

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
            utilisateur = selection;
            pokedexPanel1.setUtilisateur(utilisateur);
            pokedexPanel2.setUtilisateur(utilisateur);
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
                Type type = db.getFromDB("SELECT * FROM type WHERE id="+source.getId(), Type.class).get(0);
                System.out.println(""+type);
                break;
            case GET_ABILITY:
                source = (InfoButton)e.getSource();
                Ability ability = db.getFromDB("SELECT * FROM ability WHERE id="+source.getId(), Ability.class).get(0);
                System.out.println(""+ability);
                break;
        }
    }
}
