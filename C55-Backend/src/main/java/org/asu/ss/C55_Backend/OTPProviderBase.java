
package org.asu.ss.C55_Backend;

import java.util.HashMap;
import java.util.Map;

public abstract class OTPProviderBase implements OTPProvider {
	protected Map<String, String> properties = new HashMap<String, String>();

	public void setProperties(Map<String, String> props) {
		this.properties = props;
	}

	public Map<String, String> getProperties() {
		return properties;
	}
}
