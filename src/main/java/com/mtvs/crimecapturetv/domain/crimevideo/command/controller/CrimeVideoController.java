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
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/stores/")
public class CrimeVideoController {

    private final CommandUserService userService;
    private final CommandStoreService storeService;
    private final CommandCrimeVideoService crimeVideoService;

    @GetMapping("videos")
    public String videos(Model model, @AuthenticationPrincipal UserDetail userDetail, Pageable pageable) {

        Long storeNo = 1L;
//        UserDto userDto = getUserDto(userDetail.getId());
//        StoreDTO storeDTO = getStoreDTO(userDetail.getNo(), storeNo);

        Page<ReadAllCrimeVideoResponse> responsePageAll = crimeVideoService.readCrimeVideoLists(4L, storeNo, pageable);
        log.info("🤖 pageCount {}", responsePageAll.stream().count());
        Page<ReadAllCrimeVideoResponse> responsePageStatus0 = crimeVideoService.readCrimeVideoLists(0L, storeNo, pageable);
        log.info("🤖 pageCount {}", responsePageStatus0.stream().count());
        Page<ReadAllCrimeVideoResponse> responsePageStatus1 = crimeVideoService.readCrimeVideoLists(1L, storeNo, pageable);
        log.info("🤖 pageCount {}", responsePageStatus1.stream().count());

        model.addAttribute("videoList", responsePageAll);
        model.addAttribute("videoListStatus0", responsePageStatus0);
        model.addAttribute("videoListStatus1", responsePageStatus1);

        return "videos/videoList";
    }

    @GetMapping("videosDetail/{videoNo}")
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

    @GetMapping("all-videosDetail/{videoNo}")
    public String allVideoDetail(@PathVariable Long videoNo, Model model, @AuthenticationPrincipal UserDetail userDetail, Pageable pageable) {
        Long storeNo = 1L;
        UserDto userDto = getUserDto(userDetail.getId());
        StoreDTO storeDTO = getStoreDTO(userDetail.getNo(), storeNo);
        CrimeVideoDTO crimeVideoDTO = getCrimeVideo(videoNo);

        model.addAttribute("user", userDto);
        model.addAttribute("store", storeDTO);
        model.addAttribute("crimeVideo", crimeVideoDTO);

        return "videos/allVideoDetail";
    }

//    //영상 다운로드
//    @GetMapping("/videos/{videoPath}/download")
//    public ResponseEntity<Resource> downloadVideo(@PathVariable String videoPath) throws FileNotFoundException {
//        log.info("🤖 다운로드 메서드 실행");
//        return crimeVideoService.downloadVideo(videoPath);
//    }


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
    @GetMapping("board")
    public String board(){
        return "boards/board-list";
    }

}
