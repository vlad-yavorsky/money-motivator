package com.bepsik.moneymotivator.controller;

import com.bepsik.moneymotivator.dto.TaskStatisticDto;
import com.bepsik.moneymotivator.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping
    public Collection<TaskStatisticDto> getStatistic() {
        log.info("Request to get statistics");
        return statisticService.countTasksByMonths();
    }

}
