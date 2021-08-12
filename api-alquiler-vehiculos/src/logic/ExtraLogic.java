package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import data.ExtraData;
import entities.Extra;

public class ExtraLogic {

	ExtraData cd;
	public ExtraLogic(){
		cd = new ExtraData();
	}
	
	public LinkedList<Extra> getAll() throws SQLException, IOException{
		LinkedList<Extra> extras = cd.getAll();
		
		return extras;
	}
	
	public Extra getOne(Extra e) throws SQLException, IOException{
		Extra extra = cd.findById(e);
		
		return extra;
	}
	
	public Extra newExtra(Extra e) throws SQLException, IOException{
		Extra extra = cd.newExtra(e);
		
		return extra;
	}
	
	public Extra updateExtra(Extra e) throws SQLException, IOException{
		Extra extra = cd.updateExtra(e);
		
		return extra;
	}
	
	public Extra deleteExtra(Extra c) throws SQLException, IOException{
		Extra extra = cd.deleteExtra(c);
		
		return extra;
	}
}
