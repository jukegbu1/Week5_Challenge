package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class HomeController {

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping("/")
    public String listMessages(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "list";
    }

    //Get user data
    @GetMapping("/add")
    public String messageForm(Model model) {
        model.addAttribute("message", new Message());
        return "messageform";
    }

    //Process user information after they enter
    @PostMapping("/process")
    public String processForm(@Valid Message message,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "messageform";
        }

        //Afterwards goes back to list page
        messageRepository.save(message);
        return "redirect:/";

    }
    @RequestMapping("/detail/{id}")
    public String showMessage(@PathVariable("id") long id, Model model) {
        //get id inside of course, since unqiue ID
        model.addAttribute("message", messageRepository.findById(id).get());
        return "show";
    }


    //just returns course form so you can return values in database
    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("message", messageRepository.findById(id).get());
        return "messageform";
    }

    @RequestMapping("/delete/{id}")
    public String delMessage(@PathVariable("id") long id) {
        messageRepository.deleteById(id);
        return "redirect:/";

    }

}
