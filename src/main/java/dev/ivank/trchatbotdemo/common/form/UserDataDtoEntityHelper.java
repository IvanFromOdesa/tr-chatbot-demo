package dev.ivank.trchatbotdemo.common.form;

import dev.ivank.trchatbotdemo.security.auth.domain.User;

public interface UserDataDtoEntityHelper {
    static UserDataDto getUserData(User user) {
        return new UserDataDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
