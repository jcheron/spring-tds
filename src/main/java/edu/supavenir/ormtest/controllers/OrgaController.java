package edu.supavenir.ormtest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.supavenir.ormtest.models.Groupe;
import edu.supavenir.ormtest.models.Organization;
import edu.supavenir.ormtest.repositories.OrgaRepository;

@Controller
@RequestMapping("/orga")
public class OrgaController {

	@Autowired
	private OrgaRepository repository;

	@GetMapping("/")
	public String index(Model model) {
		List<Organization> organizations = repository.findAll();
		System.out.println(organizations);
		model.addAttribute("organizations", organizations);
		return "index";
	}

	@GetMapping("/add")
	public String formAction() {
		return "formOrga";
	}

	@PostMapping("/add")
	public @ResponseBody String addAction(Organization orga) {
		Groupe group = new Groupe();
		group.setName("Etudiants");
		group.setOrganization(orga);
		orga.getGroups().add(group);
		repository.saveAndFlush(orga);
		return "orga ajoutée : " + orga;
	}
}
