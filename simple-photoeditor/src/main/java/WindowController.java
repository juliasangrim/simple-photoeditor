import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

public class WindowController extends View {

    private HashMap<ToolStatus, IFilter> tools = new HashMap<>();

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
    }


    private void createButtons() throws NoSuchMethodException {
        addSubMenu("File", KeyEvent.VK_F);

        addMenuButton("File/Open...", "Open image", KeyEvent.VK_O, "open.png", "onOpen");
        addToolBarButton("File/Open...");
        addMenuButton("File/Save...", "Save your image", KeyEvent.VK_S, "save.png", "onSave");
        addToolBarButton("File/Save...");
//        addMenuButton("File/Change window...", "Change window size", KeyEvent.VK_U, "option.png", "onChangeSize");
//        addToolBarButton("File/Change window...");


        addSubMenu("Filter", KeyEvent.VK_T);
        addMenuButton("Filter/Black&White...", "Makes the image black and white", KeyEvent.VK_L, "", "onBlackAndWhite");
//        addToolBarButton("Tools/Line...");
//        addMenuButton("Tools/Fill...", "Fill area", KeyEvent.VK_F, "bucket.png", "onFill");
//        addToolBarButton("Tools/Fill...");
//        addMenuButton("Tools/Polygon Stamp...", "Draw polygon", KeyEvent.VK_P, "polygon.png", "onPolygon");
//        addToolBarButton("Tools/Polygon Stamp...");
//        addMenuButton("Tools/Star Stamp...", "Draw star", KeyEvent.VK_P, "star.png", "onStar");
//        addToolBarButton("Tools/Star Stamp...");
//        addMenuButton("Tools/Eraser...", "Erase area", KeyEvent.VK_E, "eraser.png", "onEraser");
//        addToolBarButton("Tools/Eraser...");
//
//        addMenuButton("Tools/Palette...", "Choose color for tools", KeyEvent.VK_C, "paint.png", "onColor");
//        addToolBarButton("Tools/Palette...");
//
//        addToolBarButton("Color");
//
//        addSubMenu("Help", KeyEvent.VK_H);
//        addMenuButton("Help/About...", "Shows program version and copyright information", KeyEvent.VK_A, "info.png", "onInfo");
//        addToolBarButton("Help/About...");
    }

    public void onInfo(ActionEvent e)
    {
        JOptionPane.showMessageDialog(this, "Paint, version 1.0\n Hello! This is simple paint!\n" +
                "The list of instrument presented below: \n " +
                "Line - draw line with two points\n " +
                "Fill - fill area with one color\n" +
                "Stamps - you can draw two kind of stamps: star and polygon\n" +
                "Also you can change color of tools, save your images wherever you want(supported extension: png) and open your works of art(supported extension: jpg, gif, bmp, png)!\n"+
                "Copyright \u00a9 2022 Julia Trubitsyna, FIT, group 19202", "About Paint", JOptionPane.INFORMATION_MESSAGE);

    }

    private void selectButton(AbstractButton selectButton) {

        if (menuButtonGroup.isSelected(selectButton.getModel())) {
            for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.getActionCommand().equals(selectButton.getActionCommand())) {
                    buttonGroup.setSelected(button.getModel(), true);
                }
            }
        } else {
            for (Enumeration<AbstractButton> buttons = menuButtonGroup.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.getActionCommand().equals(selectButton.getActionCommand())) {
                    menuButtonGroup.setSelected(button.getModel(), true);
                }
            }
        }
    }

    public void onBlackAndWhite(ActionEvent e) {
        imagePanel.changeImage(tools.get(ToolStatus.BLACK_WHITE));
    }


    public void onFill(ActionEvent e) {

    }
    public void onEraser(ActionEvent e) {
    }


    public void onPolygon(ActionEvent e) {

    }
    public void onStar(ActionEvent e) {

    }
    public void onColor(ActionEvent e) {
    }
    public void onChangeSize(ActionEvent e) {

    }
    public void onOpen(ActionEvent event) {
        FileDialog fd = new FileDialog (this, "Open image", FileDialog.LOAD);
        fd.setVisible(true);
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
        FileDialog fd = new FileDialog (this, "Save image", FileDialog.SAVE);
        fd.setFile("Untitled");
        fd.setVisible(true);
        var image = imagePanel.getProcessedImage();
        File file = new File(fd.getDirectory() + fd.getFile() + ".png");
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
