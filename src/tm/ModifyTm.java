package tm;

public class ModifyTm {
    private String barcode;
    private String date;
    private String time;
    private String description;
    private double unitPrice;
    private int qtyForCustomer;

    public ModifyTm() {
    }

    public ModifyTm(String barcode, String date, String time, String description, double unitPrice, int qtyForCustomer) {
        this.barcode = barcode;
        this.date = date;
        this.time = time;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyForCustomer = qtyForCustomer;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getQtyForCustomer() {
        return qtyForCustomer;
    }

    public void setQtyForCustomer(int qtyForCustomer) {
        this.qtyForCustomer = qtyForCustomer;
    }
}
