package com.bank.transaction;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bank.transaction.service.LedgerTransactionService;
import com.bank.transaction.web.RestExceptionHandler;
import com.bank.transaction.web.TransactionController;
import com.bank.transaction.web.dto.TransactionResponse;
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

@WebMvcTest(controllers = TransactionController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(RestExceptionHandler.class)
class TransactionControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LedgerTransactionService transactionService;

    @Test
    void listAll() throws Exception {
        UUID id = UUID.randomUUID();
        UUID acc = UUID.randomUUID();
        when(transactionService.listAll())
                .thenReturn(List.of(new TransactionResponse(id, acc, BigDecimal.ONE, "GBP", null, Instant.now())));
        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].currency").value("GBP"));
    }

    @Test
    void listByAccountParam() throws Exception {
        UUID acc = UUID.randomUUID();
        when(transactionService.listForAccount(acc)).thenReturn(List.of());
        mockMvc.perform(get("/api/transactions").param("accountId", acc.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void createReturns201() throws Exception {
        UUID id = UUID.randomUUID();
        UUID acc = UUID.randomUUID();
        when(transactionService.create(any()))
                .thenReturn(new TransactionResponse(id, acc, BigDecimal.TEN, "EUR", "x", Instant.now()));
        mockMvc.perform(
                        post("/api/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new com.bank.transaction.web.dto.CreateTransactionRequest(
                                                        acc, new BigDecimal("10.50"), "eur", "payment"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.currency").value("EUR"));
    }
}
