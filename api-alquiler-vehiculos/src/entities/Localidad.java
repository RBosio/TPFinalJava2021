package entities;

public class Localidad {
	
	private String codPostal;
	private String nombre;
	private Boolean estado;
	private int idProv;
	
	private Provincia provincia;
	
	public String getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public int getIdProv() {
		return idProv;
	}
	public void setIdProv(int idProv) {
		this.idProv = idProv;
	}
	public Provincia getProvincia() {
		return provincia;
	}
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	
}
