package mainPackage.UI;

import javax.swing.*;
import java.awt.*;

public class CreateInvoiceDialog extends JDialog {
    private JTextField customerName;
    private JTextField invDateFiled;
    private JLabel customerNameLbl;
    private JLabel invoiceDateLbl;
    private JButton addBtn;
    private JButton cancelBtn;

    public CreateInvoiceDialog(ParentFrame frame) {
        customerNameLbl = new JLabel("Customer Name:");
        customerName = new JTextField(20);
        invoiceDateLbl = new JLabel("Invoice Date:");
        invDateFiled = new JTextField(20);
        addBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        addBtn.setActionCommand("createInvoice");
        cancelBtn.setActionCommand("CancelCreateInv");
        addBtn.addActionListener(frame.getListener());
        cancelBtn.addActionListener(frame.getListener());
        setLocation(100,100);
        setLayout(new GridLayout(3, 2));
        add(invoiceDateLbl);
        add(invDateFiled);
        add(customerNameLbl);
        add(customerName);
        add(addBtn);
        add(cancelBtn);
        pack();
    }

    public JTextField getCustomerNameField() {
        return customerName;
    }

    public JTextField getInvoiceDateField() {
        return invDateFiled;
    }

}


