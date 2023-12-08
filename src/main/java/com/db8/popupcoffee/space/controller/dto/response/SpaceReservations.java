package com.db8.popupcoffee.space.controller.dto.response;

import com.db8.popupcoffee.reservation.controller.dto.response.SimpleReservationInfo;
import java.util.List;

public record SpaceReservations(
    String space,
    List<SimpleReservationInfo> reservations
) {

}
