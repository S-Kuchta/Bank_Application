package DataSQL;

public class paymentHistoryList {

    private int accountToOrFrom;
    private double amount;
    private String type;
    private String date;
    private String informationForBeneficiary;

    public int getAccountToOrFrom() {
        return accountToOrFrom;
    }

    public void setAccountToOrFrom(int accountToOrFrom) {
        this.accountToOrFrom = accountToOrFrom;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInformationForBeneficiary() {
        return informationForBeneficiary;
    }

    public void setInformationForBeneficiary(String informationForBeneficiary) {
        this.informationForBeneficiary = informationForBeneficiary;
    }
}
