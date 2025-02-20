package Panels;

import models.Receipt;
import org.jdesktop.swingx.JXDatePicker;
import settings.JsonManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddReceiptPanel extends JPanel implements ActionListener {
    private JPopupMenu categoryMenu;
    private JTextField amount;
    private JTextArea description;
    private JButton save;
    private String selectedCategory = "";
    private JButton dropdownButton;
    private JXDatePicker picker;
    private Date date;

    public AddReceiptPanel() {
        // getting screen dimension
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int height = (int) screenSize.getHeight();

        // building the app
        this.picker = new JXDatePicker();
        this.date = Calendar.getInstance().getTime();
        this.picker.setDate(this.date);
        this.picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        this.picker.addActionListener(e -> {
            this.date = this.picker.getDate();
        });

        JLabel categoryLabel = new JLabel("Category: ");
        this.categoryMenu = new JPopupMenu();

        String[] categories = {
                "Daily Expenses",
                "Transport and Mobility",
                "Home and Bills",
                "Entertainment and Leisure",
                "Shopping and Personal Care",
                "Health And Wellness",
                "Education And Learning",
                "Work And Business",
                "Pets",
                "Gifts And Donations"
        };

        for (String category : categories) {
            JMenuItem menuItem = new JMenuItem(category);
            menuItem.addActionListener(e -> {
                selectedCategory = category;
                dropdownButton.setText(category);
            });
            this.categoryMenu.add(menuItem);
        }

        dropdownButton = new JButton("Select Category");
        dropdownButton.setBounds(100, 50, 50, 50);
        dropdownButton.addActionListener(e -> this.categoryMenu.show(dropdownButton, 0, dropdownButton.getHeight()));

        JLabel amountLabel = new JLabel("Amount: ");
        this.amount = new JTextField();
        JLabel descriptionLabel = new JLabel("Description: ");
        this.description = new JTextArea();
        this.save = new JButton("Save");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.amount.setMaximumSize(new Dimension(200, 25));
        this.description.setMaximumSize(new Dimension(400, 200));

        this.save.setMaximumSize(new Dimension(75, 25));
        dropdownButton.setMaximumSize(new Dimension(200, 25));

        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dropdownButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.amount.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.picker.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.description.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.save.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0, 250)));

        this.add(categoryLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(dropdownButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(amountLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(amount);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(this.picker);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(descriptionLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(description);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(save);

        save.addActionListener(this);
    }

    public double saveAmount() {
        return Double.parseDouble(this.amount.getText());
    }

    public String saveCategory() {
        return this.selectedCategory;
    }

    public String saveDescription() {
        return this.description.getText();
    }

    public String saveDate() {
        return this.picker.getDate().toString();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == save) {
            try {
                if (selectedCategory.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please select a category!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String cat = saveCategory();
                double amt = saveAmount();
                String desc = saveDescription();

                // Formatta la data nel formato corretto
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = formatter.format(this.picker.getDate());

                Receipt receipt = new Receipt(cat, amt, desc, formattedDate);

                JsonManager jsonManager = new JsonManager();
                jsonManager.addReceipt(receipt);

                // Reset dei campi
                selectedCategory = "";
                dropdownButton.setText("Select Category");
                amount.setText("");
                description.setText("");
                picker.setDate(Calendar.getInstance().getTime()); // Reset anche della data

                JOptionPane.showMessageDialog(this,
                        "Receipt inserted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Insert a valid Amount!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}