package com.insideinterview.userservice.repo;

import com.insideinterview.userservice.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
