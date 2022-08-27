package mainPackage.UI;


import mainPackage.actionsListner.ActionListner;
import mainPackage.model.InvoiceHeader;
import mainPackage.model.InvoiceHeaderTableModule;
import mainPackage.model.InvoiceLineTableModule;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ParentFrame extends JFrame {

    private JMenuBar mainMenu;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private JMenu fileMenu;
    private JMenuItem loadFileItem;
    private JMenuItem saveFileItem;

    private String[][] customerData={
    };
    private static JTable invoiceTable;

    private JButton createInvoiceBtn;
    private JButton deleteInvoiceBtn;
    private Border border;
    private JPanel invoiceTablePanel;

    private String[] customerTableColumns= {"No.","Date","Customer","Total"};
    private JPanel invoiceDataPanel;
    private JPanel invoiceItemsPanel;
    private JPanel buttonsPanel;

    private JLabel invoiceNumberLbl;
    private JTextField invoiceDateTF;
    private JTextField customerNameTF;

    private JLabel invoiceTotalLbl;

    private Border titleBorder;

    String [] itemTableColumns= {"No.","Item Name","Item Price","Count","Item Total"};
    String [][] itemData={};
    private JTable invoiceItemsTable;
    private JButton saveBtn;
    private JButton cancelBtn;


    private ActionListner listener ;
    private ArrayList<InvoiceHeader> invoiceHeaderArray;
    private InvoiceHeaderTableModule invoiceHeaderTableModule;
    private InvoiceLineTableModule invoiceLineTableModule;
    private List<ListSelectionModel> models = new ArrayList<>();

    public List<ListSelectionModel> getModels() {
        return models;
    }

    public void register (ListSelectionModel model){
        models.add(model);
        model.addListSelectionListener(listener);
    }
    public void register(JTable t){
        register(t.getSelectionModel());
    }


    public ArrayList<InvoiceHeader> getInvoiceHeaderArray() {
        if (invoiceHeaderArray == null) invoiceHeaderArray = new ArrayList<>();
        return invoiceHeaderArray;
    }
    public void setInvoiceHeaderArray(ArrayList<InvoiceHeader> invoiceHeaderArray) {
        this.invoiceHeaderArray = invoiceHeaderArray;
        invoiceHeaderTableModule = new InvoiceHeaderTableModule(invoiceHeaderArray);
        this.invoiceTable.setModel(invoiceHeaderTableModule);
    }

    public InvoiceLineTableModule getInvoiceLineTableModule() {
        return invoiceLineTableModule;
    }
    public InvoiceHeaderTableModule getInvoiceHeaderTableModule() {
        if (invoiceHeaderTableModule == null) {
            invoiceHeaderTableModule = new InvoiceHeaderTableModule(getInvoiceHeaderArray());
        }
        return invoiceHeaderTableModule;
    }

    public ActionListner getListener() {
        return listener;
    }

    public JTable getInvoiceTable() {
        return invoiceTable;
    }

    public JTable getInvoiceItemsTable() {
        return invoiceItemsTable;
    }

    ParentFrame(){
        super("Sales Invoice App");
        listener = new ActionListner(this);
        
        setSize(1100,900);
        setLayout(new GridLayout(1,2,10,10));
        setLocation(150,70);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // add menuBar to frame
        mainMenu = new JMenuBar();
        setJMenuBar(mainMenu);
        fileMenu = new JMenu("File");
        loadFileItem=new JMenuItem("Load File",'l');
        loadFileItem.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.CTRL_DOWN_MASK));
        loadFileItem.setActionCommand("loadFile");
        loadFileItem.addActionListener(listener);

        saveFileItem=new JMenuItem("Save File",'s');
        saveFileItem.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK));
        saveFileItem.setActionCommand("saveFile");
        saveFileItem.addActionListener(listener);
        fileMenu.add(loadFileItem);
        fileMenu.add(saveFileItem);
        mainMenu.add(fileMenu);

        // Creating the left Panel

        leftPanel = new JPanel();
        leftPanel.setSize(500,700);
        leftPanel.setVisible(true);

        invoiceTable = new JTable(customerData,customerTableColumns);
        DefaultTableModel invModel = new DefaultTableModel();
        invModel.setColumnIdentifiers(customerTableColumns);
        models.add(invoiceTable.getSelectionModel());

        invoiceTable.setModel(invModel);
        invoiceTable.getSelectionModel().addListSelectionListener(listener);

        border = BorderFactory.createTitledBorder("Invoice Table");

        invoiceTablePanel=new JPanel();

        invoiceTablePanel.add(new JScrollPane(invoiceTable));
        invoiceTablePanel.setVisible(true);
        invoiceTablePanel.setBorder(border);

        leftPanel.add(invoiceTablePanel);

        createInvoiceBtn = new JButton("Create New Invoice");
        createInvoiceBtn.setActionCommand("createInvBtn");
        createInvoiceBtn.addActionListener(listener);
        leftPanel.add(createInvoiceBtn);

        deleteInvoiceBtn = new JButton("Delete Invoice");
        deleteInvoiceBtn.setActionCommand("deleteInvBtn");
        deleteInvoiceBtn.addActionListener(listener);
        leftPanel.add(deleteInvoiceBtn);

        add(leftPanel);

        // Creating the right Panel

        rightPanel = new JPanel();
        rightPanel.setVisible(true);
        rightPanel.setLayout(new GridLayout(3,1,5,5));
        rightPanel.setSize(500,700);
        invoiceDataPanel= new JPanel();
        invoiceDataPanel.setLayout(new GridLayout(4,2,-100,10));

        invoiceDataPanel.add(new JLabel("Invoice Number "));
        invoiceNumberLbl = new JLabel();
        invoiceNumberLbl.setText("");
        invoiceDataPanel.add(invoiceNumberLbl);

        invoiceDataPanel.add(new JLabel("Customer Name"));
        invoiceDateTF = new JTextField(20);

        invoiceDataPanel.add(invoiceDateTF);
        invoiceDataPanel.add(new JLabel("Invoice Date"));
        customerNameTF = new JTextField(20);
        invoiceDataPanel.add(customerNameTF);

        invoiceDataPanel.add(new JLabel("Invoice Total"));
        invoiceTotalLbl =new JLabel("");
        invoiceDataPanel.add(invoiceTotalLbl);
        rightPanel.add(invoiceDataPanel);


        invoiceItemsPanel = new JPanel();
        invoiceItemsPanel.setVisible(true);
        titleBorder =  BorderFactory.createTitledBorder("Invoice Items");
        invoiceItemsPanel.setBorder(titleBorder);
        invoiceItemsPanel.setLayout(new FlowLayout());
        invoiceItemsTable = new JTable(itemData,itemTableColumns);
        invoiceItemsTable.getSelectionModel().addListSelectionListener(listener);
        models.add(invoiceItemsTable.getSelectionModel());

        invoiceItemsPanel.add(new JScrollPane(invoiceItemsTable));

        rightPanel.add(invoiceItemsPanel);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setVisible(true);
        saveBtn=new JButton("Save");
        saveBtn.setActionCommand("saveBtn");
        saveBtn.addActionListener(listener);
        buttonsPanel.add(saveBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setActionCommand("cancelBtn");
        cancelBtn.addActionListener(listener);
        buttonsPanel.add(cancelBtn);
        rightPanel.add(buttonsPanel);

        add(rightPanel);

    }

    public int getNextInvoiceNumber() {
        int n = 0;

        for (InvoiceHeader invoice : getInvoiceHeaderArray()) {
            if (invoice.getNumber() > n) {
                n = invoice.getNumber();
            }
        }
        return ++n;
    }

    public JLabel getInvNumberLbl() {
        return invoiceNumberLbl;
    }

    public JTextField getInvoiceDateTF() {
        return invoiceDateTF;
    }

    public JTextField getCustomerNameTF() {
        return customerNameTF;
    }

    public JLabel getInvoiceTotalLbl() {
        return invoiceTotalLbl;
    }

    public static void main(String[] args) {

        new ParentFrame().setVisible(true);

    }
}
