package GUI;

import Panels.AddReceiptPanel;
import settings.Colors;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    private CardLayout cardLayout;
    private JPanel pages;

    public HomePage() {
        super("contabilis");

        // initialize cardlayout
        cardLayout = new CardLayout();

        // create the main panel for Card layout
        pages = new JPanel(cardLayout);

        // panel for add receipt
        JPanel addReceipt = new JPanel();
        addReceipt.setBackground(new Color(240, 240, 240));
        addReceipt.add(new JLabel("Add Receipts Panel"));

        // panel for dashboard
        JPanel dashboard = new JPanel();
        dashboard.setBackground(new Color(240, 240, 240));
        dashboard.add(new JLabel("Dashboard Panel"));

        // panel for view all receipts
        JPanel viewAllReceipts = new JPanel();
        viewAllReceipts.setBackground(new Color(240, 240, 240));
        viewAllReceipts.add(new JLabel("View All Receipts Panel"));

        // panel for reports
        JPanel reports = new JPanel();
        reports.setBackground(new Color(240, 240, 240));
        reports.add(new JLabel("Reports Panel"));

        // add pages with correct IDs
        pages.add(dashboard, "DASHBOARD");
        pages.add(new AddReceiptPanel(), "RECEIPTS");
        pages.add(viewAllReceipts, "VIEW_ALL");
        pages.add(reports, "REPORTS");

        // get screen dimension
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screenSize.getWidth();
        final int height = (int) screenSize.getHeight();

        // add navbar
        Navbar navbar = new Navbar(this, cardLayout, pages);

        // set window dimension
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Class with default color
        Colors color = new Colors();

        // title of application top center
        JLabel label = new JLabel("Contabilis", SwingConstants.CENTER);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(label);

        // set colors
        Color blueLight = color.LightBlue();
        Color lightGray = color.LightGray();
        Color gold = color.Gold();

        // apply colors
        this.getContentPane().setBackground(blueLight);
        label.setOpaque(true);
        label.setForeground(gold);
        label.setBackground(Color.BLACK);
        topPanel.setBackground(lightGray);

        label.setFont(new Font("Arial", Font.BOLD, 36));

        // Make sure the center panel shows the background
        navbar.setOpaque(true);

        // Set up the layout
        this.setLayout(new BorderLayout());

        // Add components to frame
        this.add(topPanel, BorderLayout.NORTH);
        this.add(navbar, BorderLayout.WEST);
        this.add(pages, BorderLayout.CENTER);  // Add the pages panel to the center

        // Center the frame on screen
        setLocationRelativeTo(null);
        this.setVisible(true);
    }
}