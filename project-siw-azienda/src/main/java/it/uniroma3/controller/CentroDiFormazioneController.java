package it.uniroma3.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.controller.validator.CentroDiFormazioneValidator;
import it.uniroma3.model.Allievo;
import it.uniroma3.model.Attività;
import it.uniroma3.model.CentroDiFormazione;
import it.uniroma3.service.AllievoService;
import it.uniroma3.service.AttivitàService;
import it.uniroma3.service.CentroDiFormazioneService;
import it.uniroma3.service.ResponsabileService;


@Controller
public class CentroDiFormazioneController {
	public List<Allievo> allievicentro=new ArrayList<Allievo>();
	@Autowired
	private CentroDiFormazioneService service;
	@Autowired
	private AllievoService allievoservice;
	@Autowired
	private AttivitàService attivitaservice;
	@Autowired
	private ResponsabileService responsabileService;
	@Autowired
	private CentroDiFormazioneValidator validator;

	@RequestMapping("/centri")
	public String centri(Model model) {
		model.addAttribute("centri", this.service.findAll());
		return "listaCentri";
	}

	@RequestMapping("/addCentro")
	public String addCentro(Model model) {
		model.addAttribute("centro", new CentroDiFormazione());
		return "centroForm";
	}

	@RequestMapping(value = "/centri/{id}", method = RequestMethod.GET)
	public String getCentro(@PathVariable("id") Long id, Model model,HttpSession session) {
		model.addAttribute("centro", this.service.findById(id));
		session.setAttribute("centro", service.findById(id));
		return "mostraCentro";
	}
	

	@RequestMapping(value = "/centro", method = RequestMethod.POST)
	public String newCentro(@Valid @ModelAttribute("centro") CentroDiFormazione centro, 
			Model model, BindingResult bindingResult, HttpSession session) {
		this.validator.validate(centro, bindingResult);

		if (this.service.alreadyExists(centro)) {
			model.addAttribute("exists", "Centro already exists");
			return "centroForm";
		}
		else {
			if (!bindingResult.hasErrors()) {
				this.service.save(centro);
				model.addAttribute("responsabili",this.responsabileService.findAll());
				session.setAttribute("centro", centro);
				return "responsabiliTotali";
			}
		}
		return "centroForm";
	}
	@RequestMapping(value ="/AllievoAttivita", method=RequestMethod.GET)
	public String getAllievo(Model model) {
		model.addAttribute("allievi", this.allievoservice.findAll());
		return "allieviTotali";
	}
	@RequestMapping(value ="/ShowAttivita/{id}", method=RequestMethod.GET)
	public String ShowAttivita(Model model,@PathVariable("id") Long id,HttpSession session) {
		session.setAttribute("allievo", allievoservice.findById(id));
		CentroDiFormazione centro=(CentroDiFormazione) session.getAttribute("centro");
		model.addAttribute("attivita", centro.getAttivita());
		return "listaAttivitaCentro";
	}
	@RequestMapping(value ="/AggiungiAllievoAttivita/{id}")
	public String addAllievoAttivita(Model model,@PathVariable("id") Long id,HttpSession session) {
		Allievo allievo= (Allievo) session.getAttribute("allievo");
		CentroDiFormazione centro = (CentroDiFormazione) session.getAttribute("centro");
		Attività attivita= attivitaservice.findById(id);
		
		/* controllo il numero di iscritti per verificare la capienza */
		List<Attività> lista=centro.getAttivita();
		int numIscritti=0;
		for(Attività a:lista) {
			numIscritti+=a.getAllievi().size();
		}
		if(numIscritti<centro.getCapienza()) {
			if(attivita.getAllievi().contains(allievo)) {
				model.addAttribute("exist", "L'allievo "+ allievo.getNome() + allievo.getCognome() + " è stato già iscritto a questa attività");
				model.addAttribute("result", centro.getAttivita());
				return "listaAttivitaCentro";
			}else {
				allievo.getAttività().add(attivita);
				attivita.getAllievi().add(allievo);
				model.addAttribute("result", "AGGIORNAMENTO: L'allievo " + allievo.getNome() + " " + allievo.getCognome() + " è iscritto a " + attivita.getNome());
				this.allievoservice.save(allievo);
				this.attivitaservice.save(attivita);
							}
			
		}else {
			model.addAttribute("result", "superata capienza massima");
		}
		model.addAttribute("centro", centro);
		return "mostraCentro";
	}
	
	


}
