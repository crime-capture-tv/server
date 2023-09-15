package com.mtvs.crimecapturetv.domain.crimevideo.command.controller;

import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.CreateCrimeVideoRequest;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.CreateCrimeVideoResponse;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.UpdateCriminalStatusRequest;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.UpdateCriminalStatusResponse;
import com.mtvs.crimecapturetv.domain.crimevideo.command.service.CommandCrimeVideoService;
import com.mtvs.crimecapturetv.exception.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class CommandCrimeVideoController {

    private final CommandCrimeVideoService crimeVideoService;

    @PostMapping("stores/{storeNo}/videos")
    public ResponseEntity<Response<CreateCrimeVideoResponse>> create(@RequestParam Long storeNo, @RequestBody CreateCrimeVideoRequest request) {

        CreateCrimeVideoResponse response = crimeVideoService.createCrimeVideo(storeNo, request);

        return ResponseEntity.ok().body(Response.success(response));
    }

    @PutMapping("stores/{storeNo}/videos/{videoNo}")
    public ResponseEntity<Response<UpdateCriminalStatusResponse>> updateCriminalStatus(@RequestParam Long storeNo, @RequestParam Long videoNo, @RequestBody UpdateCriminalStatusRequest request) {

        UpdateCriminalStatusResponse response = crimeVideoService.updateCriminalStatus(storeNo, videoNo, request);

        return ResponseEntity.ok().body(Response.success(response));
    }

}
