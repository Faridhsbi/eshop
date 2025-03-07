package id.ac.ui.cs.advprog.eshop.enums;

public enum PaymentStatus {
    SUCCESS("SUCCESS"),
    REJECTED("REJECTED");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean contains(String param) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.getValue().equalsIgnoreCase(param)) {
                return true;
            }
        }
        return false;
    }
}
