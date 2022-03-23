package ru.nsu.ccfit.trubitsyna.view;

import ru.nsu.ccfit.trubitsyna.filters.IFilter;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel  implements MouseMotionListener, MouseListener{
    private final static int DEFAULT_WIDTH = 640;
    private final static int DEFAULT_HEIGHT = 480;
    private final static int DEFAULT_IDENT = 4;

    @Getter
    private BufferedImage processedImage;
    private BufferedImage originImage;


    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;

    private final JScrollPane spImage;

    private int lastX = 0;
    private int lastY = 0;



    ImagePanel(JScrollPane scrollPane) {
        spImage = scrollPane;
        spImage.setWheelScrollingEnabled(false);
        spImage.setDoubleBuffered(true);
        spImage.setViewportView(this);

        spImage.setViewportBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(DEFAULT_IDENT, DEFAULT_IDENT, DEFAULT_IDENT, DEFAULT_IDENT),
                BorderFactory.createDashedBorder(Color.BLACK, 5, 2)));
        spImage.revalidate();
        addMouseListener(this);
        addMouseMotionListener(this);
       // setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

    }

    public static BufferedImage copyImage(BufferedImage copiedImage) {
        var cm = copiedImage.getColorModel();
        var raster =  copiedImage.copyData(null);
        return new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);
    }

    public void setImage(BufferedImage image) {
        originImage = image;
        processedImage = copyImage(originImage);
        width = image.getWidth();
        height = image.getHeight();
        setPreferredSize(new Dimension(width, height));
        repaint();
    }

    public void changeImage(IFilter filter, double... params) {
        filter.filteredImage(originImage, processedImage, params);
        repaint();
    }
//
//    private void setImageWhite() {
//        var imageGraphics = originImage.getGraphics();
//        imageGraphics.setColor(Color.WHITE);
//        imageGraphics.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
//        imageGraphics = processedImage.getGraphics();
//        imageGraphics.setColor(Color.WHITE);
//        imageGraphics.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
//        repaint();
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(processedImage, 0,0, this);
//        var k1  = originImage.getGraphics();
//        g.drawImage(originImage, originImage.getWidth(), DEFAULT_IDENT, this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point scroll = spImage.getViewport().getViewPosition();
        scroll.x += (lastX - e.getX());
        scroll.y += (lastY - e.getY());

        spImage.getHorizontalScrollBar().setValue(scroll.x);
        spImage.getVerticalScrollBar().setValue(scroll.y);
        spImage.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
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
