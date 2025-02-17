package GUI;

import settings.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Navbar extends JPanel implements ActionListener {
    //variable instances
    private JButton button;
    private JLabel label;
    private JPanel panel;
    public Navbar() {
        setOpaque(true);
        Colors color = new Colors();
        // making the navbar to the left
        this.setPreferredSize(new Dimension(150, getHeight())); //100 px of width
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // layout in vertical
        this.setBackground(color.Orange());
        button = new JButton("Dashboard");
        ImageIcon originalIcon = new ImageIcon("/home/alessandro/IdeaProjects/Contabilita/src/image/dashboard.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        label = new JLabel(scaledIcon);
        //Center the component in box Layout
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(label);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(button);
        button.addActionListener(this);
    }
    public void newWindow(){
        Component[] componentList = this.getComponents();
        for (Component c : componentList){
            this.remove(c);
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}