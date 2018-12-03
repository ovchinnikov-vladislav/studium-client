package ru.kamchatgtu.studium.engine;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SMSCSender {

    private String smsLogin = "<login>";     // логин клиента
    private String smsPassword = "<password>";  // пароль или MD5-хеш пароля в нижнем регистре
    private boolean smsHttps = false;         // использовать HTTPS протокол
    private String smsCharset = "utf-8";       // кодировка сообщения: koi8-r, windows-1251 или utf-8 (по умолчанию)
    private boolean smsDebug = false;         // флаг отладки
    private boolean smsPost = false;         // Использовать метод POST

    public SMSCSender() {
    }

    public SMSCSender(String login, String password) {
        smsLogin = login;
        smsPassword = password;
    }

    public SMSCSender(String login, String password, String charset) {
        smsLogin = login;
        smsPassword = password;
        smsCharset = charset;
    }

    public SMSCSender(String login, String password, String charset, boolean debug) {
        smsLogin = login;
        smsPassword = password;
        smsCharset = charset;
        smsDebug = debug;
    }

    private static String implode(String[] ary, String delim) {
        String out = "";

        for (int i = 0; i < ary.length; i++) {
            if (i != 0)
                out += delim;
            out += ary[i];
        }

        return out;
    }

    public static void main(String[] args) {

        SMSCSender sd = new SMSCSender("bpiw", "RabbitVlad1997", "utf-8", true);

        sd.sendSms("+79841611700", "Ваш пароль: 123", 1, "", "", 0, "", "");
        sd.getSmsCost("38*********5", "Вы успешно зарегистрированы!", 0, 0, "", "");
        sd.getBalance();
    }

    public String[] sendSms(String phones, String message, int translit, String time, String id, int format, String sender, String query) {
        String[] formats = {"", "flash=1", "push=1", "hlr=1", "bin=1", "bin=2", "ping=1"};
        String[] m = {};

        try {
            m = smscSendCmd("send", "cost=3&phones=" + URLEncoder.encode(phones, smsCharset)
                    + "&mes=" + URLEncoder.encode(message, smsCharset)
                    + "&translit=" + translit + "&id=" + id + (format > 0 ? "&" + formats[format] : "")
                    + (sender.equals("") ? "" : "&sender=" + URLEncoder.encode(sender, smsCharset))
                    + (time.equals("") ? "" : "&time=" + URLEncoder.encode(time, smsCharset))
                    + (query.equals("") ? "" : "&" + query));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (smsDebug) {
            if (Integer.parseInt(m[1]) > 0) {
                System.out.println("Сообщение отправлено успешно. ID: " + m[0] + ", всего SMS: " + m[1] + ", стоимость: " + m[2] + ", баланс: " + m[3]);
            } else {
                System.out.print("Ошибка №" + Math.abs(Integer.parseInt(m[1])));
                System.out.println(Integer.parseInt(m[0]) > 0 ? (", ID: " + m[0]) : "");
            }
        }

        return m;
    }

    public String[] getSmsCost(String phones, String message, int translit, int format, String sender, String query) {
        String[] formats = {"", "flash=1", "push=1", "hlr=1", "bin=1", "bin=2", "ping=1"};
        String[] m = {};

        try {
            m = smscSendCmd("send", "cost=1&phones=" + URLEncoder.encode(phones, smsCharset)
                    + "&mes=" + URLEncoder.encode(message, smsCharset)
                    + "&translit=" + translit + (format > 0 ? "&" + formats[format] : "")
                    + (sender.equals("") ? "" : "&sender=" + URLEncoder.encode(sender, smsCharset))
                    + (query.equals("") ? "" : "&" + query));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // (cost, cnt) или (0, -error)

        if (smsDebug) {
            if (Integer.parseInt(m[1]) > 0) {
                System.out.println("Стоимость рассылки: " + m[0] + ", Всего SMS: " + m[1]);
            } else {
                System.out.print("Ошибка №" + Math.abs(Integer.parseInt(m[1])));
            }
        }

        return m;
    }

    public String[] getStatus(int id, String phone, int all) {
        String[] m = {};
        String tmp;

        try {
            m = smscSendCmd("status", "phone=" + URLEncoder.encode(phone, smsCharset) + "&id=" + id + "&all=" + all);

            if (smsDebug) {
                if (!m[1].equals("") && Integer.parseInt(m[1]) >= 0) {
                    java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Integer.parseInt(m[1]));
                    System.out.println("Статус SMS = " + m[0]);
                } else
                    System.out.println("Ошибка №" + Math.abs(Integer.parseInt(m[1])));
            }

            if (all == 1 && m.length > 9 && (m.length < 14 || !m[14].equals("HLR"))) {
                tmp = implode(m, ",");
                m = tmp.split(",", 9);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return m;
    }

    public String getBalance() {
        String[] m = {};

        m = smscSendCmd("balance", ""); // (balance) или (0, -error)

        if (smsDebug) {
            if (m.length == 1)
                System.out.println("Сумма на счете: " + m[0]);
            else
                System.out.println("Ошибка №" + Math.abs(Integer.parseInt(m[1])));
        }

        return m.length == 2 ? "" : m[0];
    }

    private String[] smscSendCmd(String cmd, String arg) {
        String[] m = {};
        String ret = ",";

        try {
            String url = (smsHttps ? "https" : "http") + "://smsc.ru/sys/" + cmd + ".php?login=" + URLEncoder.encode(smsLogin, smsCharset)
                    + "&psw=" + URLEncoder.encode(smsPassword, smsCharset)
                    + "&fmt=1&charset=" + smsCharset + "&" + arg;

            int i = 0;
            do {
                if (i > 0)
                    Thread.sleep(2000);
                ret = smscReadUrl(url);
            }
            while (ret.equals("") && ++i < 3);
        } catch (UnsupportedEncodingException | InterruptedException e) {
            e.printStackTrace();
        }

        return ret.split(",");
    }

    private String smscReadUrl(String url) {

        String line = "", real_url = url;
        String[] param = {};
        boolean is_post = (smsPost || url.length() > 2000);

        if (is_post) {
            param = url.split("\\?", 2);
            real_url = param[0];
        }

        try {
            URL u = new URL(real_url);
            InputStream is;

            if (is_post) {
                URLConnection conn = u.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream(), smsCharset);
                os.write(param[1]);
                os.flush();
                os.close();
                System.out.println("post");
                is = conn.getInputStream();
            } else {
                is = u.openStream();
            }

            InputStreamReader reader = new InputStreamReader(is, smsCharset);

            int ch;
            while ((ch = reader.read()) != -1) {
                line += (char) ch;
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }
}
