package com.depazsotelo.matricula.controllers;

import com.depazsotelo.matricula.dtos.JwtResponse;
import com.depazsotelo.matricula.dtos.LoginRequest;
import com.depazsotelo.matricula.models.Usuario;
import com.depazsotelo.matricula.repositories.UsuarioRepository;
import com.depazsotelo.matricula.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // 1. Validar credenciales con Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // 2. Si pasa, buscamos al usuario completo en la BD para sacar su rol
        Usuario usuario = usuarioRepository.findByUsuario(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 3. Generamos el token mágico con nuestro JwtUtils
        String jwt = jwtUtils.generarToken(usuario);

        // 4. Se lo devolvemos a Postman o Angular
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}