package data;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import entities.Marca;

public class MarcaData {
	
	public LinkedList<Marca> getAll() throws SQLException, IOException{
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Marca> marcas = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM marca");
			
				while(rs.next()){
					Marca m = new Marca();
					m.setIdMarca(rs.getInt("idMarca"));
					m.setDenominacion(rs.getString("denominacion"));
					m.setEstado(rs.getBoolean("estado"));
					marcas.add(m);
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
		
		return marcas;
	}
	
	public Marca findById(Marca m) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM marca WHERE idMarca=?");
			stmt.setInt(1, m.getIdMarca());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				m = new Marca();
				m.setIdMarca(rs.getInt("idMarca"));
				m.setDenominacion(rs.getString("denominacion"));
				m.setEstado(rs.getBoolean("estado"));
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
		
		return m;
	}
	
	public Marca newBrand(Marca nuevaM) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet key = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO marca(denominacion) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, nuevaM.getDenominacion());
			stmt.executeUpdate();
			key = stmt.getGeneratedKeys();
			if(key != null && key.next()) {
				nuevaM.setIdMarca(key.getInt(1));
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
		
		return nuevaM;
	}
	
	public Marca updateBrand(Marca actM) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE marca SET denominacion=? WHERE idMarca=?");
			stmt.setString(1, actM.getDenominacion());
			stmt.setInt(2, actM.getIdMarca());
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
		
		return actM;
	}
	
	public Marca deleteBrand(Marca delM) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE marca SET estado=? WHERE idMarca=?");
			stmt.setBoolean(1, false);
			stmt.setInt(2, delM.getIdMarca());
			stmt.executeUpdate();
			
			delM.setEstado(false);
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
		
		return delM;
	}
}
