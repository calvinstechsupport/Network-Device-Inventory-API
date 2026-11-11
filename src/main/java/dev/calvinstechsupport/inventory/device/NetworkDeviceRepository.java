package dev.calvinstechsupport.inventory.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NetworkDeviceRepository extends JpaRepository<NetworkDevice, Long>,
        JpaSpecificationExecutor<NetworkDevice> {
    boolean existsByIpAddressIgnoreCase(String ipAddress);
    boolean existsByIpAddressIgnoreCaseAndIdNot(String ipAddress, Long id);
}
