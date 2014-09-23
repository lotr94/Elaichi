import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.net.*;

public class ElaichiEngine {
	private static boolean shouldStop = false;
	private static double[] table;
	public static void main(String args[]) throws Exception  {
		validateAllCertificates();
		
		//btcetester test = new btcetester();
		//test.setUp();
		//test.testInfo();
		Sherlock a = new Sherlock();
		getTable(a,"btc_usd", 1);
		for (int i = 0; i<table.length ; i++) {
			System.out.println(table[i]);
		}
		
	}
	
	static void getTable(Sherlock a, String pair, final int length) throws Exception {
		double[] x = new double[length*60];
		int count = -1;
		shouldStop = false;
		Thread timer = new Thread() {
            	public void run() {
	                try {
	                    	sleep(length * 60 * 1000);
	                } catch (InterruptedException e) {
	                }
                	shouldStop = true;
            	}};
        	timer.start();
        
	        while(!shouldStop) {
	        	try {
	        		count++;
	        		x[count] = a.getBuy(pair);
					Thread.sleep(1000);
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
	        }
        	table = x;
	}
	
	public static void validateAllCertificates() throws KeyManagementException, NoSuchAlgorithmException {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}
				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			}
		};

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}
	
}
