package Helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	
	Properties configP = new Properties();
    InputStream configI = null;
    
    private static String rutaArchivo = "C:/Users/diego/eclipse-workspace/RegistroWebII/src/Helpers/propertiesFile.properties";

    
	public void properties() {
		try {
			configI = new FileInputStream(rutaArchivo);
			configP.load(configI);
			
		}catch(IOException e) {
			System.out.println("Error en el propertie reader: "+e.getMessage());
		}
	}

	public String getValue(String valor) {
		String value;
		value = configP.getProperty(valor);
		return value;
	}
}
