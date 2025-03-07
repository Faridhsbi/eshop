package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.enums.*;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    List<Payment> payments;
    List<Order> orders;
    Order order;
    private Map<String, String> voucherData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        payments = new ArrayList<>();
        orders = new ArrayList<>();

        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                    products, 1708560000L, "Safira Sudrajat");
        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                    products, 1708570000L, "Safira Sudrajat");
        Order order3 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb079",
                    products, 1708580000L, "Safira Sudrajat");
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("bankName", "Mandiri");
        paymentData1.put("referenceCode", "12324");
        Payment payment1 = new Payment("aebc-c3af385fb078",
                order1,
                PaymentMethod.BANK_TRANSFER.getValue(),
                paymentData1,
                PaymentStatus.SUCCESS.getValue());
        payments.add(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP12345678DEF");
        Payment payment2 = new Payment("aebc-c3af385fb078",
                order2,
                PaymentMethod.VOUCHER.getValue(),
                paymentData2,
                PaymentStatus.SUCCESS.getValue());
        payments.add(payment2);

        Map<String, String> paymentData3 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP123DEF");
        Payment payment3 = new Payment("aebc-c3af385fb078",
                order3,
                PaymentMethod.VOUCHER.getValue(),
                paymentData3,
                PaymentStatus.REJECTED.getValue());
        payments.add(payment3);
    }

    @Test
    void testAddPayment() {
        Order order = orders.get(0);
        String method = PaymentMethod.BANK_TRANSFER.getValue();
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "98765");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, method, paymentData);

        assertNotNull(result);
        assertEquals(order, result.getOrder());
        assertEquals(method, result.getMethod());
        assertEquals(paymentData, result.getPaymentData());
        assertEquals(PaymentStatus.WAITING.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = payments.getFirst();
        Order order = payment.getOrder();

        when(paymentRepository.save(payment)).thenReturn(payment);
        when(orderRepository.save(order)).thenReturn(order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(PaymentStatus.SUCCESS.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = payments.getFirst();
        Order order = payment.getOrder();

        when(paymentRepository.save(payment)).thenReturn(payment);
        when(orderRepository.save(order)).thenReturn(order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetStatusToOther() {
        Payment payment = payments.getFirst();
        String newStatus = "PENDING";

        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, newStatus);

        assertEquals(newStatus, result.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testSetStatusWithNullOrder() {
        Payment payment = new Payment("test-id", null, PaymentMethod.BANK_TRANSFER.getValue(),
                new HashMap<>(), PaymentStatus.WAITING.getValue());

        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, never()).save(any(Order.class));
    }


    @Test
    void testGetPaymentReturnsCorrectPayment() {
        Payment dummyPayment = new Payment("payment-1", order, "VOUCHER", voucherData, PaymentStatus.SUCCESS.getValue());
        when(paymentRepository.findById("payment-1")).thenReturn(dummyPayment);

        Payment fetched = paymentService.getPayment("payment-1");
        assertNotNull(fetched);
        assertEquals("payment-1", fetched.getId());
    }

    @Test
    void testGetAllPaymentsReturnsAll() {
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> allPayments = paymentService.getAllPayments();
        assertNotNull(allPayments);
        assertEquals(3, allPayments.size());
    }

    @Test
    void testGetPayment() {
        String paymentId = "aebc-c3af385fb078";
        Payment expectedPayment = payments.getFirst();

        when(paymentRepository.findById(paymentId)).thenReturn(expectedPayment);
        Payment result = paymentService.getPayment(paymentId);
        assertEquals(expectedPayment, result);
        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.findAll()).thenReturn(payments);
        List<Payment> result = paymentService.getAllPayments();
        assertEquals(payments.size(), result.size());
        assertEquals(payments, result);
        verify(paymentRepository, times(1)).findAll();
    }
}
