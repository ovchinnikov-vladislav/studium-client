package engine;

import model.User;
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

    public static void readFile() throws IOException {
        final String passCrypt = "Dont hack me";
        ArrayList<String> list = new ArrayList<>();
        String dir = System.getProperty("user.dir");
        BufferedReader reader = new BufferedReader(new FileReader(dir + "/data/db.config"));
        while (reader.ready()) {
            list.add(reader.readLine());
        }
        reader.close();
        TextEncryptor encryptor = Encryptors.text(passCrypt, list.get(list.size()-1));
        if (list.size() == 6) {
            String[] data = new String[5];
            for (int i = 0; i < data.length; i++)
                data[i] = encryptor.decrypt(list.get(i));
            Database.setUrl("jdbc:mysql://" + data[0] + ":" + data[1] + "/" + data[2]);
            Database.setUser(data[3]);
            Database.setPassword(data[4]);
        } else if (list.size() == 5) {
            String[] data = new String[4];
            for (int i = 0; i < data.length; i++)
                data[i] = encryptor.decrypt(list.get(i));
            Database.setUrl("jdbc:mysql://" + data[0] + ":3306/" + data[1]);
            Database.setUser(data[2]);
            Database.setPassword(data[3]);
        }
    }

    public static void writeFile(String address, String port, String db, String login, String password) throws IOException {
        final String passCrypt = "Dont hack me";
        String salt = KeyGenerators.string().generateKey();
        TextEncryptor encryptor = Encryptors.text(passCrypt, salt);
        String dir = System.getProperty("user.dir");
        File file = new File(dir + "/data");
        if (!file.exists()) {
            file.mkdirs();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath() + "/db.config"));
        writer.write(encryptor.encrypt(address));
        writer.newLine();
        writer.write(encryptor.encrypt(port));
        writer.newLine();
        writer.write(encryptor.encrypt(db));
        writer.newLine();
        writer.write(encryptor.encrypt(login));
        writer.newLine();
        writer.write(encryptor.encrypt(password));
        writer.newLine();
        writer.write(salt);
        writer.flush();
        writer.close();
    }
}
