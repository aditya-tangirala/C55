
package org.asu.ss.C55_Backend;

public class OneTime 
{
	public static String generate(String key, String base, int digits, String provider) 
	{
		return OTPProviderFactory.getOTPProvider(provider).generate(key, base, digits);
	}
	
	
	public String testTOTP() {
		String key = "12345678";
			String totp = "";
			try {
				/*
				 * OTP generation calculation is borrowed from example source code available on github's public repo 
				 */
				totp = OneTime.generate("" + key, "" + System.currentTimeMillis(), 6, "totp");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return("X@"+totp+"z%");
		}

	public void testHOTP() {
		String secret = "helloworld";

		try {
			for (int i = 0; i < 10; i++)
			{
				//System.out.println(OneTime.generate(secret, "" + 2, 6, "hotp"));
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		OneTime otp = new OneTime();
		//otp.testTOTP();
		//System.out.println("\n");
		//System.out.println("\n");
		//System.out.println("\n");
		otp.testHOTP();
	}
}
