package dev.ivank.trchatbotdemo.common.form;

import dev.ivank.trchatbotdemo.security.auth.domain.Role;

public record UserDataDto(String firstName, String lastName, String email, Role role) {
}
