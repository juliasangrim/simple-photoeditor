package ru.nsu.ccfit.trubitsyna.dialogs;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class DitherinDialog extends JPanel{

    private final static int MIN_VALUE = 2;
    private final static int DEFAULT_VALUE = 2;
    private final static int MAX_VALUE = 128;
    private final static int MIN_STEP = 1;
    private final static int MAX_STEP = 30;

    private final JSlider redSlider;
    private final JSpinner redSpinner;
    private final JSlider greenSlider;
    private final JSpinner greenSpinner;
    private final JSlider blueSlider;
    private final JSpinner blueSpinner;

    private int oldQuantRed = MIN_VALUE;
    private int oldQuantGreen = MIN_VALUE;
    private int oldQuantBlue = MIN_VALUE;

    @Getter
    private int quantRed = MIN_VALUE;
    @Getter
    private int quantGreen = MIN_VALUE;
    @Getter
    private int quantBlue = MIN_VALUE;

    public void setOldQuant(int quantR, int quantG, int quantB) {
        oldQuantRed = quantR;
        oldQuantGreen = quantG;
        oldQuantBlue = quantB;
    }

    public void setValueToOld() {
        redSpinner.setValue(oldQuantRed);
        greenSpinner.setValue(oldQuantGreen);
        blueSpinner.setValue(oldQuantBlue);
    }



    public DitherinDialog() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel additionPanel = new JPanel();
        additionPanel.add(new Label("Chose red quantization number: "));
        redSlider = new JSlider(SwingConstants.HORIZONTAL, MIN_VALUE, MAX_VALUE, DEFAULT_VALUE);
        redSlider.setMinorTickSpacing(MIN_STEP);
        redSlider.setMajorTickSpacing(MAX_STEP);
        redSlider.setPaintTicks(true);
        redSlider.setSnapToTicks(true);
        redSlider.setPaintLabels(true);

        redSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_VALUE, MIN_VALUE, MAX_VALUE, MIN_STEP));

        redSlider.addChangeListener(e -> {
            redSpinner.setValue(redSlider.getValue());
            quantRed = redSlider.getValue();
        });
        redSpinner.addChangeListener(e -> {
            redSlider.setValue((int) redSpinner.getValue());
            quantRed = (int) redSpinner.getValue();
        });

        additionPanel.add(redSlider);
        additionPanel.add(redSpinner);
        add(additionPanel);

        additionPanel = new JPanel();
        additionPanel.add(new Label("Chose green quantization number: "));
        greenSlider = new JSlider(SwingConstants.HORIZONTAL, MIN_VALUE, MAX_VALUE, DEFAULT_VALUE);
        greenSlider.setMinorTickSpacing(MIN_STEP);
        greenSlider.setMajorTickSpacing(MAX_STEP);
        greenSlider.setPaintTicks(true);
        greenSlider.setSnapToTicks(true);
        greenSlider.setPaintLabels(true);

        greenSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_VALUE, MIN_VALUE, MAX_VALUE, MIN_STEP));

        greenSlider.addChangeListener(e -> {
            greenSpinner.setValue(greenSlider.getValue());
            quantGreen = greenSlider.getValue();
        });
        greenSpinner.addChangeListener(e -> {
            greenSlider.setValue((int) greenSpinner.getValue());
            quantGreen = (int) greenSpinner.getValue();
        });

        additionPanel.add(greenSlider);
        additionPanel.add(greenSpinner);
        add(additionPanel);

        additionPanel = new JPanel();

        additionPanel.add(new Label("Chose blue quantization number: "));
        blueSlider = new JSlider(SwingConstants.HORIZONTAL, MIN_VALUE, MAX_VALUE, DEFAULT_VALUE);
        blueSlider.setMinorTickSpacing(MIN_STEP);
        blueSlider.setMajorTickSpacing(MAX_STEP);
        blueSlider.setPaintTicks(true);
        blueSlider.setSnapToTicks(true);
        blueSlider.setPaintLabels(true);

        blueSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_VALUE, MIN_VALUE, MAX_VALUE, MIN_STEP));

        blueSlider.addChangeListener(e -> {
            blueSpinner.setValue(blueSlider.getValue());
            quantBlue = blueSlider.getValue();
        });
        blueSpinner.addChangeListener(e -> {
            blueSlider.setValue((int) blueSpinner.getValue());
            quantBlue = (int) blueSpinner.getValue();
        });

        additionPanel.add(blueSlider);
        additionPanel.add(blueSpinner);
        add(additionPanel);
    }
}
