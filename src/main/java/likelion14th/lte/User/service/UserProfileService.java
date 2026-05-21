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
public class UserProfileService {
    private final UserRepository userRepository;

    @Transactional
    public UserProfileResponse createTestUser(CreateTestUserRequest request){
        User newUser = User.builder()
                .username(request.getUsername())
                .userTag(request.getUserTag())
                .introduction(request.getIntroduction())
                .build();
        User saveUser;
        try{
            saveUser = userRepository.save(newUser);
        }
        catch(Exception e){
            throw new GeneralException(ErrorCode.BAD_REQUEST);
        }
        return UserProfileResponse.from(saveUser);
    }
    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new GeneralException(ErrorCode.USER_NOT_FOUND));

        return UserProfileResponse.from(user);
    }
}
