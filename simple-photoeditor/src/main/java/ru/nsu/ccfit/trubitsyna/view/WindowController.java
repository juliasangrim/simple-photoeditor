package ru.nsu.ccfit.trubitsyna.view;

import ru.nsu.ccfit.trubitsyna.utils.ToolStatus;
import ru.nsu.ccfit.trubitsyna.dialogs.*;
import ru.nsu.ccfit.trubitsyna.filters.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class WindowController extends View {

    private final HashMap<ToolStatus, IFilter> tools = new HashMap<>();

    private final GammaDialog gammaDialog = new GammaDialog();
    private final ContouringDialog contouringDialog = new ContouringDialog();
    private final GaussDialog gaussDialog = new GaussDialog();
    private final DitherinDialog ditherDialog = new DitherinDialog();
    private final RotateDialog rotateDialog = new RotateDialog();
    private final FitTiScreenDialog fitDialog = new FitTiScreenDialog();

    public WindowController() {
        super();
        try {
            createButtons();
            createTools();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    private void createTools() {
        BlackWhiteFilter bwFilter = new BlackWhiteFilter();
        tools.put(ToolStatus.BLACK_WHITE, bwFilter);
        EmbossingFilter eFilter = new EmbossingFilter();
        tools.put(ToolStatus.EMBOSSING, eFilter);
        InversionFilter iFilter = new InversionFilter();
        tools.put(ToolStatus.INVERSION, iFilter);
        GammaCorrection gFilter = new GammaCorrection();
        tools.put(ToolStatus.GAMMA, gFilter);
        ContouringFilter cFilter = new ContouringFilter();
        tools.put(ToolStatus.CONTOUR, cFilter);
        SharpnessFilter sFilter = new SharpnessFilter();
        tools.put(ToolStatus.SHARP, sFilter);
        GaussFilter gaussFilter = new GaussFilter();
        tools.put(ToolStatus.GAUSS, gaussFilter);
        WaterColorFilter wFilter = new WaterColorFilter();
        tools.put(ToolStatus.WATERCOLOR, wFilter);
        OrderDithering oFilter = new OrderDithering();
        tools.put(ToolStatus.ORDERDITHERING, oFilter);
        FloydDithering fFilter = new FloydDithering();
        tools.put(ToolStatus.FLOYD, fFilter);
        Rotate rFilter = new Rotate();
        tools.put(ToolStatus.ROTATE, rFilter);
        FitToScreen fitFilter = new FitToScreen();
        tools.put(ToolStatus.FIT, fitFilter);
        Vintage vFilter = new Vintage();
        tools.put(ToolStatus.VINTAGE, vFilter);
    }


    private void createButtons() throws NoSuchMethodException {
        addSubMenu("File", KeyEvent.VK_F);

        addMenuButton("File/Open...", "Open image", KeyEvent.VK_O, "open.png", "onOpen");
        addToolBarButton("File/Open...");
        addMenuButton("File/Save...", "Save your image", KeyEvent.VK_S, "save.png", "onSave");
        addToolBarButton("File/Save...");

        addSubMenu("Modify", KeyEvent.VK_T);
        addMenuButton("Modify/Rotate...", "Rotate image.", KeyEvent.VK_R, "rotate.png", "onRotate");
        addToolBarButton("Modify/Rotate...");
        addMenuButton("Modify/Fit to screen...", "Make image fit to screen.", KeyEvent.VK_F, "resize.png", "onFit");
        addToolBarButton("Modify/Fit to screen...");


        addSubMenu("Filter", KeyEvent.VK_T);
        addMenuButton("Filter/Black&White...", "Makes the image black and white.", KeyEvent.VK_B, "black-and-white.png", "onBlackAndWhite");
        addToolBarButton("Filter/Black&White...");
        addMenuButton("Filter/Contouring...", "Contouring image", KeyEvent.VK_C, "c.png", "onContouring");
        addToolBarButton("Filter/Contouring...");
        addMenuButton("Filter/Embossing...", "Image embossing", KeyEvent.VK_E, "e.png", "onEmbossing");
        addToolBarButton("Filter/Embossing...");
        addMenuButton("Filter/Floyd-Steinberg dithering...", "Dithering. Floyd-Steinberg method", KeyEvent.VK_D, "f.png", "onFloydDithering");
        addToolBarButton("Filter/Floyd-Steinberg dithering...");
        addMenuButton("Filter/Gamma Correction...", "Gamma Correction", KeyEvent.VK_G, "gamma.png", "onGamma");
        addToolBarButton("Filter/Gamma Correction...");
        addMenuButton("Filter/Gauss blur...", "Blur image", KeyEvent.VK_L, "blur.png", "onGauss");
        addToolBarButton("Filter/Gauss blur...");
        addMenuButton("Filter/Inversion...", "Inversion image", KeyEvent.VK_I, "inversion.png", "onInversion");
        addToolBarButton("Filter/Inversion...");
        addMenuButton("Filter/Order dithering...", "Dithering. Order dithering", KeyEvent.VK_W, "o.png", "onOrderDithering");
        addToolBarButton("Filter/Order dithering...");
        addMenuButton("Filter/Sharpness...", "Sharpening image.", KeyEvent.VK_K, "sharpen.png", "onSharpness");
        addToolBarButton("Filter/Sharpness...");
        addMenuButton("Filter/Vintage...", "Vintage.", KeyEvent.VK_V, "magic-wand.png", "onVintage");
        addToolBarButton("Filter/Vintage...");
        addMenuButton("Filter/Water-coloring...", "Water-coloring image.", KeyEvent.VK_A, "water.png", "onWaterColor");
        addToolBarButton("Filter/Water-coloring...");

        addSubMenu("Help", KeyEvent.VK_H);
        addMenuButton("Help/About...", "Shows program version and copyright information", KeyEvent.VK_A, "info.png", "onInfo");
        addToolBarButton("Help/About...");

    }

    public void onInfo(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Photo editor, version 1.0\n Hello! This is simple Photo editor!\n" +
                "The list of instrument presented below: \n " +
                "You can filtered your image with filters: Black and White, Embossing, Floyd-Steinberg dithering, \n" +
                "Gamma correction, Gauss blur, Inversion, Order dithering, Sharpen, Vintage and Water-coloring \n" +
                "Also you can rotate image, make fit to screen, save your images wherever you want(supported extension: png) \n" +
                "and open your works of art(supported extension: jpg, gif, bmp, png)!\n" +
                "Copyright \u00a9 2022 Julia Trubitsyna, FIT, group 19202", "About Paint", JOptionPane.INFORMATION_MESSAGE);

    }


    public void onBlackAndWhite(ActionEvent e) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        imagePanel.changeImage(tools.get(ToolStatus.BLACK_WHITE));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


    public void onEmbossing(ActionEvent e) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        imagePanel.changeImage(tools.get(ToolStatus.EMBOSSING));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void onInversion(ActionEvent e) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        imagePanel.changeImage(tools.get(ToolStatus.INVERSION));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


    public void onGamma(ActionEvent e) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        var clickedValue = JOptionPane.showOptionDialog(this, gammaDialog, "Change gamma parameter",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (clickedValue == JOptionPane.OK_OPTION) {
            var gamma = gammaDialog.getGamma();
            //save old gamma for reset gamma on spinner and slider
            gammaDialog.setOldGamma(gamma);
            imagePanel.changeImage(tools.get(ToolStatus.GAMMA), gamma);
        } else {
            //reset gamma to old value
            gammaDialog.setValueToOld();
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void onContouring(ActionEvent e) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        var clickedValue = JOptionPane.showOptionDialog(this, contouringDialog, "Change visibility",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (clickedValue == JOptionPane.OK_OPTION) {
            var threshold = contouringDialog.getThreshold();
            contouringDialog.setOldThreshold(threshold);

            ContouringFilter contouring = (ContouringFilter) tools.get(ToolStatus.CONTOUR);
            if (contouringDialog.getStatus() == ToolStatus.ROBERTS) {
                contouring.setStatus(ToolStatus.ROBERTS);
                contouringDialog.setOldStatus(ToolStatus.ROBERTS);
            } else {
                contouring.setStatus(ToolStatus.SOBELS);
                contouringDialog.setOldStatus(ToolStatus.SOBELS);
            }
            imagePanel.changeImage(contouring, threshold);
        } else {
            contouringDialog.setValueToOld();
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void onSharpness(ActionEvent e) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        imagePanel.changeImage(tools.get(ToolStatus.SHARP));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void onGauss(ActionEvent e) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        var clickedValue = JOptionPane.showOptionDialog(this, gaussDialog, "Change parameters",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (clickedValue == JOptionPane.OK_OPTION) {
            var gaussCoef = gaussDialog.getGaussCoef();
            var size = gaussDialog.getSizeKernel();
            gaussDialog.setOldGaussCoef(gaussCoef);
            gaussDialog.setOldSize(size);
            imagePanel.changeImage(tools.get(ToolStatus.GAUSS), size, gaussCoef);
        } else {
            gaussDialog.setValueToOld();
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void onOrderDithering(ActionEvent e) {
        var clickedValue = JOptionPane.showOptionDialog(this, ditherDialog, "Change parameters",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (clickedValue == JOptionPane.OK_OPTION) {
            var quantR = ditherDialog.getQuantRed();
            var quantG = ditherDialog.getQuantGreen();
            var quantB = ditherDialog.getQuantBlue();
            ditherDialog.setOldQuant(quantR, quantG, quantB);

            imagePanel.changeImage(tools.get(ToolStatus.ORDERDITHERING), quantR, quantG, quantB);
        } else {
            ditherDialog.setValueToOld();
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


    public void onFloydDithering(ActionEvent e) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        var clickedValue = JOptionPane.showOptionDialog(this, ditherDialog, "Change parameters",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (clickedValue == JOptionPane.OK_OPTION) {
            //save old values for reset gamma on spinner and slider
            var quantR = ditherDialog.getQuantRed();
            var quantG = ditherDialog.getQuantGreen();
            var quantB = ditherDialog.getQuantBlue();

            ditherDialog.setOldQuant(quantR, quantG, quantB);

            imagePanel.changeImage(tools.get(ToolStatus.FLOYD), quantR, quantG, quantB);
        } else {
            //reset old value
            ditherDialog.setValueToOld();
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


    public void onWaterColor(ActionEvent e) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        imagePanel.changeImage(tools.get(ToolStatus.WATERCOLOR));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void onRotate(ActionEvent e) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        var clickedValue = JOptionPane.showOptionDialog(this, rotateDialog, "Change parameters",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (clickedValue == JOptionPane.OK_OPTION) {
            var angle = rotateDialog.getAngle();
            rotateDialog.setOldAngle(angle);
            imagePanel.setStatus(ToolStatus.ROTATE);
            imagePanel.changeImage(tools.get(ToolStatus.ROTATE), angle);
        } else {
            rotateDialog.setValueToOld();
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void onFit(ActionEvent e) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        var clickedValue = JOptionPane.showOptionDialog(this, fitDialog, "Change parameters",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (clickedValue == JOptionPane.OK_OPTION) {
            var status = fitDialog.getStatus();
            fitDialog.setOldStatus(status);
            int shiftWidth = 0;
            int shiftHeight = 0;
            if (imagePanel.getSpImage().getHorizontalScrollBar().isVisible()) {
                shiftHeight = imagePanel.getSpImage().getHorizontalScrollBar().getHeight();
            }

            if (imagePanel.getSpImage().getVerticalScrollBar().isVisible()) {
                shiftWidth = imagePanel.getSpImage().getVerticalScrollBar().getWidth();
            }
            imagePanel.getSpImage().getVerticalScrollBar().setVisible(false);
            imagePanel.changeImage(tools.get(ToolStatus.FIT),
                    imagePanel.getSpImage().getViewport().getWidth() + shiftWidth,
                    imagePanel.getSpImage().getViewport().getHeight() + shiftHeight, status);
        } else {
            rotateDialog.setValueToOld();
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void onVintage(ActionEvent e) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        imagePanel.changeImage(tools.get(ToolStatus.VINTAGE));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


    public void onOpen(ActionEvent event) {
        FileDialog fd = new FileDialog(this, "Open image", FileDialog.LOAD);
        fd.setVisible(true);
        System.out.println(fd.getFile());
        try {
            if (fd.getFile() != null) {
                if (fd.getFile().endsWith(".bmp") || fd.getFile().endsWith(".jpg") || fd.getFile().endsWith(".gif") || fd.getFile().endsWith(".png")) {
                    imagePanel.setImage(ImageIO.read(new File(fd.getDirectory() + fd.getFile())));
                    scrollPane.revalidate();
                } else {
                    JOptionPane.showMessageDialog(this, "Application can't open file with such exception.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onSave(ActionEvent event) {
        FileDialog fd = new FileDialog(this, "Save image", FileDialog.SAVE);
        fd.setFile("Untitled");
        fd.setVisible(true);
        var image = imagePanel.getShowImage();
        File file = new File(fd.getDirectory() + fd.getFile() + ".png");
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
