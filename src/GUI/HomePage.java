package GUI;

import Panels.AddReceiptPanel;
import models.Receipt;
import networking.DeleteReceipt;
import settings.Colors;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import settings.PathManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Paths;
import java.util.List;

public class HomePage extends JFrame implements MouseListener {
    private CardLayout cardLayout;
    private JPanel pages;
    private JPanel dashboard;
    private int indexReceipt;
    private JLabel trashbinIcon;
    private JPanel topinfo;
    private boolean isDeleting = false;

    public HomePage() {
        super("contabilis");

        cardLayout = new CardLayout();
        pages = new JPanel(cardLayout);

        JPanel addReceipt = new JPanel();
        addReceipt.setBackground(new Color(240, 240, 240));
        addReceipt.add(new JLabel("Add Receipts Panel"));

        this.dashboard = new JPanel();
        this.dashboard.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 25));
        this.dashboard.setBackground(new Color(240, 240, 240));

        JPanel viewAllReceipts = new JPanel();
        viewAllReceipts.setBackground(new Color(240, 240, 240));
        viewAllReceipts.add(new JLabel("View All Receipts Panel"));

        JPanel reports = new JPanel();
        reports.setBackground(new Color(240, 240, 240));
        reports.add(new JLabel("Reports Panel"));

        pages.add(dashboard, "DASHBOARD");
        pages.add(new AddReceiptPanel(), "RECEIPTS");
        pages.add(viewAllReceipts, "VIEW_ALL");
        pages.add(reports, "REPORTS");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screenSize.getWidth();
        final int height = (int) screenSize.getHeight();

        Navbar navbar = new Navbar(this, this, cardLayout, pages);

        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Colors color = new Colors();

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

        this.setLayout(new BorderLayout());

        this.add(topPanel, BorderLayout.NORTH);
        this.add(navbar, BorderLayout.WEST);
        this.add(pages, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        this.setVisible(true);
        DrawTrashBin();
        //set default page
        UpdateDashboard();
        cardLayout.show(pages, "DASHBOARD");
    }

    public void UpdateDashboard() {
        try {
            // Assicurati che il file esista
            PathManager.ensureDirectoriesExist();

            ObjectMapper mapper = new ObjectMapper();
            List<Receipt> receipts = mapper.readValue(PathManager.getReceiptsPath().toFile(),
                    new TypeReference<List<Receipt>>() {});

            this.dashboard.removeAll();
            this.indexReceipt = 0;

            for (Receipt receipt : receipts) {
                if (receipt.getCategory() != null && receipt.getAmount() != 0 && receipt.getDescription() != null) {
                    // resto del codice rimane uguale
                    JPanel rect = new JPanel();
                    rect.setLayout(new BoxLayout(rect, BoxLayout.Y_AXIS));
                    rect.setPreferredSize(new Dimension(200, 275));
                    rect.setBackground(new Color(240, 240, 240));
                    rect.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                    rect.addMouseListener(this);

                    JPanel topinfo = new JPanel();
                    topinfo.setLayout(new FlowLayout());
                    topinfo.setBackground(new Color(50, 136, 59));
                    topinfo.setMinimumSize(new Dimension(200, 50));
                    topinfo.setMaximumSize(new Dimension(200, 50));
                    topinfo.setPreferredSize(new Dimension(200, 50));

                    topinfo.add(new JLabel(String.format("Category: %s", receipt.getCategory())));
                    topinfo.add(new JLabel(String.format("Amount: %.2f", receipt.getAmount())));

                    rect.putClientProperty("index", this.indexReceipt);

                    JTextArea descArea = new JTextArea(receipt.getDescription());
                    descArea.setLineWrap(true);
                    descArea.setWrapStyleWord(true);
                    descArea.setEditable(false);

                    JScrollPane scrollPane = new JScrollPane(descArea);
                    scrollPane.setPreferredSize(new Dimension(180, 80));

                    rect.add(topinfo);
                    rect.add(scrollPane);
                    this.dashboard.add(rect);
                    this.indexReceipt++;
                }
            }

            this.dashboard.revalidate();
            this.dashboard.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Error loading receipts: " + e.getMessage());
            this.dashboard.removeAll();
            this.dashboard.add(errorLabel);
            this.dashboard.revalidate();
            this.dashboard.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == trashbinIcon) {
            isDeleting = !isDeleting;
            for(Component comp : dashboard.getComponents()) {
                if(comp instanceof JPanel) {
                    JPanel receiptPanel = (JPanel)comp;
                    for(Component c : receiptPanel.getComponents()) {
                        if(c instanceof JPanel) {
                            JPanel topinfo = (JPanel)c;
                            topinfo.setBackground(isDeleting ? Color.RED : new Color(50, 136, 59));
                        }
                    }
                }
            }
            dashboard.revalidate();
            dashboard.repaint();
        } else if(isDeleting && e.getSource() instanceof JPanel) {
            JPanel panel = (JPanel) e.getSource();
            int index = (int) panel.getClientProperty("index");

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure to delete this?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if(choice == JOptionPane.YES_OPTION) {
                DeleteReceipt.deleteReceiptAtIndex(index);
                UpdateDashboard();
            }

            isDeleting = false;
            for(Component comp : dashboard.getComponents()) {
                if(comp instanceof JPanel) {
                    JPanel receiptPanel = (JPanel)comp;
                    for(Component c : receiptPanel.getComponents()) {
                        if(c instanceof JPanel) {
                            JPanel topinfo = (JPanel)c;
                            topinfo.setBackground(new Color(50, 136, 59));
                        }
                    }
                }
            }
            dashboard.revalidate();
            dashboard.repaint();
        }

    }
    @Override
    public void mousePressed(MouseEvent e) {
        //pass
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //pass
    }

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

    public void DrawTrashBin() {
        ImageIcon imageIcon = PathManager.getImageIcon("trashbin.png");  // Rimosso "image/" dal percorso
        if (imageIcon != null) {
            Image image = imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            this.trashbinIcon = new JLabel(new ImageIcon(image));
        } else {
            // Fallback if image cannot be loaded
            this.trashbinIcon = new JLabel("🗑️");  // Unicode trash bin symbol as fallback
            this.trashbinIcon.setFont(new Font("Dialog", Font.PLAIN, 40));
        }

        JLabel textForNameAndVersion = new JLabel("Contabilis 2025 Beta Version");
        textForNameAndVersion.setForeground(Color.orange);

        JPanel trashPanel = new JPanel(new BorderLayout());
        trashPanel.setBackground(Color.darkGray);

        trashPanel.add(textForNameAndVersion, BorderLayout.WEST);
        trashPanel.add(this.trashbinIcon, BorderLayout.EAST);

        this.add(trashPanel, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
        this.trashbinIcon.addMouseListener(this);
    }
}