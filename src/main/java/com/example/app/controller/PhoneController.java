package com.example.app.controller;

import com.example.app.model.Phone;
import com.example.app.service.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PhoneController {

    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    // Hiển thị danh sách tất cả điện thoại
    @GetMapping("/phones")
    public String listPhones(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("phones", phoneService.search(keyword));
        } else {
            model.addAttribute("phones", phoneService.findAll());
        }
        model.addAttribute("keyword", keyword);
        return "phone-list";
    }

    // Hiển thị form để thêm/sửa điện thoại
    @GetMapping("/phones/form")
    public String showPhoneForm(@RequestParam(required = false) Long id, Model model) {
        Phone phone = id != null ? phoneService.findById(id).orElse(new Phone()) : new Phone();
        model.addAttribute("phone", phone);
        model.addAttribute("manufactures", phoneService.findAllManufactures());
        return "phone-form";
    }

    // Xử lý việc lưu điện thoại
    @PostMapping("/phones")
    public String savePhone(@ModelAttribute Phone phone, @RequestParam Long manufactureId) {
        phoneService.save(phone, manufactureId);
        return "redirect:/phones";
    }

    // Xóa một điện thoại
    @GetMapping("/phones/delete")
    public String deletePhone(@RequestParam Long id) {
        phoneService.deleteById(id);
        return "redirect:/phones";
    }

    // Endpoint để tái hiện vấn đề N+1
    @GetMapping("/n-plus-1-problem")
    public String showNPlus1Problem(Model model) {
        model.addAttribute("manufactures", phoneService.getManufacturesNaively());
        // Sau khi view được render, hãy kiểm tra console log để thấy N+1 query.
        return "n-plus-1-demo";
    }

    // Endpoint để biểu diễn giải pháp
    @GetMapping("/n-plus-1-solution")
    public String showNPlus1Solution(Model model) {
        model.addAttribute("manufactures", phoneService.getManufacturesOptimized());
        // Kiểm tra console log để thấy chỉ có 1 query duy nhất.
        return "n-plus-1-demo";
    }
}