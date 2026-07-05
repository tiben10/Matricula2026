package com.depazsotelo.matricula.security;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;

// MEJORA: servicio nuevo para cumplir el requisito de "doble factor con
// Google Authenticator" en el proceso de Matrícula.
@Service
public class TotpService {

    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    // Genera un secreto nuevo para un usuario (se llama una vez, al activar 2FA)
    public GoogleAuthenticatorKey generarSecreto() {
        return gAuth.createCredentials();
    }

    // Arma la URL otpauth:// que se convierte en QR para escanear con la app
    public String getOtpAuthUrl(String secret, String usuario) {
        return String.format(
                "otpauth://totp/MatriculaApp:%s?secret=%s&issuer=MatriculaApp",
                usuario, secret);
    }

    // Valida el código de 6 dígitos que el usuario escribe desde su app
    public boolean validarCodigo(String secret, int codigo) {
        return gAuth.authorize(secret, codigo);
    }
}