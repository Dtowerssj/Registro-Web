package Helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Servlets.Register;

public class Hashing {
	
	Register user = new Register();
	
	public String generarHash(String clave) {
		
		System.out.println("Clave: "+clave);
		
		String claveHash = null;
		
		try {
			
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(clave.getBytes());
			byte[] digestOfPassword = md.digest();
			String sbuilder = digestOfPassword.toString();
			
			claveHash = sbuilder.toString();
			
			System.out.println("Clave: "+claveHash);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return claveHash;
		
	}
	
	public String doHashing(String con) {
		return null;
		
	}

}
