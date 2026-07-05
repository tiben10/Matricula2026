package com.depazsotelo.matricula.services;

import com.depazsotelo.matricula.models.Auditoria;
import com.depazsotelo.matricula.models.Usuario;
import com.depazsotelo.matricula.repositories.AuditoriaRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// MEJORA: centraliza el registro de auditoría para que TODOS los módulos
// (Matrícula, Pago, Login, etc.) lo usen igual, en vez de que cada servicio
// arme su propio objeto Auditoria a mano.
@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public void registrar(Usuario usuario, String modulo, String tablaAfectada,
                          String operacion, Integer codigoRegistro,
                          String valorAnterior, String valorNuevo,
                          HttpServletRequest request) {

        Auditoria auditoria = new Auditoria();
        auditoria.setUsuario(usuario);
        auditoria.setModulo(modulo);
        auditoria.setTablaAfectada(tablaAfectada);
        auditoria.setOperacion(operacion);
        auditoria.setCodigoRegistro(codigoRegistro);
        auditoria.setValorAnterior(valorAnterior);
        auditoria.setValorNuevo(valorNuevo);

        // MEJORA: IP real del request en lugar de "127.0.0.1" fijo.
        // X-Forwarded-For es necesario si hay proxy/nginx delante.
        String ip = request.getHeader("X-Forwarded-For");
        auditoria.setIpOrigen(ip != null ? ip.split(",")[0].trim() : request.getRemoteAddr());

        // MEJORA: navegador capturado del header real, antes no se llenaba.
        auditoria.setNavegador(request.getHeader("User-Agent"));

        auditoriaRepository.save(auditoria);
    }
}