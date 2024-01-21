package fun.domain.medal.domain.acquirable;

import fun.domain.medal.domain.MedalType;
import fun.domain.member.domain.Member;
import fun.domain.member.domain.MemberCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class MedalAcquirableNewMember implements MedalAcquirable {

    private final MemberCommandRepository memberCommandRepository;

    @Override
    public boolean checkAcquirable(final MedalCheckForm medalCheckForm) {
        if (medalCheckForm.memberId() == null) {
            return false;
        }

        final Member findMember = memberCommandRepository.findById(medalCheckForm.memberId())
                .orElseThrow(() -> new IllegalArgumentException("메달 획득을 위한 새로운 사용자인지 확인하는 과정에서 사용자 식별자값으로 조회하는데 실패하였습니다."));

        final LocalDateTime memberCreatedAt = findMember.getCreatedAt();
        final LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        return yesterday.isBefore(memberCreatedAt);
    }

    @Override
    public MedalType getMedalType() {
        return MedalType.NEW_MEMBER;
    }
}
