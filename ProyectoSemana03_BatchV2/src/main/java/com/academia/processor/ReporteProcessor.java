package com.academia.processor;

import org.springframework.batch.infrastructure.item.ItemProcessor;

import com.academia.model.Producto;
import com.academia.model.ProductoReporte;

public class ReporteProcessor implements ItemProcessor<Producto, ProductoReporte> {
    @Override
    public ProductoReporte process(Producto item) {
        ProductoReporte reporte = new ProductoReporte();
        reporte.setNombre(item.getNombre());
        
        // Lógica de negocio
        reporte.setPrecioConIva(item.getPrecio() * 1.21);
        reporte.setValorInventarioTotal(item.getPrecio() * item.getStock());
        
        if (item.getStock() < 10) {
            reporte.setEstadoStock("REABASTECIMIENTO_URGENTE");
        } else {
            reporte.setEstadoStock("ESTABLE");
        }
        
        return reporte;
    }
}