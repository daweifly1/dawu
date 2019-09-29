package net.swa.util;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ControllerThreadSocketFactory;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySecureProtocolSocketFactory
        implements SecureProtocolSocketFactory {
    private SSLContext sslContext = null;

    public static String get(String path)
            throws Exception {
        System.setProperty("jsse.enableSNIExtension", "false");
        if (path.startsWith("https")) {
            ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
            Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
        }
        HttpClient cleint = new HttpClient();
        GetMethod get = new GetMethod(path);
        cleint.executeMethod(get);

        return new String(get.getResponseBody(), "utf-8");
    }

    private static SSLContext createEasySSLContext() {
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, new TrustManager[]{new MyX509TrustManager()}, null);
            return context;
        } catch (Exception e) {
            throw new HttpClientError(e.toString());
        }
    }

    private SSLContext getSSLContext() {
        if (this.sslContext == null) {
            this.sslContext = createEasySSLContext();
        }
        return this.sslContext;
    }

    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort)
            throws IOException {
        return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }

    public Socket createSocket(String host, int port, InetAddress localAddress, int localPort, HttpConnectionParams params)
            throws IOException {
        if (params == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }
        int timeout = params.getConnectionTimeout();
        if (timeout == 0) {
            return createSocket(host, port, localAddress, localPort);
        }
        return ControllerThreadSocketFactory.createSocket(this, host, port, localAddress, localPort, timeout);
    }

    public Socket createSocket(String host, int port)
            throws IOException {
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }

    public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
            throws IOException {
        return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
    }
}
