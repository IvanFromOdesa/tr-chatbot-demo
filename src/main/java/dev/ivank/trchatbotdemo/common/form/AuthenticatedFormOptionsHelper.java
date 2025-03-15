package dev.ivank.trchatbotdemo.common.form;

import dev.ivank.trchatbotdemo.security.auth.domain.User;

import static dev.ivank.trchatbotdemo.common.form.UserDataDtoEntityHelper.getUserData;

public interface AuthenticatedFormOptionsHelper {
    default <A extends AuthenticatedFormOptionsDto> A prepareUserData(RestForm<A> form) {
        User authentication = (User) form.getAuthentication().getPrincipal();
        A dto = form.getDto();
        dto.setUserData(getUserData(authentication));
        return dto;
    }
}
