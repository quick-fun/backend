package fun.domain.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl;

    @Column(name = "age", nullable = false)
    private int age;

    protected Member() {
    }

    public Member(
            final String nickname,
            final String gender,
            final String profileImageUrl,
            final int age
    ) {
        this(null, nickname, gender, profileImageUrl, age);
    }

    protected Member(
            final Long id,
            final String nickname,
            final String gender,
            final String profileImageUrl,
            final int age
    ) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
        this.age = age;
    }
}
