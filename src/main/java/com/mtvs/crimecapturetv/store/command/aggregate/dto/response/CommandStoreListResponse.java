package com.mtvs.crimecapturetv.store.command.aggregate.dto.response;

import com.mtvs.crimecapturetv.store.command.aggregate.entity.Store;
import com.mtvs.crimecapturetv.store.command.aggregate.entity.enumType.StoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandStoreListResponse {

    private String storeName;
    private StoreType storeType;
    private String name;

    public static CommandStoreListResponse of(Store store) {
        return CommandStoreListResponse.builder()
                .storeName(store.getStoreName())
                .storeType(store.getStoreType())
                .name(store.getUser().getName())
                .build();
    }

    public static Page<CommandStoreListResponse> toDtoList(Page<Store> stores) {
        return stores.map(store -> CommandStoreListResponse.of(store));
    }

}
