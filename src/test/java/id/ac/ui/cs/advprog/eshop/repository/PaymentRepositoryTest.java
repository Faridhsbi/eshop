package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {

    private PaymentRepository repository;
    Payment payment1;
    Payment payment2;
    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        repository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");

        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> bankTransferData = new HashMap<>();
        bankTransferData.put("bankName", "BCA");
        bankTransferData.put("referenceCode", "REF123456789");

        payment1 = new Payment("payment-1", order, "VOUCHER", voucherData);
        payment2 = new Payment("payment-2", order, "BANK_TRANSFER", bankTransferData);
    }

    @Test
    void testSaveCreatePositive() {
        Payment saved = repository.save(payment1);
        assertNotNull(saved);
        assertEquals("payment-1", saved.getId());

        Payment found = repository.findById("payment-1");
        assertNotNull(found);
        assertEquals("payment-1", found.getId());
    }

    @Test
    void testSaveUpdatePositive() {
        repository.save(payment1);
        payment1.setStatus(PaymentStatus.SUCCESS.getValue());
        repository.save(payment1);

        Payment updated = repository.findById("payment-1");
        assertNotNull(updated);
        assertEquals(PaymentStatus.SUCCESS.getValue(), updated.getStatus());
    }

    @Test
    void testFindByIdIfIdFoundPositive() {
        repository.save(payment1);
        Payment found = repository.findById("payment-1");
        assertNotNull(found);
        assertEquals("payment-1", found.getId());
    }

    @Test
    void testFindByIdIfIdNotFoundNegative() {
        Payment found = repository.findById("non-existent");
        assertNull(found);
    }

    @Test
    void testGetAllPaymentsPositive() {
        repository.save(payment1);
        repository.save(payment2);
        List<Payment> allPayments = repository.findAll();
        assertNotNull(allPayments);
        assertEquals(2, allPayments.size());
        assertTrue(allPayments.stream().anyMatch(p -> p.getId().equals("payment-1")));
        assertTrue(allPayments.stream().anyMatch(p -> p.getId().equals("payment-2")));
    }

    @Test
    void testGetAllPaymentsNegative() {
        List<Payment> allPayments = repository.findAll();
        assertNotNull(allPayments);
        assertTrue(allPayments.isEmpty());
    }
}