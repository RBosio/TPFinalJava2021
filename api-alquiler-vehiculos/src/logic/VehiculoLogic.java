package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import data.VehiculoData;
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
	
	public Vehiculo newVehicle(Vehiculo v) throws SQLException, IOException{
		Vehiculo vehiculo = vd.newVehicle(v);
		
		return vehiculo;
	}
	
	public Vehiculo updateVehicle(Vehiculo v) throws SQLException, IOException{
		Vehiculo vehiculo = vd.updateVehicle(v);
		
		return vehiculo;
	}
	
	public Vehiculo deleteVehicle(Vehiculo v) throws SQLException, IOException{
		Vehiculo vehiculo = vd.deleteVehicle(vd.findById(v));
		
		return vehiculo;
	}
}
