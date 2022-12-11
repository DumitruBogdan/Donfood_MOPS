package com.donfood.dao;

import com.donfood.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDonationRepository  extends JpaRepository<Donation, Long> {
    List<Donation> findByProduct(String product);
}
