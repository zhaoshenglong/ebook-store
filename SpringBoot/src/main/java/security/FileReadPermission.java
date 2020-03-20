package security;

import java.nio.file.Path;
import java.security.Permission;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileReadPermission extends Permission {
    private String action;

    public FileReadPermission(String target, String anAction) {
        super(target);
        action = anAction;
    }

    private Set<String> Paths() {return new HashSet<>(Arrays.asList(getName().split(",")));
    }

    @Override
    public boolean implies(Permission other) {
        if (!(other instanceof FileReadPermission)) return false;
        FileReadPermission b = (FileReadPermission) other;
        if (action.equals("read")) {
            return b.action.equals("read") && Paths().containsAll(b.Paths());
        } else return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!getClass().equals(other.getClass())) return false;
        FileReadPermission b = (FileReadPermission) other;
        if (!action.equals(b.action)) return false;

        return Paths().equals(b.Paths());
    }

    @Override
    public int hashCode() {
        return getName().hashCode() + action.hashCode();
    }

    @Override
    public String getActions() {
        return action;
    }
}
