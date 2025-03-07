package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
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

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
        paymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentWithRequiredFields() {
        Payment payment = new Payment("new-payment", order, "VOUCHER", paymentData);
        assertEquals("new-payment", payment.getId());
        assertEquals(order, payment.getOrder());
        assertEquals(PaymentMethod.VOUCHER, payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.WAITING, payment.getStatus());
    }

    @Test
    void testCreatePaymentWithCustomStatus() {
        Payment payment = new Payment("new-payment", order, "VOUCHER", paymentData, "SUCCESS");
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    @Test
    void updatePaymentStatusShouldChangeValue() {
        Payment payment = new Payment("new-payment", order, "VOUCHER", paymentData);
        payment.setStatus(PaymentStatus.SUCCESS);
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    @Test
    void validateVoucherPayment_withValidCode_returnsSuccess() {
        paymentData.put("voucherCode", "ESHOP5678XYZ1234");
        Payment payment = new Payment("new-payment", order, "VOUCHER", paymentData);
        payment.validateAndSetStatus();
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    @Test
    void validateVoucherPayment_withShortCode_returnsRejected() {
        paymentData.put("voucherCode", "ESHOP9876");
        Payment payment = new Payment("new-payment", order, "VOUCHER", paymentData);
        payment.validateAndSetStatus();
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void validateVoucherPayment_withIncorrectPrefix_returnsRejected() {
        paymentData.put("voucherCode", "SHOP9876LMN54321");
        Payment payment = new Payment("new-payment", order, "VOUCHER", paymentData);
        payment.validateAndSetStatus();
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void validateVoucherPayment_withInsufficientDigits_returnsRejected() {
        paymentData.put("voucherCode", "ESHOPABCDWXYZEFG");
        Payment payment = new Payment("new-payment", order, "VOUCHER", paymentData);
        payment.validateAndSetStatus();
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void validateBankTransferPayment_withCompleteData_returnsSuccess() {
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", "REF987654321");
        Payment payment = new Payment("new-payment", order, "BANK_TRANSFER", paymentData);
        payment.validateAndSetStatus();
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    @Test
    void validateBankTransferPayment_missingBankName_returnsRejected() {
        paymentData.put("referenceCode", "REF987654321");
        Payment payment = new Payment("new-payment", order, "BANK_TRANSFER", paymentData);
        payment.validateAndSetStatus();
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void validateBankTransferPayment_withEmptyReference_returnsRejected() {
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", "");
        Payment payment = new Payment("new-payment", order, "BANK_TRANSFER", paymentData);
        payment.validateAndSetStatus();
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void validateBankTransferPayment_withNullValues_returnsRejected() {
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", null);
        Payment payment = new Payment("new-payment", order, "BANK_TRANSFER", paymentData);
        payment.validateAndSetStatus();
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void validatePayment_withUnsupportedMethod_throwsException() {
        Payment payment = new Payment("new-payment", order, "UNKNOWN_METHOD", paymentData);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            payment.validateAndSetStatus();
        });
        assertTrue(ex.getMessage().contains("Unsupported payment method"));
    }

    @Test
    void validateVoucherPayment_withNullVoucher_returnsRejected() {
        // Tidak memasukkan voucherCode
        Payment payment = new Payment("new-payment", order, "VOUCHER", paymentData);
        payment.validateAndSetStatus();
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void validateBankTransferPayment_withWhitespaceBankName_returnsRejected() {
        paymentData.put("bankName", "   ");
        paymentData.put("referenceCode", "REF987654321");
        Payment payment = new Payment("new-payment", order, "BANK_TRANSFER", paymentData);
        payment.validateAndSetStatus();
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }
}
