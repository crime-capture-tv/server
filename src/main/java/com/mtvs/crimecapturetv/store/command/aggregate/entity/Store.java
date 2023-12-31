package com.mtvs.crimecapturetv.store.command.aggregate.entity;

import com.mtvs.crimecapturetv.global.common.entity.BaseEntity;
import com.mtvs.crimecapturetv.store.command.aggregate.entity.enumType.StoreType;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Store_TB")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_no")
    private Long storeNo;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "detail_address")
    private String detailAddress;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @Column(name = "business_regist_number")
    private String businessRegistNumber;

    @Column(name = "store_phone_number")
    private String storePhoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    public void modifyStore(String storeName, String zipcode, String streetAddress, String detailAddress, String storePhoneNumber) {
        this.storeName = storeName;
        this.zipcode = zipcode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
        this.storePhoneNumber = storePhoneNumber;
    }



}
