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
@RequestMapping("/api/profile")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileController{

    public final UserProfileService userProfileService;

    // [Q9. Controller 내부에서 userRepository.findById()를 직접 호출해서 유저를 찾지 않고,
    // 반드시 userProfileService를 호출하여 작업을 위임해야 하는 이유는 무엇인가요? (단일 책임 원칙 관점)]
    // 답변: 하나의 클래스는 하나의 책임만 져야한다. 컨트롤러는 http 요청과 응답 변환을 담당하고 서비스는 도메인 관련해서 담당한다. 레포지토리는 db 입출력만 담당한다.
    //컨트롤러가 검증, 예외 처리, 여러 레포지토리 조합 등까지 작업하면 비대하다. 같은 로직을 가진 다른 컨트롤러에서 재사용이 불가하다
    // 테스트할 때 http까지 보여줘야 비즈니스 로직 검증이 가능하기에 테스트가 어렵다.
    @GetMapping
    public ApiResponse<UserProfileResponse> getUserProfile(@RequestParam Long userId){
        UserProfileResponse response = userProfileService.getUserProfile(userId);
        return ApiResponse.onSuccess(SuccessCode.USER_INFO_GET_SUCCESS, response);
    }

    @PostMapping
    public ApiResponse<UserProfileResponse> createTestUser(
            // [Q10. 클라이언트가 보낸 JSON 텍스트 데이터가 어떻게 자바 객체인 CreateTestUserRequest로
            // 변환 되는지앞의 어노테이션과 연관 지어 설명해 보세요.]
            // 답변: 클라인언트가 JSON 텍스트를 HTTP 요청 본문에 담아서 전송한다
            // { "username": "길동", "userTag": "gildong123", "introduction": "안녕" }
            //@RequestBody 어노테이션이 스프링에게 "요청 본문을 자바 객체로 변환해 달라"고 지시
            //sPRING MVC 등록된 HttpMessageConverter 중 JSON에 맞는 MappingJackson2HttpMessageconverter를 선택하고
            // 내부의 Jackson 라이브러리가 JSON의 키와 CreateTestUserRequest의 필드명을 매칭하여
            //역렬화를 수행해 자바 객체 인스턴스를 생성한 뒤 메서드 파라미터로 주입된다.
            @RequestBody CreateTestUserRequest request
    ){
        UserProfileResponse response = userProfileService.createTestUser(request);
        return ApiResponse.onSuccess(SuccessCode.CREATED, response);
    }
}