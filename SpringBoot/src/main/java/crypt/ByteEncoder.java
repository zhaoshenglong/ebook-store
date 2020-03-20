package crypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Encode java class bytecode
 */
public class ByteEncoder {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java crypt.ByteEncoder <target directory> <output directory> <key>");
            return;
        }
        encrypt(args[0], args[1], Integer.parseInt(args[2]));
    }

    private static void encrypt(String srcName, String dstName, int key) {
        File src = new File(srcName);
        new File(srcName, dstName);
        File dst = new File(dstName);
        if (!src.exists()) {
            System.out.println("Source path " + srcName + "does not exists");
            return;
        }
        if (src.isDirectory()) {
            if (!dst.exists()) {
                dst.mkdir();
            }
            String[] children = src.list();
            if (children == null) {
                return;
            }
            for (String child : children) {
                encrypt( srcName + "/" + child, dstName + "/" + child, key);
            }
        } else {
            try(
                    FileInputStream in = new FileInputStream(srcName);
                    FileOutputStream out = new FileOutputStream(dstName + ".crypt");
                    ) {
                int ch;
                while ((ch = in.read()) != -1) {
                    byte c = (byte)(ch + key);
                    out.write(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
