package mainPackage.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class InvoiceLineTableModule extends AbstractTableModel {

    private ArrayList<InvoiceLine> data;
    private String[] columns = {"No.","Item Name","Item Price","Count", "Total"};

    public InvoiceLineTableModule(ArrayList<InvoiceLine> data) {
        this.data = data;
    }

    public ArrayList<InvoiceLine> getLines() {
        return data;
    }


    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine line =data.get(rowIndex);
        switch (columnIndex){
            case 0:
                return line.getHeader().getNumber();
            case 1:
                return line.getName();
            case 2:
                return line.getPrice();
            case 3:
                return line.getCount();
            case 4:
                return line.getTotal();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
