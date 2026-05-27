package likelion14th.lte.User.service;

import likelion14th.lte.User.dto.request.CreateTestUserRequest;
import likelion14th.lte.User.dto.responce.UserProfileResponse;
import likelion14th.lte.User.entity.User;
import likelion14th.lte.User.repository.UserRepository;
import likelion14th.lte.global.api.ErrorCode;
import likelion14th.lte.global.exception.GeneralException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileService{

    // [Q5. Service 안에서 new UserRepository() 로 객체를 직접 생성하지 않고,
    // 외부에서 의존성 주입(DI)을 받는 이유는 무엇인가요? (결합도와 단위 테스트 관점)]
    // 답변: Service가 MySQL Repository에 묶여있어, 바꾸려면은 Service 코드를 직접 수정해야한다.
    // 단위 테스트할 때 실제 DB 없이 가짜 Repository로 변경하고 싶은데 new로 정의되어있으면 못바꿈

    private final UserRepository userRepository;

    // [Q6. (코딩 문제) 만약 클래스 위의 @RequiredArgsConstructor를 지운다면,
    // 우리가 직접 작성해야 할 의존성 주입용 자바 '생성자' 코드는 어떤 모습일까요? 아래에 직접 코딩해 보세요.]
    /*
       여기에 생성자 코드 작성:
        protected UserProfileService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

    */

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
        return UserProfileResponse.from(user);
    }

    @Transactional
    public UserProfileResponse createTestUser(CreateTestUserRequest request){

        // [Q7. 일반적인 생성자 new User(name, intro, tag) 방식을 쓰지 않고,
        // User.builder()...build() 라는 '빌더 패턴'을 사용하여 객체를 조립했을 때 얻는 장점은 무엇인가요?]
        // 답변: 어느 필드에 어떤 값이 들어가 있는지 명확하게 보여 가독성이 좋고, 필요한 필드만 쓰고 나머지는 생략이 가능해 선택적으로 필드를 처리할 수 있다.
        User newUser = User.builder()
                .username(request.getUsername())
                .introduction(request.getIntroduction())
                .userTag(request.getUserTag())
                .build();

        User savedUser;
        try{
            // [Q8. 데이터를 저장하는 이 메서드 위에 @Transactional이 반드시 붙어야 하는 이유는 무엇인가요?
            // (저장 도중 DB 서버가 끊겼을 때의 상황을 가정해서 설명하세요)]
            // 답변: 만약에 회원가입을 진행할 때 User를 저장하고, 관련해서 앱 내 포인트 지급하고 관련 공지사항들을 메일로 발송하는 로그를 저장한다고 가정해보자
            //여기서 포인트를 지급하는 도중에 DB 서버가 끊길때
            // Transactional이 없으면 User만 저장하고 끝나서, 데이터의 일관성이 없어지는데(포인트가 없는 사람, 있는 사람) 만약 Transactional이 있으면
            // 예외가 발생하면 자동으로 롤백되서 아무것도 변경된거 없이 복구가 가능하다.
            savedUser = userRepository.save(newUser);
        } catch (Exception e){
            throw new GeneralException(ErrorCode.BAD_REQUEST);
        }
        return UserProfileResponse.from(savedUser);
    }
}