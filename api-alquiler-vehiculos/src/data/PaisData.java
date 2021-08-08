package data;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import entities.Pais;

public class PaisData {
	
	public LinkedList<Pais> getAll(){
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Pais> paises = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM pais");
			
				while(rs.next()){
					Pais p = new Pais();
					p.setIdPais(rs.getInt("idPais"));
					p.setDenominacion(rs.getString("denominacion"));
					p.setEstado(rs.getBoolean("estado"));
					paises.add(p);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null ) rs.close();
				if(stmt!= null ) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return paises;
	}
	
	public Pais findById(Pais p){
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM pais WHERE idPais=?");
			stmt.setInt(1, p.getIdPais());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				p = new Pais();
				p.setIdPais(rs.getInt("idPais"));
				p.setDenominacion(rs.getString("denominacion"));
				p.setEstado(rs.getBoolean("estado"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null ) rs.close();
				if(stmt!= null ) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return p;
	}
	
	public Pais newCountry(Pais nuevoP){
		PreparedStatement stmt = null;
		ResultSet key = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO pais(denominacion) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, nuevoP.getDenominacion());
			stmt.executeUpdate();
			
			key = stmt.getGeneratedKeys();
			if(key != null && key.next()) {
				nuevoP.setIdPais(key.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Registro duplicado");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (key != null) key.close();
				if (stmt!= null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return nuevoP;
	}
	
	public Pais updateCountry(Pais actP){
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE pais SET denominacion=? WHERE idPais=?");
			stmt.setString(1, actP.getDenominacion());
			stmt.setInt(2, actP.getIdPais());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt!= null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return actP;
	}
	
	public Pais deleteCountry(Pais delP){
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE pais SET denominacion=?, estado=? WHERE idPais=?");
			stmt.setString(1, delP.getDenominacion());
			stmt.setBoolean(2, false);
			stmt.setInt(3, delP.getIdPais());
			stmt.executeUpdate();
			
			delP.setEstado(false);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt!= null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return delP;
	}
}
