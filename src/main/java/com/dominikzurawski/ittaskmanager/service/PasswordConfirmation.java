package com.dominikzurawski.ittaskmanager.service;

import org.springframework.stereotype.Service;

@Service
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
