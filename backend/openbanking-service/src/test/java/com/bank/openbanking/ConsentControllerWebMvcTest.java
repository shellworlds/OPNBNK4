package com.bank.openbanking;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bank.openbanking.domain.ConsentStatus;
import com.bank.openbanking.service.ConsentService;
import com.bank.openbanking.web.OpenbankingConsentController;
import com.bank.openbanking.web.RestExceptionHandler;
import com.bank.openbanking.web.dto.ConsentResponse;
import com.bank.openbanking.web.dto.CreateConsentRequest;
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

@WebMvcTest(controllers = OpenbankingConsentController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(RestExceptionHandler.class)
class ConsentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConsentService consentService;

    @Test
    void create201() throws Exception {
        UUID id = UUID.randomUUID();
        when(consentService.create(any()))
                .thenReturn(
                        new ConsentResponse(
                                id,
                                "c",
                                "tpp",
                                List.of("a"),
                                ConsentStatus.ACTIVE,
                                Instant.now(),
                                null,
                                Instant.now()));
        mockMvc.perform(
                        post("/openbanking/consents")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new CreateConsentRequest("c", "tpp", List.of("a"), null, null, null))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.consentId").value(id.toString()));
    }

    @Test
    void getById() throws Exception {
        UUID id = UUID.randomUUID();
        when(consentService.getById(id))
                .thenReturn(
                        new ConsentResponse(
                                id,
                                "c",
                                "tpp",
                                List.of("x"),
                                ConsentStatus.ACTIVE,
                                Instant.now(),
                                null,
                                Instant.now()));
        mockMvc.perform(get("/openbanking/consents/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.tppId").value("tpp"));
    }
}
