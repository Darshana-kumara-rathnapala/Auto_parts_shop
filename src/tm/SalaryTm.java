package tm;

public class SalaryTm {
    private String nic;
    private String name;
    private double amount;
    private String month;
    private String epfNo;
    private String prasantageOfEpf;
    private double netSalary;

    public SalaryTm() {
    }

    public SalaryTm(String nic, String name, double amount, String month, String epfNo, String prasantageOfEpf, double netSalary) {
        this.setNic(nic);
        this.setName(name);
        this.setAmount(amount);
        this.setMonth(month);
        this.setEpfNo(epfNo);
        this.setPrasantageOfEpf(prasantageOfEpf);
        this.setNetSalary(netSalary);
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getEpfNo() {
        return epfNo;
    }

    public void setEpfNo(String epfNo) {
        this.epfNo = epfNo;
    }

    public String getPrasantageOfEpf() {
        return prasantageOfEpf;
    }

    public void setPrasantageOfEpf(String prasantageOfEpf) {
        this.prasantageOfEpf = prasantageOfEpf;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    @Override
    public String toString() {
        return "SalaryTm{" +
                "nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", month='" + month + '\'' +
                ", epfNo='" + epfNo + '\'' +
                ", prasantageOfEpf='" + prasantageOfEpf + '\'' +
                ", netSalary=" + netSalary +
                '}';
    }
}
