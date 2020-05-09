/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author Leon
 */
public class PokedexPanel extends JPanel implements ActionListener {

    Database db;
    ImagePokedexPanel imagePanel;
    SelectionPokedexPanel selectionPanel;
    TopPokedexPanel topPanel;
    String imageName;
    MainPanel parent;

    int idActuel;

    public PokedexPanel(Database db, MainPanel parent) {
        this.db = db;
        this.parent = parent;

        setLayout(new BorderLayout());

        idActuel = 16;
        imagePanel = new ImagePokedexPanel();
        add(imagePanel, BorderLayout.CENTER);
        selectionPanel = new SelectionPokedexPanel("Professeur", this);
        add(selectionPanel, BorderLayout.SOUTH);
        topPanel = new TopPokedexPanel(this);
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

    public void setUtilisateur(String utilisateur) {
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
            case GET_POKEDEX:
                InfoButton source = (InfoButton) e.getSource();
                goToID(source.getId());
        }
    }
}
