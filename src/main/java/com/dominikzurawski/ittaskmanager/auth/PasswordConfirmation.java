package com.dominikzurawski.ittaskmanager.auth;

import org.springframework.context.annotation.Bean;

public class PasswordConfirmation {

    String passwordToConfirm;

    public PasswordConfirmation(String passwordToConfirm) {
        this.passwordToConfirm = passwordToConfirm;
    }

    public PasswordConfirmation() {
    }

    public String getPasswordToConfirm() {
        return passwordToConfirm;
    }

    public void setPasswordToConfirm(String passwordToConfirm) {
        this.passwordToConfirm = passwordToConfirm;
    }
}
