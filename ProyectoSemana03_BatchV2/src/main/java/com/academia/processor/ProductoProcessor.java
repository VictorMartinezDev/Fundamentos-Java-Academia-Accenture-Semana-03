package com.academia.processor;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.infrastructure.item.ItemProcessor;

import com.academia.model.Producto;

public class ProductoProcessor implements ItemProcessor<Producto, Producto>{
	
	private static final Logger log = LoggerFactory.getLogger(ProductoProcessor.class);

	@Override
	public @Nullable Producto process(Producto item) throws Exception {
		//Lógica de negocio 1 si no hay stock del producto devolvemos null
		//así el batch se salta el registro
		if(item.getStock() == 0) return null;
		
		//A todos los productos que pertenezcan a la categoría de "cables" o "adaptadores"
		//tienen un aumento del 15% en su precio
		if(item.getNombre()== null) {
			log.info("Registro invalido campo nombre vacio: {}", item);
			return null;
		}
		else if (item.getNombre().toLowerCase().contains("cable") || item.getNombre().toLowerCase().contains("adaptador")){
				item.setPrecio(item.getPrecio() *1.15);	
		}
		
		// Verificamos si el precio es mayor a 100
		//si es true el producto se considera PREMIUM y se concatena en su nombre
		if(item.getPrecio() == null) { 
			log.info("Registro invalido campo precio vacio: {}", item);
			throw new NullPointerException("Registro precio del producto " + item.getNombre() + " con id "
											+ item.getId() + "está vacio");
		}
		else if(item.getPrecio()>100.0) item.setNombre("[PREMIUM] - "+item.getNombre());
		
		log.info("Item procesado: {}", item);
		return item;
	}

}
