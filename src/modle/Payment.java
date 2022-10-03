package modle;

public class Payment {
    private String payCode;
    private String invoiceNo;
    private double totalPrice;
    private double cash;
    private double balance;
    private double discount;

    public Payment() {
    }

    public Payment(String payCode, String invoiceNo, double totalPrice, double cash, double balance, double discount) {
        this.payCode = payCode;
        this.invoiceNo = invoiceNo;
        this.totalPrice = totalPrice;
        this.cash = cash;
        this.balance = balance;
        this.discount = discount;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
