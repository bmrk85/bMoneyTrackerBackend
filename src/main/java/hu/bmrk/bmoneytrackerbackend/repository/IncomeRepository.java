package hu.bmrk.bmoneytrackerbackend.repository;

import hu.bmrk.bmoneytrackerbackend.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
