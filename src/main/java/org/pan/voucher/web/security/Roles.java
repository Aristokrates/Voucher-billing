package org.pan.voucher.web.security;

/**
 * Security roles 
 *
 * @author Pance.Isajeski
 */
public enum Roles {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    /** The role name. */
    private String roleName;

    private Roles(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
