import crypt.MyClassLoader;

import javax.swing.*;
import java.lang.reflect.Method;

public class ApplicationRunner {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ApplicationRunner <inputCLass> <key>");
            return;
        }
        String name = args[0];
        int key = Integer.parseInt(args[1]);
        runClass(name, key);
    }

    private static void runClass(String name, int key) {
        try {
            ClassLoader loader = new MyClassLoader(key);
            Class c = loader.loadClass(name);
            String[] args = new String[]{};

            Method m = c.getMethod("main", args.getClass());
            m.invoke(null, (Object) args);
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
