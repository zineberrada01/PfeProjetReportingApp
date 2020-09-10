package com.pfe.ClientRest.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pfe.ClientRest.model.Ciomc;
import com.pfe.ClientRest.repository.FileRepository;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;



@Controller
@RestController
public class CiomcContoller {
 
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private FileRepository FileRepo;
	
	
	
	@Value("${test.url.Ciomc}")
	  String url ;
	
	// contrelleur generateur de rapport HTML ciomc avec graph
		@RequestMapping(value="/CiomcHtml" , method=RequestMethod.GET)
		public void CiomcHtml(HttpServletResponse  response, HttpServletRequest request) throws JRException, IOException {
			try {
						response.setContentType("text/html");
						
						
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
				         JRBeanCollectionDataSource  ciomcBean = new JRBeanCollectionDataSource(result); 
						 Map<String,Object> parameters =new HashMap<String,Object>();
						 parameters.put("CollectionBeanParam", ciomcBean);
						 	
			             InputStream input = new FileInputStream(new File("D:\\save\\Desktop\\ProjectSpring\\pfeSpring\\src\\main\\resources\\ciomcReport_A4.jrxml"));
			             System.out.println("file is got1!"); 
			             JasperDesign  jasperDesign = JRXmlLoader.load(input);
						 JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
						 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource());
					
						 request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
						 System.out.println("printed1");
		     
				         HtmlExporter exporter = new HtmlExporter();
					     List<JasperPrint> jasperPrintsList = new ArrayList<JasperPrint>();
					     jasperPrintsList.add(jasperPrint);
					     exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintsList));
					     //set ImageHandler. Hack for images export to HTML
					     SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(response.getWriter());
					     WebHtmlResourceHandler webHtmlResourceHandler =  new WebHtmlResourceHandler("../image?image={0}");
					     output.setImageHandler(webHtmlResourceHandler);           
					     exporter.setExporterOutput(output);            
					     SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
					     exporter.setConfiguration(configuration);
					     exporter.exportReport();}
			// return "Home";
	}catch(Exception ex) {ex.printStackTrace();}
		}
		
		@RequestMapping(value="/getcross2" , method=RequestMethod.GET)
		public void crossHtml(HttpServletResponse  response, HttpServletRequest request) throws JRException, IOException {
			
			try {
					response.setContentType("text/html");
					String url = "http://localhost:8088/Ciomc/ciomcs";
					String authStr = "john123:password";
					String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
					
					// create headers
				    HttpHeaders headers = new HttpHeaders();
				    headers.add("Authorization", "Basic " + base64Creds);
				    
				   // create request
				    HttpEntity<String> request1 = new HttpEntity<String>(headers);
				    System.out.println("entityhttp");
				    
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
			
					     JRBeanCollectionDataSource  ciomcBean = new JRBeanCollectionDataSource(result); 
					     Map<String,Object> parameters =new HashMap<String,Object>();
					     parameters.put("CollectionBeanParam1", ciomcBean);
					 	
					     InputStream input = new FileInputStream(new File("D:\\save\\Desktop\\ProjectSpring\\pfeSpring\\src\\main\\resources\\Crossed.jrxml"));
			             System.out.println("file is got1!"); 
	                     JasperDesign  jasperDesign = JRXmlLoader.load(input);
					     JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
					     JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource());
					     request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
					     System.out.println("printed1");
	     
					     HtmlExporter exporter = new HtmlExporter();
					     List<JasperPrint> jasperPrintsList = new ArrayList<JasperPrint>();
					     jasperPrintsList.add(jasperPrint);
					     exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintsList));
					     //set ImageHandler. Hack for images export to HTML
					     SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(response.getWriter());
					     WebHtmlResourceHandler webHtmlResourceHandler =  new WebHtmlResourceHandler("../image?image={0}");
					     output.setImageHandler(webHtmlResourceHandler);           
					     exporter.setExporterOutput(output);            
					     SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
					     exporter.setConfiguration(configuration);
					     exporter.exportReport();
		       //return "Home";
	}
				}catch(Exception ex) {ex.printStackTrace();}
		}
		
		@GetMapping("/getdata")
		  public List<com.pfe.ClientRest.model.Files> getit(){
			  return FileRepo.findAll();
			  }
}
