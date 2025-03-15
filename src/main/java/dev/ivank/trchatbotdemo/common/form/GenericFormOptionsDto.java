package dev.ivank.trchatbotdemo.common.form;

import dev.ivank.trchatbotdemo.common.form.layout.LayoutFormOptionsDto;

import java.util.HashMap;
import java.util.Map;

public class GenericFormOptionsDto {
    private final Map<String, String> bundle;
    private LayoutFormOptionsDto navMenu;

    public GenericFormOptionsDto() {
        this.bundle = new HashMap<>();
    }

    public GenericFormOptionsDto(Map<String, String> bundle) {
        this.bundle = bundle;
    }

    public Map<String, String> getBundle() {
        return bundle;
    }

    public LayoutFormOptionsDto getNavMenu() {
        return navMenu;
    }

    public void setNavMenu(LayoutFormOptionsDto navMenu) {
        this.navMenu = navMenu;
    }
}
