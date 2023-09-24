package com.aupp.expensetracker.controller;

import com.aupp.expensetracker.Entity.*;
import com.aupp.expensetracker.service.Impl.AdminImpl;
import com.aupp.expensetracker.service.Impl.AdminPortalServiceImpl;
import com.aupp.expensetracker.service.Impl.CurrencyServiceImpl;
import com.aupp.expensetracker.service.Impl.ItemServiceImpl;
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
@RequestMapping("/portal")
public class AdminPortalController {
    @Autowired
    private AdminImpl adminService;
    @Autowired
    private AdminPortalServiceImpl adminPortalService;
    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private CurrencyServiceImpl currencyService;

    @GetMapping("")
    public String showPortal(HttpSession httpSession, Model model){
        Integer adminId = (Integer) httpSession.getAttribute("adminId");
        if (adminId == null) {
            return "redirect:/login_admin";
        }
        AdminEntity admin = adminService.findById(adminId);
        if (admin == null) {
            return "redirect:/login_admin";
        }
        model.addAttribute("item", new ItemEntity());
        model.addAttribute("currency", new CurrencyEntity());
        model.addAttribute("adminName", admin.getAdminName());
        model.addAttribute("adminEmail", admin.getAdminEmail());
        model.addAttribute("adminId", adminId);

        List<ItemEntity> itemEntities = itemService.getAllItems();
        model.addAttribute("items", itemEntities);

        List<CurrencyEntity> currencyEntities =  currencyService.getAllCurrencies();
        model.addAttribute("currencies", currencyEntities);

        return "admin_portal";
    }
    @PostMapping("/createItem")
    public String createItem(@ModelAttribute("item") @Validated ItemEntity itemEntity, BindingResult bindingResult, Model model, HttpSession httpSession){
        Integer adminId = (Integer) httpSession.getAttribute("adminId");
        if (adminId == null) {
            return "redirect:/login_admin";
        }
        AdminEntity admin = adminService.findById(adminId);
        if (admin == null) {
            return "redirect:/login_admin";
        }
        if (bindingResult.hasErrors()){
            List<ItemEntity> itemEntities = itemService.getAllItems();
            model.addAttribute("items", itemEntities);
            return "admin_portal";
        }
        adminPortalService.createItems(itemEntity);
        return "redirect:/portal";
    }
    @PostMapping("/createCurrency")
    public String createCurrency(@ModelAttribute("currency") @Validated CurrencyEntity currencyEntity, BindingResult bindingResult, Model model, HttpSession httpSession){
        Integer adminId = (Integer) httpSession.getAttribute("adminId");
        if (adminId == null) {
            return "redirect:/login_admin";
        }
        AdminEntity admin = adminService.findById(adminId);
        if (admin == null) {
            return "redirect:/login_admin";
        }
        if (bindingResult.hasErrors()){
            List<CurrencyEntity> currencyEntities = currencyService.getAllCurrencies();
            model.addAttribute("currencies", currencyEntities);
            return "admin_portal";
        }
        adminPortalService.createCurrencies(currencyEntity);
        return "redirect:/portal";
    }
}
