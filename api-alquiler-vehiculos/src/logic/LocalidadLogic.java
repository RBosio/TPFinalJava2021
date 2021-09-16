package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import data.LocalidadData;
import entities.Localidad;

public class LocalidadLogic {

	LocalidadData ld;
	public LocalidadLogic(){
		ld = new LocalidadData();
	}
	
	public LinkedList<Localidad> getAll() throws SQLException, IOException{
		LinkedList<Localidad> localidades = ld.getAll();
		
		return localidades;
	}
	
	public Localidad getOne(Localidad l) throws SQLException, IOException{
		Localidad localidad = ld.findByCodPostal(l);
		
		return localidad;
	}
	
	public Localidad newCity(Localidad l) throws SQLException, IOException{
		Localidad localidad = ld.newCity(l);
		
		return localidad;
	}
	
	public Localidad updateCity(Localidad l) throws SQLException, IOException{
		Localidad localidad = ld.updateCity(l);
		
		return localidad;
	}
	
	public Localidad deleteCity(Localidad l) throws SQLException, IOException{
		Localidad localidad = ld.deleteCity(l);
		
		return localidad;
	}
}
