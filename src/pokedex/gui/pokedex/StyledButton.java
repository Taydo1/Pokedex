/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokedex;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author Leon
 */
public class StyledButton extends JButton {

    private boolean opaque;

    public StyledButton(String text){
        this(text, false);
    }
    
    public StyledButton(String text, boolean opaque) {
        super(text);
        this.opaque = opaque;
        setPreferredSize(new Dimension(1, 20));
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        setContentAreaFilled(true);

        setForeground(Color.WHITE);
        setBackground(Color.GRAY);
        setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed() || isSelected()) {
            g.setColor(getBackground().darker().darker());
            g.fillRect(0, 0, getWidth(), getHeight());
        } else if (getModel().isRollover()) {
            g.setColor(getBackground().darker());
            g.fillRect(0, 0, getWidth(), getHeight());
        } else if (opaque) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(g);
    }

}
