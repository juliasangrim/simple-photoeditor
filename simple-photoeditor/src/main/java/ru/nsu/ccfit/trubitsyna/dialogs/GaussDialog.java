package ru.nsu.ccfit.trubitsyna.dialogs;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class GaussDialog extends JPanel{
    private final static int MIN_VALUE = 1;
    private final static int DEFAULT_VALUE = 1;
    private final static int MAX_VALUE = 100;
    private final static int MIN_STEP = 1;
    private final static int MAX_STEP = 10;

    private final static double MIN_VALUE_SPIN = 0.1;
    private final static double DEFAULT_VALUE_SPIN = 0.1;
    private final static double MAX_VALUE_SPIN = 10;
    private final static double MIN_STEP_SPIN = 0.1;

    private final static int MIN_VALUE_SIZE = 3;
    private final static int DEFAULT_VALUE_SIZE = 3;
    private final static int MAX_VALUE_SIZE = 11;
    private final static int MIN_STEP_SIZE = 2;
    private final static int MAX_STEP_SIZE = 2;

    private final static int DIV = 10;

    private final JSlider gaussSlider;
    private final JSpinner gaussSpinner;
    private final JSlider sizeSlider;
    private final JSpinner sizeSpinner;

    private double oldGaussCoef = (double) DEFAULT_VALUE / DIV;
    @Getter
    private double gaussCoef = (double) DEFAULT_VALUE / DIV;

    private int oldSizeKernel = MIN_VALUE_SIZE;
    @Getter
    private int sizeKernel = MIN_VALUE_SIZE;

    public void setOldGaussCoef(double gaussCoef) {
        oldGaussCoef = gaussCoef;
    }

    public void setOldSize(int size) {
        oldSizeKernel = size;
    }

    public void setValueToOld() {
        gaussSpinner.setValue(oldGaussCoef);
        sizeSpinner.setValue(oldSizeKernel);
    }

    public GaussDialog() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel additionPanel = new JPanel();
        additionPanel.add(new Label("Chose sigma value: "));
        gaussSlider = new JSlider(SwingConstants.HORIZONTAL, MIN_VALUE, MAX_VALUE, DEFAULT_VALUE);
        gaussSlider.setMinorTickSpacing(MIN_STEP);
        gaussSlider.setMajorTickSpacing(MAX_STEP);
        gaussSlider.setSnapToTicks(true);
        gaussSlider.setPaintTicks(true);
        gaussSlider.setPreferredSize(new Dimension(225, 100));

        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        double k = (double) MIN_VALUE / DIV;
        for (int i = 0; i < MAX_VALUE / DIV * 2; ++i) {
            labels.put((int)(k * DIV), new JLabel(Double.toString(k)));
            k += 1;
        }
        gaussSlider.setLabelTable(labels);
        gaussSlider.setPaintLabels(true);

        gaussSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_VALUE_SPIN, MIN_VALUE_SPIN, MAX_VALUE_SPIN, MIN_STEP_SPIN));
        gaussSlider.addChangeListener(e -> {
            gaussSpinner.setValue((double) gaussSlider.getValue() / DIV);
            gaussCoef =  (double) gaussSlider.getValue() / DIV;

        });
        gaussSpinner.addChangeListener(e -> {
            gaussSlider.setValue((int)(((double) gaussSpinner.getValue()) * DIV));
            gaussCoef = (double) gaussSpinner.getValue() ;
        });

        additionPanel.add(gaussSlider);
        additionPanel.add(gaussSpinner);
        add(additionPanel);

        additionPanel = new JPanel();
        additionPanel.add(new Label("Chose kernel size value: "));
        sizeSlider = new JSlider(SwingConstants.HORIZONTAL, MIN_VALUE_SIZE, MAX_VALUE_SIZE, DEFAULT_VALUE_SIZE);
        sizeSlider.setMinorTickSpacing(MIN_STEP_SIZE);
        sizeSlider.setMajorTickSpacing(MAX_STEP_SIZE);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setSnapToTicks(true);
        sizeSlider.setPaintLabels(true);

        sizeSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_VALUE_SIZE, MIN_VALUE_SIZE, MAX_VALUE_SIZE, MIN_STEP_SIZE));

        sizeSlider.addChangeListener(e -> {
            sizeSpinner.setValue(sizeSlider.getValue());
            sizeKernel = sizeSlider.getValue();
        });
        sizeSpinner.addChangeListener(e -> {
            sizeSlider.setValue((int) sizeSpinner.getValue());
            sizeKernel = (int) sizeSpinner.getValue();
        });

        additionPanel.add(sizeSlider);
        additionPanel.add(sizeSpinner);
        add(additionPanel);

    }
}
