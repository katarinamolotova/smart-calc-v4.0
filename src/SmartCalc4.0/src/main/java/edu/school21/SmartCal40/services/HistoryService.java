package edu.school21.SmartCal40.services;

import edu.school21.SmartCal40.entities.HistoryEntity;
import edu.school21.SmartCal40.repositories.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

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

    public List<HistoryEntity> loadHistory() {
        List<HistoryEntity> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public static HistoryEntity getEmptyEntity() {
        return new HistoryEntity();
    }
}

class HistoryComparator implements Comparator<HistoryEntity>, Predicate<HistoryEntity> {
    public int compare(HistoryEntity a, HistoryEntity b){
        return a.getData().compareTo(b.getData());
    }

    @Override
    public boolean test(HistoryEntity historyEntity) {
        return false;
    }
}
