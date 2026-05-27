package likelion14th.lte.User.entity;


import jakarta.persistence.*;
import likelion14th.lte.Entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Auditable;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
// [Q1. @NoArgsConstructor는 매개변수가 없는 기본 생성자를 만듭니다.
// 그런데 왜 누구나 쓸 수 있게 PUBLIC으로 열어두지 않고, 굳이 PROTECTED로 막아두었을까요? (객체 생성의 안전성과 JPA 관점)]
// 답변: 퍼블릭으로 하면, 다른 사람이 new User를 만들게 되는데 이때 필수 필드가 비어있는 객체를 마음대로 만들면 버그가 발생하기 때문에
// PROTECTED로 막으면 JPA는 사용이 가능하지만, new Use로 못 만듬. USER를 만들 수 있는 방법은 Builder로만 가능함.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // [Q2. @Column(nullable = false) 어노테이션이 DB와 자바 코드 사이에서 하는 역할은 무엇인가요?]
    // 답변: 자바랑 DB를 연결하는 역할. DB 방향에서는 JPA가 테이블을 자동 생성할 때 NOT NULL이 되도록 제약조건을 걸어서
    //DB에서 빈값이 안들어가도록 하고 자바에서는 JPA가 NULL인지 아닌기 검사하고 NULL이면 DB로 안들어가게 막음.
    @Column(nullable = false)
    private String username;

    @Column(length = 16, nullable = false, unique = true)
    private String userTag;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Builder(access = AccessLevel.PUBLIC)
    private User (String username, String introduction, String userTag){
        this.username = username;
        this.userTag = userTag;
        this.introduction = introduction;
    }

    // [Q3. @Setter를 위 @Getter 처럼 사용하면 모든 맴버들에 setIntruduction() 같은 setter 메서드가 생성됩니다. 하지만 왜 @Setter를 쓰지않고 updateIntroduction() 이라는 명확한 메서드를 만든 객체지향적인 이유는 무엇인가요?]
    // 답변: 업데이트를 한다라는 의미가 드러남. 그리고 setter를 안만든 필드는 바꿀 수 없어 변하지 않도록 해줌.
    public void updateIntroduction(String introduction){
        this.introduction = introduction;
    }
}