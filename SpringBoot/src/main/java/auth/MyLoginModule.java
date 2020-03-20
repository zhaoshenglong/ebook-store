package auth;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;


public class MyLoginModule implements LoginModule {
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> options;

    private boolean succeeded;
    private boolean commitSucceeded;
    private String username;
    private String role;
    private MyPrincipal userPrincipal;
    private MyPrincipal rolePrincipal;


    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.options = options;
        this.succeeded = this.commitSucceeded = false;
    }

    @Override
    public boolean login() throws LoginException {
        if (callbackHandler == null)
            throw new LoginException("no handler");
        NameCallback nameCallback = new NameCallback("username: ");
        PasswordCallback passwordCallback = new PasswordCallback("password: ", false);
        try {
            callbackHandler.handle(new Callback[]{nameCallback, passwordCallback});
        } catch (UnsupportedCallbackException e) {
            LoginException e2 = new LoginException("Unsupported callback");
            e2.initCause(e);
            throw e2;
        } catch (IOException e) {
            LoginException e2 = new LoginException("I/O exception in callback");
            e2.initCause(e);
            throw e2;
        }

        this.username = nameCallback.getName();
        char[] password = passwordCallback.getPassword();
        System.out.println(this.username);
        System.out.println(password);

        // login
        try {
            Scanner in = new Scanner(new FileReader(("" + options.get("secretfile"))));

            while (in.hasNextLine()) {
                String[] inputs = in.nextLine().split("\\|");
                System.out.println("Inputs" + inputs[0] + inputs[1] + inputs[2]);
                if (inputs[0].equals(username) && Arrays.equals(inputs[1].toCharArray(), password)) {
                    String role = inputs[2];
                    role.trim();
                    this.role = role;
                    succeeded = true;
                    System.out.println("login success!");
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            LoginException e2 = new LoginException("Cannot open secret file");
            e2.initCause(e);
            throw e2;
        }
    }

    @Override
    public boolean commit() {
        if (!succeeded) {
            return false;
        } else {
            // Add principal to subject
            userPrincipal = new MyPrincipal("username", username);
            rolePrincipal = new MyPrincipal("role", role);
            subject.getPrincipals().add(userPrincipal);
            subject.getPrincipals().add(rolePrincipal);
            username = null;
            commitSucceeded = true;
            return true;
        }
    }

    @Override
    public boolean abort() throws LoginException {
        if (!succeeded) {
            return false;
        } else if(! commitSucceeded) {
            succeeded = false;
            username = null;
            rolePrincipal = null;
            userPrincipal = null;
        }
        else {
            logout();
        }
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(userPrincipal);
        succeeded = false;
        commitSucceeded = false;
        username = null;
        userPrincipal = null;
        rolePrincipal = null;
        return true;
    }
}
