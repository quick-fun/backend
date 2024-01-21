package fun.domain.medal.event;

import fun.domain.auth.service.event.MemberCreateEvent;
import fun.domain.medal.domain.Medal;
import fun.domain.medal.domain.MedalCommandRepository;
import fun.domain.medal.domain.MedalType;
import fun.domain.medal.domain.acquirable.MedalAcquirableComposite;
import fun.domain.medal.domain.acquirable.MedalCheckForm;
import fun.domain.member.domain.Member;
import fun.domain.member.domain.MemberCommandRepository;
import fun.domain.vote.post.service.event.VotePostCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@RequiredArgsConstructor
@Transactional
@Service
public class MedalEventHandler {

    private final MedalAcquirableComposite medalAcquirableComposite;
    private final MedalCommandRepository medalCommandRepository;
    private final MemberCommandRepository memberCommandRepository;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    @Async
    public void createMedalByVotePostCreateEvent(final VotePostCreateEvent event) {
        findAcquirableMedalsAndSaveNewMedalsForMember(event.memberId(), event.votePostId());
    }

    private void findAcquirableMedalsAndSaveNewMedalsForMember(final Long event, final Long event1) {
        final Member findMember = getMemberOrException(event);
        findMember.addMedals(
                collectMedalIds(
                        medalCommandRepository.findByMedalTypes(getAcquirableMedalTypes(event, event1))
                )
        );
    }

    private Member getMemberOrException(final Long memberId) {
        return memberCommandRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("메달 획득 조건 확인 이벤트 핸들러 로직 중에 사용자 식별자값 조회에 실패하였습니다."));
    }

    private List<Long> collectMedalIds(final List<Medal> medals) {
        return medals.stream()
                .map(Medal::getId)
                .toList();
    }

    private List<MedalType> getAcquirableMedalTypes(final Long memberId, final Long votePostId) {
        return medalAcquirableComposite.findAcquirableMedalType(
                new MedalCheckForm(
                        memberId,
                        votePostId
                )
        );
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    @Async
    public void createMedalBySocialMemberJoinFirstTime(final MemberCreateEvent event) {
        findAcquirableMedalsAndSaveNewMedalsForMember(event.memberId(), null);
    }
}
