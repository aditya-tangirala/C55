package org.asu.ss.C55_Backend;

public class Configuration 
{
	private static final String EXTERNAL_PROVIDERS_FILE = "otp.providers";

	public static String getExternalOTPProviders() {
		return System.getProperty(EXTERNAL_PROVIDERS_FILE);
	}
}
