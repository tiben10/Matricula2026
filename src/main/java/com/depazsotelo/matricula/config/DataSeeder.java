package com.depazsotelo.matricula.config;

import com.depazsotelo.matricula.models.*;
import com.depazsotelo.matricula.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final AnioAcademicoRepository anioAcademicoRepository;
    private final NivelRepository nivelRepository;
    private final GradoRepository gradoRepository;
    private final AulaRepository aulaRepository;
    private final TipoConceptoRepository tipoConceptoRepository;
    private final ConceptoRepository conceptoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // 1. Sembrar Tipo de Documento
        if (tipoDocumentoRepository.count() == 0) {
            TipoDocumento dni = new TipoDocumento();
            dni.setNombre("DNI");
            dni.setEstado(true);
            tipoDocumentoRepository.save(dni);
            System.out.println("✅ Documento DNI creado (ID: 1)");
        }

        // 2. Sembrar Usuario de Auditoría
        if (rolRepository.count() == 0) {
            Rol rolAdmin = new Rol();
            rolAdmin.setNombreRol("ADMIN");
            rolRepository.save(rolAdmin);

            Usuario admin = new Usuario();
            admin.setUsuario("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol(rolAdmin);
            admin.setEstado(true);
            usuarioRepository.save(admin);
            System.out.println("✅ Usuario ADMIN creado (ID: 1)");
        }

        // 3. Sembrar Estructura de Aula (Año, Nivel, Grado)
        if (anioAcademicoRepository.count() == 0) {
            AnioAcademico anio2026 = new AnioAcademico();
            anio2026.setAnio("2026");
            anio2026.setEstado(true);
            anio2026 = anioAcademicoRepository.save(anio2026);

            Nivel secundaria = new Nivel();
            secundaria.setNombre("Secundaria");
            secundaria.setEstado(true);
            secundaria = nivelRepository.save(secundaria);

            Grado primero = new Grado();
            primero.setNombre("1° Grado");
            primero.setEstado(true);
            primero = gradoRepository.save(primero);

            // Creamos el Aula 1: 1° de Secundaria - Sección A - Año 2026
            Aula aula1 = new Aula();
            aula1.setAnioAcademico(anio2026);
            aula1.setNivel(secundaria);
            aula1.setGrado(primero);
            aula1.setSeccion("A");
            aula1.setCapacidadMaxima((short) 30);
            aula1.setEstado(true);
            aulaRepository.save(aula1);
            System.out.println("✅ Aula 1° Secundaria 'A' 2026 creada (ID: 1)");

            // 4. Sembrar Conceptos de Pago para este año académico
            TipoConcepto fijo = new TipoConcepto();
            fijo.setNombre("FIJO");
            fijo = tipoConceptoRepository.save(fijo);

            TipoConcepto mensual = new TipoConcepto();
            mensual.setNombre("MENSUAL");
            mensual = tipoConceptoRepository.save(mensual);

            // Concepto: Derecho de Matrícula (Se paga primero)
            Concepto matriculaConcepto = new Concepto();
            matriculaConcepto.setAnioAcademico(anio2026);
            matriculaConcepto.setTipoConcepto(fijo);
            matriculaConcepto.setNombreConcepto("Matrícula 2026");
            matriculaConcepto.setMonto(new BigDecimal("450.00"));
            matriculaConcepto.setOrdenPago((short) 1);
            matriculaConcepto.setObligatorio(true);
            conceptoRepository.save(matriculaConcepto);

            // Concepto: Pensión de Marzo (Se paga segundo)
            Concepto marzoConcepto = new Concepto();
            marzoConcepto.setAnioAcademico(anio2026);
            marzoConcepto.setTipoConcepto(mensual);
            marzoConcepto.setNombreConcepto("Pensión - Marzo");
            marzoConcepto.setMonto(new BigDecimal("600.00"));
            marzoConcepto.setOrdenPago((short) 2);
            marzoConcepto.setObligatorio(true);
            conceptoRepository.save(marzoConcepto);
            System.out.println("✅ Tarifario y Conceptos de Pago 2026 sembrados con éxito");
        }
    }
}