/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui;

import java.awt.Graphics;
import javax.swing.JToggleButton;

/**
 *
 * @author Leon
 */
public class ToggleButton extends JToggleButton {

    public ToggleButton(String texte) {
        super(texte);
        setContentAreaFilled(false);

    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed() || isSelected()) {
            g.setColor(getBackground().darker());
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
