package com.aupp.expensetracker.controller;

import com.aupp.expensetracker.Entity.ExpenseEntity;
import com.aupp.expensetracker.Entity.UserEntity;
import com.aupp.expensetracker.service.Impl.CurrencyServiceImpl;
import com.aupp.expensetracker.service.Impl.ExpenseServiceImpl;
import com.aupp.expensetracker.service.Impl.ItemServiceImpl;
import com.aupp.expensetracker.service.Impl.UserImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            // Redirect to login if user is not authenticated
            return "redirect:/login";
        }
        UserEntity user = userService.findById(userId);
        if (user == null) {
            // Handle the case where the user with the given ID doesn't exist
            return "redirect:/login";
        }
        model.addAttribute("expense", new ExpenseEntity());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("userId", userId);
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("currencies", currencyService.getAllCurrencies());

        //List all expense item showing to the list
        List<ExpenseEntity> expenseEntities = expenseService.getRecentExpenses(user, 10);
        model.addAttribute("expenses", expenseEntities);
        return "index";
    }

    @PostMapping("/create")
    public String createExpense(@ModelAttribute("expense") @Validated ExpenseEntity expenseEntity, BindingResult bindingResult, Model model, HttpSession httpSession){
        Integer userId = (Integer) httpSession.getAttribute("userId");

        if (userId == null) {
            // Redirect to login if user is not authenticated
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

    


}
