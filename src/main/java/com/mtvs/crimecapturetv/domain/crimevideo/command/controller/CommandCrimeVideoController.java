package com.mtvs.crimecapturetv.domain.crimevideo.command.controller;

import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.*;
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

    @PostMapping("/stores/{storeNo}/videos")
    public ResponseEntity<Response<CreateCrimeVideoResponse>> create(@RequestParam Long storeNo, @RequestBody CreateCrimeVideoRequest request) {

        CreateCrimeVideoResponse response = crimeVideoService.createCrimeVideo(storeNo, request);

        return ResponseEntity.ok().body(Response.success(response));
    }

    @PutMapping("/videos/{videoNo}")
    public ResponseEntity<Response<UpdateCriminalStatusResponse>> updateCriminalStatus(@RequestParam Long videoNo, @RequestBody UpdateCriminalStatusRequest request) {

        UpdateCriminalStatusResponse response = crimeVideoService.updateCriminalStatus(videoNo, request);

        return ResponseEntity.ok().body(Response.success(response));
    }

    @DeleteMapping("/videos/{videoNo}")
    public ResponseEntity<Response<DeleteCrimeVideoResponse>> delete(@RequestParam Long videoNo) {

        DeleteCrimeVideoResponse response = crimeVideoService.deleteCrimeVideo(videoNo);

        return ResponseEntity.ok().body(Response.success(response));
    }

}
