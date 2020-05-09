/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import javax.swing.JButton;

/**
 *
 * @author Leon
 */
public class InfoButton extends JButton {

    private int id;

    public InfoButton() {
        super();
        this.id = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}
