package dev.calvinstechsupport.inventory.device;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

public class IpAddressValidator implements ConstraintValidator<IpAddress, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // @NotBlank reports missing values separately.
        }
        String candidate = value.trim();
        if (!candidate.matches("[0-9a-fA-F:.]+")) {
            return false; // Numeric syntax only: never resolve host names or perform DNS lookups.
        }
        if (!candidate.contains(":")) {
            String[] octets = candidate.split("\\.", -1);
            if (octets.length != 4) {
                return false;
            }
            for (String octet : octets) {
                if (octet.isEmpty() || octet.length() > 3 || (octet.length() > 1 && octet.startsWith("0"))) {
                    return false;
                }
                try {
                    int number = Integer.parseInt(octet);
                    if (number < 0 || number > 255) {
                        return false;
                    }
                } catch (NumberFormatException exception) {
                    return false;
                }
            }
            return true;
        }
        try {
            return InetAddress.getByName(candidate).getAddress().length == 16;
        } catch (UnknownHostException exception) {
            return false;
        }
    }

    static String normalize(String value) {
        try {
            return InetAddress.getByName(value.trim()).getHostAddress().toLowerCase(Locale.ROOT);
        } catch (UnknownHostException exception) {
            // Request validation should catch this first; keep a defensive failure if called elsewhere.
            throw new IllegalArgumentException("Invalid IP address", exception);
        }
    }
}
