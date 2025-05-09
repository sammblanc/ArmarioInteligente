package br.com.unit.tokseg.armariointeligente.controller;

import br.com.unit.tokseg.armariointeligente.exception.ResourceNotFoundException;
import br.com.unit.tokseg.armariointeligente.model.Armario;
import br.com.unit.tokseg.armariointeligente.service.ArmarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/armarios")
public class ArmarioController {

    @Autowired
    private ArmarioService armarioService;

    @PostMapping
    public ResponseEntity<?> criarArmario(@RequestBody Armario armario) {
        Armario novoArmario = armarioService.criarArmario(armario);
        return ResponseEntity.ok(novoArmario);
    }

    @GetMapping
    public ResponseEntity<?> listarArmarios() {
        return ResponseEntity.ok(armarioService.listarArmarios());
    }

    @GetMapping("/condominio/{condominioId}")
    public ResponseEntity<?> listarArmariosPorCondominio(@PathVariable Long condominioId) {
        return ResponseEntity.ok(armarioService.listarArmariosPorCondominio(condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarArmarioPorId(@PathVariable Long id) {
        Optional<Armario> armario = armarioService.buscarArmarioPorId(id);
        if (armario.isPresent()) {
            return ResponseEntity.ok(armario.get());
        } else {
            throw new ResourceNotFoundException("Armário", "id", id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarArmario(@PathVariable Long id, @RequestBody Armario armario) {
        Armario armarioAtualizado = armarioService.atualizarArmario(id, armario);
        return ResponseEntity.ok(armarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarArmario(@PathVariable Long id) {
        armarioService.deletarArmario(id);
        return ResponseEntity.noContent().build();
    }
}
