package dev.calvinstechsupport.inventory.device;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "network_devices", uniqueConstraints = {
        @UniqueConstraint(name = "uk_network_devices_ip_address", columnNames = "ip_address")
})
public class NetworkDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false, length = 30)
    private DeviceType deviceType;

    @Column(length = 80)
    private String manufacturer;

    @Column(length = 80)
    private String model;

    @Column(name = "ip_address", nullable = false, length = 45)
    private String ipAddress;

    @Column(length = 120)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DeviceStatus status;

    @Column(length = 1000)
    private String notes;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    protected NetworkDevice() {
    }

    public NetworkDevice(String name, DeviceType deviceType, String manufacturer, String model,
                         String ipAddress, String location, DeviceStatus status, String notes) {
        this.name = name;
        this.deviceType = deviceType;
        this.manufacturer = manufacturer;
        this.model = model;
        this.ipAddress = ipAddress;
        this.location = location;
        this.status = status;
        this.notes = notes;
    }

    @PrePersist
    void beforeInsert() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void beforeUpdate() {
        updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public void update(String name, DeviceType deviceType, String manufacturer, String model,
                       String ipAddress, String location, DeviceStatus status, String notes) {
        this.name = name;
        this.deviceType = deviceType;
        this.manufacturer = manufacturer;
        this.model = model;
        this.ipAddress = ipAddress;
        this.location = location;
        this.status = status;
        this.notes = notes;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public DeviceType getDeviceType() { return deviceType; }
    public String getManufacturer() { return manufacturer; }
    public String getModel() { return model; }
    public String getIpAddress() { return ipAddress; }
    public String getLocation() { return location; }
    public DeviceStatus getStatus() { return status; }
    public String getNotes() { return notes; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}
