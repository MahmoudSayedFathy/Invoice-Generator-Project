package mainPackage.actionsListner;



import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mainPackage.UI.CreateInvoiceDialog;
import mainPackage.UI.ItemDialog;
import mainPackage.UI.ParentFrame;
import mainPackage.model.InvoiceLineTableModule;
import mainPackage.model.InvoiceHeader;
import mainPackage.model.InvoiceLine;



public class ActionListner implements java.awt.event.ActionListener, ListSelectionListener {


    private CreateInvoiceDialog invoiceDialog;
    private ItemDialog lineDialog;

    private static ParentFrame parentFrame;

    public static ParentFrame getFrame() {
        return parentFrame;
    }

    public ActionListner(ParentFrame frame){
        this.parentFrame=frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();
    switch (actionCommand){
        case "loadFile":
            loadFile();
            break;
        case"saveFile":
            saveFile();
            break;
        case"createInvBtn":
            createNewInvoice();
            break;
        case"deleteInvBtn":
            deleteInvoice();
            break;
        case"saveBtn":
            saveItemsData();
            break;
        case"cancelBtn":
            cancelItemsData();
            break;
        case "CancelCreateInv":
            cancelCreateInvoice();
            break;
        case "createInvoice":
            createInvoice();
            break;
        case "createLine":
            createLine();
            break;
        case "cancelCreateLine":
            cancelCreationLine();
            break;
    }
}


    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel selectedModel = (ListSelectionModel) e.getSource();
        for(ListSelectionModel model : parentFrame.getModels()){
            if(model != selectedModel){
                model.removeListSelectionListener(this);
                model.clearSelection();
                model.addListSelectionListener(this);
            }
        }
        int selectedRow = parentFrame.getInvoiceTable().getSelectedRow();
        if(selectedRow !=-1){
            InvoiceHeader selectedInvoice =parentFrame.getInvoiceHeaderArray().get(selectedRow);

            parentFrame.getInvNumberLbl().setText(""+selectedInvoice.getNumber());
            parentFrame.getInvoiceDateTF().setText(selectedInvoice.getDate());
            parentFrame.getCustomerNameTF().setText(selectedInvoice.getCustomerName());

            parentFrame.getInvoiceTotalLbl().setText(""+selectedInvoice.getTotal());
            InvoiceLineTableModule invLineTableMod=new InvoiceLineTableModule(selectedInvoice.getLines());
            parentFrame.getInvoiceItemsTable().setModel(invLineTableMod);
            invLineTableMod.fireTableDataChanged();
        }
    }
    private void loadFile() {
        try{
            JFileChooser fileChooser= new JFileChooser();
            int result = fileChooser.showOpenDialog(parentFrame);
            if(result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fileChooser.getSelectedFile();
                String hPath = headerFile.getAbsolutePath();
                Path hp = Paths.get(hPath);
                List<String> hLines= Files.lines(hp).collect(Collectors.toList());
                ArrayList<InvoiceHeader> invoiceHeaderArray = new ArrayList<>();
                for(String hLine : hLines) {
                    String[] invContent = hLine.split(",");

                    int id = Integer.parseInt(invContent[0]);
                    InvoiceHeader invHead = new InvoiceHeader(id,invContent[1],invContent[2]);
                    invoiceHeaderArray.add(invHead);
                }
                result = fileChooser.showOpenDialog(parentFrame);
                if(result == JFileChooser.APPROVE_OPTION){
                    String lPath = fileChooser.getSelectedFile().getAbsolutePath();
                    Path lp = Paths.get(lPath);
                    List<String> lLines = Files.lines(lp).collect(Collectors.toList());
                    for(String lLine : lLines){
                        String[] itmContent = lLine.split(",");
                        int itmID = Integer.parseInt(itmContent[0]);
                        double itmPrice = Double.parseDouble(itmContent[2]);
                        int itmCount = Integer.parseInt(itmContent[3]);
                        InvoiceHeader header = findInvoiceHeaderByID(invoiceHeaderArray,itmID);
                        InvoiceLine invLine = new InvoiceLine(itmContent[1],itmPrice,itmCount,header);
                        header.getLines().add(invLine);

                    }
                    parentFrame.setInvoiceHeaderArray(invoiceHeaderArray);


                    for(InvoiceHeader inv : invoiceHeaderArray){
                        System.out.println("\nInvoice "+ inv.getNumber() + "\n{ \n"
                                +inv.getCustomerName() + ",\n" +inv.getDate());
                        ArrayList<InvoiceLine> lines = inv.getLines();
                        for(InvoiceLine line: inv.getLines()){
                            System.out.println(line.getName() + "," +
                                    line.getPrice() + "," + line.getCount());
                        }
                        System.out.println("}");
                    }
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    private void saveFile() {
        ArrayList<InvoiceHeader> invoiceHeaders = parentFrame.getInvoiceHeaderArray();
        String invHeaders = "";
        String invLines = "";

        for (InvoiceHeader invoice : invoiceHeaders) {
            String invHeadersFormat = invoice.getAsCSV();
            invHeaders += invHeadersFormat;
            invHeaders += "\n";

            for (InvoiceLine inv : invoice.getLines()) {
                String linessFormat = inv.getAsCSV();
                invLines += linessFormat;
                invLines += "\n";
            }
        }
        try {
            JFileChooser fileChooser = new JFileChooser();
            int fileSaver = fileChooser.showSaveDialog(parentFrame);
            if (fileSaver == JFileChooser.APPROVE_OPTION) {
                File invFile = fileChooser.getSelectedFile();
                FileWriter invFileWriter = new FileWriter(invFile);
                invFileWriter.write(invHeaders);
                invFileWriter.flush();
                invFileWriter.close();

                fileSaver = fileChooser.showSaveDialog(parentFrame);
            }
            if (fileSaver == JFileChooser.APPROVE_OPTION) {
                File lineFile = fileChooser.getSelectedFile();
                FileWriter lineFileWriter = new FileWriter(lineFile);
                lineFileWriter.write(invLines);
                lineFileWriter.flush();
                lineFileWriter.close();
            }
        } catch (Exception e) {
        }

    }


    private void createNewInvoice() {
        invoiceDialog = new CreateInvoiceDialog(parentFrame);
        invoiceDialog.setVisible(true);
    }


    private void deleteInvoice() {
        int selectionRow = parentFrame.getInvoiceTable().getSelectedRow();
        if (selectionRow != -1) {
            parentFrame.getInvoiceHeaderArray().remove(selectionRow);
            parentFrame.getInvoiceHeaderTableModule().fireTableDataChanged();
        }
    }

    private void createInvoice() {
        String dateInv = invoiceDialog.getInvoiceDateField().getText();
        String customerName = invoiceDialog.getCustomerNameField().getText();
        int numberInv = parentFrame.getNextInvoiceNumber();
        try {
            String[] dateSplits = dateInv.split("-");
            if (dateSplits.length < 3) {

                JOptionPane.showMessageDialog(
                        parentFrame, "Please enter a valid Date ", "Error", JOptionPane.ERROR_MESSAGE);

            } else {
                int dayDate = Integer.parseInt(dateSplits[0]);
                int monthDate = Integer.parseInt(dateSplits[1]);
                int yearDate = Integer.parseInt(dateSplits[2]);


                if (dayDate > 31 || monthDate > 12 || yearDate > 3000||yearDate < 1000) {
                    JOptionPane.showMessageDialog(
                            parentFrame, "Please enter a valid Date ", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    InvoiceHeader invoice = new InvoiceHeader(numberInv, customerName, dateInv);
                    ArrayList<InvoiceHeader> invoiceHeader = parentFrame.getInvoiceHeaderArray();
                    invoiceHeader.add(invoice);
                    parentFrame.setInvoiceHeaderArray(invoiceHeader);
                    parentFrame.getInvoiceHeaderTableModule().fireTableDataChanged();
                    invoiceDialog.setVisible(false);
                    invoiceDialog.dispose();
                    invoiceDialog = null;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    parentFrame, "Please enter a valid Date ", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void cancelCreateInvoice() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void saveItemsData() {
        lineDialog = new ItemDialog(parentFrame);
        lineDialog.setVisible(true);
    }

    private void cancelItemsData() {
        int selectedRow = parentFrame.getInvoiceItemsTable().getSelectedRow();
        if (selectedRow != -1) {

            InvoiceLineTableModule linesTableModel = (InvoiceLineTableModule) parentFrame.getInvoiceItemsTable().getModel();
            linesTableModel.getLines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            parentFrame.getInvoiceHeaderTableModule().fireTableDataChanged();
        }

    }

    private void createLine() {
        String newItem = lineDialog.getItemNameField().getText();
        String count = lineDialog.getItemCountField().getText();
        String nwPrice = lineDialog.getItemPriceField().getText();
        int counts = Integer.parseInt(count);
        double prices = Double.parseDouble(nwPrice);
        int selectedInvoice = parentFrame.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {

            InvoiceHeader newInvoice = parentFrame.getInvoiceHeaderArray().get(selectedInvoice);
            InvoiceLine newLine = new InvoiceLine(newItem, prices, counts, newInvoice);
            newInvoice.getLines().add(newLine);
            InvoiceLineTableModule linesTableModel = (InvoiceLineTableModule) parentFrame.getInvoiceItemsTable().getModel();
            linesTableModel.fireTableDataChanged();
            parentFrame.getInvoiceHeaderTableModule().fireTableDataChanged();
        }
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;

    }

    private void cancelCreationLine() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }


    private InvoiceHeader findInvoiceHeaderByID(ArrayList<InvoiceHeader> invoices,int id){
        for(InvoiceHeader invoice : invoices){
            if(invoice.getNumber() == id){
                return invoice;
            }
        }
        return null;
    }

}
