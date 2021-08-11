package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import data.MarcaData;
import entities.Marca;

public class MarcaLogic {

	MarcaData md;
	public MarcaLogic(){
		md = new MarcaData();
	}
	
	public LinkedList<Marca> getAll() throws SQLException, IOException{
		LinkedList<Marca> marcas = md.getAll();
		
		return marcas;
	}
	
	public Marca getOne(Marca m) throws SQLException, IOException{
		Marca marca = md.findById(m);
		
		return marca;
	}
	
	public Marca newBrand(Marca m) throws SQLException, IOException{
		Marca marca = md.newBrand(m);
		
		return marca;
	}
	
	public Marca updateBrand(Marca m) throws SQLException, IOException{
		Marca marca = md.updateBrand(m);
		
		return marca;
	}
	
	public Marca deleteBrand(Marca m) throws SQLException, IOException{
		Marca marca = md.deleteBrand(m);
		
		return marca;
	}
}
