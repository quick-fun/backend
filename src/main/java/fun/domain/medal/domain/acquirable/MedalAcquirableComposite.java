package fun.domain.medal.domain.acquirable;

import fun.domain.medal.domain.MedalType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MedalAcquirableComposite {

    private final List<MedalAcquirable> medalAcquirableConditions;

    public List<MedalType> findAcquirableMedalType(final MedalCheckForm medalCheckForm) {
        return medalAcquirableConditions.stream()
                .filter(medalAcquirable -> medalAcquirable.checkAcquirable(medalCheckForm))
                .map(MedalAcquirable::getMedalType)
                .toList();
    }
}
