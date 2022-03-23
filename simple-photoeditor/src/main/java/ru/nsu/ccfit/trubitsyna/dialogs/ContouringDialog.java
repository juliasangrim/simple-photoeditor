package ru.nsu.ccfit.trubitsyna.dialogs;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.trubitsyna.ToolStatus;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class ContouringDialog extends JPanel{
    private final static int MIN_VALUE = 10;
    private final static int DEFAULT_VALUE = 10;
    private final static int MAX_VALUE = 100;
    private final static int MIN_STEP = 5;
    private final static int MAX_STEP = 10;
    private final static int DIV = 10;

    private final static double MIN_VALUE_SPIN = 1.0;
    private final static double DEFAULT_VALUE_SPIN = 1.0;
    private final static double MAX_VALUE_SPIN = 10.0;
    private final static double MIN_STEP_SPIN = 0.5;

    private JSlider contouringSlider;
    private JSpinner contouringSpinner;


    @Setter
    private int oldThreshold;
    @Getter
    private int threshold = MIN_VALUE;
    @Getter
    private ToolStatus status = ToolStatus.ROBERTS;


    public void setDefault() {
        threshold = MIN_VALUE;
        status = ToolStatus.ROBERTS;
    }

    public void setValueToOld() {
        contouringSpinner.setValue((double)(oldThreshold - MIN_VALUE) / DIV);
    }

    public ContouringDialog() {

        add(new Label("Choose the visibility of borders: "));
        contouringSlider = new JSlider(SwingConstants.HORIZONTAL, MIN_VALUE, MAX_VALUE, DEFAULT_VALUE);
        contouringSlider.setMinorTickSpacing(MIN_STEP);
        contouringSlider.setMajorTickSpacing(MAX_STEP);
        contouringSlider.setSnapToTicks(true);
        contouringSlider.setPaintTicks(true);
        contouringSlider.setPreferredSize(new Dimension(200, 95));
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        int k = 1;
        for (int i = 0; i <= (MAX_VALUE - MIN_VALUE) / DIV; ++i) {
            labels.put(k * MAX_STEP, new JLabel(String.valueOf(k)));
            System.out.println(k);
            k += MAX_STEP / DIV;
        }
        contouringSlider.setLabelTable(labels);
        contouringSlider.setPaintLabels(true);

        contouringSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_VALUE_SPIN, MIN_VALUE_SPIN, MAX_VALUE_SPIN, MIN_STEP_SPIN));
        contouringSpinner.setPreferredSize(new Dimension(50,30));


        contouringSlider.addChangeListener(e -> {
            contouringSpinner.setValue((double) contouringSlider.getValue() / DIV);
            threshold = contouringSlider.getValue() ;

        });
        contouringSpinner.addChangeListener(e -> {
            contouringSlider.setValue((int)((double) contouringSpinner.getValue() * MAX_STEP));
            threshold = (int)((double) contouringSpinner.getValue() * MAX_STEP) ;
            System.out.println(threshold);
        });

        add(contouringSlider);
        add(contouringSpinner);
        createButtons();
    }

    private void createButtons() {
        JRadioButton rButton = new JRadioButton("Roberts operator");
        JRadioButton sButton = new JRadioButton("Sobels operator");
        ButtonGroup group = new ButtonGroup();
        group.add(rButton);
        group.add(sButton);
        rButton.addActionListener(e -> {
            status = ToolStatus.ROBERTS;
        });
        sButton.addActionListener(e -> {
            status = ToolStatus.SOBELS;
        });
        add(rButton);
        add(sButton);
        rButton.setSelected(true);
    }
}
