package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
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
        this.status = PaymentStatus.WAITING.getValue();
    }

    public Payment(String id, Order order, String method, Map<String, String> paymentData, String status) {
        this(id, order, method, paymentData);
        this.status = status;
    }

    public void validateAndSetStatus() {
        if (PaymentMethod.VOUCHER.getValue().equalsIgnoreCase(method)) {
            validateVoucherPayment();
        } else if (PaymentMethod.BANK_TRANSFER.getValue().equalsIgnoreCase(method)) {
            validateBankTransferPayment();
        } else if (PaymentMethod.CASH_ON_DELIVERY.getValue().equalsIgnoreCase(method)) {
            validateCashOnDeliveryPayment();
        } else {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
    }

    private void validateVoucherPayment() {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            setStatus(PaymentStatus.REJECTED.getValue());
            return;
        }
        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }
        if (digitCount == 8) {
            setStatus(PaymentStatus.SUCCESS.getValue());
        } else {
            setStatus(PaymentStatus.REJECTED.getValue());
        }
    }

    private void validateBankTransferPayment() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        if (bankName == null || bankName.trim().isEmpty() ||
                referenceCode == null || referenceCode.trim().isEmpty()) {
            setStatus(PaymentStatus.REJECTED.getValue());
        } else {
            setStatus(PaymentStatus.SUCCESS.getValue());
        }
    }

    private void validateCashOnDeliveryPayment() {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");
        if (address == null || address.trim().isEmpty() ||
                deliveryFee == null || deliveryFee.trim().isEmpty()) {
            setStatus(PaymentStatus.REJECTED.getValue());
        } else {
            setStatus(PaymentStatus.SUCCESS.getValue());
        }
    }
}