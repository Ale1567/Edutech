package com.example.gestionProveedores.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestionProveedores.models.entities.Proveedor;
import com.example.gestionProveedores.models.request.ProveedorRequest;
import com.example.gestionProveedores.repositories.ProveedorRepository;

@Service

public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;
    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }
    public Proveedor guardar(ProveedorRequest request) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombreEmpresa(request.getNombreEmpresa());
        proveedor.setRuc(request.getRuc());
        proveedor.setCategoria(request.getCategoria());
        proveedor.setContactoEmail(request.getContactoEmail());
        
        // Por defecto, al crearlo lo marcamos como ACTIVO
        proveedor.setEstado("ACTIVO");
        
        return proveedorRepository.save(proveedor);
    }

    public Optional<Proveedor> obtenerPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }
}
