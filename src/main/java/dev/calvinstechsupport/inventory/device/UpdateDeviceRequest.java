package dev.calvinstechsupport.inventory.device;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateDeviceRequest(
        @NotBlank(message = "name is required")
        @Size(max = 120, message = "name must be at most 120 characters")
        String name,

        @NotNull(message = "deviceType is required")
        DeviceType deviceType,

        @Size(max = 80, message = "manufacturer must be at most 80 characters")
        String manufacturer,

        @Size(max = 80, message = "model must be at most 80 characters")
        String model,

        @NotBlank(message = "ipAddress is required")
        @Size(max = 45, message = "ipAddress must be at most 45 characters")
        @IpAddress
        String ipAddress,

        @Size(max = 120, message = "location must be at most 120 characters")
        String location,

        @NotNull(message = "status is required")
        DeviceStatus status,

        @Size(max = 1000, message = "notes must be at most 1000 characters")
        String notes
) {
}
