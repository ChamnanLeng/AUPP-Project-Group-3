package com.aupp.expensetracker.controller;

import com.aupp.expensetracker.Entity.ExpenseEntity;
import com.aupp.expensetracker.service.Impl.CurrencyServiceImpl;
import com.aupp.expensetracker.service.Impl.ExpenseServiceImpl;
import com.aupp.expensetracker.service.Impl.ItemServiceImpl;
import com.aupp.expensetracker.service.Impl.UserImpl;
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
    private UserImpl user;
    @Autowired
    private ExpenseServiceImpl expenseService;
    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private CurrencyServiceImpl currencyService;

    @GetMapping("/page")
    public String showExpenseForm(Model model){
        model.addAttribute("expense", new ExpenseEntity());
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("currencies", currencyService.getAllCurrencies());

        //List all expense item showing to the list
        List<ExpenseEntity> expenseEntities = expenseService.getRecentExpenses(10);
        model.addAttribute("expenses", expenseEntities);

        return "expensePage";
    }

    @PostMapping("/create")
    public String createExpense(@ModelAttribute("expense") @Validated ExpenseEntity expenseEntity, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("items", itemService.getAllItems());
            model.addAttribute("currencies", currencyService.getAllCurrencies());
            return "expensePage";
        }
        expenseService.createExpense(expenseEntity);
        return "redirect:/expenses/page";
    }
//    @GetMapping("/list")
//    public String listExpense(Model model){
//        List<ExpenseEntity> expenseEntities = expenseService.getAllWithUserItemCurrency();
//        model.addAttribute("expenses", expenseEntities);
//        return "expensePage";
//    }
}
