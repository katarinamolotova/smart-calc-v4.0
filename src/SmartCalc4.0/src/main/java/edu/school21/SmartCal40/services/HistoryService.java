package edu.school21.SmartCal40.services;

import edu.school21.SmartCal40.entities.HistoryEntity;
import edu.school21.SmartCal40.repositories.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoryService {

    private HistoryRepository repository;

    public boolean save (Object o) {
        repository.save((HistoryEntity)o);
        return true;
    }

    public Iterable<HistoryEntity> loadHistory() {
        return repository.findAll();
    }
}
