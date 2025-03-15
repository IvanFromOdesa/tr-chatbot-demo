package dev.ivank.trchatbotdemo.common.form;

public class AuthenticatedFormOptionsDto extends BaseFormOptionsDto {
    private UserDataDto userData;

    public UserDataDto getUserData() {
        return userData;
    }

    public void setUserData(UserDataDto userData) {
        this.userData = userData;
    }
}
