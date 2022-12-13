package DataSQL;

public class paymentHistoryList {

    private int receiverAccount;
    private double amount;
    private String type;
    private String date;
    private String informationForBeneficiary;
    private int senderAccount;
    private int belongsToAccount;
    private String id;

    public int getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(int receiverAccount) {
        this.receiverAccount = receiverAccount;
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

    public int getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(int senderAccount) {
        this.senderAccount = senderAccount;
    }

    public int getBelongsToAccount() {
        return belongsToAccount;
    }

    public void setBelongsToAccount(int belongsToAccount) {
        this.belongsToAccount = belongsToAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
