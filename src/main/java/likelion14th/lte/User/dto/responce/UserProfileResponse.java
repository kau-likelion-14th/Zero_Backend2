package likelion14th.lte.User.dto.responce;


import likelion14th.lte.User.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileResponse{
    private String userName;
    private String profileImageUrl;
    private String introduction;

    // [Q4. Controller가 DB에서 꺼낸 원본 Entity(User)를 클라이언트 화면에 그대로 반환하지 않고,
    // 굳이 from() 메서드를 통해 DTO로 한번 변환해서 내보내는 핵심적인 이유 2가지는 무엇인가요?]
    // 답변: 첫번째는 보안문제, entity에는 password, 내부 id, 관리용 필드 등 보여주면 안되는 정보들이 있을 수 있기에 그대로 보여주면 노출되서 보안문제가발생함
    //두번째는 유지보수 문제. entity랑 db테이블은 1:1로 되어있음. 만약 서비스를 추가(요구사항이 바뀜)되면 entity를 DB 구조를 바꿔야하는데, DTO를 사용하면
    //DTO만 수정하고 DB는 안건드려도 괜찮으니깐 사용해도 됨.(DB 유지 가능)
    public static UserProfileResponse from(User user){
        return new UserProfileResponse(
                user.getUsername() + "#" + user.getUserTag(),
                user.getProfileImage(),
                user.getIntroduction()
        );
    }
}
