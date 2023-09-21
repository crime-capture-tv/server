package com.mtvs.crimecapturetv.notification.command.repository;

import com.mtvs.crimecapturetv.notification.command.aggregate.dto.response.CommandNotificationResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Repository
@NoArgsConstructor
@Slf4j
public class EmitterRepository {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<Long, CommandNotificationResponse> eventCache = new ConcurrentHashMap<>();

    public SseEmitter save(Long emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        log.info("실시간 연결 현황 : {}" , emitters.entrySet());
        return sseEmitter;
    }

    public SseEmitter findEmitterByUserNo(Long userNo){
        return emitters.get(userNo);
    }
    public void deleteById(Long id) {
        emitters.remove(id);
    }

}
