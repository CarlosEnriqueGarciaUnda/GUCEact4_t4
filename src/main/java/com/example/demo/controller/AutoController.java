package com.example.demo.controller;

import com.example.demo.dto.AutoRequestDTO;
import com.example.demo.dto.AutoResponseDTO;
import com.example.demo.model.Auto;
import com.example.demo.model.Marca;
import com.example.demo.repository.AutoRepository;
import com.example.demo.repository.MarcaRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/autos")
public class AutoController {

    private final AutoRepository autoRepository;
    private final MarcaRepository marcaRepository;

    public AutoController(AutoRepository autoRepository, MarcaRepository marcaRepository) {
        this.autoRepository = autoRepository;
        this.marcaRepository = marcaRepository;
    }

    // a) GET para listar con paginación (ej. /api/autos?page=0&size=5)
    @GetMapping
    public ResponseEntity<Page<AutoResponseDTO>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AutoResponseDTO> autos = autoRepository.findAll(pageable).map(this::convertirADTO);
        return ResponseEntity.ok(autos);
    }

    // a) GET para ver un registro individual
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Auto> autoOpt = autoRepository.findById(id);
        if (autoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Auto no encontrado con ID: " + id));
        }
        return ResponseEntity.ok(convertirADTO(autoOpt.get()));
    }

    // b) POST para crear
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody AutoRequestDTO request) {
        Optional<Marca> marcaOpt = marcaRepository.findById(request.getMarcaId());
        if (marcaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No existe una marca con ID: " + request.getMarcaId()));
        }

        Auto auto = new Auto();
        auto.setModelo(request.getModelo());
        auto.setAnio(request.getAnio());
        auto.setPrecio(request.getPrecio());
        auto.setMarca(marcaOpt.get());

        Auto guardado = autoRepository.save(auto);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(guardado));
    }

    // c) PUT para actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody AutoRequestDTO request) {
        Optional<Auto> autoOpt = autoRepository.findById(id);
        if (autoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Auto no encontrado con ID: " + id));
        }

        Optional<Marca> marcaOpt = marcaRepository.findById(request.getMarcaId());
        if (marcaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No existe una marca con ID: " + request.getMarcaId()));
        }

        Auto auto = autoOpt.get();
        auto.setModelo(request.getModelo());
        auto.setAnio(request.getAnio());
        auto.setPrecio(request.getPrecio());
        auto.setMarca(marcaOpt.get());

        Auto actualizado = autoRepository.save(auto);
        return ResponseEntity.ok(convertirADTO(actualizado));
    }

    // d) DELETE para eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!autoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Auto no encontrado con ID: " + id));
        }
        autoRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("mensaje", "Auto eliminado correctamente"));
    }

    private AutoResponseDTO convertirADTO(Auto auto) {
        Long marcaId = auto.getMarca() != null ? auto.getMarca().getId() : null;
        String marcaNombre = auto.getMarca() != null ? auto.getMarca().getNombre() : "Sin marca";
        return new AutoResponseDTO(
                auto.getId(),
                auto.getModelo(),
                auto.getAnio(),
                auto.getPrecio(),
                marcaId,
                marcaNombre
        );
    }
}