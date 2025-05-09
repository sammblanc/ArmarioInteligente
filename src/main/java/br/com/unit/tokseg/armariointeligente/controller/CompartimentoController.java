package br.com.unit.tokseg.armariointeligente.controller;

import br.com.unit.tokseg.armariointeligente.exception.ResourceNotFoundException;
import br.com.unit.tokseg.armariointeligente.model.Compartimento;
import br.com.unit.tokseg.armariointeligente.service.CompartimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/compartimentos")
public class CompartimentoController {

    @Autowired
    private CompartimentoService compartimentoService;

    @PostMapping
    public ResponseEntity<?> criarCompartimento(@RequestBody Compartimento compartimento) {
        Compartimento novoCompartimento = compartimentoService.criarCompartimento(compartimento);
        return ResponseEntity.ok(novoCompartimento);
    }

    @GetMapping
    public ResponseEntity<?> listarCompartimentos() {
        return ResponseEntity.ok(compartimentoService.listarCompartimentos());
    }

    @GetMapping("/armario/{armarioId}")
    public ResponseEntity<?> listarCompartimentosPorArmario(@PathVariable Long armarioId) {
        return ResponseEntity.ok(compartimentoService.listarCompartimentosPorArmario(armarioId));
    }

    @GetMapping("/status")
    public ResponseEntity<?> listarCompartimentosPorStatus(@RequestParam Boolean ocupado) {
        return ResponseEntity.ok(compartimentoService.listarCompartimentosPorStatus(ocupado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCompartimentoPorId(@PathVariable Long id) {
        Optional<Compartimento> compartimento = compartimentoService.buscarCompartimentoPorId(id);
        if (compartimento.isPresent()) {
            return ResponseEntity.ok(compartimento.get());
        } else {
            throw new ResourceNotFoundException("Compartimento", "id", id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCompartimento(@PathVariable Long id, @RequestBody Compartimento compartimento) {
        Compartimento compartimentoAtualizado = compartimentoService.atualizarCompartimento(id, compartimento);
        return ResponseEntity.ok(compartimentoAtualizado);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatusCompartimento(@PathVariable Long id, @RequestParam Boolean ocupado) {
        Compartimento compartimentoAtualizado = compartimentoService.atualizarStatusCompartimento(id, ocupado);
        return ResponseEntity.ok(compartimentoAtualizado);
    }

    @PutMapping("/{id}/codigo-acesso")
    public ResponseEntity<?> gerarNovoCodigoAcesso(@PathVariable Long id) {
        Compartimento compartimentoAtualizado = compartimentoService.gerarNovoCodigoAcesso(id);
        return ResponseEntity.ok(compartimentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCompartimento(@PathVariable Long id) {
        compartimentoService.deletarCompartimento(id);
        return ResponseEntity.noContent().build();
    }
}
