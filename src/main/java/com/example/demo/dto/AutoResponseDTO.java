package com.example.demo.dto;

public class AutoResponseDTO {
    private Long id;
    private String modelo;
    private Integer anio;
    private Double precio;
    private Long marcaId;
    private String marcaNombre;

    public AutoResponseDTO(Long id, String modelo, Integer anio, Double precio, Long marcaId, String marcaNombre) {
        this.id = id;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.marcaId = marcaId;
        this.marcaNombre = marcaNombre;
    }

    public Long getId() { return id; }
    public String getModelo() { return modelo; }
    public Integer getAnio() { return anio; }
    public Double getPrecio() { return precio; }
    public Long getMarcaId() { return marcaId; }
    public String getMarcaNombre() { return marcaNombre; }
}