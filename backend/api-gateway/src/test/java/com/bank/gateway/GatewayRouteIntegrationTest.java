package com.bank.gateway;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class GatewayRouteIntegrationTest {

    static final WireMockServer WIRE_MOCK =
            new WireMockServer(WireMockConfiguration.options().port(8765));

    static {
        WIRE_MOCK.start();
    }

    @AfterAll
    static void stopWireMock() {
        WIRE_MOCK.stop();
    }

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void resetStubs() {
        WIRE_MOCK.resetAll();
    }

    @Test
    void proxiesAccountsPathToUpstream() {
        WIRE_MOCK.stubFor(
                get(urlPathEqualTo("/api/accounts"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                "[{\"id\":\"11111111-1111-1111-1111-111111111111\",\"iban\":\"GB82TEST\",\"currency\":\"GBP\",\"balance\":1,\"customerId\":\"c\",\"createdAt\":\"2026-01-01T00:00:00Z\"}]")));

        webTestClient
                .get()
                .uri("/api/accounts")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0].iban")
                .isEqualTo("GB82TEST");
    }
}
