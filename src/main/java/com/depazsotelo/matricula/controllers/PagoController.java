package com.depazsotelo.matricula.controllers;

import com.depazsotelo.matricula.dtos.PagoRequest;
import com.depazsotelo.matricula.models.Cuota;
import com.depazsotelo.matricula.services.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping("/procesar")
    public ResponseEntity<?> procesarPago(@RequestBody PagoRequest request) {
        try {
            // Mandamos a procesar el pago con el servicio
            Cuota cuotaPagada = pagoService.registrarPago(request.getCodCuota());

            return ResponseEntity.ok(cuotaPagada);

        } catch (Exception e) {
            // Si la cuota ya está pagada o no existe, tiramos un 400
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listarCuotas() {
        return ResponseEntity.ok(pagoService.listarTodasLasCuotas());
    }

}