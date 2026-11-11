package dev.calvinstechsupport.inventory.device;

import java.time.OffsetDateTime;

public record DeviceResponse(
        Long id,
        String name,
        DeviceType deviceType,
        String manufacturer,
        String model,
        String ipAddress,
        String location,
        DeviceStatus status,
        String notes,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public static DeviceResponse from(NetworkDevice device) {
        return new DeviceResponse(
                device.getId(), device.getName(), device.getDeviceType(), device.getManufacturer(),
                device.getModel(), device.getIpAddress(), device.getLocation(), device.getStatus(),
                device.getNotes(), device.getCreatedAt(), device.getUpdatedAt()
        );
    }
}
