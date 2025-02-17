package GUI;

import javax.swing.*;
import java.awt.*;

public class GuiBase extends JFrame {
    public GuiBase(){
        super("contabilis");
        // get screen dimension
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screenSize.getWidth();
        final int height = (int) screenSize.getHeight();
        // set window dimension
        this.setSize(width, height);
        // close the app when click the X
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // title of application top center
        JLabel label = new JLabel("Contabilis", SwingConstants.CENTER);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // adding the label to the window
        topPanel.add(label);

        this.add(topPanel, BorderLayout.NORTH);
        // set colors for background and title bar
        Color blueLight = new Color(163, 205, 248);
        Color lightGray = new Color(211, 211, 211);
        //apply the color
        this.getContentPane().setBackground(blueLight);
        label.setOpaque(true);
        topPanel.setBackground(lightGray);

        //add panel for insert value
        this.add(new TextAndButton(), BorderLayout.CENTER);

        // Center the frame on screen
        setLocationRelativeTo(null);

        this.setVisible(true);


    }

}
