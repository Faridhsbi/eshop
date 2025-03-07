package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
    public class PaymentServiceImpl implements PaymentService {

        @Autowired
        private PaymentRepository paymentRepository;

        @Autowired
        private OrderRepository orderRepository;

        @Override
        public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
            String paymentId = UUID.randomUUID().toString();
            Payment payment = new Payment(paymentId, order, method, paymentData);
            return paymentRepository.save(payment);
        }

        @Override
        public Payment setStatus(Payment payment, String status) {
            if (PaymentStatus.SUCCESS.getValue().equalsIgnoreCase(status)) {
                payment.setStatus(PaymentStatus.SUCCESS.getValue());
                Order relatedOrder = payment.getOrder();
                if (relatedOrder != null) {
                    relatedOrder.setStatus(PaymentStatus.SUCCESS.getValue());
                    orderRepository.save(relatedOrder);
                }
            } else if (PaymentStatus.REJECTED.getValue().equalsIgnoreCase(status)) {
                payment.setStatus(PaymentStatus.REJECTED.getValue());
                Order relatedOrder = payment.getOrder();
                if (relatedOrder != null) {
                    relatedOrder.setStatus(OrderStatus.FAILED.getValue());
                    orderRepository.save(relatedOrder);
                }
            } else {
                payment.setStatus(status);
            }
            return paymentRepository.save(payment);
        }

        @Override
        public Payment getPayment(String paymentId) {
            return paymentRepository.findById(paymentId);
        }

        @Override
        public List<Payment> getAllPayments() {
            return paymentRepository.findAll();
        }

    }
