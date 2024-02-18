package fun.domain.vote.item.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class VoteItemMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "vote_item_id", nullable = false)
    private Long voteItemId;

    protected VoteItemMember() {
    }

    public VoteItemMember(
            final Long memberId,
            final Long voteItemId
    ) {
        this(null, memberId, voteItemId);
    }

    private VoteItemMember(
            final Long id,
            final Long memberId,
            final Long voteItemId
    ) {
        this.id = id;
        this.memberId = memberId;
        this.voteItemId = voteItemId;
    }

    @Override
    public String toString() {
        return "VoteItemMember{" +
               "id=" + id +
               ", memberId=" + memberId +
               ", voteItemId=" + voteItemId +
               '}';
    }
}
