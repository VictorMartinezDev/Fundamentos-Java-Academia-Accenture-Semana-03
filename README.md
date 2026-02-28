# Academia Java - Semana 03

Este repositorio contiene una serie de ejercicios y proyectos desarrollados durante la tercera semana de la Academia Java. Los proyectos abarcan desde el uso fundamental de colecciones y lambdas hasta la implementación de procesos Batch complejos con Spring Boot.

## Contenido del Repositorio

### 1. Gestor de Contactos con Collections
**Descripción:** Un sistema básico para la gestión de contactos que permite agregar, buscar y filtrar información de manera eficiente.
- **Conceptos usados:** Java Collections Framework (`TreeSet`, `List`, `Set`), Stream API (`filter`, `collect`, `sorted`), `Optional`, `Comparator` y expresiones Lambda.

### 2. Caché Genérico con Expiración
**Descripción:** Implementación de un sistema de caché genérico que permite almacenar objetos con un tiempo de vida definido (TTL), eliminando automáticamente las entradas expiradas.
- **Conceptos usados:** Genéricos (`<K, V>`), Java Records, `HashMap`, gestión de tiempo con `System.currentTimeMillis()` y limpieza "lazy".

### 3. Validador Composable con Lambdas
**Descripción:** Un motor de validación flexible que permite encadenar múltiples reglas de validación de forma declarativa.
- **Conceptos usados:** Interfaces Funcionales (`@FunctionalInterface`), Composición de funciones, Predicados y Lambdas.

### 4. Análisis de Ventas con Streams
**Descripción:** Herramienta de análisis de datos que procesa registros de ventas para generar estadísticas como ingresos totales, promedios por categoría y productos top.
- **Conceptos usados:** Stream API avanzado, `Collectors` (`groupingBy`, `summingDouble`, `averagingDouble`, `maxBy`), `LocalDate` y Comparadores.

### 5. Pipeline de Procesamiento de Texto
**Descripción:** Un pipeline para analizar grandes volúmenes de texto, calculando frecuencias de palabras, longitudes promedio y agrupaciones alfabéticas.
- **Conceptos usados:** `flatMap`, `distinct`, manipulación de Strings con Regex y Streams de datos.

### 6. Web Scraper Concurrente con CompletableFuture
**Descripción:** Simulador de web scraper que realiza peticiones de forma asíncrona y en paralelo, optimizando el tiempo de respuesta y gestionando tiempos de espera (timeouts).
- **Conceptos usados:** Programación Asíncrona (`CompletableFuture`), `ExecutorService`, manejo de concurrencia y tareas paralelas.

### 7. Proyecto Semana 03 - Batch V2
**Descripción:** Aplicación robusta de procesamiento por lotes utilizando Spring Batch. Realiza una migración de datos en dos etapas: primero importa datos desde un archivo CSV a una base de datos MySQL, y posteriormente procesa y transfiere esos datos hacia MongoDB.
- **Conceptos usados:** Spring Boot, Spring Batch (Jobs, Steps, Readers, Writers, Processors), Spring Data JPA (MySQL), Spring Data MongoDB y persistencia de datos.

---

**Autor:** Victor Manuel Martinez
