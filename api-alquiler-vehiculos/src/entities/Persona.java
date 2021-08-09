package entities;

import java.util.LinkedList;

public class Persona {
	
	private String dni;
	private String nombre;
	private String apellido;
	private String email;
	private String password;
	private String telefono;
	private Boolean estado;
	private String codPostal;
	
	private String nomLoc;
	private String denomProv;
	private String denomPais;
	private LinkedList<Rol> roles;
	private String token;
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public String getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	public String getNomLoc() {
		return nomLoc;
	}
	public void setNomLoc(String nomLoc) {
		this.nomLoc = nomLoc;
	}
	public String getDenomProv() {
		return denomProv;
	}
	public void setDenomProv(String denomProv) {
		this.denomProv = denomProv;
	}
	public String getDenomPais() {
		return denomPais;
	}
	public void setDenomPais(String denomPais) {
		this.denomPais = denomPais;
	}
	public LinkedList<Rol> getRoles() {
		return roles;
	}
	public void setRoles(LinkedList<Rol> roles) {
		this.roles = roles;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
