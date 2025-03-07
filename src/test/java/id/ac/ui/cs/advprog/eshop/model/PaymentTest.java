package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
        this.paymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentWithRequiredFields() {
        Payment payment = new Payment("new-payment", this.order, "VOUCHER", this.paymentData);
        assertEquals("new-payment", payment.getId());
        assertEquals(this.order, payment.getOrder());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(this.paymentData, payment.getPaymentData());
        assertEquals("WAITING", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithCustomStatus() {
        Payment payment = new Payment("new-payment", this.order, "VOUCHER", this.paymentData, "SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }
    @Test
    void updatePaymentStatusShouldChangeValue() {
        Payment payment = new Payment("new-payment", this.order, "VOUCHER", this.paymentData);
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void validateVoucherPayment_withValidCode_returnsSuccess() {
        this.paymentData.put("voucherCode", "ESHOP5678XYZ1234");
        Payment payment = new Payment("new-payment", this.order, "VOUCHER", this.paymentData);
        payment.validateAndSetStatus();
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void validateVoucherPayment_withShortCode_returnsRejected() {
        this.paymentData.put("voucherCode", "ESHOP9876");
        Payment payment = new Payment("new-payment", this.order, "VOUCHER", this.paymentData);
        payment.validateAndSetStatus();
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void validateVoucherPayment_withIncorrectPrefix_returnsRejected() {
        this.paymentData.put("voucherCode", "SHOP9876LMN54321");
        Payment payment = new Payment("new-payment", this.order, "VOUCHER", this.paymentData);
        payment.validateAndSetStatus();
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void validateVoucherPayment_withInsufficientDigits_returnsRejected() {
        this.paymentData.put("voucherCode", "ESHOPABCDWXYZEFG");
        Payment payment = new Payment("new-payment", this.order, "VOUCHER", this.paymentData);
        payment.validateAndSetStatus();
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void validateBankTransferPayment_withCompleteData_returnsSuccess() {
        this.paymentData.put("bankName", "Mandiri");
        this.paymentData.put("referenceCode", "REF987654321");
        Payment payment = new Payment("new-payment", this.order, "BANK_TRANSFER", this.paymentData);
        payment.validateAndSetStatus();
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void validateBankTransferPayment_missingBankName_returnsRejected() {
        this.paymentData.put("referenceCode", "REF987654321");
        Payment payment = new Payment("new-payment", this.order, "BANK_TRANSFER", this.paymentData);
        payment.validateAndSetStatus();
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void validateBankTransferPayment_withEmptyReference_returnsRejected() {
        this.paymentData.put("bankName", "Mandiri");
        this.paymentData.put("referenceCode", "");
        Payment payment = new Payment("new-payment", this.order, "BANK_TRANSFER", this.paymentData);
        payment.validateAndSetStatus();
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void validateBankTransferPayment_withNullValues_returnsRejected() {
        this.paymentData.put("bankName", null);
        this.paymentData.put("referenceCode", null);
        Payment payment = new Payment("new-payment", this.order, "BANK_TRANSFER", this.paymentData);
        payment.validateAndSetStatus();
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void validatePayment_withUnsupportedMethod_throwsException() {
        Payment payment = new Payment("new-payment", this.order, "UNKNOWN_METHOD", this.paymentData);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            payment.validateAndSetStatus();
        });
        assertTrue(ex.getMessage().contains("Unsupported payment method"));
    }

    @Test
    void validateVoucherPayment_withNullVoucher_returnsRejected() {
        Payment payment = new Payment("new-payment", this.order, "VOUCHER", this.paymentData);
        payment.validateAndSetStatus();
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void validateBankTransferPayment_withWhitespaceBankName_returnsRejected() {
        this.paymentData.put("bankName", "   ");
        this.paymentData.put("referenceCode", "REF987654321");
        Payment payment = new Payment("new-payment", this.order, "BANK_TRANSFER", this.paymentData);
        payment.validateAndSetStatus();
        assertEquals("REJECTED", payment.getStatus());
    }
}
