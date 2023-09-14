package com.mtvs.crimecapturetv.store.command.service;

import com.mtvs.crimecapturetv.exception.AppException;
import com.mtvs.crimecapturetv.exception.ErrorCode;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.StoreDTO;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.request.CommandStoreCreateRequest;
import com.mtvs.crimecapturetv.store.command.aggregate.entity.Store;
import com.mtvs.crimecapturetv.store.command.repository.CommandStoreRepository;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import com.mtvs.crimecapturetv.user.command.repository.CommandUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommandStoreService {

    private final CommandStoreRepository commandStoreRepository;
    private final CommandUserRepository commandUserRepository;

    @Transactional
    public StoreDTO registStore(CommandStoreCreateRequest commandStoreCreateRequest, String id) {

        User user = commandUserRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        Store store = commandStoreRepository.save(commandStoreCreateRequest.toEntity(user));
        return StoreDTO.of(store);
    }

}
