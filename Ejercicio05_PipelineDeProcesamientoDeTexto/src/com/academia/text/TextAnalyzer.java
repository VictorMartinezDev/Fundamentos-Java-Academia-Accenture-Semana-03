package com.academia.text;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextAnalyzer {
    private final List<String> lines;

    public TextAnalyzer(List<String> lines) {
        this.lines = lines;
    }

    private Stream<String> words() {
        // TODO: flatMap cada linea en sus palabras (split por espacios)
        return lines.stream()
            .flatMap(line -> Arrays.stream(line.split("\\s+")))
            .filter(w -> !w.isEmpty());
    }

    private Stream<String> cleanWords() {
        // TODO: palabras en minusculas, solo caracteres alfabeticos
        return words()
            .map(w -> w.replaceAll("[^a-zA-Z]", ""))
            .filter(w -> !w.isEmpty())
            .map(s -> s.toLowerCase());
    }

    public long wordCount() {
        // TODO: contar total de palabras
        return words().count();
    }

    public Set<String> uniqueWords() {
        // TODO: palabras unicas (limpiadas)
        return cleanWords().collect(Collectors.toSet());
    }

    public List<Map.Entry<String, Long>> topN(int n) {
        // TODO: agrupar por palabra, contar, ordenar desc, limitar a n
        return cleanWords()
            .collect(Collectors.groupingBy(s -> s , Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(n)
            .collect(Collectors.toList());
    }

    public double averageWordLength() {
        // TODO: longitud promedio de palabras limpias
        return cleanWords()
            .mapToInt(s -> s.length())
            .average()
            .orElse(0.0);
    }

    public Map<Character, List<String>> wordsByFirstLetter() {
        // TODO: agrupar palabras unicas por primera letra
        return cleanWords()
            .distinct()
            .collect(Collectors.groupingBy(w ->  w.charAt(0)));
    }

    public static void main(String[] args) {
        List<String> text = List.of(
            "Java is a powerful programming language",
            "Java streams make data processing elegant",
            "Lambdas and streams are the heart of modern Java"
        );

        TextAnalyzer analyzer = new TextAnalyzer(text);

        System.out.println("=== Estadisticas de Texto ===");
        System.out.println("Total palabras: " + analyzer.wordCount());
        System.out.println("Palabras unicas: " + analyzer.uniqueWords().size());
        System.out.printf("Longitud promedio: %.2f%n", analyzer.averageWordLength());

        System.out.println("\n=== Top 5 Palabras ===");
        analyzer.topN(5).forEach(e ->
            System.out.printf("  '%s': %d veces%n", e.getKey(), e.getValue()));

        System.out.println("\n=== Palabras por Letra ===");
        analyzer.wordsByFirstLetter().entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(e -> System.out.printf("  %c: %s%n", e.getKey(), e.getValue()));
    }
}
