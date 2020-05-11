/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author Leon
 */
public class ImagePanel extends JPanel {

    Image image;

    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            int imageW = image.getWidth(null);
            int imageH = image.getHeight(null);
            int W = this.getWidth();
            int H = this.getHeight();
            double tmpRatio = Math.min((double)(W) / imageW, (double)(H) / imageH);
            int destWidth = (int) (imageW * tmpRatio);
            int destHeight = (int) (imageH * tmpRatio);
            g.drawImage(image, (W-destWidth)/2, (H-destHeight)/2, destWidth, destHeight, null);
            setBackground(Color.WHITE);
        }
    }

}
