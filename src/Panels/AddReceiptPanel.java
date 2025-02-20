package Panels;

import models.Receipt;
import settings.JsonManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddReceiptPanel extends JPanel implements ActionListener {
    private JPopupMenu categoryMenu;
    private JTextField amount;
    private JTextArea description;
    private JButton save;
    private String selectedCategory = ""; // Aggiunta questa variabile per memorizzare la categoria
    private JButton dropdownButton; // Aggiunto come campo della classe

    public AddReceiptPanel() {
        // getting screen dimension
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int height = (int) screenSize.getHeight();

        // building the app
        JLabel categoryLabel = new JLabel("Category: ");
        this.categoryMenu = new JPopupMenu();

        // Array di categorie per rendere il codice più pulito
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

        // Creazione dei menu items con gli action listener
        for (String category : categories) {
            JMenuItem menuItem = new JMenuItem(category);
            menuItem.addActionListener(e -> {
                selectedCategory = category;
                dropdownButton.setText(category); // Aggiorna il testo del bottone
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

        //placing component in y order
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.amount.setMaximumSize(new Dimension(200, 25));
        this.description.setMaximumSize(new Dimension(400, 200));
        this.save.setMaximumSize(new Dimension(75, 25));
        dropdownButton.setMaximumSize(new Dimension(200, 25));

        //placing the component
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dropdownButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.amount.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.description.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.save.setAlignmentX(Component.CENTER_ALIGNMENT);

        //padding of 250px
        add(Box.createRigidArea(new Dimension(0, 250)));

        this.add(categoryLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(dropdownButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(amountLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(amount);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(descriptionLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(description);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(save);

        //add listener
        save.addActionListener(this);
    }

    public double saveAmount() {
        return Double.parseDouble(this.amount.getText());
    }

    public String saveCategory() {
        return this.selectedCategory; // Restituisce la categoria selezionata
    }

    public String saveDescription() {
        return this.description.getText();
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

                //create an object Receipt
                Receipt receipt = new Receipt(cat, amt, desc);

                // save in JSON
                JsonManager jsonManager = new JsonManager();
                jsonManager.addReceipt(receipt);

                // clear the form
                selectedCategory = "";
                dropdownButton.setText("Select Category");
                amount.setText("");
                description.setText("");

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