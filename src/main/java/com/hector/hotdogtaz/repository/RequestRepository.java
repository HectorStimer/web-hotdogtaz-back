package com.hector.hotdogtaz.repository;

import com.hector.hotdogtaz.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    public List<Request> findByStatusNot(Request.Status status);
    public List<Request> findByStatus(Request.Status status);

}
