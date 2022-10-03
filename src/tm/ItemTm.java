package tm;

public class ItemTm {
    private String barcode;
    private String description;
    private int quantity;
    private double cost;
    private String broughtId;
    private String date;

    public ItemTm() {
    }

    public ItemTm(String barcode, String description, int quantity, double cost, String broughtId, String date) {
        this.barcode = barcode;
        this.description = description;
        this.quantity = quantity;
        this.cost = cost;
        this.broughtId = broughtId;
        this.date = date;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getBroughtId() {
        return broughtId;
    }

    public void setBroughtId(String broughtId) {
        this.broughtId = broughtId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ItemTm{" +
                "barcode='" + barcode + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", cost=" + cost +
                ", broughtId='" + broughtId + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
