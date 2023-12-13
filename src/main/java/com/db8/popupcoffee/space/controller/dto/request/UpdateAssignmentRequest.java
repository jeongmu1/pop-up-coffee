package com.db8.popupcoffee.space.controller.dto.request;

import java.time.LocalDate;

public record UpdateAssignmentRequest(
    Long id,
    boolean fromFlexible,
    Long spaceId,
    LocalDate startDate,
    LocalDate endDate
) {

}
