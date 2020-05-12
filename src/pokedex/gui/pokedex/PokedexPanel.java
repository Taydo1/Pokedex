/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokedex;

import pokedex.gui.ImagePanel;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import pokedex.database.*;
import pokedex.gui.*;

/**
 *
 * @author Leon
 */
public class PokedexPanel extends JPanel implements ActionListener {

    Database db;
    ImagePanel imagePanel;
    BottomPokedexPanel bottomPanel;
    TopPokedexPanel topPanel;
    String imageName;
    MainPanel parent;

    public int currentId;

    public PokedexPanel(Database db, MainPanel parent) {
        this.db = db;
        this.parent = parent;

        setLayout(new BorderLayout());

        currentId = 16;
        imagePanel = new ImagePanel();
        add(imagePanel, BorderLayout.CENTER);
        bottomPanel = new BottomPokedexPanel("Visiteur", this);
        add(bottomPanel, BorderLayout.SOUTH);
        topPanel = new TopPokedexPanel(this);
        add(topPanel, BorderLayout.NORTH);

        imageName = "image";
        setId(currentId);

    }

    public void setId(int id) {
        //Go to id
        try {
            bottomPanel.setId(id, db);
            topPanel.setId(id, db);
            String imageRequest;
            //switch
            Image image = db.getImage("SELECT " + imageName + " FROM pokedex WHERE id=" + id);
            imagePanel.setImage(image);
            currentId = id;
        } catch (NumberFormatException ex) {

        }
    }

    public void setUtilisateur(String utilisateur) {
        bottomPanel.setUser(utilisateur);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Action.valueOf(e.getActionCommand())) {
            case UP:
                setId(currentId + 1);
                break;
            case DOWN:
                setId(currentId - 1);
                break;
            case GO: {
                try {
                    setId(bottomPanel.getGoId());
                    bottomPanel.clearGoId();
                } catch (NumberFormatException ex) {
                }
                break;
            }
            case IMAGE_NORMAL:
                imageName = "image";
                setId(currentId);
                break;
            case IMAGE_SHINY:
                imageName = "image_shiny";
                setId(currentId);
                break;
            case IMAGE_MEGA:
                imageName = "image_mega";
                setId(currentId);
                break;
            case START_POKEDEX_MODIFICATION:
                ArrayList<Pokedex> listname = db.getFromDB("SELECT * FROM pokedex WHERE id=" + String.valueOf(currentId), Pokedex.class);
                String name = listname.get(0).name;
                parent.addTab(new PokedexModificationPanel(currentId, parent), "Modification de" + name, MainPanel.PROFESSOR_TAB);
                break;
            case GO_NOM: {
                try {
                    setId(bottomPanel.getIDFromNom(db));

                } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                    System.out.println("Pokemon inexistant");
                    JFrame frame = new JFrame("");
                    ImageIcon icon = new ImageIcon(getClass().getResource("/images/icones/PikachuGif.gif"));
                    Object[] options = {"Get coronavirus", "OK Boomer"};
                    JOptionPane.showOptionDialog(frame, "Le Pokemon '" + bottomPanel.goName.getText() + "' n'existe pas\n"
                            + "Veuillez entrer un nom valide", "Pokemon introuvable", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, icon, options, options[1]);
                }
                bottomPanel.clearGoNom();
                break;
            }
        }
    }
}
