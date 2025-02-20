package GUI;

import Panels.AddReceiptPanel;
import Panels.BalanceTracker;
import settings.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Navbar extends JPanel implements ActionListener {
    private JButton addReceipt;
    private JButton viewAll;
    private JButton balanceTracker;
    private JButton dashboard;
    private CardLayout cardLayout;
    private JPanel pages;
    private JFrame parentFrame;
    private HomePage homepage;
    private BalanceTracker balanceTrackerPanel;

    public Navbar(HomePage homepage, JFrame parentFrame, CardLayout cardLayout, JPanel pages) {
        this.cardLayout = cardLayout;
        this.pages = pages;
        this.parentFrame = parentFrame;
        this.homepage = homepage;
        this.balanceTrackerPanel = new BalanceTracker();

        setOpaque(true);
        Colors color = new Colors();
        this.setPreferredSize(new Dimension(150, getHeight()));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(color.Orange());

        this.dashboard = new JButton("Dashboard");
        this.addReceipt = new JButton("Add Receipt");
        this.viewAll = new JButton("View All Receipts");
        this.balanceTracker = new JButton("Balance Tracker");

        dashboard.setAlignmentX(Component.CENTER_ALIGNMENT);
        addReceipt.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewAll.setAlignmentX(Component.CENTER_ALIGNMENT);
        balanceTracker.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0, 150)));
        add(dashboard);
        add(Box.createRigidArea(new Dimension(0, 150)));
        add(addReceipt);
        add(Box.createRigidArea(new Dimension(0, 150)));
        add(viewAll);
        add(Box.createRigidArea(new Dimension(0, 150)));
        add(balanceTracker);

        dashboard.addActionListener(this);
        addReceipt.addActionListener(this);
        viewAll.addActionListener(this);
        balanceTracker.addActionListener(this);

        // Add the balance tracker panel to the pages
        pages.add(balanceTrackerPanel, "BALANCE_TRACKER");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dashboard) {
            homepage.refreshDashboard();
            // Also refresh the balance tracker data
            balanceTrackerPanel.checkAndShowAppropriateView();
            cardLayout.show(pages, "DASHBOARD");
        } else if (e.getSource() == addReceipt) {
            cardLayout.show(pages, "RECEIPTS");
        } else if (e.getSource() == viewAll) {
            cardLayout.show(pages, "VIEW_ALL");
        } else if (e.getSource() == balanceTracker) {
            balanceTrackerPanel.checkAndShowAppropriateView();
            cardLayout.show(pages, "BALANCE_TRACKER");
        }
    }
}