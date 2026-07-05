package com.depazsotelo.matricula.security;

import com.depazsotelo.matricula.models.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    // Clave secreta para firmar los tokens (mínimo 256 bits para algoritmo HS256)
    // En producción esto debe ir oculto en el application.properties
    private static final String SECRET_KEY = "C0ntr4s3n4S3cr3t4Pr0y3ct0M4tr1cul4B4ck3ndS3gur0";

    // El token durará 8 horas activo (en milisegundos)
    private static final long JWT_EXPIRATION = 28800000L;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 1. GENERAR EL TOKEN AL HACER LOGIN
    public String generarToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        // Metemos el rol del usuario dentro del token para que el frontend lo lea
        claims.put("rol", usuario.getRol().getNombreRol());
        claims.put("idUsuario", usuario.getIdUsuario());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getUsuario()) // El username del dueño del token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. EXTRAER EL USERNAME DEL TOKEN
    public String extraerUsuario(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    // 3. VALIDAR SI EL TOKEN ES CORRECTO Y NO HA EXPIRADO
    public boolean validarToken(String token, String username) {
        final String usuarioToken = extraerUsuario(token);
        return (usuarioToken.equals(username) && !isTokenExpirado(token));
    }

    private boolean isTokenExpirado(String token) {
        return extraerClaim(token, Claims::getExpiration).before(new Date());
    }

    public <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}