package ru.nsu.ccfit.trubitsyna.view;

import ru.nsu.ccfit.trubitsyna.filters.IFilter;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel  implements MouseListener{
    private final static int DEFAULT_WIDTH = 640;
    private final static int DEFAULT_HEIGHT = 480;
    private final static int DEFAULT_IDENT = 4;

    @Getter
    private BufferedImage processedImage;
    private BufferedImage originImage;


    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;


    ImagePanel() {
        originImage = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        processedImage = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        setPreferredSize(new Dimension(DEFAULT_WIDTH + DEFAULT_IDENT, DEFAULT_HEIGHT + DEFAULT_IDENT));
        setImageWhite();
    }

    public void setImage(BufferedImage image) {
        originImage = image;
        var cm = image.getColorModel();
        var raster =  image.copyData(null);
        processedImage = new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);
        width = image.getWidth();
        height = image.getHeight();
        setPreferredSize(new Dimension(width, height));
        repaint();
    }

    public void changeImage(IFilter filter, double... params) {
        filter.filteredImage(originImage, processedImage, params);
        repaint();
    }

    private void setImageWhite() {
        var imageGraphics = originImage.getGraphics();
        imageGraphics.setColor(Color.WHITE);
        imageGraphics.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        imageGraphics = processedImage.getGraphics();
        imageGraphics.setColor(Color.WHITE);
        imageGraphics.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        originImage.createGraphics();
        processedImage.createGraphics();

        var k = processedImage.getGraphics();
        g.drawImage(processedImage, DEFAULT_IDENT, DEFAULT_IDENT, this);

//        var k1  = originImage.getGraphics();
//        g.drawImage(originImage, originImage.getWidth(), DEFAULT_IDENT, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
