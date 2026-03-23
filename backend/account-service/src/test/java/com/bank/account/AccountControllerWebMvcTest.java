package com.bank.account;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bank.account.service.BankAccountService;
import com.bank.account.web.AccountController;
import com.bank.account.web.RestExceptionHandler;
import com.bank.account.web.dto.AccountResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
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

@WebMvcTest(controllers = AccountController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(RestExceptionHandler.class)
class AccountControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BankAccountService accountService;

    @Test
    void listReturnsArray() throws Exception {
        var id = UUID.randomUUID();
        when(accountService.listAll())
                .thenReturn(
                        List.of(new AccountResponse(id, "GB82WEST12345698765432", "GBP", BigDecimal.TEN, "c1", Instant.now())));

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].iban").value("GB82WEST12345698765432"));
    }

    @Test
    void createValidatesBody() throws Exception {
        mockMvc.perform(
                        post("/api/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"iban\":\"x\",\"currency\":\"GBP\",\"customerId\":\"c\",\"initialBalance\":0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createReturns201() throws Exception {
        var id = UUID.randomUUID();
        when(accountService.create(any()))
                .thenReturn(
                        new AccountResponse(id, "GB82WEST12345698765432", "GBP", BigDecimal.ZERO, "c1", Instant.now()));

        mockMvc.perform(
                        post("/api/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new com.bank.account.web.dto.CreateAccountRequest(
                                                        "GB82WEST12345698765432",
                                                        "GBP",
                                                        "c1",
                                                        BigDecimal.ZERO))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }
}
