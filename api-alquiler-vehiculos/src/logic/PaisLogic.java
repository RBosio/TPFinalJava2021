package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import data.PaisData;
import entities.Pais;

public class PaisLogic {

	PaisData pd;
	public PaisLogic(){
		pd = new PaisData();
	}
	
	public LinkedList<Pais> getAll() throws SQLException, IOException{
		LinkedList<Pais> paises = pd.getAll();
		
		return paises;
	}
	
	public Pais getOne(Pais p) throws SQLException, IOException{
		Pais pais = pd.findById(p);
		
		return pais;
	}
	
	public Pais newCountry(Pais p) throws SQLException, IOException{
		Pais pais = pd.newCountry(p);
		
		return pais;
	}
	
	public Pais updateCountry(Pais p) throws SQLException, IOException{
		Pais pais = pd.updateCountry(p);
		
		return pais;
	}
	
	public Pais deleteCountry(Pais p) throws SQLException, IOException{
		Pais pais = pd.deleteCountry(pd.findById(p));
		
		return pais;
	}
}
