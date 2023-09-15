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

    @PostMapping("/videos")
    public ResponseEntity<Response<CreateCrimeVideoResponse>> create(@RequestBody CreateCrimeVideoRequest request) {

        CreateCrimeVideoResponse response = crimeVideoService.createCrimeVideo(request);

        return ResponseEntity.ok().body(Response.success(response));
    }

    @PutMapping("/videos/{videoNo}")
    public ResponseEntity<Response<UpdateCriminalStatusResponse>> updateCriminalStatus(@RequestParam Long videoNo, @RequestBody UpdateCriminalStatusRequest request) {

        UpdateCriminalStatusResponse response = crimeVideoService.updateCriminalStatus(videoNo, request);

        return ResponseEntity.ok().body(Response.success(response));
    }

}
