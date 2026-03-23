package com.bank.openbanking;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.hasSize;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class OpenbankingPostgresIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("openbankingdb")
                    .withUsername("bank")
                    .withPassword("bank");

    @DynamicPropertySource
    static void registerDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void persistsPublicConsentSamples() throws Exception {
        JsonNode root = objectMapper.readTree(getClass().getResourceAsStream("/public-banking-samples.json"));
        JsonNode consents = root.get("consents");

        for (JsonNode c : consents) {
            var payload = objectMapper.createObjectNode();
            payload.put("tppId", c.get("tppId").asText());
            payload.put("customerId", c.get("customerId").asText());
            ArrayNode scopes = objectMapper.createArrayNode();
            for (JsonNode s : c.get("scopes")) {
                scopes.add(s.asText());
            }
            payload.set("scopes", scopes);
            if (c.hasNonNull("validUntil")) {
                payload.put("validUntil", c.get("validUntil").asText());
            }

            mockMvc.perform(
                            post("/api/openbanking/consents")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(payload)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.tppId").value(c.get("tppId").asText()));
        }

        mockMvc.perform(get("/api/openbanking/consents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(consents.size())));
    }
}
