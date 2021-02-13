package Controllers;

public class Usuario {
	
	private String cedula, nombre, correo, birth, telefono, clave;

	public Usuario() {
		super();
	}

	public Usuario(String cedula, String nombre, String correo, String birth, String telefono, String clave) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.correo = correo;
		this.birth = birth;
		this.telefono = telefono;
		this.clave = clave;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
	
}
