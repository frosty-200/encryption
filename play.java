package encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;




public class play {
	public static void main(String[] args) {
        try {
            // Generate a secret key
        SecretKey secretKey = generateKey(128);

        // Example text to encrypt
        String plainText = "Hello, World!";

        // Encrypt the text
        String encryptedText = encrypt(plainText, secretKey);
        System.out.println("Encrypted Text: " + encryptedText);

        // Decrypt the text
        String decryptedText = decrypt(encryptedText, secretKey);
        System.out.println("Decrypted Text: " + decryptedText);

        // Optionally, encode and decode the key for sharing
        String encodedKey = encodeKey(secretKey);
        System.out.println("Encoded Key: " + encodedKey);

        SecretKey decodedKey = decodeKey(encodedKey);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static SecretKey decodeKey(String encodedKey) {
    byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
    return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
}

public static String encodeKey(SecretKey key) {
    return Base64.getEncoder().encodeToString(key.getEncoded());
}

public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
    return new String(decryptedBytes);
}

public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
    return Base64.getEncoder().encodeToString(encryptedBytes);
}

public static SecretKey generateKey(int n) throws Exception {
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    keyGen.init(n); // Specifying the key size (128, 192, or 256 bits)
		        SecretKey secretKey = keyGen.generateKey();
		        return secretKey;
		    }
		}		

