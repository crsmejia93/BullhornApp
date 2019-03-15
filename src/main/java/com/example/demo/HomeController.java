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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


@Controller
public class HomeController {
    @Autowired
    MessageRepository messageRepository;

//    private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    @RequestMapping("/")
    public String listMessages(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String messageForm(Model model){
        model.addAttribute("message", new Message());
        return "messageform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Message message, BindingResult result){
        if(result.hasErrors()){
            return "messageform";
        }
        try{
            message.setPostedDate(LocalDateTime.now());
        }catch (Exception e){
            e.printStackTrace();
        }
        messageRepository.save(message);
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("message", messageRepository.findById(id).get());
        return "messageform";
    }

    @RequestMapping("/detail/{id}")
    public String showMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("message", messageRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/delete/{id}")
    public String delMessage(@PathVariable("id") long id){
        messageRepository.deleteById(id);
        return "redirect:/";
    }
}
