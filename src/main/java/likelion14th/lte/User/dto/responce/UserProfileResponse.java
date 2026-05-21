package likelion14th.lte.User.dto.responce;


import likelion14th.lte.User.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)


public class UserProfileResponse {
    private String username;
    private String profileImageUrl;
    private String introduction;


    public static UserProfileResponse from (User user) {
        return new UserProfileResponse(
                    user.getUsername() + "#" + user.getUserTag(),
                    user.getProfileImage(),
                    user.getIntroduction()
        );
    }
}
