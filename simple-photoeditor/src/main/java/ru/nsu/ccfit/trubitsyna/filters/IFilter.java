package ru.nsu.ccfit.trubitsyna.filters;

import java.awt.image.BufferedImage;

public interface IFilter {
    BufferedImage filteredImage(BufferedImage oldImage, BufferedImage newImage, double... params);
}
