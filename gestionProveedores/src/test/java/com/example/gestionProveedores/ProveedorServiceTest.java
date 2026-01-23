package com.example.gestionProveedores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.gestionProveedores.models.entities.Proveedor;
import com.example.gestionProveedores.models.request.ProveedorRequest;
import com.example.gestionProveedores.repositories.ProveedorRepository;
import com.example.gestionProveedores.services.ProveedorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @Test
    public void alGuardarProveedor_debeAsignarEstadoActivo() {
        // Datos de prueba
        ProveedorRequest request = new ProveedorRequest();
        request.setNombreEmpresa("Tech Corp");
        
        // Simulamos que el repositorio devuelve un proveedor con lo que guardó
        when(proveedorRepository.save(any(Proveedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ejecutar
        Proveedor resultado = proveedorService.guardar(request);

        // Verificar lógica de negocio
        assertEquals("ACTIVO", resultado.getEstado(), "El proveedor nuevo debería nacer como ACTIVO");
    }
}
