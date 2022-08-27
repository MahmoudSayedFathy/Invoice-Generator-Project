package mainPackage.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class InvoiceHeaderTableModule extends AbstractTableModel {
    private ArrayList<InvoiceHeader> data;
    private String[] colsNames = {"No.","Date","Customer","Total"};

    public InvoiceHeaderTableModule(ArrayList<InvoiceHeader> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
       return colsNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    InvoiceHeader header = data.get(rowIndex);
    switch (columnIndex){
        case 0:
            return header.getNumber();
        case 1:
            return header.getCustomerName();
        case 2:
            return header.getDate();
        case 3:
            return header.getTotal();
    }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return colsNames[column];
    }


}
