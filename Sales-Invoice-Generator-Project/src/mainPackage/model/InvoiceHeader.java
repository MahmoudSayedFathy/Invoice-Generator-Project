package mainPackage.model;

import java.util.ArrayList;

public class InvoiceHeader {
    private int number;
    private String date;
    private String customerName;
    private double total;
    ArrayList<InvoiceLine> lines;

    InvoiceHeader(){
    }
    public InvoiceHeader(int number, String date, String customerName) {
        this.number = number;
        this.date = date;
        this.customerName = customerName;

    }

    public int getNumber() {
        return number;
    }

    public void setInvNumber(int invNumber) {
        this.number = invNumber;
    }

    public String getDate() {
        return date;
    }

    public void setInvDate(String invDate) {
        this.date = invDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public double getTotal() {
        total = 0.0;
        for(InvoiceLine line: getLines()){
            total += line.getTotal();
        }
        return total;
    }


    public ArrayList<InvoiceLine> getLines() {
        if (lines == null) {
        lines = new ArrayList<>();
        }

        return lines;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" +
                "number=" + number +
                ", invDate='" + date + '\'' +
                ", customerName='" + customerName +
                '}';
    }

    public String getAsCSV(){
        return number + "," + date + "," + customerName;
    }
}
