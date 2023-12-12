package com.db8.popupcoffee.space.controller.dto.response;

import com.db8.popupcoffee.space.domain.Space;

public record SpaceInfo(
    Long id,
    String number
) {

    public static SpaceInfo from(Space space) {
        return new SpaceInfo(space.getId(), space.getNumber());
    }
}
