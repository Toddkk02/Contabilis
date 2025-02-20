package GUI;

import Panels.AddReceiptPanel;
import Panels.BalanceTracker;
import models.Receipt;
import networking.DeleteReceipt;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.PieStyler;
import settings.Colors;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import settings.PathManager;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends JFrame implements MouseListener {
    // Main components of the HomePage
    private CardLayout cardLayout;
    private JPanel pages;
    private JPanel dashboard;
    private JPanel receiptsPanel;
    private int indexReceipt;
    private JLabel trashbinIcon;
    private JPanel topinfo;
    private boolean isDeleting = false;

    public HomePage() {
        super("contabilis");

        // Initialize the card layout for page navigation
        cardLayout = new CardLayout();
        pages = new JPanel(cardLayout);

        // Panel for adding new receipts
        JPanel addReceipt = new JPanel();
        addReceipt.setBackground(new Color(240, 248, 255)); // Cornflower blue
        addReceipt.add(new JLabel("Add Receipts Panel"));

        // Main dashboard with grid layout (2 columns)
        this.dashboard = new JPanel(new GridLayout(1, 2, 25, 25));
        this.dashboard.setBackground(new Color(240, 248, 255)); // Cornflower blue

        // Initialize the receipts panel (left side)
        this.receiptsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));
        this.receiptsPanel.setBackground(new Color(240, 248, 255)); // Cornflower blue
        JScrollPane scrollReceipts = new JScrollPane(this.receiptsPanel);

        // Content panel (right side)
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(240, 248, 255)); // Cornflower blue

        ObjectMapper mapper = new ObjectMapper();
        List<Receipt> receipts = new ArrayList<>();
        Map<String, Double> categoryTotals = new HashMap<>();

        try {
            receipts = mapper.readValue(PathManager.getReceiptsPath().toFile(),
                    new TypeReference<List<Receipt>>() {
                    });

            for (Receipt receipt : receipts) {
                if (receipt.getCategory() != null) {
                    categoryTotals.put(receipt.getCategory(),
                            categoryTotals.getOrDefault(receipt.getCategory(), 0.0) + receipt.getAmount());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add panels to the dashboard
        this.dashboard.add(scrollReceipts);
        this.dashboard.add(contentPanel);

        //Create chart
        PieChart chart = new PieChartBuilder()
                .width(200)
                .height(200)
                .title("Category")
                .build();

        // Configura lo stile del grafico
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setLabelsDistance(1.15);
        chart.getStyler().setPlotContentSize(.7);
        chart.getStyler().setStartAngleInDegrees(90);
        chart.getStyler().setLabelType(PieStyler.LabelType.NameAndPercentage);

        XChartPanel<PieChart> chartPanel = new XChartPanel<>(chart);
        contentPanel.add(chartPanel, BorderLayout.CENTER);

        // Aggiungi i dati al grafico
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            chart.addSeries(entry.getKey(), entry.getValue());
        }

        // View all receipts panel
        JPanel viewAllReceipts = new JPanel();
        viewAllReceipts.setBackground(new Color(240, 248, 255)); // Cornflower blue
        viewAllReceipts.add(new JLabel("View All Receipts Panel"));

        // Reports panel
        JPanel finacialTarget = new JPanel();
        finacialTarget.setBackground(new Color(240, 248, 255)); // Cornflower blue
        finacialTarget.add(new JLabel("Reports Panel"));

        // Add all pages to the card layout
        pages.add(dashboard, "DASHBOARD");
        pages.add(new AddReceiptPanel(), "RECEIPTS");
        pages.add(viewAllReceipts, "VIEW_ALL");
        pages.add(new BalanceTracker(), "FINANCIAL_TARGET");

        // Get screen dimensions for responsive design
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screenSize.getWidth();
        final int height = (int) screenSize.getHeight();

        // Initialize navigation bar
        Navbar navbar = new Navbar(this, this, cardLayout, pages);

        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Colors color = new Colors();

        // Setup header/title panel
        JLabel label = new JLabel("Contabilis", SwingConstants.CENTER);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(label);

        Color blueLight = color.LightBlue();
        Color lightGray = color.LightGray();
        Color gold = color.Gold();

        this.getContentPane().setBackground(blueLight);
        label.setOpaque(true);
        label.setForeground(gold);
        label.setBackground(Color.BLACK);
        topPanel.setBackground(lightGray);

        label.setFont(new Font("Arial", Font.BOLD, 36));

        navbar.setOpaque(true);

        // Layout setup
        this.setLayout(new BorderLayout());

        // Add main components to frame
        this.add(topPanel, BorderLayout.NORTH);
        this.add(navbar, BorderLayout.WEST);
        this.add(pages, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        this.setVisible(true);
        DrawTrashBin();

        // Initialize dashboard and show it
        refreshDashboard();
        cardLayout.show(pages, "DASHBOARD");
    }

    public void refreshDashboard() {
        try {
            // Ensure storage directories exist
            PathManager.ensureDirectoriesExist();

            // Read receipts from storage
            ObjectMapper mapper = new ObjectMapper();
            List<Receipt> receipts = mapper.readValue(PathManager.getReceiptsPath().toFile(),
                    new TypeReference<List<Receipt>>() {
                    });

            this.receiptsPanel.removeAll();
            this.indexReceipt = 0;

            // Create visual elements for each receipt
            for (Receipt receipt : receipts) {
                if (receipt.getCategory() != null && receipt.getAmount() != 0 && receipt.getDescription() != null) {
                    // Create receipt panel with BorderLayout for better control
                    JPanel rect = new JPanel(new BorderLayout(0, 0));
                    rect.setPreferredSize(new Dimension(200, 275));
                    rect.setBackground(new Color(240, 248, 255));
                    rect.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                    rect.addMouseListener(this);
                    rect.putClientProperty("index", this.indexReceipt);

                    // Create header info panel
                    JPanel topinfo = new JPanel(new GridLayout(2, 1));
                    topinfo.setBackground(new Color(50, 136, 59));

                    // Center-align the labels
                    JLabel categoryLabel = new JLabel(String.format("Category: %s", receipt.getCategory()), SwingConstants.CENTER);
                    JLabel amountLabel = new JLabel(String.format("Amount: %.2f", receipt.getAmount()), SwingConstants.CENTER);
                    categoryLabel.setForeground(Color.WHITE);
                    amountLabel.setForeground(Color.WHITE);

                    topinfo.add(categoryLabel);
                    topinfo.add(amountLabel);

                    // Create center panel for description
                    JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
                    centerPanel.setBackground(new Color(240, 248, 255));
                    centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    JTextArea descArea = new JTextArea(receipt.getDescription());
                    descArea.setLineWrap(true);
                    descArea.setWrapStyleWord(true);
                    descArea.setEditable(false);
                    descArea.setBackground(new Color(255, 255, 255));

                    JScrollPane scrollPane = new JScrollPane(descArea);
                    scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    centerPanel.add(scrollPane, BorderLayout.CENTER);

                    // Create bottom panel for date with simple format
                    JPanel datePanel = new JPanel(new BorderLayout());
                    datePanel.setBackground(new Color(240, 248, 255));

                    String formattedDate = receipt.getFormattedDate();

                    JLabel dateLabel = new JLabel("Date: " + formattedDate, SwingConstants.CENTER);
                    dateLabel.setForeground(Color.DARK_GRAY);
                    dateLabel.setFont(new Font("Arial", Font.ITALIC, 12));
                    dateLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

                    datePanel.add(dateLabel, BorderLayout.CENTER);

                    // Add all components to the receipt panel
                    rect.add(topinfo, BorderLayout.NORTH);
                    rect.add(centerPanel, BorderLayout.CENTER);
                    rect.add(datePanel, BorderLayout.SOUTH);

                    this.receiptsPanel.add(rect);
                    this.indexReceipt++;
                }
            }

            // Update the chart
            updateChart();

            this.receiptsPanel.revalidate();
            this.receiptsPanel.repaint();

        } catch (Exception e) {
            // Handle errors loading receipts
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Error loading receipts: " + e.getMessage());
            this.receiptsPanel.removeAll();
            this.receiptsPanel.add(errorLabel);
            this.receiptsPanel.revalidate();
            this.receiptsPanel.repaint();
        }
    }

    // Mouse event handlers
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == trashbinIcon) {
            // Toggle delete mode
            isDeleting = !isDeleting;
            for (Component comp : receiptsPanel.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel receiptPanel = (JPanel) comp;
                    for (Component c : receiptPanel.getComponents()) {
                        if (c instanceof JPanel) {
                            JPanel topinfo = (JPanel) c;
                            topinfo.setBackground(isDeleting ? Color.RED : new Color(50, 136, 59));
                        }
                    }
                }
            }
            receiptsPanel.revalidate();
            receiptsPanel.repaint();
        } else if (isDeleting && e.getSource() instanceof JPanel) {
            // Handle receipt deletion
            JPanel panel = (JPanel) e.getSource();
            int index = (int) panel.getClientProperty("index");

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure to delete this?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                DeleteReceipt.deleteReceiptAtIndex(index);
                refreshDashboard();
            }

            // Reset delete mode
            isDeleting = false;
            for (Component comp : receiptsPanel.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel receiptPanel = (JPanel) comp;
                    for (Component c : receiptPanel.getComponents()) {
                        if (c instanceof JPanel) {
                            JPanel topinfo = (JPanel) c;
                            topinfo.setBackground(new Color(50, 136, 59));
                        }
                    }
                }
            }
            receiptsPanel.revalidate();
            receiptsPanel.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    // Handle hover effects
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            JPanel panel = (JPanel) e.getSource();
            if (panel.getClientProperty("index") != null) {
                int index = (int) panel.getClientProperty("index");
                System.out.println("Mouse entered panel " + index);
                panel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            JPanel panel = (JPanel) e.getSource();
            if (panel.getClientProperty("index") != null) {
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            }
        }
    }

    // Initialize and draw the trash bin icon
    public void DrawTrashBin() {
        ImageIcon imageIcon = PathManager.getImageIcon("trashbin.png");
        if (imageIcon != null) {
            Image image = imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            this.trashbinIcon = new JLabel(new ImageIcon(image));
        } else {
            this.trashbinIcon = new JLabel("🗑️");
            this.trashbinIcon.setFont(new Font("Dialog", Font.PLAIN, 40));
        }

        // Create version label
        JLabel textForNameAndVersion = new JLabel("Contabilis 2025 Beta Version");
        textForNameAndVersion.setForeground(Color.orange);

        // Setup trash bin panel
        JPanel trashPanel = new JPanel(new BorderLayout());
        trashPanel.setBackground(Color.darkGray);

        trashPanel.add(textForNameAndVersion, BorderLayout.WEST);
        trashPanel.add(this.trashbinIcon, BorderLayout.EAST);

        this.add(trashPanel, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
        this.trashbinIcon.addMouseListener(this);
    }

    private void updateChart() {
        try {
            JPanel contentPanel = (JPanel) dashboard.getComponent(1);
            contentPanel.removeAll();
            contentPanel.setLayout(new GridBagLayout());

            ObjectMapper mapper = new ObjectMapper();
            List<Receipt> receipts = mapper.readValue(PathManager.getReceiptsPath().toFile(),
                    new TypeReference<List<Receipt>>() {});

            Map<String, Double> categoryTotals = new HashMap<>();
            double grandTotal = 0.0;

            for (Receipt receipt : receipts) {
                if (receipt.getCategory() != null) {
                    categoryTotals.put(receipt.getCategory(),
                            categoryTotals.getOrDefault(receipt.getCategory(), 0.0) + receipt.getAmount());
                    grandTotal += receipt.getAmount();
                }
            }

            PieChart chart = new PieChartBuilder()
                    .width(500)
                    .height(500)
                    .title("Category")
                    .build();

            chart.getStyler().setLegendVisible(true);
            chart.getStyler().setLabelsDistance(1.15);
            chart.getStyler().setPlotContentSize(.7);
            chart.getStyler().setStartAngleInDegrees(90);
            chart.getStyler().setLabelType(PieStyler.LabelType.NameAndPercentage);

            for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                chart.addSeries(entry.getKey(), entry.getValue());
            }

            XChartPanel<PieChart> chartPanel = new XChartPanel<>(chart);
            chartPanel.setPreferredSize(new Dimension(500, 500));

            // GridBagConstraints per il grafico
            GridBagConstraints gbcChart = new GridBagConstraints();
            gbcChart.gridx = 0;
            gbcChart.gridy = 0;
            gbcChart.weightx = 1.0;
            gbcChart.weighty = 0.9;
            gbcChart.anchor = GridBagConstraints.CENTER;
            contentPanel.add(chartPanel, gbcChart);

            // Label per il totale
            JLabel totalLabel = new JLabel("Total Amount: € " + String.format("%.2f", grandTotal));
            totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

            GridBagConstraints gbcLabel = new GridBagConstraints();
            gbcLabel.gridx = 0;
            gbcLabel.gridy = 1;
            gbcLabel.weightx = 1.0;
            gbcLabel.weighty = 0.1;
            gbcLabel.anchor = GridBagConstraints.NORTH;
            contentPanel.add(totalLabel, gbcLabel);

            contentPanel.revalidate();
            contentPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
