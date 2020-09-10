package com.pfe.ClientRest.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Base64;
import java.io.InputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.pfe.ClientRest.model.*;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Controller
public class PageController {
	
	@Autowired
	private RestTemplate restTemplate;

	@Value("${test.url.Ciomc}")
	  String url ;
	@Value("${test.url.Tladj}")
	  String url1 ;
	
	
	  @RequestMapping(value="/index", method = RequestMethod.GET) public String
	  index() { return "index"; }
	  
	  @RequestMapping(value="/uploadOneFile", method = RequestMethod.GET) public String
	  uploadOneFile() { return "uploadOneFile"; }
	  
	  @RequestMapping(value="/uploadMulti", method = RequestMethod.GET) public String
	  uploadMulti() { return "uploadMulti"; }
	  
	  
	  
	  
	 
	
	  @RequestMapping(value="/403", method = RequestMethod.GET) public String
	  error403() { return "403"; }
	 
	
	
	  @RequestMapping(value="/Home", method = RequestMethod.GET) public String
	  GetHome() { return "Home"; }
	  
	
	  @RequestMapping(value="/welcome", method = RequestMethod.POST) public String
	  GetWelcomePage() { return "Welcome"; }
	  
	  @RequestMapping(value="/logout",method = RequestMethod.POST)
	  public String logout() {
		  return("index");
	  } 
	  
	 
	  @RequestMapping(value="/index", method=RequestMethod.POST) 
		public String login( @ModelAttribute(name="LoginForm") LoginForm log, Model model ) {
			
			String username = log.getUsername();
			String password = log.getPassword();
			
			
			if("admin".equals(username) && "123456".equals(password)) {
				System.out.println("logged");
				 return "welcome";
			}
			model.addAttribute("invalidCredentials", true);
			
			return "index";
		}	
	
	
	/*
	 * @RequestMapping(value="/index", method=RequestMethod.POST) public String
	 * login( @ModelAttribute(name="LoginForm") LoginForm log, Model model ) {
	 * 
	 * String username = log.getUsername(); String password = log.getPassword();
	 * 
	 * 
	 * if("admin".equals(username) && "123456".equals(password)) {
	 * System.out.println("logged"); return "Welcome"; }
	 * model.addAttribute("invalidCredentials", true);
	 * 
	 * return "index"; }
	 */
	  
	  @RequestMapping(value="/getdataRechercher")
		public String viewHomePage(Model model ,@Param("keyword") String keyword ) {
			try {
				
				String url = "http://localhost:8088/Ciomc/getdataRechercher";
				String authStr = "john123:password";
				String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
				
				// create headers
			    HttpHeaders headers = new HttpHeaders();
			    headers.add("Authorization", "Basic " + base64Creds);
			   // create request
			    
			    HttpEntity<String> request1 = new HttpEntity<String>(headers);
				ResponseEntity<List<Ciomc>> responses = restTemplate.exchange(url, //
		        HttpMethod.GET, request1,new ParameterizedTypeReference<List<Ciomc>>() {});
				List<Ciomc> result =responses.getBody();
			   
				HttpStatus statusCode = responses.getStatusCode();
				
		        System.out.println("Response Satus Code: " + statusCode);
		 
			/* 
			 * TlAdjClass[] response1 = restTemplate.getForObject(url, TlAdjClass[].class);
			 * List<Object> tladjs = Arrays.asList(response1);
			 */
			
			if (statusCode == HttpStatus.FOUND) {
			 model.addAttribute("ciomc",result);
			 model.addAttribute("keyword", keyword);
			 System.out.println(" ligne:" + result);
			 }
			}catch(Exception ex) {ex.printStackTrace();}
			return"Welcome";
		}
	 	
	
	@RequestMapping(value="/getTlAdj2")
	public String tladjPDF() throws FileNotFoundException, JRException {
		
		
		  String outputFile = "C:\\Users\\Pc\\Desktop\\ReportPFE\\" +"tladjj.pdf";
		  try {
		      
				
					
					String authStr = "john123:password";
					String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
					// create headers
				    HttpHeaders headers = new HttpHeaders();
				    headers.add("Authorization", "Basic " + base64Creds);
				   // create request
				    HttpEntity<String> request1 = new HttpEntity<String>(headers);
				    
					ResponseEntity<List<TlAdjClass>> responses = restTemplate.exchange(url1, //
			        HttpMethod.GET, request1,new ParameterizedTypeReference<List<TlAdjClass>>() {});
					List<TlAdjClass> result =responses.getBody();
					HttpStatus statusCode = responses.getStatusCode();
					
			        System.out.println("Response Satus Code: " + statusCode);
		 
				/* 
				 * TlAdjClass[] response1 = restTemplate.getForObject(url, TlAdjClass[].class);
				 * List<Object> tladjs = Arrays.asList(response1);
				 */
				System.out.println("got tladj");
				if (statusCode == HttpStatus.FOUND) {
						 System.out.println("got tladj1");
					     JRBeanCollectionDataSource  tladj= new JRBeanCollectionDataSource(result); 
					     Map<String,Object> parameters =new HashMap<String,Object>();
						 parameters.put("CollectionParam", tladj);
					 
			             InputStream input = new FileInputStream(new File("D:\\save\\Desktop\\ProjectSpring\\pfeSpring\\src\\main\\resources\\Tladj1.jrxml"));
						 JasperDesign  jasperDesign = JRXmlLoader.load(input);
					     JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
					     JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource());
			             FileOutputStream outputStream = new FileOutputStream(new File(outputFile));
					     JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			             System.out.println("generated");
					 
						 ImageIcon icon = new ImageIcon("/images/icon_pdf_256_30059.png"); //object for displaying a custom icon
						 final JPanel panel = new JPanel(); // the object for the pop-up
						      //this displays the pop-up and returns an integer depending on what the user clicks
						 JOptionPane.showConfirmDialog(panel, "the report is successfully generated in path : " + outputFile, "SUCCESS!",JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, icon);
							}
				}catch(Exception ex) {ex.printStackTrace();}
		    return "Welcome";

}
	@RequestMapping(value="/CiomcPdf")
	public String Ciomcpdf() throws FileNotFoundException, JRException {
		
		  String outputFile = "C:\\Users\\Pc\\Desktop\\ReportPFE\\" +"CiomcExample.pdf";
		  try {
				
				String authStr = "john123:password";
				String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
				// create headers
			    HttpHeaders headers = new HttpHeaders();
			    headers.add("Authorization", "Basic " + base64Creds);
			   // create request
			    HttpEntity<String> request1 = new HttpEntity<String>(headers);
			    
				ResponseEntity<List<Ciomc>> responses = restTemplate.exchange(url, //
		        HttpMethod.GET, request1,new ParameterizedTypeReference<List<Ciomc>>() {});
				List<Ciomc> result =responses.getBody();
			   /* List<Object> tladjs = Arrays.asList(responses); */
				HttpStatus statusCode = responses.getStatusCode();
				
		        System.out.println("Response Satus Code: " + statusCode);
		 
				/* 
				 * TlAdjClass[] response1 = restTemplate.getForObject(url, TlAdjClass[].class);
				 * List<Object> tladjs = Arrays.asList(response1);
				 */
				if (statusCode == HttpStatus.FOUND) {
		
				     JRBeanCollectionDataSource  ciomcBean = new JRBeanCollectionDataSource(result); 
					 Map<String,Object> parameters =new HashMap<String,Object>();
					 parameters.put("CollectionBeanParam", ciomcBean);
				 
				     InputStream input = new FileInputStream(new File("D:\\save\\Desktop\\ProjectSpring\\pfeSpring\\src\\main\\resources\\ciomcReport_A4.jrxml"));
					 JasperDesign jasperDesign = JRXmlLoader.load(input);
				     JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				     JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource());
				 
				     FileOutputStream outputStream = new FileOutputStream(new File(outputFile));
				     JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
				     System.out.println("generated");
				 
					 ImageIcon icon = new ImageIcon("src/main/webapp/img/BS_icon_example.png"); //object for displaying a custom icon
					 final JPanel panel = new JPanel(); // the object for the pop-up
					      //this displays the pop-up and returns an integer depending on what the user clicks
					 JOptionPane.showConfirmDialog(panel, "the report is successfully generated in path : " + outputFile, "SUCCESS!",JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, icon);
						}
				}catch(Exception ex) {ex.printStackTrace();}
		    return "Home";
	}
	
	@RequestMapping(value="/getcrossed")
	public String CiomcCrossed() throws FileNotFoundException, JRException {
		try {
			
			String authStr = "john123:password";
			String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
			// create headers
		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Authorization", "Basic " + base64Creds);
		   // create request
		    HttpEntity<String> request1 = new HttpEntity<String>(headers);
		    
			ResponseEntity<List<Ciomc>> responses = restTemplate.exchange(url, //
	                HttpMethod.GET, request1,new ParameterizedTypeReference<List<Ciomc>>() {});
			List<Ciomc> result =responses.getBody();
		   /* List<Object> tladjs = Arrays.asList(responses); */
			HttpStatus statusCode = responses.getStatusCode();
			
	        System.out.println("Response Satus Code: " + statusCode);
	 
			/* 
			 * TlAdjClass[] response1 = restTemplate.getForObject(url, TlAdjClass[].class);
			 * List<Object> tladjs = Arrays.asList(response1);
			 */

			if (statusCode == HttpStatus.FOUND) {
		
				     String outputFile = "C:\\Users\\Pc\\Desktop\\ReportPFE\\" +"CiomcCrossed.pdf";
				
				     JRBeanCollectionDataSource  ciomcBean = new JRBeanCollectionDataSource(result); 
				     Map<String,Object> parameters =new HashMap<String,Object>();
					 parameters.put("CollectionBeanParam1", ciomcBean);
				 
				     InputStream input = new FileInputStream(new File("D:\\save\\Desktop\\ProjectSpring\\pfeSpring\\src\\main\\resources\\Crossed.jrxml"));
					 JasperDesign  jasperDesign = JRXmlLoader.load(input);
				     JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				     JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource());
				 
				     FileOutputStream outputStream = new FileOutputStream(new File(outputFile));
				     JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
				     System.out.println("generated");
				 
					 ImageIcon icon = new ImageIcon("src/main/webapp/img/BS_icon_example.png"); //object for displaying a custom icon
					 final JPanel panel = new JPanel(); // the object for the pop-up
					      //this displays the pop-up and returns an integer depending on what the user clicks
					 JOptionPane.showConfirmDialog(panel, "the report is successfully generated in path : " + outputFile, "SUCCESS!",JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, icon);
					}
			}catch(Exception ex) {ex.printStackTrace();}
		   return "Welcome";
	}
}
