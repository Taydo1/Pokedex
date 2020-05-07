/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

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
            System.out.println(""+tmpRatio+" "+ W+" "+H+" "+imageW+" "+imageH);
            int destWidth = (int) (imageW * tmpRatio);
            int destHeight = (int) (imageH * tmpRatio);

            System.out.println(""+destWidth+" "+destHeight+" "+(destWidth-imageW)/2+" "+(destHeight-imageH)/2);
            g.drawImage(image, (W-destWidth)/2, (H-destHeight)/2, destWidth, destHeight, null);
        }
    }

}
