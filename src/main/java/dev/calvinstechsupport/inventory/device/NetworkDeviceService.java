package dev.calvinstechsupport.inventory.device;

import dev.calvinstechsupport.inventory.shared.DuplicateIpAddressException;
import dev.calvinstechsupport.inventory.shared.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class NetworkDeviceService {
    private final NetworkDeviceRepository repository;

    public NetworkDeviceService(NetworkDeviceRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<DeviceResponse> findAll(String search, DeviceType type, DeviceStatus status) {
        Specification<NetworkDevice> specification = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (search != null && !search.isBlank()) {
            String pattern = "%" + search.trim().toLowerCase(Locale.ROOT) + "%";
            specification = specification.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("manufacturer")), pattern),
                    cb.like(cb.lower(root.get("model")), pattern),
                    cb.like(cb.lower(root.get("ipAddress")), pattern),
                    cb.like(cb.lower(root.get("location")), pattern)
            ));
        }
        if (type != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("deviceType"), type));
        }
        if (status != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        return repository.findAll(specification, Sort.by(Sort.Direction.ASC, "name"))
                .stream().map(DeviceResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public DeviceResponse findById(Long id) {
        return DeviceResponse.from(getEntity(id));
    }

    public DeviceResponse create(CreateDeviceRequest request) {
        String ipAddress = IpAddressValidator.normalize(request.ipAddress());
        ensureIpAddressAvailable(ipAddress, null);
        NetworkDevice device = new NetworkDevice(
                request.name().trim(), request.deviceType(), clean(request.manufacturer()), clean(request.model()),
                ipAddress, clean(request.location()), request.status(), clean(request.notes())
        );
        return DeviceResponse.from(repository.save(device));
    }

    public DeviceResponse update(Long id, UpdateDeviceRequest request) {
        NetworkDevice device = getEntity(id);
        String ipAddress = IpAddressValidator.normalize(request.ipAddress());
        ensureIpAddressAvailable(ipAddress, id);
        device.update(
                request.name().trim(), request.deviceType(), clean(request.manufacturer()), clean(request.model()),
                ipAddress, clean(request.location()), request.status(), clean(request.notes())
        );
        return DeviceResponse.from(repository.save(device));
    }

    public void delete(Long id) {
        NetworkDevice device = getEntity(id);
        repository.delete(device);
    }

    private NetworkDevice getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Network device " + id + " was not found."));
    }

    private void ensureIpAddressAvailable(String ipAddress, Long currentId) {
        boolean exists = currentId == null
                ? repository.existsByIpAddressIgnoreCase(ipAddress)
                : repository.existsByIpAddressIgnoreCaseAndIdNot(ipAddress, currentId);
        if (exists) {
            throw new DuplicateIpAddressException("IP address " + ipAddress + " is already assigned to another device.");
        }
    }

    private String clean(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
