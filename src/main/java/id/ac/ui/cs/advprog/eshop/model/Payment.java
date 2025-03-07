package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private Order order;
    private String method;
    @Setter
    private String status;
    private Map<String, String> paymentData;


    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.paymentData = paymentData;
        this.status = "WAITING";
    }

    public Payment(String id, Order order, String method, Map<String, String> paymentData, String status) {
        this(id, order, method, paymentData);
        this.status = status;
    }

    public void validateAndSetStatus() {
        if ("VOUCHER".equalsIgnoreCase(method)) {
            validateVoucherPayment();
        } else if ("BANK_TRANSFER".equalsIgnoreCase(method)) {
            validateBankTransferPayment();
        } else if ("CASH_ON_DELIVERY".equalsIgnoreCase(method)) {
            validateCashOnDeliveryPayment();
        } else {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
    }

    private void validateVoucherPayment() {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            setStatus("REJECTED");
            return;
        }
        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }
        if (digitCount == 8) {
            setStatus("SUCCESS");
        } else {
            setStatus("REJECTED");
        }
    }

    private void validateBankTransferPayment() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        if (bankName == null || bankName.trim().isEmpty() ||
                referenceCode == null || referenceCode.trim().isEmpty()) {
            setStatus("REJECTED");
        } else {
            setStatus("SUCCESS");
        }
    }

    private void validateCashOnDeliveryPayment() {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");
        if (address == null || address.trim().isEmpty() ||
                deliveryFee == null || deliveryFee.trim().isEmpty()) {
            setStatus("REJECTED");
        } else {
            setStatus("SUCCESS");
        }
    }
}