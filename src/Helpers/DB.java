package Helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONException;
import org.json.JSONObject;

import Controllers.Usuario;
import Helpers.PropertiesReader;


public class DB {

	/*
	private String url="jdbc:postgresql://localhost:5432/Registro";
	private String uname="postgres";
	private String password="17camarones";
	private String driver="org.postgresql.Driver";
	*/
	
	private static String url,uname,pass,driver;
	private static PropertiesReader prop = new PropertiesReader();
	private static Connection conn = null;
	
	/*
	public void cargaDriver(String driver) {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	*/
	
	
	
	public static Connection getConnection() {
		
		prop.properties();	
		driver = prop.getValue("driver");
		System.out.println(driver);
		url = prop.getValue("url");
		pass = prop.getValue("pass");
		uname = prop.getValue("usuario");
		
		Connection con=null;
		
		try {
			if (con == null) {
			Class.forName(driver);
			con=DriverManager.getConnection(url, uname, pass);
			System.out.println("conexion exitosa");
			}
		} catch (SQLException | ClassNotFoundException e1 ) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		}
		
		
		return con;
	}
	
	
	//REGISTRO
	public String insert(String[] user) {
		
		Connection con = getConnection();
		int result = 0;
		String x = "";
		
		String sql = prop.getValue("registro");
    
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, user[0]);
			ps.setString(2, user[1]);
			ps.setString(3, user[2]);
			ps.setString(4, user[3]);
			ps.setString(5, user[4]);
			ps.setString(6, user[5]);
			ps.executeUpdate();
			result= 1; //exitoso
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			result = 0; //fallido
		}
		
		if(result == 1) {
			x = "{\"message\": \"Registro realizado exitosamente\", \"status\": 200 }";
		}else {
			x = "{\"message\": \"El Registro no pudo ser realizado\", \"status\": 503 }";
		}
		
		return x;
	}
	
	//Obtener credenciales del user
	public static String[] getCredenciales(String cedula, String clave) {
		
		Hashing hash = new Hashing();
		hash.generarHash(clave);
		Connection con = getConnection();
		
		String[] user = {"", "", "", "", "", ""};
		
		try {
			String sql = prop.getValue("login");
			PreparedStatement ps;
			ResultSet rs;
			
			ps = con.prepareStatement(sql);
			ps.setString(1, cedula);
			ps.setString(2, clave);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
					for(int y = 0; y < 6; y++) {
					user[y] = rs.getString(y+1);
					System.out.println(user[y]);
					}
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return user;
	}
	
	//LOGIN
	public String doLogin(String cedula, HttpServletResponse response) {

		Connection con = getConnection();
		String x = "";
		String[] cookies = {"","","","","",""};
				
		try {
			String sql = prop.getValue("login");
			PreparedStatement ps;
			ResultSet rs;
			
			ps = con.prepareStatement(sql);
			ps.setString(1, cedula);
					
			rs = ps.executeQuery();
					
			if(rs.next()) {
				
				x = "{\"message\": \"El login realizado exitosamente\", \"status\": 200 }";

				
				for (int i = 0; i < 6 ; i++) {
					cookies[i]=rs.getString(i+1);
				}
			
				Cookie c1 = new Cookie("cedula", cookies[0]);
				response.addCookie(c1);
				
				Cookie c2 = new Cookie("nombre", cookies[1]);
				response.addCookie(c2);
				
				Cookie c3 = new Cookie("correo", cookies[2]);
				response.addCookie(c3);
				
				Cookie c4 = new Cookie("birth", cookies[3]);
				response.addCookie(c4);
				
				Cookie c5 = new Cookie("telefono", cookies[4]);
				response.addCookie(c5);
				
				Cookie c6 = new Cookie("clave", cookies[5]);
				response.addCookie(c6);
			} else {
				x = "{\"message\": \"El login no pudo ser realizado\", \"status\": 503 }";
			}
			
		} catch (SQLException e) {
			System.out.println(e);
			x = "{\"message\": \"El login no pudo ser realizado\", \"status\": 503 }";
		}
		
		return x;
	}
	
	
	public static String subir(String cedula, Part file) throws ServletException, IOException, JSONException{

	    Part filePart = file; 
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); 
	    JSONObject json = new JSONObject();	
	    String ci = "", archivo ="";
	    ci = cedula;
	    archivo = fileName;

	    String path="C:/Users/diego/eclipse-workspace/RegistroWebII/WebContent/Archivos/";
	    File uploads = new File(path); 
	    if(uploads.exists()) {
	    }else {
	    	uploads.mkdirs(); 
	    }	    
  
	    Path destinoPath = Paths.get(path+archivo);
	    try (InputStream input = filePart.getInputStream()){
	        Files.copy(input, destinoPath, StandardCopyOption.REPLACE_EXISTING);
	    }
	    
	    try {
	    	DB.getConnection();
			
	    	
			if(!cedula.equals("") && !archivo.equals("")) {
				
			    json.put("mensaje", "Archivo Subido");
		        json.put("status", 200);
		        json.put("filename", archivo);
		        json.put("email", cedula);  
		        return json.toString();
			}else {
				 json.put("mensaje", "Error Al Subir el Archivo");
			     json.put("status", 500);
			     return json.toString();
			}
			
			}catch(Exception e) {
				System.out.println(e.getMessage());

			}
	     json.put("mensaje", "Error Al Subir el Archivo");
	     json.put("status", 500);
	     return json.toString();
	        
	   
	}
	
	public static String obtenerDatos(String cedula)throws ServletException, JSONException {
		DB.getConnection();
		
		JSONObject json = new JSONObject();
    	Connection conexion = null;
    	PreparedStatement query = null;
    	String ci = ""; 
    	ci = cedula;
    	try {
    		if(!cedula.equals("") ) {			
				

			    json.put("mensaje","Se Encontraron Archivos" );
				json.put("status", 200);
				
								        			        
					
				return json.toString();
    		}else {
			 json.put("status", 500);
		     json.put("mensaje","No Tiene Archivos Guardados" );	       	 
	         return json.toString();
    		}
    	}catch (Exception e) {			
			e.printStackTrace();
		}
		 
		json.put("status", 500);
	    json.put("mensaje","No Tiene Archivos Guardados" );    	 
        return json.toString();
    }
	
	public static String borrarArchivos(String cedula, String filename)throws ServletException, JSONException {
		
				DB.getConnection();
				JSONObject json = new JSONObject();
		    	Connection conexion = null;
		    	PreparedStatement query = null;
		    	String file = ""; file = filename;
		    	String ci = ""; 
		    	ci =cedula;
		    	try {
		    		if(!file.equals("") ) {			



					    json.put("mensaje","Archivo Borrado" );
						json.put("status", 200);																	        			        										
						return json.toString();
		    		}else {
					 json.put("status", 500);
				     json.put("mensaje","El Archivo No Pudo Ser Borrado" );	       	 
			         return json.toString();
		    		}
		    	}catch (Exception e) {			
					e.printStackTrace();
				}
				 
		    	json.put("status", 500);
			    json.put("mensaje","El Archivo No Pudo Ser Borrado" );   	 
		        return json.toString();	
	}
	
	public static String imagen(Part file)throws ServletException, JSONException{

	    Part filePart = file; 
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); 
	    JSONObject json = new JSONObject();	
	    String archivo ="";
	    archivo = fileName;
	    
	    try {
	    String path="C:/Users/diego/eclipse-workspace/RegistroWebII/WebContent/Archivos/";
	    File uploads = new File(path); 
	    if(uploads.exists()) {
	    }else {
	    	uploads.mkdirs(); 
	    }	    
  
	    Path destinoPath = Paths.get(path+archivo);
	    try (InputStream input = filePart.getInputStream()){
	        Files.copy(input, destinoPath, StandardCopyOption.REPLACE_EXISTING);
	    }	    	    	    	 
			    
	    json.put("mensaje", "Foto Subida");
		json.put("status", 200);
		json.put("filename", archivo); 
		return json.toString();					
			
	    }catch(Exception e) {
			System.out.println(e.getMessage());
			json.put("mensaje", "Error Al Subir el Archivo");
		    json.put("status", 500);
		    return json.toString();

		}
	    	        	   
	}
	
	public static String cerrarSesion(HttpServletRequest req) throws JSONException {
        String mensaje = req.getParameter("mensaje");
        JSONObject json = new JSONObject();
        
        if(mensaje.equals("cerrar Sesion")) {
        	HttpSession session = req.getSession();
        	session.invalidate();
        	 
	         json.put("mensaje", "Cerrando Sesion");
	         json.put("status", 200);
            	 
	         return json.toString();
        }else {
        	json.put("mensaje", "Error Al Cerrar Sesion");
	        json.put("status", 500);
           	 
	        return json.toString();
        }
		
	}
		 				    		   	 	
}

