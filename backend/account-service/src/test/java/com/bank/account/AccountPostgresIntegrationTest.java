package com.bank.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bank.account.domain.AccountType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hamcrest.Matchers;
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

@SpringBootTest(
        properties = {
            "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration",
            "day2.kafka.consumer.enabled=false",
            "spring.flyway.enabled=true",
            "spring.jpa.hibernate.ddl-auto=validate"
        })
@AutoConfigureMockMvc
@Testcontainers
class AccountPostgresIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16-alpine").withDatabaseName("accountdb").withUsername("bank").withPassword("bank");

    @DynamicPropertySource
    static void registerDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void persistsPublicIbanFixtureSamples() throws Exception {
        JsonNode root = objectMapper.readTree(getClass().getResourceAsStream("/public-banking-samples.json"));
        JsonNode accounts = root.get("accounts");
        assertThat(accounts.isArray()).isTrue();

        for (JsonNode acc : accounts) {
            ObjectNode payload = objectMapper.createObjectNode();
            payload.put("customerId", acc.get("customerId").asText());
            payload.put("accountNumber", acc.get("iban").asText());
            payload.put("accountType", "CHECKING");
            payload.put("currency", acc.get("currency").asText());
            payload.put("initialBalance", acc.get("initialBalance").asDouble());

            mockMvc.perform(
                            post("/api/accounts")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(payload)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.accountNumber").value(acc.get("iban").asText()));
        }

        String customerId = accounts.get(0).get("customerId").asText();
        mockMvc.perform(get("/api/accounts/customer/" + customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }
}
