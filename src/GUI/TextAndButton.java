package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextAndButton extends JPanel implements ActionListener {
    //variable instances

    private JButton button;
    private JTextField textField;
    private JLabel label;
    public TextAndButton() {
        textField = new JTextField();

        // Use BorderLayout to center the main panel
        setLayout(new BorderLayout());

        // Create inner panel for components with BoxLayout
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        // Add padding to inner panel
        innerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Create components with larger dimensions
        label = new JLabel("Insert value");
        label.setFont(new Font("Arial", Font.BOLD, 24)); // Larger font

        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        // Set preferred size for textfield
        textField.setMaximumSize(new Dimension(400, 40));
        textField.setPreferredSize(new Dimension(400, 40));

        button = new JButton("submit");
        button.setFont(new Font("Arial", Font.BOLD, 20));
        // Set preferred size for button
        button.setMaximumSize(new Dimension(200, 50));
        button.setPreferredSize(new Dimension(200, 50));

        // Center-align all components horizontally
        label.setAlignmentX(CENTER_ALIGNMENT);
        textField.setAlignmentX(CENTER_ALIGNMENT);
        button.setAlignmentX(CENTER_ALIGNMENT);

        // Add components with vertical spacing
        innerPanel.add(Box.createVerticalGlue()); // Flexible space above
        innerPanel.add(label);
        innerPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Fixed space
        innerPanel.add(textField);
        innerPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Fixed space
        innerPanel.add(button);
        innerPanel.add(Box.createVerticalGlue()); // Flexible space below

        // Add inner panel to the center of main panel
        add(innerPanel, BorderLayout.CENTER);

        //listener for the button
        button.addActionListener(this);
        //listener for text if hit enter
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // enter detected
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { // if press enter
                    newWindow();  // new window
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        newWindow();
    }
    public void newWindow(){
        Component[] componentList = this.getComponents();
        for (Component c : componentList){
            this.remove(c);
        }
        this.revalidate();
        this.repaint();
    }

}