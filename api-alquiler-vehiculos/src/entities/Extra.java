package entities;

public class Extra {
	private int idExtra;
	private String descripcion;
	private double precioDia;
	private boolean estado;
	
	public int getIdExtra() {
		return idExtra;
	}
	public void setIdExtra(int idExtra) {
		this.idExtra = idExtra;
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
