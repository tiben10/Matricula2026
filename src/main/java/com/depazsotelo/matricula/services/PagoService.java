package com.depazsotelo.matricula.services;

import com.depazsotelo.matricula.models.Cuota;
import com.depazsotelo.matricula.repositories.CuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final CuotaRepository cuotaRepository;

    @Transactional
    public Cuota registrarPago(Integer codCuota) throws Exception {
        // 1. Buscamos la cuota en la BD
        Cuota cuota = cuotaRepository.findById(codCuota)
                .orElseThrow(() -> new Exception("Error: La cuota especificada no existe."));

        // 2. Validamos que no nos quieran pagar algo que ya está pagado
        if ("PAGADO".equalsIgnoreCase(cuota.getEstado())) {
            throw new Exception("Operación rechazada: Esta cuota ya se encuentra pagada.");
        }

        // 3. Efectuamos el pago (cambiamos estado y le ponemos la fecha actual)
        cuota.setEstado("PAGADO");
        cuota.setFechaPago(LocalDateTime.now());

        // 4. Guardamos los cambios
        return cuotaRepository.save(cuota);
    }
    public java.util.List<Cuota> listarTodasLasCuotas() {
        return cuotaRepository.findAll();
    }

}