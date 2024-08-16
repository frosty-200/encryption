package encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

                   // Import the Scanner class to read user input

import javax.crypto.Cipher;                  // Import the Cipher class for encryption/decryption operations
import javax.crypto.KeyGenerator;            // Import the KeyGenerator class to generate a secret key
import javax.crypto.SecretKey;               // Import the SecretKey class to hold the secret key
import javax.crypto.spec.SecretKeySpec;      // Import the SecretKeySpec class to create a key from byte data
import java.util.Base64;                     // Import the Base64 class to encode/decode byte data to/from a string
import java.util.Scanner;                    // Import the Scanner class to read user input

public class play {                          // Define the class "Play"

    public static void main(String[] args) { // Main method where the program execution begins
        try {                                // Start a try block to catch any exceptions that might occur

            // Generate a secret key
            SecretKey secretKey = generateKey(256); // Call the generateKey method to create a 256-bit AES key

            // Example text to encrypt
            System.out.println("What do you want to encrypt"); // Prompt the user to enter text to encrypt
            Scanner in = new Scanner(System.in);               // Create a Scanner object to read user input
            String plainText = in.nextLine();                  // Read the user's input as a string (plaintext)

            // Encrypt the text
            String encryptedText = encrypt(plainText, secretKey); // Call the encrypt method to encrypt the plaintext
            System.out.println("Encrypted Text: " + encryptedText); // Print the encrypted text

            // Decrypt the text
            String decryptedText = decrypt(encryptedText, secretKey); // Call the decrypt method to decrypt the encrypted text
            System.out.println("Decrypted Text: " + decryptedText); // Print the decrypted text (should match the original plaintext)

            // Optionally, encode and decode the key for sharing
            String encodedKey = encodeKey(secretKey);          // Encode the secret key to a string format (Base64)
            System.out.println("Encoded Key: " + encodedKey);  // Print the encoded key (can be shared as a string)

            SecretKey decodedKey = decodeKey(encodedKey);      // Decode the encoded key back into a SecretKey object

        } catch (Exception e) {                                // Catch any exceptions that occur during execution
            e.printStackTrace();                               // Print the exception stack trace for debugging
        }
    }

    public static SecretKey decodeKey(String encodedKey) {     // Method to decode a Base64 encoded string into a SecretKey
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey); // Decode the Base64 encoded string into a byte array
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); // Create a new SecretKeySpec using the decoded bytes
    }

    public static String encodeKey(SecretKey keys) {            // Method to encode a SecretKey into a Base64 string
        return Base64.getEncoder().encodeToString(keys.getEncoded()); // Convert the key's byte array to a Base64 encoded string
    }

    public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception { // Method to decrypt text
        Cipher cipher = Cipher.getInstance("AES");             // Create a Cipher instance for AES encryption
        cipher.init(Cipher.DECRYPT_MODE, secretKey);           // Initialize the cipher for decryption mode with the secret key
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText)); // Decrypt the encrypted text
        return new String(decryptedBytes);                     // Convert the decrypted byte array back to a string
    }

    public static String encrypt(String plainText, SecretKey secretKey) throws Exception { // Method to encrypt text
        Cipher cipher = Cipher.getInstance("AES");             // Create a Cipher instance for AES encryption
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);           // Initialize the cipher for encryption mode with the secret key
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes()); // Encrypt the plaintext string and get the byte array
        return Base64.getEncoder().encodeToString(encryptedBytes); // Convert the encrypted byte array to a Base64 encoded string
    }

    public static SecretKey generateKey(int n) throws Exception { // Method to generate a secret key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES"); // Create a KeyGenerator instance for AES
        keyGen.init(n);                                        // Initialize the KeyGenerator with the specified key size (n bits)
        SecretKey secretKey = keyGen.generateKey();            // Generate the secret key
        return secretKey;                                      // Return the generated key
    }
}



