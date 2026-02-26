package org.arcticquests.dev.oneironaut.Oneironaut.casting.idea;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;

public interface IdeaKeyable {
    String getKey();
    boolean isValidKey(CastingEnvironment env);
}
