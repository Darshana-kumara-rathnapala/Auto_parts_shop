package modle;

public class Payroll {
    private String paymentNo;
    private String employeeNic;
    private double salary;
    private String presantage;
    private double netSalary;
    private String month;

    public Payroll() {
    }
    public Payroll(String paymentNo, String employeeNic, double salary, String presantage, double netSalary, String month) {
        this.paymentNo = paymentNo;
        this.employeeNic = employeeNic;
        this.salary = salary;
        this.presantage = presantage;
        this.netSalary = netSalary;
        this.month = month;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getEmployeeNic() {
        return employeeNic;
    }

    public void setEmployeeNic(String employeeNic) {
        this.employeeNic = employeeNic;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPresantage() {
        return presantage;
    }

    public void setPresantage(String presantage) {
        this.presantage = presantage;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
