package ru.nsu.ccfit.trubitsyna.view;

import lombok.Setter;
import ru.nsu.ccfit.trubitsyna.utils.ToolStatus;
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

    private BufferedImage originImage;
    private BufferedImage processedImage;
    private BufferedImage processedImageCopy;

    @Getter
    private BufferedImage showImage;

    @Setter
    private ToolStatus status = ToolStatus.NOTOOL;

    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;

    @Getter
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
        processedImageCopy = copyImage(originImage);
        showImage = originImage;
        width = image.getWidth();
        height = image.getHeight();
        setPreferredSize(new Dimension(width, height));
        repaint();
    }

    public void changeImage(IFilter filter, double... params) {
        if (showImage != null) {
            if (status == ToolStatus.ROTATE) {
                if (showImage == originImage) {
                    processedImageCopy = copyImage(originImage);
                }
                showImage = processedImageCopy;
            }
            processedImage = filter.filteredImage(showImage, params);
            if (status != ToolStatus.ROTATE) {
                processedImageCopy = copyImage(processedImage);
            }
            status = ToolStatus.NOTOOL;
            showImage = processedImage;
        repaint();

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (originImage != null) {
            setPreferredSize(new Dimension(showImage.getWidth(), showImage.getHeight()));
        }
        spImage.revalidate();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(showImage, 0,0, this);
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
        if (showImage == originImage) {
            showImage = processedImage;
        } else {
            showImage = originImage;

        }
        repaint();
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
