package com.academia.sale;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SalesAnalyzer {
    public static void main(String[] args) {
        List<Sale> sales = List.of(
            new Sale("Laptop",  "Tech",   25000, "CDMX", LocalDate.of(2026, 1, 15)),
            new Sale("Phone",   "Tech",   15000, "GDL",  LocalDate.of(2026, 1, 20)),
            new Sale("Desk",    "Office",  8000, "CDMX", LocalDate.of(2026, 2, 1)),
            new Sale("Chair",   "Office",  5000, "MTY",  LocalDate.of(2026, 2, 10)),
            new Sale("Monitor", "Tech",   12000, "CDMX", LocalDate.of(2026, 2, 15)),
            new Sale("Tablet",  "Tech",   10000, "GDL",  LocalDate.of(2026, 1, 25)),
            new Sale("Lamp",    "Office",  2000, "MTY",  LocalDate.of(2026, 1, 30))
        );

        // 1. Ingreso total
        double total = sales.stream()
            .mapToDouble(amou -> amou.amount())
            .sum();
        System.out.println("=== Ingreso Total ===");
        System.out.printf("$%,.2f%n", total);

        // 2. Ingresos por categoria
        Map<String, Double> byCategory = sales.stream()
            .collect(Collectors.groupingBy(s -> s.category(), Collectors.summingDouble(s -> s.amount())));
        System.out.println("\n=== Ingresos por Categoria ===");
        byCategory.forEach((cat, sum) ->
            System.out.printf("  %s: $%,.2f%n", cat, sum));

        // 3. Top 3 productos por monto
        System.out.println("\n=== Top 3 Productos ===");
        sales.stream()
            .sorted(Comparator.comparingDouble(Sale::amount).reversed())
            .limit(3)
            .forEach(s -> System.out.printf("  %s: $%,.2f%n", s.product(), s.amount()));

        // 4. Ingresos por region (mayor a menor)
        System.out.println("\n=== Ingresos por Region ===");
        // TODO: groupingBy region, summingDouble, ordenar por valor descendente
        sales.stream()
            .collect(Collectors.groupingBy(Sale::region, Collectors.summingDouble(Sale::amount)))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .forEach(e -> System.out.printf("  %s: $%,.2f%n", e.getKey(), e.getValue()));

        // 5. Promedio por categoria
        Map<String, Double> avgByCategory = sales.stream()
            .collect(Collectors.groupingBy(Sale::category, Collectors.averagingDouble(Sale::amount)));
        System.out.println("\n=== Promedio por Categoria ===");
        avgByCategory.forEach((cat, avg) ->
            System.out.printf("  %s: $%,.2f%n", cat, avg));

        // 6. Producto mas caro por region
        System.out.println("\n=== Producto mas Caro por Region ===");
        // TODO: groupingBy region, maxBy comparando amount
        Map<String, Optional<Sale>> bestPerRegion = sales.stream()
            .collect(Collectors.groupingBy(Sale::region,
                Collectors.maxBy(Comparator.comparingDouble(Sale::amount))));
        bestPerRegion.forEach((region, sale) ->
            sale.ifPresent(s -> System.out.printf("  %s: %s ($%,.2f)%n",
                region, s.product(), s.amount())));

        // 7. Conteo por mes
        Map<Integer, Long> byMonth = sales.stream()
            .collect(Collectors.groupingBy(
                s -> s.date().getMonthValue(), Collectors.counting()));
        System.out.println("\n=== Ventas por Mes ===");
        byMonth.forEach((month, count) ->
            System.out.printf("  Mes %d: %d ventas%n", month, count));
    }
}