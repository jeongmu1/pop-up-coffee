package com.db8.popupcoffee.seasonality.service;

import com.db8.popupcoffee.seasonality.domain.DateInfo;
import com.db8.popupcoffee.seasonality.repository.DateInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DateInfoService {

    private final DateInfoRepository dateInfoRepository;

    public List<DateInfo> findDateInfos() {
        return dateInfoRepository.findAll();
    }

}
