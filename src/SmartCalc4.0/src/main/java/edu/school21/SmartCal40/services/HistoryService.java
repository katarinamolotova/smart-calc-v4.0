package edu.school21.SmartCal40.services;

import edu.school21.SmartCal40.entities.HistoryEntity;
import edu.school21.SmartCal40.repositories.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class HistoryService {
    private final HistoryRepository repository;

    public void save(final String entity) {
        HistoryEntity tmp = getEmptyEntity();
        tmp.setData(entity);
        tmp.setDateTime(LocalDateTime.now());
        repository.save(tmp);
    }

    public Iterable<HistoryEntity> loadHistory() {
        return repository.findAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public static HistoryEntity getEmptyEntity() {
        return new HistoryEntity();
    }
}
