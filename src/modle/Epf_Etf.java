package modle;

public class Epf_Etf {
    private String epfNo;
    private double epf;
    private double etf;
    private String month;

    public Epf_Etf() {
    }

    public Epf_Etf(String epfNo, double epf, double etf, String month) {
        this.epfNo = epfNo;
        this.epf = epf;
        this.etf = etf;
        this.month = month;
    }

    public String getEpfNo() {
        return epfNo;
    }

    public void setEpfNo(String epfNo) {
        this.epfNo = epfNo;
    }

    public double getEpf() {
        return epf;
    }

    public void setEpf(double epf) {
        this.epf = epf;
    }

    public double getEtf() {
        return etf;
    }

    public void setEtf(double etf) {
        this.etf = etf;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "Epf_Etf{" +
                "epfNo='" + epfNo + '\'' +
                ", epf=" + epf +
                ", etf=" + etf +
                ", month='" + month + '\'' +
                '}';
    }
}
