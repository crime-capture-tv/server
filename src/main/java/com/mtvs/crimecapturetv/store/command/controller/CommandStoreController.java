package com.mtvs.crimecapturetv.store.command.controller;

import com.mtvs.crimecapturetv.exception.Response;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.StoreDTO;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.request.CommandStoreCreateRequest;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.request.CommandStoreDeleteRequest;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.request.CommandStoreUpdateRequest;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.response.CommandStoreCreateResponse;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.response.CommandStoreDeleteResponse;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.response.CommandStoreUpdateResponse;
import com.mtvs.crimecapturetv.store.command.service.CommandStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class CommandStoreController {

    private final CommandStoreService commandStoreService;

    @PostMapping("/add-store")
    public ResponseEntity<Response<CommandStoreCreateResponse>> create(@RequestBody CommandStoreCreateRequest request, Long id) {

        StoreDTO storeDTO = commandStoreService.registStore(request, id);

        return ResponseEntity.ok(Response.success(CommandStoreCreateResponse.of(storeDTO)));

    }

    @PutMapping("/{storeNo}")
    public ResponseEntity<Response<CommandStoreUpdateResponse>> modify(@RequestBody CommandStoreUpdateRequest request, Long id, @PathVariable Long storeNo) {

        Long modifiedStore = commandStoreService.modifyStore(request, id, storeNo);

        return ResponseEntity.ok(Response.success(new CommandStoreUpdateResponse("수정 성공", modifiedStore)));
    }


    @DeleteMapping("/{storeNo}")
    public ResponseEntity<Response<CommandStoreDeleteResponse>> delete(@RequestBody CommandStoreDeleteRequest request, Long id, @PathVariable Long storeNo) {

        Long deletedStore = commandStoreService.deleteStore(request, id, storeNo);

        return ResponseEntity.ok(Response.success(new CommandStoreDeleteResponse("삭제 성공" ,deletedStore)));
    }

    @GetMapping("/{storeNo}")
    public ResponseEntity<Response<StoreDTO>> detail(@PathVariable Long storeNo, Long id) {
        StoreDTO storeDTO = commandStoreService.detail(id, storeNo);
        return ResponseEntity.ok(Response.success(storeDTO));

    }






}
