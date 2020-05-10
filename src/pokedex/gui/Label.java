/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author Leon
 */
public class Label extends JLabel {

    public Label() {
        this("");
    }

    public Label(String text) {
        this(text, false);
    }

    public Label(String text, boolean opaque) {
        super(text);
        setPreferredSize(new Dimension(1, 20));
        setOpaque(opaque);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        setForeground(Color.WHITE);
        setBackground(Color.GRAY);

    }
}
