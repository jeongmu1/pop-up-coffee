package com.db8.popupcoffee.rental.controller.dto.request;

import com.db8.popupcoffee.rental.domain.SpaceRentalStatus;

public record ChangeStatusRequest(
    SpaceRentalStatus status
) {

}
