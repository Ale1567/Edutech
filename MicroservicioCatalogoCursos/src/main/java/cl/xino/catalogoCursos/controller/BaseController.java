package cl.xino.catalogoCursos.controller;

import cl.xino.catalogoCursos.models.dto.VersionInfo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/")
public class BaseController {


    @Value("${app.name}")
    private String nombre;

    @Value("${app.version}")
    private String version;




    @GetMapping("")
    public VersionInfo base() {
        return new VersionInfo(nombre,version);
    }
    
    
}
