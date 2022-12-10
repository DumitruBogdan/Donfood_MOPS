package com.donfood.dao;

import com.donfood.domain.ONG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface IONGRepository extends JpaRepository <ONG, Long> {
    List<ONG> findByAccountONGFullName(String fullName);
}
