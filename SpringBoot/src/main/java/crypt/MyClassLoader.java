package crypt;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class MyClassLoader extends ClassLoader{
    private int key;

    public MyClassLoader(int k) {
        key = k;
    }

    protected Class findClass(String name) throws ClassNotFoundException {
        byte[] classBytes;
        try {
            classBytes = loadClassBytes(name);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
        Class cl = defineClass(name, classBytes, 0, classBytes.length);
        if (cl == null)
            throw new ClassNotFoundException(name);
        return cl;
    }

    private byte[] loadClassBytes(String name) throws IOException {
        String cname = name.replace(".", "/") + ".class.crypt";
        System.out.println(cname);
        try (FileInputStream in = new FileInputStream(cname)) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int ch;
            while ((ch = in.read()) != -1) {
                byte b = (byte) (ch - key);
                buffer.write(b);
            }
            return buffer.toByteArray();
        }
    }
}
