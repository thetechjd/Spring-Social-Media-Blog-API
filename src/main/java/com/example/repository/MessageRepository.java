package com.example.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;


public interface MessageRepository extends JpaRepository<Message, Integer> {
    
    @Query("FROM Message WHERE message_id = :accountId")
    public Optional<ArrayList<Message>> findAllUserMessages(@Param("accountId") Integer account_id);
}
