/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Leon
 */
public class PokedexPanel extends JPanel  implements ActionListener {

    Database db;
    ImagePanel imagePanel;
    SelectionPanel selectionPanel;
    TopPanel topPanel;
    String imageName;

    int idActuel;

    public PokedexPanel(Database db) {
        this.db = db;

        setLayout(new BorderLayout());

        idActuel = 2;
        imagePanel = new ImagePanel();
        add(imagePanel, BorderLayout.CENTER);
        selectionPanel = new SelectionPanel(idActuel, db, "Visiteur", this);
        add(selectionPanel, BorderLayout.SOUTH);
        topPanel = new TopPanel(this, idActuel, db);
        add(topPanel, BorderLayout.NORTH);
        
        imageName = "image";
        goToID(idActuel);

    }

        private void goToID(int id) {
        //Go to id
        try {
            selectionPanel.setId(id, db);
            topPanel.setId(id, db);
            String imageRequest;
            //switch
            Image image = db.getImage("SELECT " + imageName + " FROM pokedex WHERE id=" + id);
            imagePanel.setImage(image);
            idActuel = id;
        } catch (NumberFormatException ex) {

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

    private void changeUser(JComboBox comboBox) {
        String selection = (String) topPanel.choix.getSelectedItem();
        String mdp;
        if (selection.equals("Visiteur")) {
            mdp = "visiteur";
        } else {
            mdp = getPassword();
        }

        if (mdp != null && mdp.toLowerCase().equals(selection.toLowerCase())) {
            selectionPanel.setUtilisateur(selection);
        } else if (mdp != null) {
            comboBox.setSelectedItem(selectionPanel.getUtilisateur());
            JOptionPane.showMessageDialog(null, "Mauvais mot de passe, sry !", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            comboBox.setSelectedItem(selectionPanel.getUtilisateur());

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Action.valueOf(e.getActionCommand())) {
            case UP:
                goToID(idActuel + 1);
                break;
            case DOWN:
                goToID(idActuel - 1);
                break;
            case GO: {
                try {
                    goToID(selectionPanel.getGoId());
                    selectionPanel.clearGoId();
                } catch (NumberFormatException ex) {
                }
                break;
            }
            case IMAGE_NORMAL:
                imageName = "image";
                goToID(idActuel);
                break;
            case IMAGE_SHINY:
                imageName = "image_shiny";
                goToID(idActuel);
                break;
            case IMAGE_MEGA:
                imageName = "image_mega";
                goToID(idActuel);
                break;
            case CHANGE_USER:
                changeUser((JComboBox) e.getSource());
        }
    }

    
}
