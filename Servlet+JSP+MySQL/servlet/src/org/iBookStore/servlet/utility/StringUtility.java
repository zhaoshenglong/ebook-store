package org.iBookStore.servlet.utility;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public class StringUtility {
    public static String getReaderContent(HttpServletRequest request){
        StringBuffer jb = new StringBuffer();
        String line;
        try {
            request.setCharacterEncoding("utf-8");
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                jb.append(line);
            }
            return jb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String encodeUTF8(String old) {
        try{
            return  new String(old.getBytes("iso-8859-1"), "utf-8");
        } catch (Exception e) {
            return null;
        }
    }
    public static String hash (String s) {
        String rs = "";
        int P = 17;
        for (char c : s.toCharArray()) {
            rs += ((c - 'a') * P) % 10 + '0';
        }
        if (rs.length() > 4)
            return rs.substring(0, 4);
        else return rs;
    }
}
