package com.pfe.ClientRest.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pfe.ClientRest.model.DBFiles;
import com.pfe.ClientRest.model.Files;

@Repository
public interface FileRepository extends JpaRepository<Files, String>{
	@Transactional
	@Modifying
    @Query( value="UPDATE Files f SET f.verif =:verif WHERE f.file_name = :fileName" , nativeQuery=true)
    void updateFile(@Param("fileName") String fileName, @Param("verif") String verif);
}
