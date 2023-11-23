package edu.school21.SmartCal40.services;

import edu.school21.SmartCal40.entities.HistoryEntity;
import edu.school21.SmartCal40.repositories.HistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public record HistoryService(HistoryRepository repository) {

    public void save(final String entity) {
        if (entity == null || entity.isEmpty()) {
            return;
        }
        final HistoryEntity tmp = getEmptyEntity();
        tmp.setData(entity);
        tmp.setDateTime(LocalDateTime.now());
        repository.save(tmp);
    }

    public List<String> loadLastTenRecordsOfHistory() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .sorted(Comparator.comparing(HistoryEntity::getDateTime).reversed())
                .map(HistoryEntity::getData)
                .distinct()
                .limit(10)
                .collect(Collectors.toList());
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public static HistoryEntity getEmptyEntity() {
        return new HistoryEntity();
    }
}