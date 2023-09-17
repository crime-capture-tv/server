package com.mtvs.crimecapturetv.store.command.aggregate.dto.response;

import com.mtvs.crimecapturetv.store.command.aggregate.dto.StoreDTO;
import com.mtvs.crimecapturetv.store.command.aggregate.entity.enumType.StoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandStoreDeleteResponse {

//    private String storeName;
//    private String storeAddress;
//    private StoreType storeType;
//    private String businessRegistNumber;
//    private String storePhoneNumber;
//    private Long userNo;
//
//
//    public static CommandStoreDeleteResponse of (StoreDTO storeDTO) {
//        return CommandStoreDeleteResponse.builder()
//                .storeName(storeDTO.getStoreName())
//                .storeAddress(storeDTO.getStoreAddress())
//                .storeType(storeDTO.getStoreType())
//                .businessRegistNumber(storeDTO.getBusinessRegistNumber())
//                .storePhoneNumber(storeDTO.getStorePhoneNumber())
//                .userNo(storeDTO.getUserNo())
//                .build();
//
//    }

    private String message;
    private Long storeNo;
}
