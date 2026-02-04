package accountspack;

import java.util.Date;

public class Transaction {
    private Date date;
    private String refNo;
    private String particulars;
    private double debit;
    private double credit;
    private String type;

    public Transaction(Date date, String refNo, String particulars, double debit, double credit, String type) {
        this.date = date;
        this.refNo = refNo;
        this.particulars = particulars;
        this.debit = debit;
        this.credit = credit;
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
