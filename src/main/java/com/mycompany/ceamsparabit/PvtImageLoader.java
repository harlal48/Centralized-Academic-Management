package com.mycompany.ceamsparabit;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class PvtImageLoader {

    //BufferedImage img;
    String filePath;
    File f;
    Image picture[]= new Image[5];

    public void loadImage(JLabel label, int in) {
        JFileChooser jf = new JFileChooser();
        int a = jf.showOpenDialog(null); //open, close
        if (a == JFileChooser.APPROVE_OPTION) {
            f = jf.getSelectedFile();
            filePath = f.getAbsolutePath();
            try {
                //img = ImageIO.read(new File(filePath));
                ImageIcon ii=new ImageIcon(filePath);
                Image img=ii.getImage();
                picture[in]=img;
                Image img1 = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);// set image is here
                ImageIcon im = new ImageIcon(img1);//img1 is final image;
                // and im is ImageIcon;         
                label.setIcon(im);// small image
                
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
    public void showImage(JLabel label1, int index) {
        Image pp =picture[index];
        Image img2 = pp.getScaledInstance(label1.getWidth(), label1.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon im2 = new ImageIcon(img2);
                label1.setIcon(im2);
    }
}