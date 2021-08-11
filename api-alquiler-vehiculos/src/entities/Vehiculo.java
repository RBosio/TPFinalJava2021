package entities;

public class Vehiculo {
	
	private int idVeh;
	private String denominacion;
	private String imagen;
	private int cantPersonas;
	private String tipoCambio;
	private Boolean aireAc;
	private Boolean abs;
	private Double precioDia;
	private int cantidad;
	private Boolean estado;
	private int idMarca;
	
	private Marca marca;

	public int getIdVeh() {
		return idVeh;
	}

	public void setIdVeh(int idVeh) {
		this.idVeh = idVeh;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getCantPersonas() {
		return cantPersonas;
	}

	public void setCantPersonas(int cantPersonas) {
		this.cantPersonas = cantPersonas;
	}

	public String getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public Boolean getAireAc() {
		return aireAc;
	}

	public void setAireAc(Boolean aireAc) {
		this.aireAc = aireAc;
	}

	public Boolean getAbs() {
		return abs;
	}

	public void setAbs(Boolean abs) {
		this.abs = abs;
	}

	public Double getPrecioDia() {
		return precioDia;
	}

	public void setPrecioDia(Double precioDia) {
		this.precioDia = precioDia;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	public int getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(int idMarca) {
		this.idMarca = idMarca;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
}
