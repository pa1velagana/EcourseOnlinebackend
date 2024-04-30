package com.example.RegisterLogin.modals;

public class OrderRequest {
    private Long amount;
    private String currency;
    private String receipt;
    // Add other necessary fields as needed

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    // Add getters and setters for other fields as needed

    @Override
    public String toString() {
        return "OrderRequest{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                ", receipt='" + receipt + '\'' +
                // Add other fields here for toString() representation
                '}';
    }
}
