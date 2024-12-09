package model;

public class PaymentInformation {
	private String cardNumber;
	private String cardCode;
	private String cardExpiryDate;

    public PaymentInformation() {}

    public PaymentInformation(String cardNumber, String cardCode, String cardExpiryDate) {
        this.cardNumber = cardNumber;
        this.cardCode = cardCode;
        this.cardExpiryDate = cardExpiryDate;
    }

    // Getters and Setters
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }
}