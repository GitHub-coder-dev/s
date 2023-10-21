package com.sarvesh.codetest2.repository;
import org.springframework.data.repository.CrudRepository;

import com.sarvesh.codetest2.model.CompanyRecord;

public interface CompanyRecordRepository extends CrudRepository<CompanyRecord, Long> {

}
