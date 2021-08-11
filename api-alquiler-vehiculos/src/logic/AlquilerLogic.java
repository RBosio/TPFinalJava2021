package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import data.AlquilerData;
import data.VehiculoData;
import entities.Alquiler;
import entities.Vehiculo;

public class AlquilerLogic {

	AlquilerData ad;
	VehiculoData vd;
	public AlquilerLogic(){
		ad = new AlquilerData();
		vd = new VehiculoData();
	}
	
	public LinkedList<Alquiler> getAll() throws SQLException, IOException{
		LinkedList<Alquiler> alquileres = ad.getAll();
		
		return alquileres;
	}
	
	
	
	public Alquiler newRent(Alquiler a) throws SQLException, IOException{
		Alquiler alquiler = ad.newRent(a);
		
		Vehiculo v = new Vehiculo();
		v.setIdVeh(a.getIdVeh());
		vd.reducirCantidad(v);
		
		return alquiler;
	}
	
	public Alquiler confirmRent(Alquiler a) throws SQLException, IOException{
		Alquiler alquiler = ad.confirmRent(a);
		
		return alquiler;
	}
	
	public Alquiler finishRent(Alquiler a) throws SQLException, IOException{
		Alquiler alquiler = ad.finishRent(a);
		
		return alquiler;
	}
	
	public Alquiler cancelRent(Alquiler a) throws SQLException, IOException{
		Alquiler alquiler = ad.cancelRent(a);
		alquiler = ad.findByDni(a);
		
		Vehiculo v = new Vehiculo();
		v.setIdVeh(alquiler.getIdVeh());
		vd.aumentarCantidad(v);
		
		return alquiler;
	}
}