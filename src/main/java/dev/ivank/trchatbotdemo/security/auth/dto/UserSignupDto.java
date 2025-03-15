package dev.ivank.trchatbotdemo.security.auth.dto;

public class UserSignupDto {
    private String firstName;
    private String lastName;
    private String email;
    private PasswordDto pwdForm;
    private boolean tosChecked;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PasswordDto getPwdForm() {
        return pwdForm;
    }

    public void setPwdForm(PasswordDto pwdForm) {
        this.pwdForm = pwdForm;
    }

    public boolean isTosChecked() {
        return tosChecked;
    }

    public void setTosChecked(boolean tosChecked) {
        this.tosChecked = tosChecked;
    }
}
