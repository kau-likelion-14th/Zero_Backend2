package likelion14th.lte.User.controller;


import io.swagger.v3.oas.annotations.Operation;
import likelion14th.lte.User.dto.request.CreateTestUserRequest;
import likelion14th.lte.User.dto.responce.UserProfileResponse;
import likelion14th.lte.User.service.UserProfileService;
import likelion14th.lte.global.api.ApiResponse;
import likelion14th.lte.global.api.SuccessCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/profile")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)

public class UserProfileController {
    public final UserProfileService userProfileService;


    @GetMapping
    @Operation(summary = "유저 프로필 조회", description = "유저 아이디를 받아 유저 프로필을 반환하는 api입니다.")
    public ApiResponse<UserProfileResponse> getUserProfile(
            @RequestParam Long userId

    ){
        UserProfileResponse userProfileResponse = userProfileService.getUserProfile(userId);

        return ApiResponse.onSuccess(SuccessCode.OK, userProfileResponse);
    }
    @PostMapping
    @Operation(summary = "테스트 유저를 생성", description = "이름, 한줄소개, 유저 태그를 받아 유저를 생성")
    public ApiResponse<UserProfileResponse> createUserProfile(
            @RequestBody CreateTestUserRequest createTestUserRequest
    ){
        UserProfileResponse response = userProfileService.createTestUser(createTestUserRequest);
        return ApiResponse.onSuccess(SuccessCode.CREATED, response);
    }
}
