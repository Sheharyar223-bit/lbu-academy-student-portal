package com.lbu.academy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class AccountService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${finance.service.url:http://localhost:8081}")
    private String financeUrl;

    @Value("${library.service.url:http://localhost:8082}")
    private String libraryUrl;

    public void createFinanceAccount(String studentId) {
        try {
            restTemplate.postForObject(
                    financeUrl + "/api/accounts",
                    Map.of("studentId", studentId),
                    Object.class
            );
        } catch (Exception e) {
            System.out.println("Finance account creation failed: " + e.getMessage());
        }
    }

    public void createInvoice(String studentId, double amount) {
        try {
            restTemplate.postForObject(
                    financeUrl + "/api/invoices",
                    Map.of("studentId", studentId, "amount", amount),
                    Object.class
            );
        } catch (Exception e) {
            System.out.println("Invoice creation failed: " + e.getMessage());
        }
    }

    public boolean hasOutstandingBalance(String studentId) {
        try {
            Map response = restTemplate.getForObject(
                    financeUrl + "/api/accounts/" + studentId,
                    Map.class
            );
            return response != null && Boolean.TRUE.equals(response.get("hasOutstandingBalance"));
        } catch (Exception e) {
            System.out.println("Finance check failed: " + e.getMessage());
            return false;
        }
    }

    public void createLibraryAccount(String studentId) {
        try {
            restTemplate.postForObject(
                    libraryUrl + "/api/library/accounts",
                    Map.of("studentId", studentId),
                    Object.class
            );
        } catch (Exception e) {
            System.out.println("Library account creation failed: " + e.getMessage());
        }
    }
}