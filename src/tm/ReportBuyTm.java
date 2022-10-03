package tm;

public class ReportBuyTm {
    private String buy_id;
    private double cost;
    private double payment_cash;
    private double balance;

    public ReportBuyTm() {
    }
    public ReportBuyTm(String buy_id, double cost, double payment_cash, double balance) {
        this.buy_id = buy_id;
        this.cost = cost;
        this.payment_cash = payment_cash;
        this.balance = balance;
    }

    public String getBuy_id() {
        return buy_id;
    }

    public void setBuy_id(String buy_id) {
        this.buy_id = buy_id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPayment_cash() {
        return payment_cash;
    }

    public void setPayment_cash(double payment_cash) {
        this.payment_cash = payment_cash;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
