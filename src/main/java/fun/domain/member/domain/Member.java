package fun.domain.member.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @BatchSize(size = 100)
    @ElementCollection
    @CollectionTable(name = "member_medal", joinColumns = @JoinColumn(name = "member_id"))
    private List<Long> medalIds = new ArrayList<>();

    protected Member() {
    }

    public Member(
            final String nickname,
            final String gender,
            final String profileImageUrl,
            final int age
    ) {
        this(null, nickname, gender, profileImageUrl, age, new ArrayList<>());
    }

    protected Member(
            final Long id,
            final String nickname,
            final String gender,
            final String profileImageUrl,
            final int age,
            List<Long> memberIds
    ) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
        this.age = age;
        this.medalIds = memberIds;
    }

    public void addMedal(final Long requestMedalId) {
        medalIds.add(requestMedalId);
    }

    public Long getLatestMemberId() {
        return medalIds.stream()
                .min(Long::compare)
                .orElse(0L);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Member{" +
               "id=" + id +
               ", nickname='" + nickname + '\'' +
               ", gender='" + gender + '\'' +
               ", profileImageUrl='" + profileImageUrl + '\'' +
               ", age=" + age +
               ", medalIds=" + medalIds +
               '}';
    }
}
