package com.bank.account;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bank.account.domain.AccountStatus;
import com.bank.account.domain.AccountType;
import com.bank.account.service.AccountService;
import com.bank.account.web.AccountController;
import com.bank.account.web.RestExceptionHandler;
import com.bank.account.web.dto.AccountResponse;
import com.bank.account.web.dto.CreateAccountRequest;
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
    private AccountService accountService;

    @Test
    void listByCustomerReturnsArray() throws Exception {
        var id = UUID.randomUUID();
        when(accountService.listForCustomer("c1"))
                .thenReturn(
                        List.of(new AccountResponse(
                                id, "c1", "GB82WEST12345698765432", AccountType.CHECKING, "GBP", BigDecimal.TEN, AccountStatus.ACTIVE, Instant.now(), 0L)));

        mockMvc.perform(get("/api/accounts/customer/c1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountNumber").value("GB82WEST12345698765432"));
    }

    @Test
    void createValidatesBody() throws Exception {
        mockMvc.perform(
                        post("/api/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"customerId\":\"x\",\"accountNumber\":\"ab\",\"accountType\":\"CHECKING\",\"currency\":\"GB\",\"initialBalance\":0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createReturns201() throws Exception {
        var id = UUID.randomUUID();
        when(accountService.createAccount(any()))
                .thenReturn(
                        new AccountResponse(
                                id, "c1", "GB82WEST12345698765432", AccountType.CHECKING, "GBP", BigDecimal.ZERO, AccountStatus.ACTIVE, Instant.now(), 0L));

        var payload = new CreateAccountRequest("c1", "GB82WEST12345698765432", AccountType.CHECKING, "GBP", BigDecimal.ZERO);

        mockMvc.perform(
                        post("/api/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void getById() throws Exception {
        var id = UUID.randomUUID();
        when(accountService.getAccount(eq(id)))
                .thenReturn(
                        new AccountResponse(
                                id, "c", "N1", AccountType.SAVINGS, "EUR", BigDecimal.ONE, AccountStatus.ACTIVE, Instant.now(), 1L));
        mockMvc.perform(get("/api/accounts/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.accountNumber").value("N1"));
    }
}
