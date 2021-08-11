package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import data.ProvinciaData;
import entities.Provincia;

public class ProvinciaLogic {

	ProvinciaData pd;
	public ProvinciaLogic(){
		pd = new ProvinciaData();
	}
	
	public LinkedList<Provincia> getAll() throws SQLException, IOException{
		LinkedList<Provincia> provincias = pd.getAll();
		
		return provincias;
	}
	
	public Provincia getOne(Provincia p) throws SQLException, IOException{
		Provincia provincia = pd.findById(p);
		
		return provincia;
	}
	
	public Provincia newProvince(Provincia p) throws SQLException, IOException{
		Provincia provincia = pd.newProvince(p);
		
		return provincia;
	}
	
	public Provincia updateProvince(Provincia p) throws SQLException, IOException{
		Provincia provincia = pd.updateProvince(p);
		
		return provincia;
	}
	
	
	public Provincia deleteProvince(Provincia p) throws SQLException, IOException{
		Provincia provincia = pd.deleteProvince(p);
		
		return provincia;
	}
}
