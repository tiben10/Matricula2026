package com.depazsotelo.matricula.security;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Converter
public class AesEncryptor implements AttributeConverter<String, String> {

    // La clave ya no debería vivir en el código fuente.
    // Idealmente se lee de una variable de entorno (AES_SECRET_KEY) definida
    // en docker-compose.yml o application.properties. Se deja un fallback
    // solo para que el proyecto siga corriendo si no está configurada.
    private static final String SECRET_KEY =
            System.getenv().getOrDefault("AES_SECRET_KEY", "Pr0y3ct0Cripto26");
    private static final String ALGORITHM = "AES";
    // IV aleatorio ==> se utiliza para inicializar el proceso de cifrado y
    // garantiza que el cifrado del primer bloque dependa del IV
    // Antes era "AES" a secas -> Cipher usaba ECB por defecto.
    // ECB cifra cada bloque igual si el texto plano se repite (patrones visibles,
    // no cumple buena práctica criptográfica). Cambiado a CBC + IV aleatorio.
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int IV_LENGTH = 16;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);

            // Genera un IV distinto en cada cifrado.
            // Esto evita que el mismo dato en texto plano produzca siempre
            // el mismo resultado cifrado.
            byte[] iv = new byte[IV_LENGTH];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            byte[] encryptedBytes = cipher.doFinal(attribute.getBytes());

            // Guarda IV + texto cifrado juntos (necesita ser único).
            // Al desencriptar lo separamos de nuevo.
            byte[] combined = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar el dato con AES", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            byte[] combined = Base64.getDecoder().decode(dbData);

            // Separa el IV (primeros 16 bytes) del texto cifrado real.
            byte[] iv = new byte[IV_LENGTH];
            byte[] encryptedBytes = new byte[combined.length - IV_LENGTH];
            System.arraycopy(combined, 0, iv, 0, IV_LENGTH);
            System.arraycopy(combined, IV_LENGTH, encryptedBytes, 0, encryptedBytes.length);

            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar el dato con AES", e);
        }
    }
}