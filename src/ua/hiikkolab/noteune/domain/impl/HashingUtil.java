package ua.hiikkolab.noteune.domain.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

public class HashingUtil {
  private HashingUtil(){
    throw new IllegalStateException("Utility class");
  }
  public static String hashPassword(String password, String salt) {
    byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);
    byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);

    Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
        .withSalt(saltBytes)
        .withIterations(3)
        .withMemoryAsKB(65536) // 64 MB
        .withParallelism(1);
    Argon2BytesGenerator generator = new Argon2BytesGenerator();
    generator.init(builder.build());

    byte[] result = new byte[32];
    generator.generateBytes(passwordBytes, result, 0, result.length);

    return Base64.getEncoder().encodeToString(result);
  }

  public static boolean checkPassword(String password, String salt, String hashedPassword) {
    String newHashedPassword = hashPassword(password, salt);
    return newHashedPassword.equals(hashedPassword);
  }
}
