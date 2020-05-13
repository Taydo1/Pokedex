/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import pokedex.gui.pokedex.StyledButton;

/**
 *
 * @author Leon
 */
public class InfoButton extends StyledButton {

    private int id;

    public InfoButton(String text, int id) {
        this(text, id, false);
    }

    public InfoButton(String text, int id, boolean opaque) {
        super(text, opaque);
        this.id = id;
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
    public String toString() {
        return getText();
    }

}
