package br.com.unit.tokseg.armariointeligente.controller;

import br.com.unit.tokseg.armariointeligente.exception.ResourceNotFoundException;
import br.com.unit.tokseg.armariointeligente.model.Reserva;
import br.com.unit.tokseg.armariointeligente.model.StatusReserva;
import br.com.unit.tokseg.armariointeligente.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<?> criarReserva(@RequestBody Reserva reserva) {
        Reserva novaReserva = reservaService.criarReserva(reserva);
        return ResponseEntity.ok(novaReserva);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        Reserva reservaAtualizada = reservaService.cancelarReserva(id);
        return ResponseEntity.ok(reservaAtualizada);
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<?> concluirReserva(@PathVariable Long id) {
        Reserva reservaAtualizada = reservaService.concluirReserva(id);
        return ResponseEntity.ok(reservaAtualizada);
    }

    @GetMapping
    public ResponseEntity<?> listarReservas() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }

    @GetMapping("/compartimento/{compartimentoId}")
    public ResponseEntity<?> listarReservasPorCompartimento(@PathVariable Long compartimentoId) {
        return ResponseEntity.ok(reservaService.listarReservasPorCompartimento(compartimentoId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> listarReservasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.listarReservasPorUsuario(usuarioId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> listarReservasPorStatus(@PathVariable StatusReserva status) {
        return ResponseEntity.ok(reservaService.listarReservasPorStatus(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarReservaPorId(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.buscarReservaPorId(id);
        if (reserva.isPresent()) {
            return ResponseEntity.ok(reserva.get());
        } else {
            throw new ResourceNotFoundException("Reserva", "id", id);
        }
    }

    @GetMapping("/periodo")
    public ResponseEntity<?> listarReservasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(reservaService.listarReservasPorPeriodo(inicio, fim));
    }
}
