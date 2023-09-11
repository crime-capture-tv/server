package com.mtvs.crimecapturetv.store.command.aggregate.entity;

import com.mtvs.crimecapturetv.store.command.aggregate.entity.enumType.StoreType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Store_TB")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_no")
    private Long storeNo;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_address")
    private String StoreAddress;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @Column(name = "business_regist_number")
    private String businessRegistNumber;

    @Column(name = "store_phone_number")
    private String storePhoneNumber;

}
