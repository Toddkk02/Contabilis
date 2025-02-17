package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddReceiptPanel extends JPanel implements ActionListener {
    private JTextField category;
    private JTextField amount;
    private JTextArea description;
    private JButton save;

    public AddReceiptPanel() {
        // getting screen dimension
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int height = (int) screenSize.getHeight();
        // building the app
         JLabel categoryLabel = new JLabel("Category: ");
        this.category = new JTextField();
        JLabel amountLabel = new JLabel("Amount: ");
         this.amount = new JTextField();
        JLabel descriptionLabel = new JLabel("Description: ");
         this.description = new JTextArea();
         this.save = new JButton("Save");

        //placing component in y order
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        this.category.setMaximumSize(new Dimension(200, 25));
        this.amount.setMaximumSize(new Dimension(200, 25));
        this.description.setMaximumSize(new Dimension(400, 200));
        this.save.setMaximumSize(new Dimension(75, 25));

        //placing the component
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.category.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.amount.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.description.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.save.setAlignmentX(Component.CENTER_ALIGNMENT);
        //adding the component

        //padding of 250px
        add(Box.createRigidArea(new Dimension(0, 250)));

        this.add(categoryLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(category);
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
    public double saveAmount(){
        return Double.parseDouble(this.amount.getText());
    }
    public String saveCategory(){
        return this.category.getText();
    }
    public String saveDescription(){
        return this.description.getText();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == save){
            saveCategory();
            saveAmount();
            saveDescription();
            System.out.println(saveCategory());
            System.out.println(saveAmount());
            System.out.println(saveDescription());

        }

    }




}

