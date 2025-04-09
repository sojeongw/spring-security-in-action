package security.springsecurityinaction.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import security.springsecurityinaction.services.ProductService;

@Controller
public class MainPageController {

    @Autowired
    private ProductService productService;

    @CrossOrigin("http://localhost:8080")
    @GetMapping("/main")
    public String main(Authentication a, Model model) {
        model.addAttribute("username", a.getName());
        model.addAttribute("products", productService.findAll());
        return "main.html";
    }
}
