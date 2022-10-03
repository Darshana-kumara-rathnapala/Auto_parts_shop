package modle;

public class BoughtDetail {
    private String barCode;
    private String bId;
    private int qty;
    private double cost;

    public BoughtDetail() {
    }

    public BoughtDetail(String barCode, String bId, int qty, double cost) {
        this.setBarCode(barCode);
        this.setbId(bId);
        this.setQty(qty);
        this.setCost(cost);
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
