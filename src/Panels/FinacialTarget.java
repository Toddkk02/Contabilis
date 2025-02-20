package Panels;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FinacialTarget extends JPanel implements ActionListener {
    private CardLayout cardLayout;
    private JPanel pages;
    private JPanel mainPage;
    private JTextField finacialGoal;
    private JButton submit;
    private double salary;
    private GridBagConstraints gbc;

    public FinacialTarget() {
        // Initialize main layout
        setLayout(new BorderLayout());

        // Initialize card layout
        cardLayout = new CardLayout();
        pages = new JPanel(cardLayout);

        // Create and configure main page
        this.mainPage = new JPanel(new GridBagLayout());
        mainPage.setBackground(new Color(240, 248, 255));

        // Create and configure components
        JLabel labelGoal = new JLabel("Insert your Salary");
        labelGoal.setFont(new Font("Arial", Font.BOLD, 14));
        JXDatePicker picker = new JXDatePicker();

        this.finacialGoal = new JTextField();

        this.finacialGoal.setPreferredSize(new Dimension(150, 30));

        this.submit = new JButton("Submit");


        // Use GridBagConstraints for better layout
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPage.add(labelGoal, gbc);

        gbc.gridy = 1;
        mainPage.add(finacialGoal, gbc);

        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        gbc.gridy = 2;
        mainPage.add(picker, gbc);


        gbc.gridy = 3;
        mainPage.add(submit, gbc);

        submit.addActionListener(this);

        // Add main page to card layout
        pages.add(mainPage, "MAIN");

        // Add pages panel to main component
        add(pages, BorderLayout.CENTER);

        // Show main page
        cardLayout.show(pages, "MAIN");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {

        }

    }
}