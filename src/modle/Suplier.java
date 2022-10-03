package modle;

public class Suplier {
    private String supId;
    private String supName;
    private int supContact;

    public Suplier() {
    }

    public Suplier(String supId, String supName, int supContact) {
        this.setSupId(supId);
        this.setSupName(supName);
        this.setSupContact(supContact);
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public int getSupContact() {
        return supContact;
    }

    public void setSupContact(int supContact) {
        this.supContact = supContact;
    }
}
