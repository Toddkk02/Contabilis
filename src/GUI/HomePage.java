package GUI;

import Panels.AddReceiptPanel;
import models.Receipt;
import networking.PopulateDashboard;
import settings.Colors;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Main window of the application that contains all the panels
 */
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

        // panel for dashboard with FlowLayout
        JPanel dashboard = new JPanel();
        dashboard.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 25));  // padding 25px
        dashboard.setBackground(new Color(240, 240, 240));
        // Get receipts data
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Receipt> receipts = mapper.readValue(Paths.get("/home/alessandro/IdeaProjects/Contabilita/receipts.json").toFile(),
                    new TypeReference<List<Receipt>>() {});
            // a counter for receipt
            int numbOfReceipt = 0;
            // Create a rectangle for each valid receipt
            for (Receipt receipt : receipts) {
                if (receipt.getCategory() != null && receipt.getAmount() != 0 && receipt.getDescription() != null) {
                    // Create and setup rectangle
                    JPanel rect = new JPanel();
                    rect.setLayout(new BoxLayout(rect, BoxLayout.Y_AXIS));  // Importante: usa BoxLayout
                    rect.setPreferredSize(new Dimension(200, 150));
                    rect.setBackground(new Color(240, 240, 240));
                    rect.setBorder(BorderFactory.createLineBorder(Color.black, 1));

                    // Top info panel
                    JPanel topinfo = new JPanel();
                    topinfo.setBackground(new Color(50, 136, 59));
                    topinfo.add(new JLabel(String.format("Category: %s", receipt.getCategory())));
                    topinfo.add(new JLabel(String.format("Amount: %.2f", receipt.getAmount())));

                    // Description area with scroll
                    JTextArea descArea = new JTextArea(receipt.getDescription());
                    descArea.setLineWrap(true);
                    descArea.setWrapStyleWord(true);
                    descArea.setEditable(false);

                    JScrollPane scrollPane = new JScrollPane(descArea);
                    scrollPane.setPreferredSize(new Dimension(180, 80));

                    rect.add(topinfo);
                    rect.add(scrollPane);

                    // Add rectangle to dashboard
                    dashboard.add(rect);
                    numbOfReceipt++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Error loading receipts");
            dashboard.add(errorLabel);
        }

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