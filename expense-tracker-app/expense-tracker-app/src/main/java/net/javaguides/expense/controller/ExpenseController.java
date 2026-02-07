package net.javaguides.expense.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.javaguides.expense.dto.ExpenseDto;
import net.javaguides.expense.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Expense Resource",
        description = "CRUD REST APIs for Expense Resource - " +
                "Create Expense, Update Expense, Read Expense, and Delete Expense"
)
@AllArgsConstructor
@RestController
@RequestMapping("/api/expenses") // base URI
public class ExpenseController {

    // inject the ExpenseService using constructor based DI
    private ExpenseService expenseService;

    @Operation(
            summary = "Create Expense REST API",
            description = "Create Expense REST API to save a expense in a database"
    )

    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    // Build create expense REST API
    @PostMapping
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseDto expenseDto){

        ExpenseDto savedExpense = expenseService.createExpense(expenseDto);

        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Expense REST API",
            description = "Get Expense REST API to get a expense from database"
    )

    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    // Build get expense by id REST API
    @GetMapping("{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable("id") Long expenseId){

        ExpenseDto expense = expenseService.getExpenseById(expenseId);

        return ResponseEntity.ok(expense);
    }

    @Operation(
            summary = "Get All Expenses REST API",
            description = "Get All Expenses REST API to get all expenses from database"
    )

    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    // Build get all expenses REST API
    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getAllExpenses(){
        List<ExpenseDto> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    @Operation(
            summary = "Update Expense REST API",
            description = "Update Expense REST API to update an existing expense in database"
    )

    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    // Build update expense REST API
    @PutMapping("{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable("id") Long expenseId,
                                                    @RequestBody ExpenseDto expenseDto){

        ExpenseDto updatedExpense = expenseService.updateExpense(expenseId, expenseDto);

        return ResponseEntity.ok(updatedExpense);

    }

    @Operation(
            summary = "Delete Expense REST API",
            description = "Delete Expense REST API to delete an existing expense from the database"
    )

    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    // Build delete expense REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable("id") Long expenseId){
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok("Expense deleted successfully!");
    }
}
