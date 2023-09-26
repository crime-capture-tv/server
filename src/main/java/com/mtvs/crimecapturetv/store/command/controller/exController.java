package com.mtvs.crimecapturetv.store.command.controller;

import com.mtvs.crimecapturetv.store.command.aggregate.dto.request.CommandStoreCreateRequest;
import com.mtvs.crimecapturetv.store.command.service.CommandStoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stores")
public class exController {

    private final CommandStoreService commandStoreService;

    public exController(CommandStoreService commandStoreService) {
        this.commandStoreService = commandStoreService;
    }

//    @GetMapping("/store")
//    public String first() {
//        return "store";
//    }


    @GetMapping("/store-regist")
    public String store() { return "stores/store-regist";}

    @PostMapping("/store-regist")
    public String registStore(@ModelAttribute CommandStoreCreateRequest commandStoreCreateRequest, Long id) {

        commandStoreService.registStore(commandStoreCreateRequest, id);

        return "redirect:/stores/store-list";
    }

    @GetMapping("/store-list")
    public String storeList() { return "stores/store-list";}






}
