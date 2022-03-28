package ru.nsu.ccfit.trubitsyna.filters;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class FitToScreen implements IFilter{


    @Override
    public BufferedImage filteredImage(BufferedImage oldImage, double... params) {
        double width = params[0];
        double height = params[1];
        int type = (int)params[2];

        double coef = Math.min(width / oldImage.getWidth(), height / oldImage.getHeight());
        AffineTransformOp fit = new AffineTransformOp(AffineTransform.getScaleInstance(coef, coef), type);
        return fit.filter(oldImage, null);
    }
}
