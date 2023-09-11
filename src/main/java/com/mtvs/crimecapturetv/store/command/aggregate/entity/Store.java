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
    @Column
    private Long storeNo;

    @Column
    private String storeName;

    @Column
    private String StoreAddress;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @Column
    private String businessRegistNumber;

    @Column
    private String storePhoneNumber;

}
