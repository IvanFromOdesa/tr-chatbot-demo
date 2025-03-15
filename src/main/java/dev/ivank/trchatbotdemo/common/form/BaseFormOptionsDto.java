package dev.ivank.trchatbotdemo.common.form;

import java.util.Map;

public class BaseFormOptionsDto extends GenericFormOptionsDto {
    private Map<String, String> pathNavigation;
    private Map<String, String> assets;
    private String locale;

    public Map<String, String> getPathNavigation() {
        return pathNavigation;
    }

    public void setPathNavigation(Map<String, String> pathNavigation) {
        this.pathNavigation = pathNavigation;
    }

    public Map<String, String> getAssets() {
        return assets;
    }

    public void setAssets(Map<String, String> assets) {
        this.assets = assets;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
