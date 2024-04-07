package com.bepsik.moneymotivator.repository;

import com.bepsik.moneymotivator.entity.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Long> {

    List<BalanceHistory> findByUserEmailOrderByDateDesc(String email);

}
