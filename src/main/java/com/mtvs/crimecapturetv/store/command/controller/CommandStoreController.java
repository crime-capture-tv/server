package com.mtvs.crimecapturetv.store.command.controller;

import com.mtvs.crimecapturetv.exception.Response;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.StoreDTO;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.request.CommandStoreCreateRequest;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.response.CommandStoreCreateResponse;
import com.mtvs.crimecapturetv.store.command.service.CommandStoreService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class CommandStoreController {

    private final CommandStoreService commandStoreService;

    @PostMapping("/add-store")
    public ResponseEntity<Response<CommandStoreCreateResponse>> create(@RequestBody CommandStoreCreateRequest request, String id) {

        StoreDTO storeDTO = commandStoreService.registStore(request, id);

        return ResponseEntity.ok(Response.success(CommandStoreCreateResponse.of(storeDTO)));

        }
}
