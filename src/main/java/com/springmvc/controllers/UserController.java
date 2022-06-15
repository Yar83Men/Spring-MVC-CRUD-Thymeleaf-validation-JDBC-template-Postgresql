package com.springmvc.controllers;

import com.springmvc.dao.PersonDAO;
import com.springmvc.models.Person;
import com.springmvc.util.PersonValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    public UserController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        List<Person> list = personDAO.index();
        model.addAttribute("people", list);
        // System.out.println(list);
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "users/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "users/new";
    }

    @PostMapping()
    public String create(@Valid @ModelAttribute("person") Person person,
                         BindingResult bindingResult)
    {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "users/new";
        }
        personDAO.save(person);
        return "redirect:/user";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@Valid @ModelAttribute("person") Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id)
    {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) return "users/edit";

        personDAO.update(id, person);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/user";
    }
}
