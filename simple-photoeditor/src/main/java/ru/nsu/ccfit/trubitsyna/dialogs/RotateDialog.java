package ru.nsu.ccfit.trubitsyna.dialogs;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class RotateDialog extends JPanel{
    private final static int MIN_VALUE = -180;
    private final static int DEFAULT_VALUE = 0;
    private final static int MAX_VALUE = 180;
    private final static int MIN_STEP = 10;
    private final static int MAX_STEP = 30;


    private final JSlider rotateSlider;
    private final JSpinner rotateSpinner;


    @Getter
    private int angle = DEFAULT_VALUE;
    private int oldAngle = DEFAULT_VALUE;

    public void setOldAngle(int angle) {
        oldAngle = angle;
    }

    public void setValueToOld() {
       rotateSpinner.setValue(oldAngle);

    }

    public RotateDialog() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new Label("Chose angle of rotate: "));
        rotateSlider = new JSlider(SwingConstants.HORIZONTAL, MIN_VALUE, MAX_VALUE, DEFAULT_VALUE);
        rotateSlider.setPreferredSize(new Dimension(300, 50));
        rotateSlider.setMinorTickSpacing(MIN_STEP);
        rotateSlider.setMajorTickSpacing(MAX_STEP);
        rotateSlider.setPaintTicks(true);
        rotateSlider.setSnapToTicks(true);
        rotateSlider.setPaintLabels(true);

        rotateSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_VALUE, MIN_VALUE, MAX_VALUE, MIN_STEP));

        rotateSlider.addChangeListener(e -> {
            rotateSpinner.setValue(rotateSlider.getValue());
            angle = rotateSlider.getValue();
        });
        rotateSpinner.addChangeListener(e -> {
            rotateSlider.setValue((int) rotateSpinner.getValue());
            angle = (int) rotateSpinner.getValue();
        });

        JPanel additionPanel = new JPanel();
        additionPanel.add(rotateSlider);
        additionPanel.add(rotateSpinner);
        add(additionPanel);
    }
}
