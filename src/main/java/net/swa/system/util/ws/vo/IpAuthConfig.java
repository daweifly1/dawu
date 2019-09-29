package net.swa.system.util.ws.vo;

import java.util.List;

public class IpAuthConfig {
    private List<String> allowedList;
    private List<String> deniedList;

    public List<String> getAllowedList() {
        return this.allowedList;
    }

    public void setAllowedList(List<String> allowedList) {
        this.allowedList = allowedList;
    }

    public List<String> getDeniedList() {
        return this.deniedList;
    }

    public void setDeniedList(List<String> deniedList) {
        this.deniedList = deniedList;
    }
}
