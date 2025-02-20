package Panels;

import models.Receipt;
import models.Target;
import org.jdesktop.swingx.JXDatePicker;
import settings.JsonManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class BalanceTracker extends JPanel implements ActionListener {
    private CardLayout cardLayout;
    private JPanel pages;
    private JPanel inputPage;
    private JPanel statsPage;
    private JTextField initialBalance;
    private JButton submit;
    private JsonManager jsonManager;

    public BalanceTracker() {
        jsonManager = new JsonManager();
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        pages = new JPanel(cardLayout);

        createInputPage();
        createStatsPage();

        add(pages, BorderLayout.CENTER);
        checkAndShowAppropriateView();
    }

    public void checkAndShowAppropriateView() {
        Target balance = jsonManager.getTarget();
        if (balance != null) {
            updateStatsPage();
            cardLayout.show(pages, "STATS");
        } else {
            cardLayout.show(pages, "INPUT");
        }
    }

    private void createInputPage() {
        inputPage = new JPanel(new GridBagLayout());
        inputPage.setBackground(new Color(240, 248, 255));

        JLabel labelBalance = new JLabel("Enter your current balance");
        labelBalance.setFont(new Font("Arial", Font.BOLD, 14));

        initialBalance = new JTextField();
        initialBalance.setPreferredSize(new Dimension(150, 30));

        submit = new JButton("Submit");
        submit.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        inputPage.add(labelBalance, gbc);

        gbc.gridy = 1;
        inputPage.add(initialBalance, gbc);

        gbc.gridy = 2;
        inputPage.add(submit, gbc);

        pages.add(inputPage, "INPUT");
    }

    private void createStatsPage() {
        statsPage = new JPanel(new GridBagLayout());
        statsPage.setBackground(new Color(240, 248, 255));
        pages.add(statsPage, "STATS");
    }

    private void updateStatsPage() {
        statsPage.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Target balance = jsonManager.getTarget();
        List<Receipt> receipts = jsonManager.getReceipts();

        double totalExpenses = 0;
        for (Receipt receipt : receipts) {
            // Consider all receipts as expenses
            totalExpenses += receipt.getAmount();
        }

        double initialAmount = balance.getFinancialGoal();
        double currentBalance = initialAmount - Math.abs(totalExpenses);
        double spentPercentage = (Math.abs(totalExpenses) / initialAmount) * 100;

        // Labels
        JLabel initialBalanceLabel = new JLabel(String.format("Initial Balance: €%.2f", initialAmount));
        JLabel expensesLabel = new JLabel(String.format("Total Expenses: €%.2f", Math.abs(totalExpenses)));
        JLabel currentBalanceLabel = new JLabel(String.format("Current Balance: €%.2f", currentBalance));

        // Progress/spent percentage with color coding
        JLabel spentLabel = new JLabel(String.format("Spent: %.1f%%", spentPercentage));
        spentLabel.setFont(new Font("Arial", Font.BOLD, 16));
        if (spentPercentage >= 90) {
            spentLabel.setForeground(new Color(150, 0, 0));  // Red if spent >= 90%
        } else if (spentPercentage >= 75) {
            spentLabel.setForeground(new Color(255, 165, 0));  // Orange if spent >= 75%
        } else {
            spentLabel.setForeground(new Color(0, 150, 0));  // Green if spent < 75%
        }

        // Style
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        initialBalanceLabel.setFont(labelFont);
        expensesLabel.setFont(labelFont);
        currentBalanceLabel.setFont(labelFont);

        expensesLabel.setForeground(new Color(150, 0, 0));
        if (currentBalance < 0) {
            currentBalanceLabel.setForeground(new Color(150, 0, 0));
        } else {
            currentBalanceLabel.setForeground(new Color(0, 150, 0));
        }

        // Add components
        statsPage.add(initialBalanceLabel, gbc);

        gbc.gridy++;
        statsPage.add(expensesLabel, gbc);

        gbc.gridy++;
        statsPage.add(currentBalanceLabel, gbc);

        gbc.gridy++;
        statsPage.add(spentLabel, gbc);

        // Reset button
        JButton resetButton = new JButton("Reset Balance");
        resetButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to reset your balance tracking?",
                    "Confirm Reset",
                    JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                jsonManager.setTarget(null);
                cardLayout.show(pages, "INPUT");
            }
        });

        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        statsPage.add(resetButton, gbc);

        statsPage.revalidate();
        statsPage.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            try {
                double initialAmount = Double.parseDouble(initialBalance.getText().replace(",", "."));
                if (initialAmount <= 0) {
                    throw new NumberFormatException();
                }

                Target balance = new Target(
                        new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()),
                        initialAmount
                );
                jsonManager.setTarget(balance);

                updateStatsPage();
                cardLayout.show(pages, "STATS");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid positive number",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}