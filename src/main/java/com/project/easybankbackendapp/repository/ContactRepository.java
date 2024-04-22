package com.project.easybankbackendapp.repository;


import com.project.easybankbackendapp.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {


}
