package security;

import java.security.AccessControlException;

public class TestPermission {
    public static void main(String[] args) {
        FileReadPermission p1 = new FileReadPermission("/tmp", "read");
        SecurityManager manager = System.getSecurityManager();
        try {
            if(manager != null) {
                manager.checkPermission(p1);
            }
            System.out.println("You can read /tmp");
        } catch (AccessControlException e) {
            e.printStackTrace();
            System.out.println("You can not read /tmp!");
        }
        FileReadPermission p2 = new FileReadPermission("/home/dragon", "read");
        try {
            if(manager != null) {
                manager.checkPermission(p2);
            }
            System.out.println("You can read /home/dragon");
        } catch (AccessControlException e) {
            e.printStackTrace();
            System.out.println("You can not read /home/dragon!");
        }
    }
}
