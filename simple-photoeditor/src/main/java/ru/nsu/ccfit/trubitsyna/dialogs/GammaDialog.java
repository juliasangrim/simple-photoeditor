package ru.nsu.ccfit.trubitsyna.dialogs;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class GammaDialog extends JPanel {
    private final static int MIN_VALUE = 1;
    private final static int DEFAULT_VALUE = 1;
    private final static int MAX_VALUE = 100;
    private final static int MIN_STEP = 1;
    private final static int MAX_STEP = 10;

    private final static double MIN_VALUE_SPIN = 0.1;
    private final static double DEFAULT_VALUE_SPIN = 0.1;
    private final static double MAX_VALUE_SPIN = 10;
    private final static double MIN_STEP_SPIN = 0.1;

    private final static int DIV = 10;

    private  JSlider gammaSlider;
    private JSpinner gammaSpinner;


    private double oldGamma = (double) DEFAULT_VALUE / DIV;
    @Getter
    private double gamma = (double) DEFAULT_VALUE / DIV;

    public void setDefault() {
        gamma = MIN_VALUE;
    }

    public void setOldGamma(double gamma) {
        oldGamma = gamma;
    }

    public void setValueToOld() {
        gammaSpinner.setValue(oldGamma);
    }

    public GammaDialog() {

        add(new Label("Gamma: "));
        gammaSlider = new JSlider(SwingConstants.HORIZONTAL, MIN_VALUE, MAX_VALUE, DEFAULT_VALUE);
        gammaSlider.setMinorTickSpacing(MIN_STEP);
        gammaSlider.setMajorTickSpacing(MAX_STEP);
        gammaSlider.setPaintTicks(true);
        gammaSlider.setPreferredSize(new Dimension(300, 95));
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        double k = (double) MIN_VALUE / DIV;
        for (int i = 0; i < MAX_VALUE / DIV * 2; ++i) {
            labels.put((int)(k * DIV), new JLabel(Double.toString(k)));
            k += 1;
        }
        gammaSlider.setLabelTable(labels);
        gammaSlider.setPaintLabels(true);

        gammaSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_VALUE_SPIN, MIN_VALUE_SPIN, MAX_VALUE_SPIN, MIN_STEP_SPIN));



        gammaSlider.addChangeListener(e -> {
            gammaSpinner.setValue((double)gammaSlider.getValue() / DIV);
            gamma =  (double) gammaSlider.getValue() / DIV;

        });
        gammaSpinner.addChangeListener(e -> {
            gammaSlider.setValue((int)(((double) gammaSpinner.getValue()) * DIV));
            gamma = (double) gammaSpinner.getValue() ;
        });

        add(gammaSlider);
        add(gammaSpinner);
    }
}
