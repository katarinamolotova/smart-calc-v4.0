package edu.school21.SmartCal40.services;

import edu.school21.SmartCal40.entities.HistoryEntity;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {
//    private final HistoryRepository repository;

//    @Autowired
//    public HistoryService(HistoryRepository repository) {
////        this.repository = repository;
//    }

    public boolean save (Object o) {
//        repository.save((HistoryEntity)o);
        return true;
    }

    public Iterable<HistoryEntity> loadHistory() {
//        return repository.findAll();
        return null;
    }
}
