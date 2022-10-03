package modle;

import java.util.ArrayList;

public class Buy {
    private String bId;
    private String supId;
    private String date;
    private ArrayList<BoughtDetail> brought_detail;

    public Buy() {
    }

    public Buy(String bId, String supId, String date, ArrayList<BoughtDetail> brought_detail) {
        this.bId = bId;
        this.supId = supId;
        this.date = date;
        this.brought_detail = brought_detail;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<BoughtDetail> getBrought_detail() {
        return brought_detail;
    }

    public void setBrought_detail(ArrayList<BoughtDetail> brought_detail) {
        this.brought_detail = brought_detail;
    }
}
