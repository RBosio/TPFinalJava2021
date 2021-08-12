package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import data.CoberturaData;
import entities.Cobertura;

public class CoberturaLogic {

	CoberturaData cd;
	public CoberturaLogic(){
		cd = new CoberturaData();
	}
	
	public LinkedList<Cobertura> getAll() throws SQLException, IOException{
		LinkedList<Cobertura> coberturas = cd.getAll();
		
		return coberturas;
	}
	
	public Cobertura getOne(Cobertura c) throws SQLException, IOException{
		Cobertura cobertura = cd.findById(c);
		
		return cobertura;
	}
	
	public Cobertura newInsurance(Cobertura c) throws SQLException, IOException{
		Cobertura cobertura = cd.newInsurance(c);
		
		return cobertura;
	}
	
	public Cobertura updateInsurance(Cobertura c) throws SQLException, IOException{
		Cobertura cobertura = cd.updateInsurance(c);
		
		return cobertura;
	}
	
	public Cobertura deleteInsurance(Cobertura c) throws SQLException, IOException{
		Cobertura cobertura = cd.deleteInsurance(c);
		
		return cobertura;
	}
}
