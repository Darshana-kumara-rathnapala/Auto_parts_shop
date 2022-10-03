package tm;

public class BillTm {
    private String barcode;
    private String description;
    private double unitPrice;
    private int qty;
    private double amount;

    public BillTm() {
    }

    public BillTm(String barcode, String description, double unitPrice, int qty, double amount) {
        this.barcode = barcode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.amount = amount;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
