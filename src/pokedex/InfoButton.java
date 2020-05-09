/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

/**
 *
 * @author Leon
 */
public class InfoButton extends JButton {

    private int id;

    public InfoButton() {
        super();
        this.id = 0;
        setPreferredSize(new Dimension(1, 20));
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        setContentAreaFilled(false);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed() || isSelected()) {
            g.setColor(getBackground().darker().darker());
        } else if (getModel().isRollover()) {
            g.setColor(getBackground().darker());
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}
