package tm;

public class ReportSalaryTm {
    private String paymnet_no;
    private double salary;
    private double epf;
    private double netSalary;

    public ReportSalaryTm() {
    }

    public ReportSalaryTm(String paymnet_no, double salary, double epf, double netSalary) {
        this.paymnet_no = paymnet_no;
        this.salary = salary;
        this.epf = epf;
        this.netSalary = netSalary;
    }

    public String getPaymnet_no() {
        return paymnet_no;
    }

    public void setPaymnet_no(String paymnet_no) {
        this.paymnet_no = paymnet_no;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getEpf() {
        return epf;
    }

    public void setEpf(double epf) {
        this.epf = epf;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }
}

