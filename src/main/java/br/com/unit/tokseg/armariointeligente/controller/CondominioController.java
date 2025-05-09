package br.com.unit.tokseg.armariointeligente.controller;

import br.com.unit.tokseg.armariointeligente.exception.ResourceNotFoundException;
import br.com.unit.tokseg.armariointeligente.model.Condominio;
import br.com.unit.tokseg.armariointeligente.service.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/condominios")
public class CondominioController {

    @Autowired
    private CondominioService condominioService;

    @PostMapping
    public ResponseEntity<?> criarCondominio(@RequestBody Condominio condominio) {
        Condominio novoCondominio = condominioService.criarCondominio(condominio);
        return ResponseEntity.ok(novoCondominio);
    }

    @GetMapping
    public ResponseEntity<?> listarCondominios() {
        return ResponseEntity.ok(condominioService.listarCondominios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCondominioPorId(@PathVariable Long id) {
        Optional<Condominio> condominio = condominioService.buscarCondominioPorId(id);
        if (condominio.isPresent()) {
            return ResponseEntity.ok(condominio.get());
        } else {
            throw new ResourceNotFoundException("Condomínio", "id", id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCondominio(@PathVariable Long id, @RequestBody Condominio condominio) {
        Condominio condominioAtualizado = condominioService.atualizarCondominio(id, condominio);
        return ResponseEntity.ok(condominioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCondominio(@PathVariable Long id) {
        condominioService.deletarCondominio(id);
        return ResponseEntity.noContent().build();
    }
}
