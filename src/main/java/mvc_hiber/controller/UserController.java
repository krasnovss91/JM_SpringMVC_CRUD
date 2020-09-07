package mvc_hiber.controller;


import mvc_hiber.model.User;
import mvc_hiber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class UserController {
   // @GetMapping(value = "navigation")
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
 //проблемы на 31, 34 и 44 строках user-form.html
    @GetMapping("/list-of-users")
    public String showUsers(Model model) {// Этот контроллер работает исправно
        model.addAttribute("users", userService.getAllUsers());//вызов метода
        return "list-of-users";//страница, которую возвращаем
    }

    @GetMapping("/")
    public String showAll(Model model){
        model.addAttribute("users",userService.getAllUsers());
        return "navigation";
    }

    @PostMapping("showUserForm/add")//должен ловить запрос с этого адреса
    public String addUser(@ModelAttribute User user){
        if(user.getId() == 0){
            userService.saveUser(user);
        }else {
            userService.editUser(user);
        }
        return "redirect:/showUserForm";//обрабатывать и возвращать сюда
    }

    @GetMapping("/showUserForm")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", userService.getAllUsers());
        return "user-form";
    }


   @GetMapping("showUserForm/edit/{id}")//здесь та же проблема
   public String editUser(@PathVariable("id") int id, Model model) {
       User user = userService.getUserById(id);
       model.addAttribute("listUsers", this.userService.getAllUsers());
       model.addAttribute("user", user);
       return "redirect:/showUserForm";
   }
   /*
    @PostMapping("/edit")
    public String editUser(@ModelAttribute("editUser") User user) {
        userService.editUser(user);
        return "redirect:/user-form";
    }

    */

    @GetMapping("showUserForm/delete/{id}")//и здесь
    public String deleteUser(@PathVariable("id") long id) {
       userService.deleteUser(id);
       return "redirect:/showUserForm";

    }

    @GetMapping("/{id}")
    public String userData(@PathVariable long id, Model model) {
        model.addAttribute("user", this.userService.getUserById(id));
        return "list-of-users";
    }



}


