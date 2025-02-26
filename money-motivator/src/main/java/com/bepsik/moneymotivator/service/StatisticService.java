package com.bepsik.moneymotivator.service;

import com.bepsik.moneymotivator.dto.TaskStatisticDto;
import com.bepsik.moneymotivator.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticService {

    private final TaskRepository taskRepository;
    private final CurrentUserService currentUserService;

    public Collection<TaskStatisticDto> countTasksByMonths() {
        LocalDate currentDate = LocalDate.now().withDayOfMonth(1).plusMonths(1).minusYears(1);

        Random random = new Random();
        boolean randomEnabled = true;
        Map<Pair<Integer, Integer>, TaskStatisticDto> statistics = new LinkedHashMap<>();
        for (int i = 0; i < 12; i++) {
            LocalDate date = currentDate.plusMonths(i);
            statistics.put(Pair.of(date.getYear(), date.getMonthValue()),
                    new TaskStatisticDto(date.getYear(), date.getMonthValue(), randomEnabled ? random.nextLong(13) : 0));
        }

        List<TaskStatisticDto> existingStatistics = taskRepository.countByYearAndMonth(currentUserService.getEmail());

        for (TaskStatisticDto dto : existingStatistics) {
            var taskStatisticDto = statistics.get(Pair.of(dto.getYear(), dto.getMonth()));
            if (taskStatisticDto != null) {
                taskStatisticDto.setCount(dto.getCount());
            }
        }

        return statistics.values();
    }

}
