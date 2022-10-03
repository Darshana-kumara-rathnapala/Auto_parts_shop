package tm;

public class ReportSellTm {
    private String invoiceNo;
    private  String date;
    private double totleBill;
    private double cash;
    private  double discount;
    private double balance;

    public ReportSellTm() {
    }

    public ReportSellTm(String invoiceNo, String date, double totleBill, double cash, double discount, double balance) {
        this.invoiceNo = invoiceNo;
        this.date = date;
        this.totleBill = totleBill;
        this.cash = cash;
        this.discount = discount;
        this.balance = balance;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotleBill() {
        return totleBill;
    }

    public void setTotleBill(double totleBill) {
        this.totleBill = totleBill;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
