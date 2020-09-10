
  package com.pfe.ClientRest.service;
  
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Service;
  import org.springframework.util.StringUtils;
  import org.springframework.web.multipart.MultipartFile;

import com.pfe.ClientRest.Exception.FileStorageException;
import com.pfe.ClientRest.Exception.MyFileNotFoundException;
import com.pfe.ClientRest.model.DBFiles;
import com.pfe.ClientRest.repository.DBFileRepository;

import java.io.IOException;

  @Service
  public class DBFileStorageService {

      @Autowired
      private DBFileRepository dbFileRepository;

      public DBFiles storeFile(MultipartFile file) {
          // Normalize file name
          String fileName = StringUtils.cleanPath(file.getOriginalFilename());

          try {
              // Check if the file's name contains invalid characters
              if(fileName.contains("..")) {
                  throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
              }

              DBFiles dbFile = new DBFiles(fileName, file.getContentType(), file.getBytes(), " not decided yet ");

              return dbFileRepository.save(dbFile);
          } catch (IOException ex) {
              throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
          }
      }

      public DBFiles getFile(String fileId) {
          return dbFileRepository.findById(fileId)
                  .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
      }
  }