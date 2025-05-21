package com.dohi.ansimtalk.service;

import com.dohi.ansimtalk.domain.Message;
import com.dohi.ansimtalk.repository.MemberRepository;
import com.dohi.ansimtalk.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository meessageRepository;

    public List<Message> findByReid(Long Reid){
        return meessageRepository.findByReceiveid2(Reid);
    }

    public void save(Message message){
        meessageRepository.save(message);
    }
}
