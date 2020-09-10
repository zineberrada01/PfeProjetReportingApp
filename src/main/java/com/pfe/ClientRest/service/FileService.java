package com.pfe.ClientRest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfe.ClientRest.model.DBFiles;
import com.pfe.ClientRest.model.Files;
import com.pfe.ClientRest.repository.DBFileRepository;
import com.pfe.ClientRest.repository.FileRepository;

@Service
public class FileService {
	
	@Autowired
	private FileRepository repo;
	
	public List<Files> listAll(){
		return repo.findAll();
	}
	
	public void save (Files DbFile) {
		repo.save(DbFile);
	}
	
	public Files get(String id) {
		Optional<Files> optional = repo.findById(id);
		Files dbfile = null;
		if(optional.isPresent()) {
			dbfile = optional.get();
		}else {
			throw new RuntimeException("Employee not found for id " +id);
		}
		return dbfile;
	}
	
	public void delete(String id) {
		repo.deleteById(id);
	}
	
	
		
	}
	


