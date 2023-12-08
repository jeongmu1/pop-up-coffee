package com.db8.popupcoffee.seasonality.service;

import com.db8.popupcoffee.rental.service.RentalService;
import com.db8.popupcoffee.seasonality.controller.dto.response.DateInfoResponse;
import com.db8.popupcoffee.seasonality.repository.DateInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DateInfoService {

    private final DateInfoRepository dateInfoRepository;
    private final RentalService rentalService;

    public List<DateInfoResponse> findDateInfos() {
        return dateInfoRepository.findAll().stream().map(dateInfo -> DateInfoResponse.of(dateInfo,
            rentalService.countAvailableSpaces(dateInfo.getDate()))).toList();
    }

}
