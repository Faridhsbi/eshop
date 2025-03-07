package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {
    private Map<String, Payment> payments;

    public PaymentRepository() {
        this.payments = new HashMap<>();
    }

    public Payment findById(String id) {return null;}

    public Payment save(Payment payment) {return null;}

    public List<Payment> findAll() {return null;}

}