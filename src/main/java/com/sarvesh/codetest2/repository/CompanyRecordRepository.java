package com.sarvesh.codetest2.repository;
import org.springframework.data.repository.CrudRepository;

import com.sarvesh.codetest2.model.CompanyRecord;

public interface CompanyRecordRepository extends CrudRepository<CompanyRecord, Long> {

//Create a repository interface for the Record entity to interact with the database using Spring Data JPA.

}
