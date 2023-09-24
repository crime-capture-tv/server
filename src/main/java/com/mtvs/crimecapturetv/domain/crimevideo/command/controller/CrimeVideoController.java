package com.mtvs.crimecapturetv.domain.crimevideo.command.controller;

import com.mtvs.crimecapturetv.configuration.login.UserDetail;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.CrimeVideoDTO;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.dto.ReadAllCrimeVideoResponse;
import com.mtvs.crimecapturetv.domain.crimevideo.command.aggregate.entity.CrimeVideo;
import com.mtvs.crimecapturetv.domain.crimevideo.command.service.CommandCrimeVideoService;
import com.mtvs.crimecapturetv.store.command.aggregate.dto.StoreDTO;
import com.mtvs.crimecapturetv.store.command.service.CommandStoreService;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.UserDto;
import com.mtvs.crimecapturetv.user.command.service.CommandUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;


@Controller
@Slf4j
@RequiredArgsConstructor
public class CrimeVideoController {

    private final CommandUserService userService;
    private final CommandStoreService storeService;
    private final CommandCrimeVideoService crimeVideoService;

    @GetMapping("/stores/videos")
    public String videos(Model model, @AuthenticationPrincipal UserDetail userDetail, Pageable pageable) {

        Long storeNo = 1L;
        UserDto userDto = getUserDto(userDetail.getId());
        StoreDTO storeDTO = getStoreDTO(userDetail.getNo(), storeNo);

        Page<ReadAllCrimeVideoResponse> responsePage = crimeVideoService.readCrimeVideoLists(4L, storeNo, pageable);
        model.addAttribute("videoList", responsePage);

        Long value = 4L;

        return "videos/videoList";
    }

    @GetMapping("/stores/videosDetail/{videoNo}")
    public String videoDetail(@PathVariable Long videoNo, Model model, @AuthenticationPrincipal UserDetail userDetail, Pageable pageable) {
        Long storeNo = 1L;
        UserDto userDto = getUserDto(userDetail.getId());
        StoreDTO storeDTO = getStoreDTO(userDetail.getNo(), storeNo);
        CrimeVideoDTO crimeVideoDTO = getCrimeVideo(videoNo);

        model.addAttribute("user", userDto);
        model.addAttribute("store", storeDTO);
        model.addAttribute("crimeVideo", crimeVideoDTO);

        return "videos/videoDetail";
    }

    @RequestMapping(value = "api/resource", method = RequestMethod.POST)
    public ResponseEntity<Resource> videoUrl() throws Exception {
        String path = "/video" + "/test3.mp4";
        Resource resource = new FileSystemResource(path);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=s", "test3.mp4"));
        headers.setContentType(MediaType.parseMediaType("video/mp4"));

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    public UserDto getUserDto(String userId) {
        return userService.findUser(userId);
    }

    public CrimeVideoDTO getCrimeVideo(Long crimeVideoNo) {
        CrimeVideo crimeVideo = crimeVideoService.getCrimeVideo(crimeVideoNo);
        return CrimeVideoDTO.toDto(crimeVideo);
    }

    public StoreDTO getStoreDTO(Long userNo, Long storeNo) {
        return storeService.detail(userNo, storeNo);
    }
}
