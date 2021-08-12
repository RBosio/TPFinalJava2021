package data;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import entities.Cobertura;

public class CoberturaData {
	
	public LinkedList<Cobertura> getAll() throws SQLException, IOException{
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Cobertura> coberturas = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM cobertura");
			
				while(rs.next()){
					Cobertura c = new Cobertura();
					c.setIdCob(rs.getInt("idCob"));
					c.setDescripcion(rs.getString("descripcion"));
					c.setPrecioDia(rs.getDouble("precioDia"));
					c.setEstado(rs.getBoolean("estado"));
					coberturas.add(c);
				}
		} catch (SQLException e) {
			throw new SQLException();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				if(rs != null ) rs.close();
				if(stmt!= null ) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				throw new SQLException();
			}
		}
		
		return coberturas;
	}
	
	public Cobertura findById(Cobertura c) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM cobertura WHERE idCob=?");
			stmt.setInt(1, c.getIdCob());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				c = new Cobertura();
				c.setIdCob(rs.getInt("idCob"));
				c.setDescripcion(rs.getString("descripcion"));
				c.setPrecioDia(rs.getDouble("precioDia"));
				c.setEstado(rs.getBoolean("estado"));
			} else {
				throw new NullPointerException();
			}
		} catch (SQLException e) {
			throw new SQLException();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				if(rs != null ) rs.close();
				if(stmt!= null ) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				throw new SQLException();
			}
		}
		
		return c;
	}
	
	public Cobertura newInsurance(Cobertura nuevaC) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet key = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO cobertura(descripcion, precioDia) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, nuevaC.getDescripcion());
			stmt.setDouble(2, nuevaC.getPrecioDia());
			stmt.executeUpdate();
			key = stmt.getGeneratedKeys();
			if(key != null && key.next()) {
				nuevaC.setIdCob(key.getInt(1));
			}
		} catch (SQLException e) {
			throw new SQLException();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				if (key != null) key.close();
				if (stmt!= null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				throw new SQLException();
			}
		}
		
		return nuevaC;
	}
	
	public Cobertura updateInsurance(Cobertura actC) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE cobertura SET descripcion=?, precioDia=? WHERE idCob=?");
			stmt.setString(1, actC.getDescripcion());
			stmt.setDouble(2, actC.getPrecioDia());
			stmt.setInt(3, actC.getIdCob());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new SQLException();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				if (stmt!= null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				throw new SQLException();
			}
		}
		
		return actC;
	}
	
	public Cobertura deleteInsurance(Cobertura delC) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE cobertura SET estado=? WHERE idCob=?");
			stmt.setBoolean(1, false);
			stmt.setInt(2, delC.getIdCob());
			stmt.executeUpdate();
			
			delC.setEstado(false);
		} catch (SQLException e) {
			throw new SQLException();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				if (stmt!= null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException | IOException e) {
				throw new SQLException();
			}
		}
		
		return delC;
	}
}
