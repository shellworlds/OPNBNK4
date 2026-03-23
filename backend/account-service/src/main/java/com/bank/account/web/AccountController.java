package com.bank.account.web;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @GetMapping
    public List<Map<String, Object>> listAccounts() {
        return List.of(
                Map.of("id", "acc-001", "iban", "GB82WEST12345698765432", "currency", "GBP", "balance", 1250.50),
                Map.of("id", "acc-002", "iban", "GB29NWBK60161331926819", "currency", "EUR", "balance", 320.00));
    }
}
