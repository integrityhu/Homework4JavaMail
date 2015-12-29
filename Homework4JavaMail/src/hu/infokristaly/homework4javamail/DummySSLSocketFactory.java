package hu.infokristaly.homework4javamail;


import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;

public class DummySSLSocketFactory extends SSLSocketFactory {
    private SSLSocketFactory factory;

    public DummySSLSocketFactory() {

    	try {
    	    SSLContext sslcontext = SSLContext.getInstance("TLS");
    	    sslcontext.init(null,
    				 new TrustManager[] { new DummyTrustManager()},
    				 null);
    	    factory = (SSLSocketFactory)sslcontext.getSocketFactory();
    	} catch(Exception ex) {
    	    // ignore
    	}
    }

    public static SocketFactory getDefault() {
        return new DummySSLSocketFactory();
    }
    
    public Socket createSocket() throws IOException {
        return factory.createSocket();
    }

    public Socket createSocket(Socket socket, String s, int i, boolean flag)
            throws IOException {
        return factory.createSocket(socket, s, i, flag);
    }

    public Socket createSocket(InetAddress inaddr, int i, InetAddress inaddr2, int j)
            throws IOException {
        return factory.createSocket(inaddr, i, inaddr2, j);
    }

    public Socket createSocket(InetAddress inaddr, int i) throws IOException {
        return factory.createSocket(inaddr, i);
    }

    public Socket createSocket(String s, int i, InetAddress inaddr, int j) throws IOException {
        return factory.createSocket(s, i, inaddr, j);
    }

    public Socket createSocket(String s, int i) throws IOException {
        return factory.createSocket(s, i);
    }

    public String[] getDefaultCipherSuites() {
        return factory.getSupportedCipherSuites();
    }

    public String[] getSupportedCipherSuites() {
        return factory.getSupportedCipherSuites();
    }
}

/**
 * Trust manager which accepts certificates without any validation
 * except date validation.
 */
class DummyTrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        // Do nothing for now.
    }

    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        // Do nothing for now.
    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
