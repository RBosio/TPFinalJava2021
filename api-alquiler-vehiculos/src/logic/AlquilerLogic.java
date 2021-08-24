package logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import static java.time.temporal.ChronoUnit.DAYS;
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
import data.ExtraData;
import data.PersonaData;
import data.VehiculoData;
import entities.Alquiler;
import entities.Cobertura;
import entities.Extra;
import entities.Persona;
import entities.Vehiculo;

public class AlquilerLogic {

	AlquilerData ad;
	VehiculoData vd;
	PersonaData pd;
	CoberturaData cd;
	ExtraData ed;
	String email;
	String pass;
	Double total = 0.0;
	public AlquilerLogic(){
		ad = new AlquilerData();
		vd = new VehiculoData();
		pd = new PersonaData();
		cd = new CoberturaData();
		ed = new ExtraData();
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
		Vehiculo vehiculo = new Vehiculo();
		Persona persona = new Persona();
		Cobertura cobertura = new Cobertura();
		LinkedList<Extra> extras = new LinkedList<Extra>();
		int id;
		
		alquiler = ad.findByDni(a);
		
		vehiculo = alquiler.getVehiculo();
		persona = alquiler.getPersona();
		cobertura = alquiler.getCobertura();
		extras = alquiler.getExtras();
		
		persona.setDni(a.getDni());
		id = ad.getIdFactura();
		
		long periodo = DAYS.between(a.getFechaHoraInicio().toLocalDate(), a.getFechaHoraFin().toLocalDate());
		
		envioEmail(persona, cobertura, a, id, periodo, vehiculo, extras);
		
		return alquiler;
	}
	
	public Alquiler confirmRent(Alquiler a) throws SQLException, IOException{
		Alquiler alquiler = ad.confirmRent(a);
		
		return alquiler;
	}
	
	public Alquiler finishRent(Alquiler a) throws SQLException, IOException{
		Alquiler alquiler = ad.finishRent(a);
		alquiler = ad.findByDni(a);
		
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
	
	public void envioEmail(Persona persona, Cobertura cobertura, Alquiler a, int id, long periodo, Vehiculo v, LinkedList<Extra> extras) throws SQLException, IOException{
		
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props);
		
		String correoRemitente = email;
		String passwordRemitente = pass;
		String correoReceptor = persona.getEmail();
		String asunto = "Factura de alquiler de vehiculo - RappiCars";
		
		 try {
			 MimeMessage message = new MimeMessage(session);
			 message.setFrom(new InternetAddress(correoRemitente));
			 
			 message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
			 message.setSubject(asunto);
			 
			 MimeMultipart multipart = new MimeMultipart("related");
			 
			 BodyPart messageBodyPart = new MimeBodyPart();
			 String htmlText = 
			 "<div style=\"text-align: center; font-family: Arial, Helvetica, sans-serif; font-size: 18px; color: black;\">"+
		        "<h1 style=\"text-align: left; font-size: 30px;\">RappiCars</h1>"+
		        "<table style=\"width: 100%; margin-top: 40px;\">"+
		            "<tr style=\"\">"+
		                "<th style=\"text-align: left; width: 33%; font-weight: 300;\">Rosario, Santa Fe</th>"+
		                "<th style=\"text-align: center; width: 33%; font-weight: 300;\">Nº de factura #"+id+"</th>"+
		                "<th style=\"text-align: right; width: 33%; font-weight: 300;\">"+persona.getNombre()+" "+persona.getApellido()+"</th>"+
		            "</tr>"+
		            "<tr>"+
		                "<td style=\"text-align: left;\">Argentina</td>"+
		                "<td style=\"text-align: center; padding-top: 10px;\">Desde: "+a.getFechaHoraInicio().toLocalDate()+"</td>"+
		                "<td style=\"text-align: right;\">DNI: "+persona.getDni()+"</td>"+
		            "</tr>"+
		            "<tr>"+
		                "<td style=\"text-align: left;\">javaapp.alquiler01@gmail.com</td>"+
		                "<td style=\"text-align: center;\" rowspan=\"2\">Hasta: "+a.getFechaHoraFin().toLocalDate()+"</td>"+
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
		                "<th style=\"border-bottom: 1px solid #ddd; width: 200px;\">Precio por dia ($)</th>"+
		                "<th style=\"border-bottom: 1px solid #ddd; width: 150px;\">Importe ($)</th>"+
		            "</tr>"+
		            "<tr>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">"+v.getMarca().getDenominacion()+" "+v.getDenominacion()+"</td>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">"+v.getPrecioDia()+"</td>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">"+calcularImporte(v.getPrecioDia(), periodo)+"</td>"+
		            "</tr>"+
		            "<tr>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">"+cobertura.getDescripcion()+"</td>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">"+cobertura.getPrecioDia()+"</td>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">"+calcularImporte(cobertura.getPrecioDia(), periodo)+"</td>"+
		            "</tr>"+
		            imprimirExtras(extras, periodo, a)+
		        "</table>"+
		        "<table style=\"width: 40%; margin-left: 60%;\">"+
		            "<tr>"+
		                "<th style=\"border-bottom: 1px solid #ddd; font-weight: bold; color: white; background-color: rgb(124, 124, 124);\">Subtotal</th>"+
		                "<th style=\"border-bottom: 1px solid #ddd; font-weight: 300; width: 150px;\">"+devolverSubTotal(total)+"</th>"+
		            "</tr>"+
		            "<tr>"+
		                "<td style=\"border-bottom: 1px solid #ddd; font-weight: bold; color: white; background-color: rgb(124, 124, 124);\">IVA (21%)</td>"+
		                "<td style=\"border-bottom: 1px solid #ddd;\">"+calcularIva(total, a)+"</td>"+
		            "</tr>"+
		            "<tr style=\"color: white; background-color: rgb(77, 77, 77); height: 30px;\">"+
		                "<td style=\"border-bottom: 1px solid #ddd; font-weight: bold;\">Total</td>"+
		                "<td style=\"border-bottom: 1px solid #ddd; font-weight: bold; background-color: rgb(219, 0, 0)\">"+total+"</td>"+
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
		total += precioDia * periodo;
		return precioDia * periodo;
	}
	
	public String imprimirExtras(LinkedList<Extra> extras, long periodo, Alquiler alq){
		String s = "";
		for(Extra e: extras){
			s +="<tr>"+
					"<td style=\"border-bottom: 1px solid #ddd;\">"+e.getDescripcion()+"</td>"+
					"<td style=\"border-bottom: 1px solid #ddd;\">"+e.getPrecioDia()+"</td>"+
					"<td style=\"border-bottom: 1px solid #ddd;\">"+calcularImporte(e.getPrecioDia(), periodo)+"</td>"+
				"</tr>";
		}
		return s;
	}
	
	public double devolverSubTotal(Double tot){
		double subTotal = tot;
		return subTotal;
	}
	
	public double calcularIva(Double tot, Alquiler alq) throws SQLException, IOException{
		double iva = 21 * tot / 100;
		total += iva;
		alq.setCostoTotal(total);
		ad.setearCostoTotal(alq);
		
		return iva;
	}
}