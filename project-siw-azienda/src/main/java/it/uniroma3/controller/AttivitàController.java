package it.uniroma3.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import it.uniroma3.controller.validator.AttivitàValidator;
import it.uniroma3.model.Attività;
import it.uniroma3.model.CentroDiFormazione;
import it.uniroma3.service.AttivitàService;

@Controller

public class AttivitàController {
	@Autowired
    private AttivitàService service;

    @Autowired
    private AttivitàValidator validator;

    @RequestMapping("/attivita")
    public String attivita(Model model, @SessionAttribute("centro") CentroDiFormazione centro) {
        model.addAttribute("attivita", this.service.findByCentro(centro));
        return "listaAttivita";
    }

    @RequestMapping("/addAttivita")
    public String addAttivita(Model model) {
        model.addAttribute("a", new Attività());
        return "attivitaForm";
    }

    @RequestMapping(value = "/attivita/{id}", method = RequestMethod.GET)
    public String getAttivita(@PathVariable("id") Long id, Model model) {
        model.addAttribute("a", this.service.findById(id));
    	return "MostraAttivita";
    }

    @RequestMapping(value = "/attivita", method = RequestMethod.POST)
    public String newAttivita(@Valid @ModelAttribute("a") Attività att, 
    									Model model, BindingResult bindingResult, @SessionAttribute("centro") CentroDiFormazione centro) {
        this.validator.validate(att, bindingResult);
        
        if (this.service.alreadyExists(att)) {
            model.addAttribute("exists", "Attivita already exists");
            return "attivitaForm";
        }
        else {
            if (!bindingResult.hasErrors()) {
            	att.setCentro(centro);
                this.service.save(att);
                model.addAttribute("centro", centro);
                return "mostraCentro";
            }
        }
        return "attivitaForm";
    }
}
