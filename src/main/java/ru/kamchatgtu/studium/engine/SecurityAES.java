package ru.kamchatgtu.studium.engine;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import ru.kamchatgtu.studium.entity.user.User;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

public class SecurityAES {

    public final static User USER_LOGIN = new User();

    public static String encryptPass(String password) {
        try {
            byte[] keyStart = "1dj3fcdbgh4jf".getBytes(StandardCharsets.UTF_8);
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(keyStart);
            kgen.init(128, sr);
            SecretKey skey = kgen.generateKey();    // генерирует один и тот же ключ
            byte[] key = skey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            String sumPass = Base64.getEncoder().withoutPadding().encodeToString(encrypted) + Base64.getEncoder().withoutPadding().encodeToString(key);
            String[] string = sumPass.split("\n");
            String str = "";
            for (String s : string) {
                if (!s.isEmpty()) str += s;
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptPass(String encrypted) throws Exception {
        String key = encrypted.substring(encrypted.length() - 22);
        String password = encrypted.substring(0, encrypted.length() - 22);
        SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(password));
        return new String(decrypted, StandardCharsets.UTF_8);
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

    public static String getRandomPass() {
        return KeyGenerators.string().generateKey();
    }

}
