package com.academia.config;


import java.util.Collections;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.data.MongoItemWriter;
import org.springframework.batch.infrastructure.item.data.RepositoryItemReader;
import org.springframework.batch.infrastructure.item.data.RepositoryItemWriter;
import org.springframework.batch.infrastructure.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.infrastructure.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.infrastructure.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.academia.model.Producto;
import com.academia.model.ProductoReporte;
import com.academia.processor.ProductoProcessor;
import com.academia.processor.ReporteProcessor;
import com.academia.repository.ProductoRepository;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;

@Configuration
public class BatchConfig {
	
	@Bean
	public MongoTemplate mongoTemplate() {
	    // Forzamos la URI manualmente en el código
	    ConnectionString connectionString = new ConnectionString("mongodb://root:vic190922.@localhost:27018/academia?authSource=admin");
	    MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
	        .applyConnectionString(connectionString)
	        .build();
	    return new MongoTemplate(MongoClients.create(mongoClientSettings), "academia");
	}
	
	@Bean
    public FlatFileItemReader<Producto> leerCsv() {
        return new FlatFileItemReaderBuilder<Producto>()
                .name("productoReader")
                .resource(new ClassPathResource("productos.csv"))
                .delimited()                          // separado por comas
                .names("id", "nombre", "precio", "stock") // columnas del CSV
                .targetType(Producto.class)            // mapea a nuestro POJO
                .linesToSkip(1)                        // saltar la linea de encabezado
                .build();
    }
	
	@Bean
    public ProductoProcessor procesarProducto() {
        return new ProductoProcessor();
    }
	
	@Bean
	public RepositoryItemWriter<Producto> writer(ProductoRepository repository) {
	    return new RepositoryItemWriterBuilder<Producto>()
	            .repository(repository)
	            .methodName("save") 
	            .build();
	}
	
	@Bean
    public Step step1(JobRepository jobRepository, 
                     PlatformTransactionManager transactionManager,
                     ProductoRepository repository) {
        return new StepBuilder("importar productos", jobRepository)
                .<Producto, Producto>chunk(10, transactionManager) // Procesa de 10 en 10
                .reader(leerCsv())
                .processor(procesarProducto())
                .writer(writer(repository))
                .build();
    }
	
	@Bean
	public RepositoryItemReader<Producto> leerDeRepo(ProductoRepository repository) {
	    return new RepositoryItemReaderBuilder<Producto>()
	            .name("productoRepoReader")
	            .repository(repository)
	            .methodName("findAll") 
	            .pageSize(10)          
	            .sorts(Collections.singletonMap("id", Sort.Direction.ASC)) 
	            .build();
	}
	
	@Bean
	public ReporteProcessor procesarReporte() {
		return new ReporteProcessor();
	}
	
	@Bean
	public MongoItemWriter<ProductoReporte> escribirEnMongo(MongoTemplate mongoTemplate) {
	    return new MongoItemWriterBuilder<ProductoReporte>()
	            .template(mongoTemplate)
	            .collection("reporte_productos") 
	            .build();
	}
	
	@Bean
	public Step step2(JobRepository jobRepository, 
	                  PlatformTransactionManager transactionManager,
	                  RepositoryItemReader<Producto> reader,
	                  MongoItemWriter<ProductoReporte> writer) {
	    
	    return new StepBuilder("paso-jpa-a-mongo", jobRepository)
	            .<Producto, ProductoReporte>chunk(10, transactionManager)
	            .reader(reader)
	            .processor(procesarReporte()) // Tu lógica de IVA y Valor Total
	            .writer(writer)
	            .build();
	}

    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1, Step step2) {
        return new JobBuilder("import-productos-job", jobRepository)
                .start(step1)
                .next(step2)
                .build();
    }
	
	

}
