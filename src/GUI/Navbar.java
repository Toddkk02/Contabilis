package GUI;

import Panels.AddReceiptPanel;
import settings.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Navigation bar component that handles page switching and user interactions
 */
public class Navbar extends JPanel implements ActionListener {
    // UI Components
    private JButton addReceipt;
    private JButton viewAll;
    private JButton report;
    private JButton dashboard;

    // Navigation control
    private CardLayout cardLayout;
    private JPanel pages;

    // Parent components
    private JFrame parentFrame;
    private HomePage homepage;

    /**
     * Creates a new navigation bar
     * @param homepage Reference to the main HomePage
     * @param parentFrame The parent JFrame container
     * @param cardLayout CardLayout for switching between pages
     * @param pages Panel containing all the pages
     */
    public Navbar(HomePage homepage, JFrame parentFrame, CardLayout cardLayout, JPanel pages) {
        // Initialize fields
        this.cardLayout = cardLayout;
        this.pages = pages;
        this.parentFrame = parentFrame;
        this.homepage = homepage;

        // Set up the navbar panel
        setOpaque(true);
        Colors color = new Colors();
        this.setPreferredSize(new Dimension(150, getHeight()));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(color.Orange());

        // Initialize navigation buttons
        this.dashboard = new JButton("Dashboard");
        this.addReceipt = new JButton("Add Receipt");
        this.viewAll = new JButton("View All Receipts");
        this.report = new JButton("Reports");

        // Center align all buttons
        dashboard.setAlignmentX(Component.CENTER_ALIGNMENT);
        addReceipt.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewAll.setAlignmentX(Component.CENTER_ALIGNMENT);
        report.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add buttons with spacing
        add(Box.createRigidArea(new Dimension(0, 150))); // Top spacing
        add(dashboard);
        add(Box.createRigidArea(new Dimension(0, 150))); // Spacing between buttons
        add(addReceipt);
        add(Box.createRigidArea(new Dimension(0, 150)));
        add(viewAll);
        add(Box.createRigidArea(new Dimension(0, 150)));
        add(report);

        // Add action listeners to handle button clicks
        dashboard.addActionListener(this);
        addReceipt.addActionListener(this);
        viewAll.addActionListener(this);
        report.addActionListener(this);
    }

    /**
     * Handles button click events and switches between pages
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dashboard) {
            cardLayout.show(pages, "DASHBOARD");
            homepage.UpdateDashboard(); // Refresh dashboard content
        } else if (e.getSource() == addReceipt) {
            cardLayout.show(pages, "RECEIPTS");
        } else if (e.getSource() == viewAll) {
            cardLayout.show(pages, "VIEW_ALL");
        } else if (e.getSource() == report) {
            cardLayout.show(pages, "REPORTS");
        }
    }
}