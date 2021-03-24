package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Helpers.DB;


@MultipartConfig
@WebServlet("/Archivos")
public class Archivos extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public Archivos() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DB videos = new DB();
		PrintWriter out = response.getWriter();        
		response.setContentType("application/json");	    	   	      
		try {
			String cedula = request.getParameter("cedula");
			if(!cedula.equals("")) {
				out.println(videos.subir(cedula, request.getPart("archivo")));	
			}else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
