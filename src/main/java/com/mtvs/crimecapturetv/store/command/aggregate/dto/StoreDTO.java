package com.mtvs.crimecapturetv.store.command.aggregate.dto;

import com.mtvs.crimecapturetv.store.command.aggregate.entity.Store;
import com.mtvs.crimecapturetv.store.command.aggregate.entity.enumType.StoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class StoreDTO {

    private Long storeNo;                   // 점포 고유번호
    private String storeName;               // 점포명
    private String zipcode;                 // 우편번호
    private String streetAddress;               // 도로명 주소
    private String detailAddress;               // 상세 주소
    private StoreType storeType;            // 점포 유형
    private String businessRegistNumber;    // 사업자 등록 번호
    private String storePhoneNumber;        // 점포 전화 번호
    private Long userNo;                // 회원명(점주)

    public static StoreDTO of(Store store) {
        return StoreDTO.builder()
                .storeNo(store.getStoreNo())
                .storeName(store.getStoreName())
                .zipcode(store.getZipcode())
                .streetAddress(store.getStreetAddress())
                .detailAddress(store.getDetailAddress())
                .storeType(store.getStoreType())
                .businessRegistNumber(store.getBusinessRegistNumber())
                .storePhoneNumber(store.getStorePhoneNumber())
                .userNo(store.getUser().getNo())
                .build();
    }

}

