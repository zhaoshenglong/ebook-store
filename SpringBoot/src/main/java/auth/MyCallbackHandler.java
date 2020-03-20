package auth;

import javax.security.auth.callback.*;
import java.io.IOException;

public class MyCallbackHandler implements CallbackHandler {
    private String username;
    private char[] password;

    MyCallbackHandler(String username, char[] password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void handle(Callback[] callbacks) {
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                ((NameCallback) callback).setName(username);
            } else if (callback instanceof PasswordCallback) {
                ((PasswordCallback) callback).setPassword(password);
            }
        }
    }
}
