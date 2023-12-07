package com.db8.popupcoffee.seasonality.service;

import com.db8.popupcoffee.seasonality.controller.dto.response.DatePriceInfo;
import com.db8.popupcoffee.seasonality.repository.DateInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DateInfoService {

    private final DateInfoRepository dateInfoRepository;

    public List<DatePriceInfo> findDateInfos() {
        return dateInfoRepository.findAll().stream().map(DatePriceInfo::of).toList();
    }

}
