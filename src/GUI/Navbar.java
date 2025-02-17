package GUI;

import settings.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Navbar extends JPanel implements ActionListener {
    private CardLayout cardLayout;
    private JPanel cards;
    private JButton addReceipt;
    private JButton viewAll;
    private JButton report;
    private JButton dashboard;
    private JFrame parentFrame;

    public Navbar(JFrame parentFrame, CardLayout cardLayout, JPanel cards) {
        this.cardLayout = cardLayout;
        this.cards = cards;
        this.parentFrame = parentFrame;

        setOpaque(true);
        Colors color = new Colors();

        this.setPreferredSize(new Dimension(150, getHeight()));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(color.Orange());

        // Initialize buttons
        this.dashboard = new JButton("Dashboard");
        this.addReceipt = new JButton("Add Receipt");
        this.viewAll = new JButton("View All Receipts");
        this.report = new JButton("Reports");

        // Center alignment for all buttons
        dashboard.setAlignmentX(Component.CENTER_ALIGNMENT);
        addReceipt.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewAll.setAlignmentX(Component.CENTER_ALIGNMENT);
        report.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add spacing and buttons
        add(Box.createRigidArea(new Dimension(0, 150)));
        add(dashboard);
        add(Box.createRigidArea(new Dimension(0, 150)));
        add(addReceipt);
        add(Box.createRigidArea(new Dimension(0, 150)));
        add(viewAll);
        add(Box.createRigidArea(new Dimension(0, 150)));
        add(report);

        // Add action listeners to all buttons
        dashboard.addActionListener(this);
        addReceipt.addActionListener(this);
        viewAll.addActionListener(this);
        report.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dashboard) {
            cardLayout.show(cards, "DASHBOARD");
        } else if (e.getSource() == addReceipt) {
            cardLayout.show(cards, "RECEIPTS");
        } else if (e.getSource() == viewAll) {
            cardLayout.show(cards, "VIEW_ALL");
        } else if (e.getSource() == report) {
            cardLayout.show(cards, "REPORTS");
        }
    }
}