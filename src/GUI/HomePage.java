package GUI;

import settings.Colors;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    public HomePage(){
        super("contabilis");
        // get screen dimension
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screenSize.getWidth();
        final int height = (int) screenSize.getHeight();
        // set window dimension
        this.setSize(width, height);
        // close the app when click the X
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Class with default color
        Colors color = new Colors();

        // title of application top center
        JLabel label = new JLabel("Contabilis", SwingConstants.CENTER);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // adding the label to the window
        topPanel.add(label);

        // set colors for background and title bar
        Color blueLight = color.LightBlue();
        Color lightGray = color.LightGray();
        Color gold = color.Gold();
        //apply the color
        this.getContentPane().setBackground(blueLight);
        // set the color gold
        label.setOpaque(true);
        label.setForeground(gold);
        label.setBackground(Color.BLACK);
        // set the background color blue
        topPanel.setBackground(lightGray);

        label.setFont(new Font("Arial", Font.BOLD, 36));

        //add panel for insert value
        Navbar navbar = new Navbar();
        // Make sure the center panel shows the background
        navbar.setOpaque(true);

        // Add panels to frame
        this.add(topPanel, BorderLayout.NORTH);
        this.add(navbar, BorderLayout.WEST);
        // Center the frame on screen
        setLocationRelativeTo(null);
        this.setVisible(true);
    }
}