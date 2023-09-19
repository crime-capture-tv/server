package com.mtvs.crimecapturetv.store.command.service;

import com.mtvs.crimecapturetv.exception.AppException;
import com.mtvs.crimecapturetv.exception.ErrorCode;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.StoreDTO;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.request.CommandStoreCreateRequest;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.request.CommandStoreDeleteRequest;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.request.CommandStoreUpdateRequest;
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

    // 점포 등록
    @Transactional
    public StoreDTO registStore(CommandStoreCreateRequest commandStoreCreateRequest, Long id) {

        User user = commandUserRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        Store store = commandStoreRepository.save(commandStoreCreateRequest.toEntity(user));
        return StoreDTO.of(store);
    }

    // 점포 수정
    @Transactional
    public Long modifyStore(CommandStoreUpdateRequest commandStoreUpdateRequest, Long id, Long storeNo) {

        User user = commandUserRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        Store store = commandStoreRepository.findByStoreNo(storeNo)
                .orElseThrow(() -> new AppException(ErrorCode.STORE_NOT_FOUND));

        store.modifyStore(commandStoreUpdateRequest.getStoreName(), commandStoreUpdateRequest.getZipcode(), commandStoreUpdateRequest.getStreetAddress(), commandStoreUpdateRequest.getDetailAddress(), commandStoreUpdateRequest.getStorePhoneNumber());

//        Store savedStore = commandStoreRepository.save(commandStoreUpdateRequest.toEntity(user));
        commandStoreRepository.save(store);
        return storeNo;

    }

    // 점포 삭제
    @Transactional
    public Long deleteStore(CommandStoreDeleteRequest commandStoreDeleteRequest, Long id, Long storeNo) {
        User user = commandUserRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        Store store = commandStoreRepository.findByStoreNo(storeNo)
                .orElseThrow(() -> new AppException(ErrorCode.STORE_NOT_FOUND));

        commandStoreRepository.deleteByStoreNo(storeNo);
        return storeNo;
    }


    // 점포 조회
    public StoreDTO detail(Long id, Long storeNo) {

        User user = commandUserRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        Store store = commandStoreRepository.findByStoreNo(storeNo)
                .orElseThrow(() -> new AppException(ErrorCode.STORE_NOT_FOUND));

        return StoreDTO.of(store);

    }


}
