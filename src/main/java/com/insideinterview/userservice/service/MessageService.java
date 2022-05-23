package com.insideinterview.userservice.service;

import com.insideinterview.userservice.domain.Message;

import java.util.List;

public interface MessageService {

    Message saveMessage(Message message);

    List<Message> getMessages();
}
