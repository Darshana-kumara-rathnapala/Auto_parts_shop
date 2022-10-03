package tm;

public class OrderTm {
    private String invoiceNo;
    private String barcode;
    private String description;
    private int qty;
    private double price;

    public OrderTm() {
    }

    public OrderTm(String invoiceNo, String barcode, String description, int qty, double price) {
        this.invoiceNo = invoiceNo;
        this.barcode = barcode;
        this.description = description;
        this.qty = qty;
        this.price = price;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderTm{" +
                "invoiceNo='" + invoiceNo + '\'' +
                ", barcode='" + barcode + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
