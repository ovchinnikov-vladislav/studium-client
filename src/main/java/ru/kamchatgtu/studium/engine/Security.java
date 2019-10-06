package ru.kamchatgtu.studium.engine;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import ru.kamchatgtu.studium.entity.User;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

public class Security {

    public final static User USER_LOGIN = new User();

    public static boolean clearUser() {
        USER_LOGIN.setPassword(null);
        USER_LOGIN.setGroup(null);
        USER_LOGIN.setEmail(null);
        USER_LOGIN.setFio(null);
        USER_LOGIN.setLogin(null);
        USER_LOGIN.setPhone(null);
        USER_LOGIN.setDateAuth(null);
        USER_LOGIN.setDateReg(null);
        return true;
    }

    public static String encryptPass(String password) {
        try {
            if (java.security.Security.getProvider("JStribog") == null) {
                java.security.Security.addProvider(new ru.kamchatgtu.studium.engine.jstribog.StribogProvider());
            }
            MessageDigest md = MessageDigest.getInstance("Stribog256");
            byte[] digest = md.digest(password.getBytes());
            return printHex(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
  /*  public static String decryptPass(String encrypted) throws Exception {
        String key = encrypted.substring(encrypted.length() - 22);
        String password = encrypted.substring(0, encrypted.length() - 22);
        SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(password));
        return new String(decrypted, StandardCharsets.UTF_8);
    }*/

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

    private static String printHex(byte[] digest) {
        String hex = "";
        for (byte b : digest) {
            int iv = (int) b & 0xFF;
            if (iv < 0x10) {
                hex += '0';
            }
            hex += Integer.toHexString(iv).toUpperCase();
        }
        return hex;
    }

    private static byte[] reverse(byte[] ba) {
        byte[] result = new byte[ba.length];
        for (int i = ba.length - 1; i >= 0; i--) {
            result[ba.length - 1 - i] = ba[i];
        }
        return result;
    }
}
