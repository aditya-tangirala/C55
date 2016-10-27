package org.asu.ss.dao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;
import java.security.KeyFactory;
import java.security.MessageDigest;

import javax.crypto.Cipher;

public class SecuritySS {
	public static final String SymmAlgo = "RSA";
	public static final String HashAlgo = "SHA-256";
	private static String PublicKeyName;
	private static String TransactionFileName;
	private static String BasePath;
	private static String HashFileName;
	private static String homePath;

	// Function calculates the hash of the file whose path is passed as input
	// In our case it will be the path of the bank statement generated
	// It returns a byte array containing the hash
	public  void loadProperties(){
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(homePath+"/conf/config.properties");
			prop.load(input);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		BasePath = prop.getProperty("BasePath");
		PublicKeyName = prop.getProperty("PublicKeyName");
		TransactionFileName = prop.getProperty("TransactionFileName");
		HashFileName = prop.getProperty("HashFileName");
	}
	public  byte[] calculateHash() {
		loadProperties();
		byte[] hashValue = null;
		String FilePath =homePath+ BasePath + TransactionFileName;

		try{
			MessageDigest digest = MessageDigest.getInstance(HashAlgo);
			byte[] fileContent = Files.readAllBytes(Paths.get(FilePath));
			hashValue = digest.digest(fileContent);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return hashValue;
	}
	// This function encrypts the hash calculated in the previous function
	// Input is the hash calculated previously and the "path of the private key file"
	// The return value is the encrypted byte array
	public  byte[] encryptHash(byte[] hashValue){
		byte[] cipherText = null;
		String KeyPath = homePath+BasePath + PublicKeyName;
		String OutputFilePath =homePath+ BasePath + HashFileName;
		System.out.println(OutputFilePath);
		File file =new File(OutputFilePath);

		//if file doesn't exists, then create it
		if(!file.exists()){
			try
			{
				file.createNewFile();

			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		try{
			byte[] keyBytes = Files.readAllBytes(new File(KeyPath).toPath());
		    X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		    KeyFactory kf = KeyFactory.getInstance("RSA");
		    PublicKey publicKey = (PublicKey)kf.generatePublic(spec);
			Cipher cipher = Cipher.getInstance(SymmAlgo);
			cipher.init(Cipher.ENCRYPT_MODE,publicKey);
			cipherText = cipher.doFinal(hashValue);
	        FileOutputStream stream = new FileOutputStream(OutputFilePath,true);
			try {
				stream.write(cipherText);
			} finally {
				stream.close();
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return cipherText;
	}

	public  void pki(String basePath){
		homePath=basePath;
		byte []hash;
		hash = calculateHash();
		encryptHash(hash);
	}

}
