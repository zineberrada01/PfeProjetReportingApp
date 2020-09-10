package com.pfe.ClientRest.controller;

import java.nio.file.Files;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pfe.ClientRest.model.DBFiles;
import com.pfe.ClientRest.payload.UploadFileResponse;
import com.pfe.ClientRest.repository.DBFileRepository;
import com.pfe.ClientRest.repository.FileRepository;
import com.pfe.ClientRest.service.DBFileStorageService;
import com.pfe.ClientRest.service.FileService;





@ControllerAdvice
@Controller

public class Ccontroller {
	
	

    @Autowired
    private DBFileStorageService dbFileStorageService;
    @Autowired
    private DBFileRepository dbfileRepo;
    @Autowired
    private FileService fileServ;
    @Autowired
    private FileRepository FileRepo;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        DBFiles dbFile = dbFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        UploadFileResponse ap = new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
        return "uploadOneFile";
    }
    @GetMapping("/listFile")
    public String ListFiles(Model model) {
        
    	List<com.pfe.ClientRest.model.Files> listFile = FileRepo.findAll();
    	
    	model.addAttribute("Files",listFile);
    	return "ListFile" ;
    }
    
    @PostMapping("/update/{id}")
    public String UpdateList(@PathVariable(name="id") String id, @Valid com.pfe.ClientRest.model.Files files,BindingResult result,Model model) {
    	if(result.hasErrors()) {
    		files.setId(id);
    		return "updateList";
    	}
    	FileRepo.save(files);
    	model.addAttribute("Files", FileRepo.findAll());
    	return "ListFile" ;
    	}
        
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
    	com.pfe.ClientRest.model.Files file = FileRepo.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid  Id:" + id));

        model.addAttribute("Files", file);
        return "updateList";
    }
    
    @GetMapping("/previouswelcome")
    public String ppage() {
    	return "welcome";
    }
    
    
    
	/*
	 * @PostMapping("/Save") public String saveRepport(@ModelAttribute("Files")
	 * com.pfe.ClientRest.model.Files dbfile) {
	 * 
	 * fileServ.save(dbfile);
	 * 
	 * return "/redirect:/ListFile"; }
	 */
    
	/*
	 * @GetMapping("/upd") public String upd(Model model) {
	 * FileRepo.updateFile("tladjj.pdf", "reject");
	 * List<com.pfe.ClientRest.model.Files> listFile = FileRepo.findAll();
	 * model.addAttribute("Files" , listFile); return "ListFile";
	 * 
	 * }
	 */

    
    
    
    
    
    

    
	/*
	 * @PostMapping("/uploadMultipleFiles") public List<UploadFileResponse>
	 * uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) { return
	 * Arrays.asList(files) .stream() .map(file -> uploadFile(file))
	 * .collect(Collectors.toList()); }
	 */
	 
    
	 

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
    	
        // Load file from database
        DBFiles dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
	
	
	
	
	
	/*
	  // GET: Show upload form page.
	  
	  @RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET) public
	  String uploadOneFileHandler(Model model) {
	  
	  MyUploadForm myUploadForm = new MyUploadForm();
	  model.addAttribute("myUploadForm", myUploadForm);
	  
	  return "uploadOneFile"; }
	  
	  // POST: Do Upload
	  
	  @RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST) public
	  String uploadOneFileHandlerPOST(HttpServletRequest request, // Model model,
	  //
	  
	  @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
	  
	  return this.doUpload(request, model, myUploadForm);
	  
	  }
	  
	  // GET: Show upload form page.
	  
	  @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)
	  public String uploadMultiFileHandler(Model model) {
	  
	  MyUploadForm myUploadForm = new MyUploadForm();
	  model.addAttribute("myUploadForm", myUploadForm);
	  
	  return "uploadMulti"; }
	  
	  // POST: Do Upload
	  
	  @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
	  public String uploadMultiFileHandlerPOST(HttpServletRequest request, // Model
	  model, //
	  
	  @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
	  
	  return this.doUpload(request, model, myUploadForm);
	  
	  }
	  
	  private String doUpload(HttpServletRequest request, Model model, //
	  MyUploadForm myUploadForm) {
	  
	  String description = myUploadForm.getDescription();
	  System.out.println("Description: " + description);
	  
	  // Root Directory. String uploadRootPath =
	  request.getServletContext().getRealPath("upload");
	  System.out.println("uploadRootPath=" + uploadRootPath);
	  
	  File uploadRootDir = new File(uploadRootPath); // Create directory if it not
	  exists. if (!uploadRootDir.exists()) { uploadRootDir.mkdirs(); }
	  MultipartFile[] fileDatas = myUploadForm.getFileDatas(); // List<File>
	  uploadedFiles = new ArrayList<File>(); List<String> failedFiles = new
	  ArrayList<String>();
	  
	  for (MultipartFile fileData : fileDatas) {
	  
	  // Client File Name String name = fileData.getOriginalFilename();
	  System.out.println("Client File Name = " + name);
	  
	  if (name != null && name.length() > 0) { try { // Create the file at server
	  File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator +
	  name);
	  
	  BufferedOutputStream stream = new BufferedOutputStream(new
	  FileOutputStream(serverFile)); stream.write(fileData.getBytes());
	  stream.close(); // uploadedFiles.add(serverFile);
	  System.out.println("Write file: " + serverFile); } catch (Exception e) {
	  System.out.println("Error Write file: " + name); failedFiles.add(name); } } }
	  model.addAttribute("description", description);
	  model.addAttribute("uploadedFiles", uploadedFiles);
	  model.addAttribute("failedFiles", failedFiles); return "uploadResult"; }
	 
	
	
	
	
	
	
	
	
	
	/*
	 * @Autowired private FileStorageService fileStorageService;
	 * 
	 * private static final Logger logger =
	 * LoggerFactory.getLogger(Ccontroller.class);
	 */

	/*
	 * @PostMapping("/uploadFile") public UploadFileResponse
	 * uploadFile(@RequestParam("file") MultipartFile file) { String fileName =
	 * fileStorageService.storeFile(file);
	 * 
	 * String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	 * .path("/downloadFile/") .path(fileName) .toUriString();
	 * 
	 * return new UploadFileResponse(fileName, fileDownloadUri,
	 * file.getContentType(), file.getSize()); }
	 * 
	 * @PostMapping("/uploadMultipleFiles") public List<UploadFileResponse>
	 * uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) { return
	 * Arrays.asList(files) .stream() .map(file -> uploadFile(file))
	 * .collect(Collectors.toList()); }
	 * 
	 * 
	 * @GetMapping("/downloadFile/{fileName:.+}") public ResponseEntity<Resource>
	 * downloadFile(@PathVariable String fileName, HttpServletRequest request) { //
	 * Load file as Resource Resource resource =
	 * fileStorageService.loadFileAsResource(fileName);
	 * 
	 * // Try to determine file's content type String contentType = null; try {
	 * contentType =
	 * request.getServletContext().getMimeType(resource.getFile().getAbsolutePath())
	 * ; } catch (IOException ex) { logger.info("Could not determine file type."); }
	 * 
	 * // Fallback to the default content type if type could not be determined
	 * if(contentType == null) { contentType = "application/octet-stream"; }
	 * 
	 * return ResponseEntity.ok()
	 * .contentType(MediaType.parseMediaType(contentType))
	 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
	 * resource.getFilename() + "\"") .body(resource); }
	 * 
	 * 
	 */
    
    
    
    
    
    

	/*
	 * @GetMapping("/uploadImage") public ResponseEntity<Object> downloadFile()
	 * throws IOException{ String
	 * filename="C:/Users/Pc/Desktop/ReportPFE/tladjj.pdf"; File file = new
	 * File(filename); InputStreamResource resource = new InputStreamResource(new
	 * FileInputStream(file)); HttpHeaders headers = new HttpHeaders();
	 * headers.add("Content-Disposition",String.format("attachement; filename\"%s\""
	 * ,file.getName()));
	 * headers.add("Cache-Control","no-cache , no-store, must-revalidate");
	 * headers.add("Pragma","no-cache"); headers.add("Expires","0");
	 * 
	 * ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
	 * .contentLength(file.length())
	 * .contentType(MediaType.parseMediaType("application/txt")).body(resource);
	 * return responseEntity;
	 * 
	 * }
	 * 
	 * public static String uploadDiretory=System.getProperty("user.dir"
	 * +"/uploads");
	 * 
	 * @RequestMapping("/upload") public String Upload(Model
	 * model, @RequestParam("files") MultipartFile[] files) { StringBuilder
	 * filenames = new StringBuilder(); for(MultipartFile file : files) { Path
	 * fileNameAndPath = Paths.get(uploadDiretory,file.getOriginalFilename());
	 * filenames.append(file.getOriginalFilename()); try {
	 * Files.write(fileNameAndPath,file.getBytes()); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * } model.addAttribute("msg","Succefully uploades" +filenames.toString());
	 * return "uploadview"; }
	 */
	
	
	
		
	}

