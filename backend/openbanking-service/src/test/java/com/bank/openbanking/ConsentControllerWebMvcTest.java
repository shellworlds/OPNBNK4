package com.bank.openbanking;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bank.openbanking.domain.ConsentStatus;
import com.bank.openbanking.service.ConsentService;
import com.bank.openbanking.web.ConsentController;
import com.bank.openbanking.web.RestExceptionHandler;
import com.bank.openbanking.web.dto.ConsentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ConsentController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(RestExceptionHandler.class)
class ConsentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConsentService consentService;

    @Test
    void list() throws Exception {
        UUID id = UUID.randomUUID();
        when(consentService.listAll())
                .thenReturn(
                        List.of(
                                new ConsentResponse(
                                        id, "tpp", List.of("accounts:read"), ConsentStatus.ACTIVE, "c", Instant.now(), null)));
        mockMvc.perform(get("/api/openbanking/consents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tppId").value("tpp"));
    }

    @Test
    void create201() throws Exception {
        UUID id = UUID.randomUUID();
        when(consentService.create(any()))
                .thenReturn(
                        new ConsentResponse(
                                id, "tpp", List.of("a"), ConsentStatus.ACTIVE, "c", Instant.now(), null));
        mockMvc.perform(
                        post("/api/openbanking/consents")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new com.bank.openbanking.web.dto.CreateConsentRequest(
                                                        "tpp", List.of("a"), "c", null))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.consentId").value(id.toString()));
    }
}
