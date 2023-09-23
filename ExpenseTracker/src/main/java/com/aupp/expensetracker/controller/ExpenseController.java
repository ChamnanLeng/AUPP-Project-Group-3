package com.aupp.expensetracker.controller;

import com.aupp.expensetracker.Entity.DateRange;
import com.aupp.expensetracker.Entity.ExpenseEntity;
import com.aupp.expensetracker.Entity.ItemEntity;
import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.service.Impl.CurrencyServiceImpl;
import com.aupp.expensetracker.service.Impl.ExpenseServiceImpl;
import com.aupp.expensetracker.service.Impl.ItemServiceImpl;
import com.aupp.expensetracker.service.Impl.UserImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {
    @Autowired
    private UserImpl userService;
    @Autowired
    private ExpenseServiceImpl expenseService;
    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private CurrencyServiceImpl currencyService;

    @GetMapping("")
    public String showExpenseForm(HttpSession httpSession, Model model){
        Integer userId = (Integer) httpSession.getAttribute("userId");
        if (userId == null) {
            // Redirect to log in if user is not authenticated
            return "redirect:/login";
        }
        UserEntity user = userService.findById(userId);
        if (user == null) {
            // Handle the case where the user with the given ID doesn't exist
            return "redirect:/login";
        }
        model.addAttribute("expense", new ExpenseEntity());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("userId", userId);
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("currencies", currencyService.getAllCurrencies());

        //List all expense item showing to the list
        List<ExpenseEntity> expenseEntities = expenseService.getRecentExpenses(user, 10);
        model.addAttribute("expenses", expenseEntities);

        // Check if fromDate and toDate are already set in the model (from a previous form submission)
        String fromDate = (String) model.getAttribute("fromDate");
        String toDate = (String) model.getAttribute("toDate");

        if (fromDate == null || toDate == null) {
            // If fromDate and toDate are not set, initialize them with default values
            LocalDate currentDate = LocalDate.now();
            LocalDate passDate = currentDate.minusDays(7);

            // Format the dates as yyyy-MM-dd
            fromDate = passDate.toString();
            toDate = currentDate.toString();

            model.addAttribute("fromDate", fromDate);
            model.addAttribute("toDate", toDate);
        }

        LocalDate dateWithMostExpense = expenseService.findDateWithMostExpenses(user, fromDate, toDate);
        model.addAttribute("dateWithMostExpenses", dateWithMostExpense);

        List<ItemEntity> topItems = expenseService.findTop3ItemsByDateRange(user, fromDate, toDate);
        model.addAttribute("top3ItemWithMostExpenses", topItems);

        double averageExpense = expenseService.calculateAverageSpendingByDateRange(user, fromDate, toDate);
        model.addAttribute("averageExpenseByUSD", averageExpense);

        return "index";
    }

    @PostMapping("/create")
    public String createExpense(@ModelAttribute("expense") @Validated ExpenseEntity expenseEntity, BindingResult bindingResult, Model model, HttpSession httpSession){
        Integer userId = (Integer) httpSession.getAttribute("userId");

        if (userId == null) {
            // Redirect to log in if user is not authenticated
            return "redirect:/login";
        }
        UserEntity user = userService.findById(userId);
        if (user == null) {
            // Handle the case where the user with the given ID doesn't exist
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("items", itemService.getAllItems());
            model.addAttribute("currencies", currencyService.getAllCurrencies());

            List<ExpenseEntity> expenseEntities = expenseService.getRecentExpenses(user, 10);
            model.addAttribute("expenses", expenseEntities);
            return "index";
        }
        expenseEntity.setUserId(user);
        expenseService.createExpense(expenseEntity);
        return "redirect:/expenses";
    }

    @PostMapping("/generateReport")
    public String generateReport(@RequestBody DateRange dateRange, Model model) {
        // Validate and process the new date range here

        // Update the model attributes with the new date values
        model.addAttribute("fromDate", dateRange.getFromDate());
        model.addAttribute("toDate", dateRange.getToDate());

        // Return a response if needed (e.g., success message)
        return "redirect:/expenses";
    }

}
