/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui;

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
    private boolean opaque;

    public InfoButton(String text, int id) {
        this(text, id, false);
    }

    public InfoButton(String text, int id, boolean opaque) {
        super(text);
        this.id = id;
        setPreferredSize(new Dimension(1, 20));
        setOpaque(false);
        this.opaque=opaque;
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        setContentAreaFilled(true);

        setForeground(Color.WHITE);
        setBackground(Color.GRAY);

    }

    public InfoButton() {
        this("", 0);
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
            g.fillRect(0, 0, getWidth(), getHeight());
        } else if (getModel().isRollover()) {
            g.setColor(getBackground().darker());
            g.fillRect(0, 0, getWidth(), getHeight());
        }else if(opaque){
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(g);
    }

    @Override
    public String toString() {
        return getText();
    }

}
