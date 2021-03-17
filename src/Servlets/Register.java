package Servlets;


import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controllers.Usuario;
import Helpers.DB;
import Helpers.Hashing;


@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Hashing hash = new Hashing();
	DB db = new DB();
	
  
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		
		PrintWriter salida = response.getWriter();
		
		String cedula=request.getParameter("cedula");
		String nombre=request.getParameter("nombre");
		String correo=request.getParameter("correo");
		String birth=request.getParameter("birth");
		String telefono=request.getParameter("telefono");
		String clave=request.getParameter("clave");
		String nuevaClave = hash.generarHash(clave);
		System.out.println(nuevaClave);
		//Usuario user = new Usuario(cedula, nombre, correo, birth, telefono, nuevaClave);
		
		String[] user = {cedula, nombre, correo, birth, telefono, nuevaClave};
		String result = db.insert(user);
		
		System.out.println(result);
		salida.println(result);
	}

}
