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
public class CommandStoreCreateResponse {

    private String storeName;
    private String zipcode;
    private String streetAddress;
    private String detailAddress;
    private StoreType storeType;
    private String businessRegistNumber;
    private String storePhoneNumber;
    private Long userNo;

    public static CommandStoreCreateResponse of (StoreDTO storeDTO) {
        return CommandStoreCreateResponse.builder()
                .storeName(storeDTO.getStoreName())
                .zipcode(storeDTO.getZipcode())
                .streetAddress(storeDTO.getStreetAddress())
                .detailAddress(storeDTO.getDetailAddress())
                .storeType(storeDTO.getStoreType())
                .businessRegistNumber(storeDTO.getBusinessRegistNumber())
                .storePhoneNumber(storeDTO.getStorePhoneNumber())
                .userNo(storeDTO.getUserNo())
                .build();
    }

}
