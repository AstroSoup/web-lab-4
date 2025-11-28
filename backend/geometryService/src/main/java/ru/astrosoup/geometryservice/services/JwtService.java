package ru.astrosoup.geometryservice.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import ru.astrosoup.geometryservice.exceptions.InvalidJwtException;
import ru.astrosoup.geometryservice.filters.AuthorisationFilter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@ApplicationScoped
public class JwtService {

    public enum TokenLiveSpan {
        SHORT,
        LONG;

        public int getTtl() {
            return switch (this) {
                case SHORT -> (15 * 60);
                case LONG -> (7 * 24 * 60 * 60);
            };
        }
    }

    public JsonObject validateTokenAndGetClaims(String jwt) throws InvalidJwtException {


        String[] parts = jwt.split("\\.");

        if (parts.length != 3) {

            throw new InvalidJwtException("JWT structure is invalid");
        }

        String header = parts[0];
        String payload = parts[1];
        String signature = parts[2];

        String signingInput = header + "." + payload;
        byte[] signatureBytes = Base64.getUrlDecoder().decode(signature);
        try {
            PublicKey publicKey = readPublicKey("META-INF/public.pem");

            Signature verifier = Signature.getInstance("SHA256withRSA");
            verifier.initVerify(publicKey);
            verifier.update(signingInput.getBytes(StandardCharsets.UTF_8));

            if (!verifier.verify(signatureBytes)) {
                throw new InvalidJwtException("Invalid JWT signature");
            }
        } catch (InvalidJwtException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidJwtException("Exception occurred while validating JWT");
        }


        String decodedPayload = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
        JsonReader reader = Json.createReader(new StringReader(decodedPayload));
        JsonObject claims = reader.readObject();
        reader.close();

        if (claims.containsKey("exp") && claims.getJsonNumber("exp").longValue() * 1000L < System.currentTimeMillis()) {
            throw new InvalidJwtException("JWT is expired");
        }
        if (claims.containsKey("iss") && !claims.getString("iss").equals("weblab4")) {
            throw new InvalidJwtException("Invalid issuer in JWT");
        }
        return claims;
    }


    private PublicKey readPublicKey(String resourceName) throws Exception {
        try (InputStream is = AuthorisationFilter.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + resourceName);
            }
            String keyPem = new String(is.readAllBytes(), StandardCharsets.UTF_8)
                    .replaceAll("-----BEGIN (.*)-----", "")
                    .replaceAll("-----END (.*)-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(keyPem);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
            return KeyFactory.getInstance("RSA").generatePublic(keySpec);
        }
    }
}

