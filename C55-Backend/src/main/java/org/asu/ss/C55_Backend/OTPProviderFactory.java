
package org.asu.ss.C55_Backend;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class OTPProviderFactory {
	private static final String PROPS_CLASS = "class";
	private static final String PROPS_PROVIDERS = "providers";
	private static final String PROVIDERS = "providers.properties";
	public static Map<String, OTPProvider> providers = new HashMap<String, OTPProvider>();

	static {
		loadProvider(OTPProviderFactory.class.getClassLoader().getResourceAsStream(PROVIDERS));

		String ext = Configuration.getExternalOTPProviders();
		if (ext != null) {
			try {
				loadProvider(new FileInputStream(ext));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	};

	private static void loadProvider(InputStream is) {
		Properties props = new Properties();
		try {
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			System.out.println("Error is:"+e);
			e.printStackTrace();
		}

		String[] ps = props.getProperty(PROPS_PROVIDERS).split(",");
		for (String p : ps) {
			try {
				OTPProvider otpp = (OTPProvider) Class.forName((String) props.get(p + "." + PROPS_CLASS)).newInstance();
				Map<String, String> pprops = new HashMap<String, String>();
				Enumeration<Object> keys = props.keys();

				while (keys.hasMoreElements()) {
					String key = keys.nextElement().toString();

					if (key.startsWith(p + ".") && !key.equalsIgnoreCase(p + "." + PROPS_CLASS)) {
						String att = key.substring(key.indexOf('.') + 1);

						// System.out.println(att + ":" +
						// props.getProperty(key));
						pprops.put(att, props.getProperty(key));
					}
				}

				otpp.setProperties(pprops);

				providers.put(p.toLowerCase(), otpp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static OTPProvider getOTPProvider(String name) {
		return providers.get(name.toLowerCase());
	}

	public static void addProvider(String name, OTPProvider provider) {
		providers.put(name, provider);
	}
}
