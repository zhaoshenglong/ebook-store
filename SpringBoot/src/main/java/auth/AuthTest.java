package auth;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class AuthTest {
    public static void main(final String[] args) {
        try {
            System.setSecurityManager(new SecurityManager());
            String username = "admin";
            char[] password = new char[] {'1', '2', '3', '4', '5', '6'};
            LoginContext context = new LoginContext("Login1", new MyCallbackHandler(username, password));
            context.login();
            System.out.println("Authentication success");

            context.logout();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
