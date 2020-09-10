package com.pfe.ClientRest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Table( name="Files")
@Entity
public class Files {
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	@Id
	 private String id;
	 private String FileName;
	 private String Verif;
     public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	 

 
 public Files() {

}

public Files(String fileName, String verif) {
	
	FileName = fileName;
	Verif = verif;
}

public String getFileName() {
	return FileName;
}

public void setFileName(String fileName) {
	FileName = fileName;
}

public String getVerif() {
	return Verif;
}

public void setVerif(String verif) {
	Verif = verif;
}
 
 
 
 
}
