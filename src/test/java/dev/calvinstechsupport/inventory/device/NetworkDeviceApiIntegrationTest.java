package dev.calvinstechsupport.inventory.device;

import tools.jackson.databind.json.JsonMapper;
import dev.calvinstechsupport.inventory.NetworkDeviceInventoryApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = NetworkDeviceInventoryApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NetworkDeviceApiIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private JsonMapper objectMapper;
    @Autowired private NetworkDeviceRepository repository;

    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
    }

    @Test
    void createsListsAndFindsDevice() throws Exception {
        mockMvc.perform(post("/api/v1/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(device("Core Router", "192.168.10.1", "ACTIVE"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Core Router"))
                .andExpect(jsonPath("$.deviceType").value("ROUTER"));

        mockMvc.perform(get("/api/v1/devices").param("q", "core"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/api/v1/devices").param("status", "ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ipAddress").value("192.168.10.1"));
    }

    @Test
    void updatesAndDeletesDevice() throws Exception {
        String body = mockMvc.perform(post("/api/v1/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(device("Access Point", "192.168.10.2", "ACTIVE"))))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        long id = objectMapper.readTree(body).get("id").asLong();

        mockMvc.perform(get("/api/v1/devices/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Access Point"));

        mockMvc.perform(put("/api/v1/devices/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(device("Lab Access Point", "192.168.10.2", "MAINTENANCE"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lab Access Point"))
                .andExpect(jsonPath("$.status").value("MAINTENANCE"));

        mockMvc.perform(delete("/api/v1/devices/{id}", id))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/devices/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void rejectsDuplicateIpAddress() throws Exception {
        mockMvc.perform(post("/api/v1/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(device("Router One", "192.168.10.10", "ACTIVE"))))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(device("Router Two", "192.168.10.10", "ACTIVE"))))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Duplicate IP address"));
    }

    @Test
    void rejectsInvalidIpAddress() throws Exception {
        mockMvc.perform(post("/api/v1/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Bad Router\",\"deviceType\":\"ROUTER\",\"ipAddress\":\"999.999.1.1\",\"status\":\"ACTIVE\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Validation failed"))
                .andExpect(jsonPath("$.fieldErrors.ipAddress").exists());
    }

    @Test
    void rejectsMissingRequiredFields() throws Exception {
        mockMvc.perform(post("/api/v1/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.name").exists())
                .andExpect(jsonPath("$.fieldErrors.deviceType").exists());
    }

    @Test
    void acceptsIpv6AndStoresItsCanonicalForm() throws Exception {
        mockMvc.perform(post("/api/v1/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(device("IPv6 Router", "2001:0DB8:0:0:0:0:0:1", "ACTIVE"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ipAddress").value("2001:db8:0:0:0:0:0:1"));
    }

    @Test
    void returnsNotFoundForUnknownDevice() throws Exception {
        mockMvc.perform(get("/api/v1/devices/999999"))
                .andExpect(status().isNotFound());
    }

    private Map<String, Object> device(String name, String ipAddress, String status) {
        return Map.of(
                "name", name,
                "deviceType", "ROUTER",
                "manufacturer", "Cisco",
                "model", "ISR 1000",
                "ipAddress", ipAddress,
                "location", "CPUT Bellville Lab",
                "status", status,
                "notes", "Demonstration inventory data only"
        );
    }

    private String json(Object value) throws Exception {
        return objectMapper.writeValueAsString(value);
    }
}
