package dev.calvinstechsupport.inventory.shared;

public class DuplicateIpAddressException extends RuntimeException {
    public DuplicateIpAddressException(String message) {
        super(message);
    }
}
