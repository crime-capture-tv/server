package com.mtvs.crimecapturetv.domain.crimevideo.command.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.*;
import com.mtvs.crimecapturetv.domain.crimevideo.command.service.CommandCrimeVideoService;
import com.mtvs.crimecapturetv.exception.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.mail.MessagingException;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class CommandCrimeVideoController {

    private final CommandCrimeVideoService crimeVideoService;

    @PostMapping("/stores/{storeNo}/videos")
    public ResponseEntity<Response<CreateCrimeVideoResponse>> create(@RequestParam Long storeNo, @RequestBody CreateCrimeVideoRequest request) throws MessagingException, JsonProcessingException {
        log.info("service 코드 시작");
        CreateCrimeVideoResponse response = crimeVideoService.createCrimeVideo(storeNo, request);

        return ResponseEntity.ok().body(Response.success(response));
    }

    @PutMapping("/videos/{videoNo}")
    public ResponseEntity<Response<UpdateCriminalStatusResponse>> updateCriminalStatus(@PathVariable Long videoNo, @RequestBody UpdateCriminalStatusRequest request) {

        UpdateCriminalStatusResponse response = crimeVideoService.updateCriminalStatus(videoNo, request);

        return ResponseEntity.ok().body(Response.success(response));
    }

    @DeleteMapping("/videos/{videoNo}")
    public ResponseEntity<Response<DeleteCrimeVideoResponse>> delete(@PathVariable Long videoNo) {

        DeleteCrimeVideoResponse response = crimeVideoService.deleteCrimeVideo(videoNo);

        return ResponseEntity.ok().body(Response.success(response));
    }

    // 해당 점포에 있는 모든 영상 리스트 조회
    @GetMapping("/stores/{storeNo}/videos")
    public ResponseEntity<Page<ReadAllCrimeVideoResponse>> readAllCrimeVideo(
            @PathVariable Long storeNo,
            @RequestParam(name = "value", defaultValue = "4") Long criminalStatus) {

        PageRequest pageable = PageRequest.of(0, 20, Sort.by("no").descending());

        Page<ReadAllCrimeVideoResponse> response = crimeVideoService.readCrimeVideoLists(criminalStatus, storeNo, pageable);

        return ResponseEntity.ok().body(response);
    }

}
