package net.javaguides.expense.repository;

import net.javaguides.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Spring data jpa provides implementation for this interface
    // CRUD methods to perform CRUD database operations on Expense entity
    // Spring Data JPA provides transaction for all the CRUD methods
}
