package edu.school21.SmartCal40.repositories;

import edu.school21.SmartCal40.entities.HistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends CrudRepository<HistoryEntity, String> {}
