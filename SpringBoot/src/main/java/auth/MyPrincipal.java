package auth;

import java.security.Principal;

public class MyPrincipal implements Principal {
    private String desc;
    private String value;

    public MyPrincipal(String desc, String value) {
        this.desc = desc; this.value = value;
    }

    @Override
    public String getName() {
        return desc + "=" + value;
    }

    public boolean equals(Object otherObject)
    {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        MyPrincipal other = (MyPrincipal) otherObject;
        return getName().equals(other.getName());
    }

    public int hashCode() { return getName().hashCode(); }
}
