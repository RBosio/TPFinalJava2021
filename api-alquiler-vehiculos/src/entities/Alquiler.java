package entities;

import java.time.LocalDateTime;

public class Alquiler {
	private String dni;
	private LocalDateTime fechaHoraInicio;
	private int idVeh;
	private LocalDateTime fechaHoraFin;
	private String estado;
	private Double costoTotal;
	private int idCob;
	
	private Persona persona;
	private Vehiculo vehiculo;
	private Cobertura cobertura;
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public LocalDateTime getFechaHoraInicio() {
		return fechaHoraInicio;
	}
	public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}
	public int getIdVeh() {
		return idVeh;
	}
	public void setIdVeh(int idVeh) {
		this.idVeh = idVeh;
	}
	public LocalDateTime getFechaHoraFin() {
		return fechaHoraFin;
	}
	public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Double getCostoTotal() {
		return costoTotal;
	}
	public void setCostoTotal(Double costoTotal) {
		this.costoTotal = costoTotal;
	}
	public int getIdCob() {
		return idCob;
	}
	public void setIdCob(int idCob) {
		this.idCob = idCob;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	public Cobertura getCobertura() {
		return cobertura;
	}
	public void setCobertura(Cobertura cobertura) {
		this.cobertura = cobertura;
	}
	
}
