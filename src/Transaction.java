public class Transaction {
    private String username;
    private String date;
    private boolean type;
    private double amount;
    private String description;


    public Transaction(String username, double amount, boolean type, String description, String date) {
        this.username = username;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public boolean getType() {return type;}
    public void setType(boolean type) {this.type = type;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

}


