package ru.nsu.ccfit.trubitsyna.utils;

public class ColorUtils {

    public static int getColorRGB(int red, int green, int blue) {
        int rgb = 0xff;
        rgb = (rgb << 8) | red;
        rgb = (rgb << 8) | green;
        rgb = (rgb << 8) | blue;
        return rgb;
    }

    public static int parseColorRed(int color) {
        return (color >> 16) & 0xff;
    }

    public static int parseColorGreen(int color) {
        return (color >> 8) & 0xff;
    }
    public static int parseColorBlue(int color) {
        return color & 0xff;
    }
}
