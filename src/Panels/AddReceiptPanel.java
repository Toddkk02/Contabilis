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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int height = (int) screenSize.getHeight();

        this.picker = new JXDatePicker();
        this.date = Calendar.getInstance().getTime();
        this.picker.setDate(this.date);
        this.picker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        this.picker.addActionListener(e -> {
            this.date = this.picker.getDate();
        });

        JLabel categoryLabel = new JLabel("Categoria: ");
        this.categoryMenu = new JPopupMenu();

        String[] categories = {
                "Spese Quotidiane",
                "Trasporti e Mobilità",
                "Casa e Bollette",
                "Intrattenimento e Svago",
                "Shopping e Cura Personale",
                "Salute e Benessere",
                "Istruzione e Formazione",
                "Lavoro e Business",
                "Animali",
                "Regali e Donazioni",
                "Altro"
        };

        for (String category : categories) {
            JMenuItem menuItem = new JMenuItem(category);
            menuItem.addActionListener(e -> {
                selectedCategory = category;
                dropdownButton.setText(category);
            });
            this.categoryMenu.add(menuItem);
        }

        dropdownButton = new JButton("Seleziona Categoria");
        dropdownButton.setBounds(100, 50, 50, 50);
        dropdownButton.addActionListener(e -> this.categoryMenu.show(dropdownButton, 0, dropdownButton.getHeight()));

        JLabel amountLabel = new JLabel("Importo: ");
        this.amount = new JTextField();
        JLabel descriptionLabel = new JLabel("Descrizione: ");
        this.description = new JTextArea();
        this.save = new JButton("Salva");

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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == save) {
            try {
                if (selectedCategory.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Seleziona una categoria!",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double amt = Double.parseDouble(amount.getText().replace(",", "."));
                String desc = description.getText();

                // Formatta la data nel formato corretto
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = outputFormat.format(this.picker.getDate());

                Receipt receipt = new Receipt(selectedCategory, amt, desc, formattedDate);

                JsonManager jsonManager = new JsonManager();
                jsonManager.addReceipt(receipt);

                // Reset dei campi
                selectedCategory = "";
                dropdownButton.setText("Seleziona Categoria");
                amount.setText("");
                description.setText("");
                picker.setDate(Calendar.getInstance().getTime());

                JOptionPane.showMessageDialog(this,
                        "Ricevuta inserita con successo!",
                        "Successo",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Inserisci un importo valido!",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}