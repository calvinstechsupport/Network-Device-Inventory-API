package dev.calvinstechsupport.inventory.device;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
@Tag(name = "Network devices", description = "Register, search and maintain network infrastructure inventory.")
public class NetworkDeviceController {
    private final NetworkDeviceService service;

    public NetworkDeviceController(NetworkDeviceService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List network devices", description = "Optionally filter by search text, device type and status.")
    public List<DeviceResponse> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) DeviceType type,
            @RequestParam(required = false) DeviceStatus status) {
        return service.findAll(q, type, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a network device by ID")
    public DeviceResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Register a network device")
    public ResponseEntity<DeviceResponse> create(@Valid @RequestBody CreateDeviceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Replace a network device's editable details")
    public DeviceResponse update(@PathVariable Long id, @Valid @RequestBody UpdateDeviceRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a network device")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
