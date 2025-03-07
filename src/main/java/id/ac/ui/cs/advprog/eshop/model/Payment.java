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
    private PaymentMethod method;
    @Setter
    private PaymentStatus status;
    private Map<String, String> paymentData;

    public Payment(String id, Order order, PaymentMethod method, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.paymentData = paymentData;
        this.status = PaymentStatus.REJECTED;
    }

    public Payment(String id, Order order, PaymentMethod method, Map<String, String> paymentData, PaymentStatus status) {
        this(id, order, method, paymentData);
        this.status = status;
    }

    public void validateAndSetStatus() {
        switch (method) {
            case VOUCHER:
                validateVoucherPayment();
                break;
            case BANK_TRANSFER:
                validateBankTransferPayment();
                break;
            case CASH_ON_DELIVERY:
                validateCashOnDeliveryPayment();
                break;
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
    }

    private void validateVoucherPayment() {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            setStatus(PaymentStatus.REJECTED);
            return;
        }
        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }
        if (digitCount == 8) {
            setStatus(PaymentStatus.SUCCESS);
        } else {
            setStatus(PaymentStatus.REJECTED);
        }
    }

    private void validateBankTransferPayment() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        if (bankName == null || bankName.trim().isEmpty() ||
                referenceCode == null || referenceCode.trim().isEmpty()) {
            setStatus(PaymentStatus.REJECTED);
        } else {
            setStatus(PaymentStatus.SUCCESS);
        }
    }

    private void validateCashOnDeliveryPayment() {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");
        if (address == null || address.trim().isEmpty() ||
                deliveryFee == null || deliveryFee.trim().isEmpty()) {
            setStatus(PaymentStatus.REJECTED);
        } else {
            setStatus(PaymentStatus.SUCCESS);
        }
    }
}