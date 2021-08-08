package logic;

import java.util.LinkedList;

import data.PaisData;
import entities.Pais;

public class PaisLogic {

	PaisData pd;
	public PaisLogic(){
		pd = new PaisData();
	}
	
	public LinkedList<Pais> getAll(){
		LinkedList<Pais> paises = null;
		paises = pd.getAll();
		
		return paises;
	}
	
	public Pais newCountry(Pais p){
		Pais pais = null;
		pais = pd.newCountry(p);
		
		return pais;
	}
	
	public Pais updateCountry(Pais p){
		Pais pais = null;
		pais = pd.updateCountry(p);
		
		return pais;
	}
	
	
	public Pais deleteCountry(Pais p){
		Pais pais = null;
		pais = pd.deleteCountry(pd.findById(p));
		
		return pais;
	}
}
