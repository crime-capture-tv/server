package com.mtvs.crimecapturetv.store.command.aggregate.entity;

import com.mtvs.crimecapturetv.store.command.aggregate.entity.enumType.StoreType;

import javax.persistence.*;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long storeNo;

    @Column
    private String storeName;

    @Column
    private String StoreAddress;

    @Column
    private StoreType storeType;

    @Column
    private String businessRegistNumber;

    @Column
    private String storePhoneNumber;

}
