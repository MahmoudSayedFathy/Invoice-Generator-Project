package mainPackage.UI;

import javax.swing.*;
import java.awt.*;

public class ItemDialog extends JDialog {
    private JTextField nameFiledItem;
    private JTextField countFieldItem;
    private JTextField priceFieldItem;
    private JLabel nameItemLbl;
    private JLabel countItemLbl;
    private JLabel priceItemLbl;
    private JButton addBtn;
    private JButton cancelBtn;

    public ItemDialog(ParentFrame frame) {
        nameFiledItem = new JTextField(20);
        nameItemLbl = new JLabel("Item Name");

        countFieldItem = new JTextField(20);
        countItemLbl = new JLabel("Item Count");

        priceFieldItem = new JTextField(20);
        priceItemLbl = new JLabel("Item Price");

        addBtn = new JButton("Add");
        cancelBtn = new JButton("Cancel");

        addBtn.setActionCommand("createLine");
        cancelBtn.setActionCommand("cancelCreateLine");

        addBtn.addActionListener(frame.getListener());
        cancelBtn.addActionListener(frame.getListener());
        setLayout(new GridLayout(4, 2));
        setLocation(600,100);
        add(nameItemLbl);
        add(nameFiledItem);
        add(countItemLbl);
        add(countFieldItem);
        add(priceItemLbl);
        add(priceFieldItem);
        add(addBtn);
        add(cancelBtn);
        pack();
    }

    public JTextField getItemNameField() {
        return nameFiledItem;
    }

    public JTextField getItemCountField() {
        return countFieldItem;
    }

    public JTextField getItemPriceField() {
        return priceFieldItem;
    }
}
