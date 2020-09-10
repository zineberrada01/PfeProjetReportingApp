package com.pfe.ClientRest.controller;

import java.io.File;
import java.util.Base64;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pfe.ClientRest.model.TlAdjClass;

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
public class TladjController {
	
	@Autowired
	private RestTemplate restTemplate;
	  
	
	
	
	
	/*
	 * @GetMapping("/tladjs") public List<Object> getTladj(){
	 * 
	 * String url ="http://localhost:8088/tladj/tladjs"; Object[] objects =
	 * restTemplate.getForObject(url,Object[].class); return Arrays.asList(objects);
	 * 
	 * 
	 * }
	 */
	
	
	  @Bean 
	  public ServletRegistrationBean<ImageServlet> imageServlet() {
	  ServletRegistrationBean<ImageServlet> servRegBean = new ServletRegistrationBean<>(); 
	  servRegBean.setServlet(new ImageServlet());
	  servRegBean.addUrlMappings("/image"); 
	  servRegBean.setLoadOnStartup(1); 
	  return servRegBean; 
	  }
	 
	  @Value("${test.url.Tladj}")
	  String url ;
	
	@RequestMapping(value="/getTlAdj1" , method=RequestMethod.GET)
	public void TladjHtml(HttpServletResponse  response, HttpServletRequest request) throws JRException, IOException {
		try {
		      
				response.setContentType("text/html");
				
				
				String authStr = "john123:password";
				String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
				System.out.println("coded");
				// create headers
			    HttpHeaders headers = new HttpHeaders();
			    headers.add("Authorization", "Basic " + base64Creds);
			    System.out.println("added");
			   // create request
			    HttpEntity<String> request1 = new HttpEntity<String>(headers);
			    System.out.println("entityhttp");
			    
				ResponseEntity<List<TlAdjClass>> responses = restTemplate.exchange(url, //
		                HttpMethod.GET, request1,new ParameterizedTypeReference<List<TlAdjClass>>() {});
				System.out.println("exchange");
				List<TlAdjClass> result =responses.getBody();
			   /* List<Object> tladjs = Arrays.asList(responses); */
				System.out.println("list");
				HttpStatus statusCode = responses.getStatusCode();
				
		        System.out.println("Response Satus Code: " + statusCode);
		 
				/* 
				 * TlAdjClass[] response1 = restTemplate.getForObject(url, TlAdjClass[].class);
				 * List<Object> tladjs = Arrays.asList(response1);
				 */
				System.out.println("got tladj");
				if (statusCode == HttpStatus.FOUND) {
					 JRBeanCollectionDataSource  tladj = new JRBeanCollectionDataSource(result); 
					 System.out.println("got bean");
					 Map<String,Object> parameters =new HashMap<String,Object>();
					 parameters.put("CollectionParam", tladj);
						 	
				     InputStream input = new FileInputStream(new File("D:\\save\\Desktop\\ProjectSpring\\pfeSpring\\src\\main\\resources\\Tladj1.jrxml"));
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
				     }
	//return "Home";

	
	}catch(Exception ex) {ex.printStackTrace();}
		}
	}
	

