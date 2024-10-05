import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Scanner;


public class simetricAlgoritm {
    public static void main(String[] args) {
    
        Scanner scanner = new Scanner(System.in);
        String file, cfile;
        Path fileToRead;
        File newFile;

        try{

            KeyGenerator keygen = KeyGenerator.getInstance("DES");
            SecretKey key = keygen.generateKey();
            Cipher desCipher = Cipher.getInstance("DES");

        while(true){

            System.out.println("-----------------------------------");
            System.out.println("1. Cifrar");
            System.out.println("2. Descifrar");
            System.out.println("3. Salir");
            System.out.println("-----------------------------------");
            String option = scanner.nextLine();
            
            switch (option) {         
                case "1":
                    System.out.println("Introduzca nombre del fichero que desea leer:");
                    file = scanner.nextLine();
                    fileToRead = Paths.get(file);
                    System.out.println("Introduzca nombre del fichero en el que desea guardar el mensaje encriptado:");
                    cfile = scanner.nextLine();
                    newFile = new File(cfile);

                    cipher(fileToRead, newFile, key, desCipher);

                    System.out.println("El mensaje ha sido cifrado y almacenado en: " + newFile.getAbsolutePath());
                    
                    break;

                case "2":
                    System.out.println("Introduzca nombre del fichero que desea descifrar:");
                    file = scanner.nextLine();
                    fileToRead = Paths.get(file);
                    decipher(fileToRead, key, desCipher);

                    break;
            
                case "3": System.out.println("Saliendo del programa.");
                    scanner.close();
                    return;
            
                default:
                    System.out.println("Por favor pulse un número del 1 al 3.");
                    break;
                }
            }

        }catch (Exception e){
            System.out.println("Ha ocurrido un error:"+ e.getMessage());
        }
    }

    public static void cipher (Path fileToRead, File newfFile, SecretKey key, Cipher desCipher) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        
        desCipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] reader = Files.readAllBytes(fileToRead);

        System.out.println("El mensaje que se va a encriptar es: " + new String(reader));

        byte[] cipheredBytes = desCipher.doFinal(reader);

        System.out.println("El mensaje encriptado es: " + new String(cipheredBytes));

        try (FileOutputStream writer = new FileOutputStream(newfFile, true)) {
            writer.write(cipheredBytes);
        }
    }

    public static void decipher (Path fileToRead, SecretKey key, Cipher desCipher) throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {

        desCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] reader = Files.readAllBytes(fileToRead);
        byte[] decipheredBytes = desCipher.doFinal(reader);

        System.out.println("Mensaje desencriptado: " + new String(decipheredBytes));   
    }
}
