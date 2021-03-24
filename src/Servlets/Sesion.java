package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import Helpers.DB;

@MultipartConfig
@WebServlet("/Sesion")
public class Sesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public Sesion() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	
		PrintWriter out = resp.getWriter();        
		resp.setContentType("application/json");	    	   	      
		try {
			String mensaje = req.getParameter("mensaje");
		
			if(mensaje.equals("Foto Subida")) {
				out.println(DB.imagen(req.getPart("imagenes")));	
			}else {
				return;
			}
								
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
			out.println(DB.cerrarSesion(request));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
