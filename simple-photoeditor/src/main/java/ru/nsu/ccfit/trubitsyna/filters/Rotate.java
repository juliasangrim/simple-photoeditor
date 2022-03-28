package ru.nsu.ccfit.trubitsyna.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rotate implements IFilter {
    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, double... params) {
        double angle = params[0];
        double sin = Math.abs(Math.sin(Math.toRadians(angle)));
        double cos = Math.abs(Math.cos(Math.toRadians(angle)));

        int hPrime = (int) ((oldImage.getWidth() * sin) + (oldImage.getHeight() * cos));
        int wPrime = (int) ((oldImage.getHeight() * sin) + (oldImage.getWidth() * cos));

        BufferedImage newImage = new BufferedImage(wPrime, hPrime, oldImage.getType());
        Graphics2D g = (Graphics2D) newImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
        g.translate(wPrime / 2, hPrime / 2);
        g.rotate(Math.toRadians(angle));
        g.translate(-oldImage.getWidth() / 2, -oldImage.getHeight() / 2);
        g.drawImage(oldImage, 0, 0, null);

        return newImage;
    }
}
