package br.com.unit.tokseg.armariointeligente.controller;

import br.com.unit.tokseg.armariointeligente.exception.ResourceNotFoundException;
import br.com.unit.tokseg.armariointeligente.model.Entrega;
import br.com.unit.tokseg.armariointeligente.model.StatusEntrega;
import br.com.unit.tokseg.armariointeligente.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/entregas")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('ENTREGADOR')")
    public ResponseEntity<?> registrarEntrega(@RequestBody Entrega entrega) {
        Entrega novaEntrega = entregaService.registrarEntrega(entrega);
        return ResponseEntity.ok(novaEntrega);
    }

    @PutMapping("/{id}/retirada")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('CLIENTE') or hasRole('ENTREGADOR')")
    public ResponseEntity<?> registrarRetirada(@PathVariable Long id, @RequestParam String codigoAcesso) {
        Entrega entregaAtualizada = entregaService.registrarRetirada(id, codigoAcesso);
        return ResponseEntity.ok(entregaAtualizada);
    }

    @PutMapping("/{id}/cancelar")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('ENTREGADOR')")
    public ResponseEntity<?> cancelarEntrega(@PathVariable Long id) {
        Entrega entregaAtualizada = entregaService.cancelarEntrega(id);
        return ResponseEntity.ok(entregaAtualizada);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('ENTREGADOR')")
    public ResponseEntity<?> listarEntregas() {
        return ResponseEntity.ok(entregaService.listarEntregas());
    }

    @GetMapping("/compartimento/{compartimentoId}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('ENTREGADOR')")
    public ResponseEntity<?> listarEntregasPorCompartimento(@PathVariable Long compartimentoId) {
        return ResponseEntity.ok(entregaService.listarEntregasPorCompartimento(compartimentoId));
    }

    @GetMapping("/entregador/{entregadorId}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('ENTREGADOR')")
    public ResponseEntity<?> listarEntregasPorEntregador(@PathVariable Long entregadorId) {
        return ResponseEntity.ok(entregaService.listarEntregasPorEntregador(entregadorId));
    }

    @GetMapping("/destinatario/{destinatarioId}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('ENTREGADOR') or @usuarioServiceImpl.isCurrentUser(#destinatarioId)")
    public ResponseEntity<?> listarEntregasPorDestinatario(@PathVariable Long destinatarioId) {
        return ResponseEntity.ok(entregaService.listarEntregasPorDestinatario(destinatarioId));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('ENTREGADOR')")
    public ResponseEntity<?> listarEntregasPorStatus(@PathVariable StatusEntrega status) {
        return ResponseEntity.ok(entregaService.listarEntregasPorStatus(status));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('ENTREGADOR') or @entregaServiceImpl.isDestinatario(#id)")
    public ResponseEntity<?> buscarEntregaPorId(@PathVariable Long id) {
        Optional<Entrega> entrega = entregaService.buscarEntregaPorId(id);
        if (entrega.isPresent()) {
            return ResponseEntity.ok(entrega.get());
        } else {
            throw new ResourceNotFoundException("Entrega", "id", id);
        }
    }

    @GetMapping("/rastreio/{codigoRastreio}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('ENTREGADOR') or hasRole('CLIENTE')")
    public ResponseEntity<?> buscarEntregaPorCodigoRastreio(@PathVariable String codigoRastreio) {
        Optional<Entrega> entrega = entregaService.buscarEntregaPorCodigoRastreio(codigoRastreio);
        if (entrega.isPresent()) {
            return ResponseEntity.ok(entrega.get());
        } else {
            throw new ResourceNotFoundException("Entrega", "código de rastreio", codigoRastreio);
        }
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('ENTREGADOR')")
    public ResponseEntity<?> listarEntregasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(entregaService.listarEntregasPorPeriodo(inicio, fim));
    }
}
