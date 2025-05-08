package com.library.management.repository;

import com.library.management.model.BorrowingRecord;
import com.library.management.model.BorrowingStatus;
import com.library.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    List<BorrowingRecord> findByUser(User user);
    List<BorrowingRecord> findByUserAndStatus(User user, BorrowingStatus status);
    List<BorrowingRecord> findByDueDateBeforeAndStatus(LocalDate date, BorrowingStatus status);
    Optional<BorrowingRecord> findByBookIdAndStatus(Long bookId, BorrowingStatus status);
}