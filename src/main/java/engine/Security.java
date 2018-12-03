package engine;

import entity.User;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Security {

    public static final User userLogin = new User();

    public static String getMd5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            byte[] buffer = pass.getBytes(StandardCharsets.UTF_8);
            md.update(buffer);
            byte[] digest = md.digest();

            StringBuilder hexStr = new StringBuilder("");
            for (int i = 0; i < digest.length; i++) {
                hexStr.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            return hexStr.toString();
        } catch (NoSuchAlgorithmException exc) {
            exc.printStackTrace();
            return pass;
        }
    }

    public static String readFile() throws IOException {
        final String passCrypt = "Dont hack me";
        ArrayList<String> list = new ArrayList<>();
        String dir = System.getProperty("user.dir");
        BufferedReader reader = new BufferedReader(new FileReader(dir + "/data/connection.config"));
        while (reader.ready()) {
            list.add(reader.readLine());
        }
        reader.close();
        if (list.size() == 2) {
            TextEncryptor encryptor = Encryptors.text(passCrypt, list.get(1));
            return encryptor.decrypt(list.get(0));
        }
        return "";
    }

    public static void writeFile(String address) throws IOException {
        final String passCrypt = "Dont hack me";
        String salt = KeyGenerators.string().generateKey();
        TextEncryptor encryptor = Encryptors.text(passCrypt, salt);
        String dir = System.getProperty("user.dir");
        File file = new File(dir + "/data");
        if (!file.exists()) {
            file.mkdirs();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath() + "/connection.config"));
        writer.write(encryptor.encrypt(address));
        writer.newLine();
        writer.write(salt);
        writer.flush();
        writer.close();
    }
}
