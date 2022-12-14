package mainPackage.model;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class OperationOfFile {

    static ArrayList<InvoiceHeader> invoiceHeaders ;
    public ArrayList<InvoiceHeader> readFile() {
        JFileChooser fc = new JFileChooser();
        try {
            JOptionPane.showMessageDialog(null, "Select Invoice Header File",
                    "Invoice Header", JOptionPane.INFORMATION_MESSAGE);
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);

                ArrayList<InvoiceHeader> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    try {
                        String[] headerParts = headerLine.split(",");
                        int invoiceNum = Integer.parseInt(headerParts[0]);
                        String invoiceDate = headerParts[1];
                        String customerName = headerParts[2];
                        InvoiceHeader invoices = new InvoiceHeader(invoiceNum, invoiceDate, customerName);
                        invoicesArray.add(invoices);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                System.out.println("Check point");
                JOptionPane.showMessageDialog(null, "Select Invoice Line File",
                        "Invoice Line", JOptionPane.INFORMATION_MESSAGE);
                result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> Lines = Files.readAllLines(linePath);
                    System.out.println("Lines have been read");
                    for (String lineLine : Lines) {
                        try {
                            String[] lineLineParts = lineLine.split(",");
                            int invoiceNum = Integer.parseInt(lineLineParts[0]);
                            String itemName = (lineLineParts[1]);
                            double itemPrice = Double.parseDouble(lineLineParts[2]);
                            int count = Integer.parseInt(lineLineParts[3]);
                            InvoiceHeader inv = null;
                            for (InvoiceHeader invoice : invoicesArray) {
                                if (invoice.getNumber() == invoiceNum) {
                                    inv = invoice;
                                    break;
                                }
                            }
                            InvoiceLine line;
                            line = new InvoiceLine(itemName, itemPrice, count, inv);
                            inv.getLines().add(line);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
                invoiceHeaders = invoicesArray;
            }
        } catch (IOException x) {
            x.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return invoiceHeaders;
    }
    public void writeFile(ArrayList<InvoiceHeader> invoiceHeaders){
        for(InvoiceHeader invoice : invoiceHeaders)
        {
            int invId = invoice.getNumber();
            String date = invoice.getDate();
            String customer = invoice.getCustomerName();
            System.out.println("\n Invoice " + invId + "\n {\n " + date + "," + customer);
            ArrayList<InvoiceLine> lines = invoice.getLines();
            for(InvoiceLine line : invoice.getLines()){
                System.out.println( line.getName() + "," + line.getPrice() + "," + line.getCount());
            }

            System.out.println(" } \n");
        }
    }

}
