package com.academia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ProyectoSemana02BatchApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ProyectoSemana02BatchApplication.class, args);
        
        // Obtenemos el entorno para ver las propiedades cargadas
        Environment env = ctx.getBean(Environment.class);
        String mongoUri = env.getProperty("spring.data.mongodb.uri");
        
        System.out.println("=========================================");
        System.out.println("URI DE MONGO CARGADA: " + mongoUri);
        System.out.println("=========================================");
    }
}
