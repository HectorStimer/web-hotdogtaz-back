package com.hector.hotdogtaz.repository;

import com.hector.hotdogtaz.model.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository <Command, Long> {


    boolean existsByNumber(int Number);

}
