package com.academia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reporte_productos")
public class ProductoReporte {
	
	@Id
	private String id;
	private String nombre;
	private Double precioConIva;
	private Double valorInventarioTotal;
	private String estadoStock;
	
	public ProductoReporte() {}

	public String getId() {
		return id;
	}





	public void setId(String id) {
		this.id = id;
	}





	public String getNombre() {
		return nombre;
	}





	public void setNombre(String nombre) {
		this.nombre = nombre;
	}





	public Double getPrecioConIva() {
		return precioConIva;
	}





	public void setPrecioConIva(Double precioConIva) {
		this.precioConIva = precioConIva;
	}





	public Double getValorInventarioTotal() {
		return valorInventarioTotal;
	}





	public void setValorInventarioTotal(Double valorInventarioTotal) {
		this.valorInventarioTotal = valorInventarioTotal;
	}





	public String getEstadoStock() {
		return estadoStock;
	}





	public void setEstadoStock(String estadoStock) {
		this.estadoStock = estadoStock;
	}





	@Override
	public String toString() {
		return id + "||" + nombre + "||" + precioConIva + "||" + estadoStock;
	}
	
	
}
