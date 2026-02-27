package com.academia.sale;

import java.time.LocalDate;

record Sale(String product, String category, double amount,
        String region, LocalDate date) {}