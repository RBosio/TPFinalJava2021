package logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import data.AlquilerData;
import data.CoberturaData;
import data.PersonaData;
import data.VehiculoData;
import entities.Alquiler;
import entities.Cobertura;
import entities.Persona;
import entities.Vehiculo;

public class AlquilerLogic {

	AlquilerData ad;
	VehiculoData vd;
	PersonaData pd;
	CoberturaData cd;
	String email;
	String pass;
	public AlquilerLogic(){
		ad = new AlquilerData();
		vd = new VehiculoData();
		pd = new PersonaData();
		cd = new CoberturaData();
		try {
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String appConfigPath = rootPath + "config.properties";
			Properties props = new Properties();
			props.load(new FileInputStream(appConfigPath));
			email = props.getProperty("email");
			pass = props.getProperty("password");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public LinkedList<Alquiler> getAll() throws SQLException, IOException{
		LinkedList<Alquiler> alquileres = ad.getAll();
		
		return alquileres;
	}
	
	
	
	public Alquiler newRent(Alquiler a) throws SQLException, IOException{
		Alquiler alquiler = ad.newRent(a);
		int id;
		
		Vehiculo v = new Vehiculo();
		v.setIdVeh(a.getIdVeh());
		vd.reducirCantidad(v);
		
		alquiler = ad.findByDni(a);
		v = alquiler.getVehiculo();
		
		Persona persona = new Persona();
		persona = alquiler.getPersona();
		persona.setDni(a.getDni());
		id = ad.getIdFactura();
		long periodo = Duration.between(a.getFechaHoraInicio(), a.getFechaHoraFin()).toDays();
		
		Cobertura cobertura = new Cobertura();
		cobertura = alquiler.getCobertura();
		
		envioEmail(persona, cobertura, a, id, periodo, v);
		
		
		return alquiler;
	}
	
	public Alquiler confirmRent(Alquiler a) throws SQLException, IOException{
		Alquiler alquiler = ad.confirmRent(a);
		
		return alquiler;
	}
	
	public Alquiler finishRent(Alquiler a) throws SQLException, IOException{
		Alquiler alquiler = ad.finishRent(a);
		
		Vehiculo v = new Vehiculo();
		v.setIdVeh(alquiler.getIdVeh());
		vd.aumentarCantidad(v);
		
		return alquiler;
	}
	
	public Alquiler cancelRent(Alquiler a) throws SQLException, IOException{
		Alquiler alquiler = ad.cancelRent(a);
		alquiler = ad.findByDni(a);
		
		Vehiculo v = new Vehiculo();
		v.setIdVeh(alquiler.getIdVeh());
		vd.aumentarCantidad(v);
		
		return alquiler;
	}
	
	public void envioEmail(Persona persona, Cobertura cobertura, Alquiler a, int id, long periodo, Vehiculo v){
		
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props);
		
		String correoRemitente = email;
		String passwordRemitente = pass;
		String correoReceptor = "roccomatumbo@gmail.com";
		String asunto = "Factura";
		
		 try {
			 MimeMessage message = new MimeMessage(session);
			 message.setFrom(new InternetAddress(correoRemitente));
			 
			 message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
			 message.setSubject(asunto);
			 
			 MimeMultipart multipart = new MimeMultipart("related");
			 
			 BodyPart messageBodyPart = new MimeBodyPart();
			 String htmlText = 
			 "<div style=\"text-align: center; font-family: Arial, Helvetica, sans-serif; font-size: 18px;\">"+
		        "<h1 style=\"text-align: left; font-size: 30px;\">RappiCars</h1>"+
		        "<table style=\"width: 100%; margin-top: 40px;\">"+
		            "<tr style=\"\">"+
		                "<th style=\"text-align: left; width: 33%; font-weight: 300;\">Rosario, Santa Fe</th>"+
		                "<th style=\"text-align: center; width: 33%; font-weight: 300;\">Nº de factura #"+id+"</th>"+
		                "<th style=\"text-align: right; width: 33%; font-weight: 300;\">"+persona.getNombre()+" "+persona.getApellido()+"</th>"+
		            "</tr>"+
		            "<tr>"+
		                "<td style=\"text-align: left;\">Argentina</td>"+
		                "<td style=\"text-align: center; padding-top: 10px;\">Desde: "+a.getFechaHoraInicio()+"</td>"+
		                "<td style=\"text-align: right;\">DNI: "+persona.getDni()+"</td>"+
		            "</tr>"+
		            "<tr>"+
		                "<td style=\"text-align: left;\">javaapp.alquiler01@gmail.com</td>"+
		                "<td style=\"text-align: center;\" rowspan=\"2\">Hasta: "+a.getFechaHoraFin()+"</td>"+
		                "<td style=\"text-align: right;\">"+persona.getEmail()+"</td>"+
		            "</tr>"+
		            "<tr>"+
		                "<td style=\"text-align: left;\">341 657-4367</td>"+
		                "<td style=\"text-align: right;\">"+persona.getTelefono()+"</td>"+
		            "</tr>"+
		        "</table>"+
		        "<table style=\"width: 100%; margin-top: 40px;\">"+
		            "<tr style=\"color: white; background-color: rgb(124, 124, 124);\">"+
		                "<th style=\"border-bottom: 1px solid #ddd;\">Descripcion</th>"+
		                "<th style=\"border-bottom: 1px solid #ddd;\">Precio por dia</th>"+
		                "<th style=\"border-bottom: 1px solid #ddd; width: 150px;\">Importe</th>"+
		            "</tr>"+
		            "<tr>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">"+v.getDenominacion()+"</td>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">"+v.getPrecioDia()+"</td>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">"+calcularImporte(v.getPrecioDia(), periodo)+"</td>"+
		            "</tr>"+
		            "<tr>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">"+cobertura.getDescripcion()+"</td>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">"+cobertura.getPrecioDia()+"</td>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">"+calcularImporte(cobertura.getPrecioDia(), periodo)+"</td>"+
		            "</tr>"+
		        "</table>"+
		        "<table style=\"width: 40%; margin-left: 60%;\">"+
		            "<tr>"+
		                "<th style=\"border-bottom: 1px solid #ddd; font-weight: bold; color: white; background-color: rgb(124, 124, 124);\">Subtotal</th>"+
		                "<th style=\"border-bottom: 1px solid #ddd; font-weight: 300; width: 150px;\">Precio por dia</th>"+
		            "</tr>"+
		            "<tr>"+
		                "<td style=\"border-bottom: 1px solid #ddd; font-weight: bold; color: white; background-color: rgb(124, 124, 124);\">IVA (21%)</td>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">3000</td>"+
		            "</tr>"+
		            "<tr style=\"color: white; background-color: rgb(77, 77, 77); height: 30px;\">"+
		                "<td style=\"border-bottom: 1px solid #ddd; font-weight: bold;\">Total</td>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">300</td>"+
		            "</tr>"+
		        "</table>"+
		     "</div>";
			 messageBodyPart.setContent(htmlText, "text/html");
			 multipart.addBodyPart(messageBodyPart);
			 message.setContent(multipart);
			 Transport t = session.getTransport("smtp");
			 t.connect(correoRemitente, passwordRemitente);
			 t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			 t.close();
			 System.out.println("Mail sent successfully!!!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public double calcularImporte(double precioDia, long periodo){
		return precioDia * periodo;
	}
}