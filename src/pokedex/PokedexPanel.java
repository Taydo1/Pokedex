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

    public void setUtilisateur(String utilisateur){
        selectionPanel.setUtilisateur(utilisateur);
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
        }
    }

    
}
