package cl.xino.catalogoCursos.models.requests;

public class ContenidoRequest {
    private Long id;
    private String titulo;
    private String rutaArchivo;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getRutaArchivo() { return rutaArchivo; }
    public void setRutaArchivo(String rutaArchivo) { this.rutaArchivo = rutaArchivo; }
}