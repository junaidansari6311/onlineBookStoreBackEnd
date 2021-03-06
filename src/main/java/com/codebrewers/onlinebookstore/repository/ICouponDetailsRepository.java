package com.codebrewers.onlinebookstore.repository;

import com.codebrewers.onlinebookstore.model.CouponsDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICouponDetailsRepository extends JpaRepository<CouponsDetails,Integer> {

    List<CouponsDetails> findByUserId(Integer id);

}
