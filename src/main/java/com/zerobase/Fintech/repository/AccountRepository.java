package com.zerobase.Fintech.repository;

import com.zerobase.Fintech.entity.Account;
import com.zerobase.Fintech.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  Integer countByHolder(String holder);

  Optional<Account> findByAccountNumber(String AccountNumber);

  boolean existsByAccountNumber(String accountNumber);

  List<Account> findByHolder(String name);


}
