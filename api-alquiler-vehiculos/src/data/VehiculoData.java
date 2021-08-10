package data;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import entities.Marca;
import entities.Vehiculo;

public class VehiculoData {
	
	public LinkedList<Vehiculo> getAll() throws SQLException, IOException{
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Vehiculo> vehiculos = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM vehiculo v INNER JOIN marca m ON v.idMarca = m.idMarca");
			
				while(rs.next()){
					Vehiculo v = new Vehiculo();
					Marca m = new Marca();
					v.setIdVeh(rs.getInt("idVeh"));
					v.setDenominacion(rs.getString("v.denominacion"));
					v.setImagen(rs.getString("imagen"));
					v.setCantPersonas(rs.getInt("cantPersonas"));
					v.setTipoCambio(rs.getString("tipoCambio"));
					v.setAireAc(rs.getBoolean("aireAc"));
					v.setAbs(rs.getBoolean("abs"));
					v.setPrecioDia(rs.getDouble("precioDia"));
					v.setEstado(rs.getBoolean("estado"));
					
					m.setDenominacion(rs.getString("m.denominacion"));
					m.setEstado(rs.getBoolean("m.estado"));
					v.setMarca(m);
					
					vehiculos.add(v);
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
		
		return vehiculos;
	}
	
	public Vehiculo findById(Vehiculo v) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM vehiculo v INNER JOIN marca m ON v.idMarca = m.idMarca WHERE idVeh=?");
			stmt.setInt(1, v.getIdVeh());
			rs = stmt.executeQuery();
			
			if(rs.next()){
				Marca m = new Marca();
				v.setIdVeh(rs.getInt("idVeh"));
				v.setDenominacion(rs.getString("v.denominacion"));
				v.setImagen(rs.getString("imagen"));
				v.setCantPersonas(rs.getInt("cantPersonas"));
				v.setTipoCambio(rs.getString("tipoCambio"));
				v.setAireAc(rs.getBoolean("aireAc"));
				v.setAbs(rs.getBoolean("abs"));
				v.setPrecioDia(rs.getDouble("precioDia"));
				v.setEstado(rs.getBoolean("estado"));

				m.setDenominacion(rs.getString("m.denominacion"));
				m.setEstado(rs.getBoolean("m.estado"));
				v.setMarca(m);
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
		
		return v;
	}
	
	public Vehiculo newVehicle(Vehiculo nuevoV) throws SQLException, IOException{
		PreparedStatement stmt = null;
		ResultSet key = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO vehiculo(denominacion, imagen, cantPersonas, tipoCambio, aireAc, abs, precioDia, idMarca) VALUES(?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, nuevoV.getDenominacion());
			stmt.setString(2, nuevoV.getImagen());
			stmt.setInt(3, nuevoV.getCantPersonas());
			stmt.setString(4, nuevoV.getTipoCambio());
			stmt.setBoolean(5, nuevoV.getAireAc());
			stmt.setBoolean(6, nuevoV.getAbs());
			stmt.setDouble(7, nuevoV.getPrecioDia());
			stmt.setInt(8, nuevoV.getIdMarca());
			stmt.executeUpdate();
			key = stmt.getGeneratedKeys();
			if(key != null && key.next()) {
				nuevoV.setIdVeh(key.getInt(1));
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
		
		return nuevoV;
	}
	
	public Vehiculo updateVehicle(Vehiculo actV) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE vehiculo SET denominacion=?, imagen=?, cantPersonas=?, tipoCambio=?, aireAc=?, abs=?, precioDia=?, idMarca=? WHERE idVeh=?");
			stmt.setString(1, actV.getDenominacion());
			stmt.setString(2, actV.getImagen());
			stmt.setInt(3, actV.getCantPersonas());
			stmt.setString(4, actV.getTipoCambio());
			stmt.setBoolean(5, actV.getAireAc());
			stmt.setBoolean(6, actV.getAbs());
			stmt.setDouble(7, actV.getPrecioDia());
			stmt.setInt(8, actV.getIdMarca());
			stmt.setInt(9, actV.getIdVeh());
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
		
		return actV;
	}
	
	public Vehiculo deleteVehicle(Vehiculo delV) throws SQLException, IOException{
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE vehiculo SET estado=? WHERE idVeh=?");
			stmt.setBoolean(1, false);
			stmt.setInt(2, delV.getIdVeh());
			stmt.executeUpdate();
			
			delV.setEstado(false);
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
		
		return delV;
	}
}
