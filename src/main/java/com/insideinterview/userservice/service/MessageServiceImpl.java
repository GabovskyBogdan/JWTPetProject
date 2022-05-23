package com.insideinterview.userservice.service;

import com.insideinterview.userservice.domain.Message;
import com.insideinterview.userservice.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepo messageRepo;

    @Override
    public Message saveMessage(Message message) {
        return messageRepo.save(message);
    }

    @Override
    public List<Message> getMessages() {
        return messageRepo.findAll();
    }
}
