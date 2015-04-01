package com.demandforce.bluebox.admin.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
 
public class Role implements GrantedAuthority {
 
    private static final long serialVersionUID = 1L;
    private String name;
 
    private List<Privilege> privileges;
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    @Override
    public String getAuthority() {
        return this.name;
    }
 
    public List<Privilege> getPrivileges() {
        return privileges;
    }
 
    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }
 
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Role [name=");
        builder.append(name);
        builder.append(", privileges=");
        builder.append(privileges);
        builder.append("]");
        return builder.toString();
    }
}