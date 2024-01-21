package fun.domain.medal.domain.acquirable;

import fun.domain.medal.domain.MedalType;

public interface MedalAcquirable {

    boolean checkAcquirable(final MedalCheckForm medalCheckForm);

    MedalType getMedalType();
}
