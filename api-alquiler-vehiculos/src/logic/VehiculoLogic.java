package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import data.VehiculoData;
import entities.Alquiler;
import entities.Vehiculo;

public class VehiculoLogic {

	VehiculoData vd;
	public VehiculoLogic(){
		vd = new VehiculoData();
	}
	
	public LinkedList<Vehiculo> getAll() throws SQLException, IOException{
		LinkedList<Vehiculo> vehiculos = vd.getAll();
		
		return vehiculos;
	}
	
	public Vehiculo getOne(Vehiculo v) throws SQLException, IOException{
		Vehiculo vehiculo = vd.findById(v);
		
		return vehiculo;
	}
	
	public LinkedList<Vehiculo> getVehiclesAvailable(String fechaHoraIni, String fechaHoraFin) throws SQLException, IOException{
		fechaHoraIni = fechaHoraIni.replace("T", " ");
    	fechaHoraFin = fechaHoraFin.replace("T", " ");
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime fechaHoraI = LocalDateTime.parse(fechaHoraIni, formatter);
		LocalDateTime fechaHoraF = LocalDateTime.parse(fechaHoraFin, formatter);
		
    	Alquiler alquiler = new Alquiler();
    	alquiler.setFechaHoraInicio(fechaHoraI);
    	alquiler.setFechaHoraFin(fechaHoraF);
		
		LinkedList<Vehiculo> vehiculos = vd.getVehiclesAvailable(alquiler);
		
		return vehiculos;
	}
	
	public Vehiculo newVehicle(Vehiculo v) throws SQLException, IOException{
		Vehiculo vehiculo = vd.newVehicle(v);
		
		return vehiculo;
	}
	
	public Vehiculo updateVehicle(Vehiculo v) throws SQLException, IOException{
		Vehiculo vehiculo = vd.updateVehicle(v);
		
		return vehiculo;
	}
	
	public Vehiculo deleteVehicle(Vehiculo v) throws SQLException, IOException{
		Vehiculo vehiculo = vd.deleteVehicle(v);
		
		return vehiculo;
	}
}
