package entities;

public class Provincia {
	private int idProvincia;
	private String denominacion;
	private Boolean estado;
	private int idPais;
	public int getIdProvincia() {
		return idProvincia;
	}
	
	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public int getIdPais() {
		return idPais;
	}
	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}

	@Override
	public String toString() {
		return "Provincia [idProvincia=" + idProvincia + ", denominacion="
				+ denominacion + ", estado=" + estado + ", idPais=" + idPais
				+ "]";
	}
	
}
