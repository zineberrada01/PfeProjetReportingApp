package com.pfe.ClientRest.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class DBFiles {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;

    private String fileType;
    
    private String verif;

    @Lob
    private byte[] data;

    public DBFiles() {

    }

    public DBFiles(String fileName, String fileType, byte[] data,String verif) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.verif=verif;
    }

	public String getVerif() {
		return verif;
	}

	public void setVerif(String verif) {
		this.verif = verif;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

    
}