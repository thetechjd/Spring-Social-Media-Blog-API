package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    public Optional<Account> findByUsername(String username);

    public Optional<Account> findByUsernameAndPassword(String username, String password);

    @Query("From Account WHERE account_id = :accountId")
    public Optional<Account> findByAccountId(@Param("accountId") Integer account_id);

}
