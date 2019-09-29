package net.swa.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener
        implements HttpSessionListener {
    private long onlineCount;

    public void sessionCreated(HttpSessionEvent event) {
        this.onlineCount += 1L;
        event.getSession().setAttribute("_onlineCount", Long.valueOf(this.onlineCount));
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        if (this.onlineCount > 0L) {
            this.onlineCount -= 1L;
        }
        event.getSession().setAttribute("_onlineCount", Long.valueOf(this.onlineCount));
    }
}
