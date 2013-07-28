package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

	
	public Invoice findBySuborderId(Long suborderId);

}
