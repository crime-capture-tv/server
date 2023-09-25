package com.mtvs.crimecapturetv.store.command.controller;

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




}
