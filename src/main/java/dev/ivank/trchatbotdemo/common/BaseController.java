package dev.ivank.trchatbotdemo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class BaseController {
    @Autowired
    protected ControllerProperties properties;

    protected String getBaseUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }
}
