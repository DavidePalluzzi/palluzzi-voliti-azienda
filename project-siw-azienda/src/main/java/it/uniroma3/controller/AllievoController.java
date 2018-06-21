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

import it.uniroma3.controller.validator.AllievoValidator;
import it.uniroma3.model.Allievo;
import it.uniroma3.service.AllievoService;

@Controller
public class AllievoController {
	@Autowired
    private AllievoService service;

    @Autowired
    private AllievoValidator validator;

    @RequestMapping("/allievi")
    public String allievo(Model model) {
        model.addAttribute("allievi", this.service.findAll());
        return "listaAllievi";
    }

    @RequestMapping("/addAllievo")
    public String addAllievo(Model model) {
        model.addAttribute("allievo", new Allievo());
        return "allievoForm";
    }

    @RequestMapping(value = "/allievi/{id}", method = RequestMethod.GET)
    public String getAllievo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("allievo", this.service.findById(id));
    	return "MostraAllievi";
    }

    @RequestMapping(value = "/allievi", method = RequestMethod.POST)
    public String newAllievo(@Valid @ModelAttribute("allievo") Allievo allievo, 
    									Model model, BindingResult bindingResult) {
        this.validator.validate(allievo, bindingResult);
        
        if (this.service.alreadyExists(allievo)) {
            model.addAttribute("exists", "Allievo already exists");
            return "allievoForm";
        }
        else
            if (!bindingResult.hasErrors()) {
                this.service.save(allievo);
                model.addAttribute("allievi", this.service.findAll());
                return "listaAllievi";
            }
        
        return "allievoForm";
    }
}