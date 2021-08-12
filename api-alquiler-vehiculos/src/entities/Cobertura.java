package entities;

public class Cobertura {
	private int idCob;
	private String descripcion;
	private double precioDia;
	private boolean estado;
	
	public int getIdCob() {
		return idCob;
	}
	public void setIdCob(int idCob) {
		this.idCob = idCob;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getPrecioDia() {
		return precioDia;
	}
	public void setPrecioDia(double precioDia) {
		this.precioDia = precioDia;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
}
