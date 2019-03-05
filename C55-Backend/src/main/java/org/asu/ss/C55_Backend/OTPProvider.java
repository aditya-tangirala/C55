
package org.asu.ss.C55_Backend;

import java.util.Map;

public interface OTPProvider {
	public String getName();

	public String generate(String key, String base, int digits);

	public void setProperties(Map<String, String> props);

	public Map<String, String> getProperties();
}
