package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AutoRequestDTO {

    @NotBlank(message = "El modelo del auto es obligatorio")
    private String modelo;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "El año debe ser mayor o igual a 1900")
    private Integer anio;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;

    @NotNull(message = "El ID de la marca es obligatorio")
    private Long marcaId;

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Long getMarcaId() { return marcaId; }
    public void setMarcaId(Long marcaId) { this.marcaId = marcaId; }
}