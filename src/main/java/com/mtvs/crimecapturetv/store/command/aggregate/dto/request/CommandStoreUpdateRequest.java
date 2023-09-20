package com.mtvs.crimecapturetv.store.command.aggregate.dto.request;

import com.mtvs.crimecapturetv.store.command.aggregate.entity.Store;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandStoreUpdateRequest {

    private String storeName;
    private String zipcode;
    private String streetAddress;
    private String detailAddress;
    private String storePhoneNumber;

    public Store toEntity(User user) {
        return Store.builder()
                .storeName(this.storeName)
                .zipcode(this.zipcode)
                .streetAddress(this.streetAddress)
                .detailAddress(this.detailAddress)
                .storePhoneNumber(this.storePhoneNumber)
                .user(user)
                .build();
    }





}
