package modle;

public class OrderDetail {
    private String invoiceNo;
    private String barCode;
    private double unitPrice;
    private int qtyForSell;

    public OrderDetail() {
    }

    public OrderDetail(String invoiceNo, String barCode, double unitPrice, int qtyForSell) {
        this.invoiceNo = invoiceNo;
        this.barCode = barCode;
        this.unitPrice = unitPrice;
        this.qtyForSell = qtyForSell;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyForSell() {
        return qtyForSell;
    }

    public void setQtyForSell(int qtyForSell) {
        this.qtyForSell = qtyForSell;
    }
}
