/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.FlowLayout;
import javax.swing.JLabel;

/**
 *
 * @author Leon
 */
public class PasswordPanel extends JPanel {

    private final JPasswordField passwordField = new JPasswordField(8);
    private boolean gainedFocusBefore;

    /**
     * "Hook" method that causes the JPasswordField to request focus the first
     * time this method is called.
     */
    void gainedFocus() {
        if (!gainedFocusBefore) {
            gainedFocusBefore = true;
            passwordField.requestFocusInWindow();
        }
    }

    public PasswordPanel() {
        super(new FlowLayout());

        add(new JLabel("Saisissez votre mot de passe : "));
        add(passwordField);
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }
}
