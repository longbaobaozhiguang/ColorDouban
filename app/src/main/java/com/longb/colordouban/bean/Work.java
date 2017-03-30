package com.longb.colordouban.bean;

import java.util.List;

public class Work {

    private List<String> roles;
    private Subject subject;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
