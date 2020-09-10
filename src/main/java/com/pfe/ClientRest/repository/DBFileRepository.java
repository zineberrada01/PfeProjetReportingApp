package com.pfe.ClientRest.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pfe.ClientRest.model.DBFiles;
import com.pfe.ClientRest.model.Files;

@Repository
public interface DBFileRepository extends JpaRepository<DBFiles, String> {
 
	/*
	 * @Query(value="select f.file_Name, f.verif from Files f",nativeQuery=true)
	 * public List<Files> GetData();
	 */
}