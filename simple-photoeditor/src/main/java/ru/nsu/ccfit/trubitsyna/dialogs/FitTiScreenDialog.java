package ru.nsu.ccfit.trubitsyna.dialogs;

import lombok.Getter;

import javax.swing.*;
import java.awt.image.AffineTransformOp;

public class FitTiScreenDialog extends JPanel {
    private JRadioButton bilinearButton;
    private JRadioButton bicubicButton;
    private JRadioButton nearestNeighbour;

    private int oldStatus = AffineTransformOp.TYPE_BILINEAR;
    @Getter
    private int status = AffineTransformOp.TYPE_BILINEAR;

    public void setOldStatus(int status) {
        oldStatus = status;
    }

    public void setValueToOld() {
        status = oldStatus;
        if (status == AffineTransformOp.TYPE_BILINEAR) {
            bilinearButton.setSelected(true);
        }
        if (status == AffineTransformOp.TYPE_BICUBIC) {
            bicubicButton.setSelected(true);
        }
        if (status == AffineTransformOp.TYPE_NEAREST_NEIGHBOR) {
            nearestNeighbour.setSelected(true);
        }
    }
    public FitTiScreenDialog() {
        createButtons();
    }

    private void createButtons() {
        bilinearButton = new JRadioButton("Bilinear interpolation");
        bicubicButton = new JRadioButton("Bicubic interpolation");
        nearestNeighbour = new JRadioButton("Nearest-Neighbour interpolation");
        ButtonGroup group = new ButtonGroup();
        group.add(bilinearButton);
        group.add(bicubicButton);
        group.add(nearestNeighbour);
        bilinearButton.addActionListener(e -> {
            status = AffineTransformOp.TYPE_BILINEAR;
        });
        bicubicButton.addActionListener(e -> {
            status = AffineTransformOp.TYPE_BICUBIC;
        });
        nearestNeighbour.addActionListener(e -> {
            status = AffineTransformOp.TYPE_NEAREST_NEIGHBOR;
        });
        add(bilinearButton);
        add(bicubicButton);
        add(nearestNeighbour);
        bilinearButton.setSelected(true);
    }
}
