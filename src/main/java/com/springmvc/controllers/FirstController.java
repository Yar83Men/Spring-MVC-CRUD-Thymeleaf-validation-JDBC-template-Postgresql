package com.springmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "surname", required = false) String surname,
                            Model model) {
        model.addAttribute("message", name + " " + surname);
        // System.out.println(name + " " + surname);
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodByePage() {
        return "first/goodbye";
    }

    @GetMapping("/calculator")
    public String calculator(@RequestParam(value = "a", required = false) Integer a,
                             @RequestParam(value = "b", required = false) Integer b,
                             @RequestParam(value = "action", required = false) String action,
                             Model model) {
        int res = 0;
        if (action != null && a != null && b != null) {
            if (action.equals("addition")) {
                res = a + b;
            }

            if (action.equals("subtraction")) {
                res = a - b;
            }

            if (action.equals("multiplication")) {
                res = a * b;
            }

            if (action.equals("division") && b != 0) {
                res = a / b;
            } else if (action.equals("division") && b == 0) {
                res = 0;
            }
        }
        model.addAttribute("result", res);
        return "first/calculator";
    }
}
