package it.uniroma3.controller;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.controller.validator.ResponsabileValidator;
import it.uniroma3.model.CentroDiFormazione;
import it.uniroma3.model.Responsabile;
import it.uniroma3.service.CentroDiFormazioneService;
import it.uniroma3.service.ResponsabileService;



@Controller
public class ResponsabileController {
	@Autowired
	private CentroDiFormazioneService centroService;
	
	@Autowired
	private ResponsabileService responsabileService;
	


	
	@Autowired
	private ResponsabileValidator responsabileValidator;

	@RequestMapping("/responsabili")
	public String responsabili(Model model) {
		model.addAttribute("responsabili", this.responsabileService.findAll());
		return "listaResponsabili";
	}

	@RequestMapping("/addResponsabile")
	public String addResponsabile(Model model) {
		model.addAttribute("responsabile", new Responsabile());
		return "responsabileForm";
	}

	@RequestMapping(value="/selectResponsabile")
	public String selectResponsabile(Model model, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Responsabile responsabile = responsabileService.findByUsername(user.getUsername());
		System.out.println("admin " + responsabile.getUsername() + ","+ responsabile.getRole());

		if(responsabile.getRole().equals("ADMIN")) {
			System.out.println("admin" + responsabile.getUsername() + " "+ responsabile.getRole());

			return "adminHome";
		}else {
			if(responsabile.getRole().equals("USER")) {		
				System.out.println("user" + responsabile.getUsername() + " "+ responsabile.getRole());
				return "forward:/centri/"+centroService.findByResponsabile(responsabile).getId();
			}
		}
		
		return "non autorizzato";
}
	

	@RequestMapping(value = "/responsabile", method = RequestMethod.POST)
	public String newCentro(@Valid @ModelAttribute("responsabile") Responsabile responsabile, 
			Model model, BindingResult bindingResult) {
		this.responsabileValidator.validate(responsabile, bindingResult);

		if (this.responsabileService.alreadyExists(responsabile)) {
			model.addAttribute("exists", "Responsabile already exists");
			return "responsabileForm";
		}
		else {
			if (!bindingResult.hasErrors()) {
				this.responsabileService.save(responsabile);
				model.addAttribute("responsabili", this.responsabileService.findAll());
				return "listaResponsabili";
			}
		}
		return "responsabileForm";
	}
	


	@RequestMapping(value ="/showResponsabili/{id}", method=RequestMethod.GET)
	public String showResponsabili(Model model,@PathVariable("id") Long id, HttpSession session) {
		session.setAttribute("centro", this.centroService.findById(id));
		model.addAttribute("responsabili", responsabileService.findAll());
		return "responsabiliTotali";
	}
	
	@RequestMapping(value ="/connectRespCentro/{id}")
	public String connectRespCentro(Model model,@PathVariable("id") Long id, HttpSession session) {
		Responsabile responsabile = responsabileService.findById(id);
		CentroDiFormazione centro = (CentroDiFormazione) session.getAttribute("centro");
		centro.setResponsabile(responsabile);
		this.centroService.save(centro);
		model.addAttribute("centro", centro);
		return "MostraCentro";
	}
	
}
