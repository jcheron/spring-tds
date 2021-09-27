package edu.supavenir.td0.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import edu.supavenir.td0.models.Categorie;
import edu.supavenir.td0.models.Element;
import edu.supavenir.td0.technics.CssMessage;

@Controller
@SessionAttributes("categories")
public class ItemsController {

	private Categorie getCategorieByName(String nom, List<Categorie> categories) {
		int index = categories.indexOf(new Categorie(nom));
		return categories.get(index);
	}

	@ModelAttribute("categories")
	public List<Categorie> getCategories() {
		return new ArrayList<>(Arrays.asList(new Categorie("Amis"), new Categorie("Famille"), new Categorie("Pros")));
	}

	@GetMapping("/")
	public String itemsAction(Model model, @ModelAttribute String categorie) {
		if (categorie != null) {
			model.addAttribute("categorie", categorie);
		} else {
			model.addAttribute("categorie", "Amis");
		}
		return "items";
	}

	@GetMapping("/items/new/{categorie}")
	public String itemsNew(@PathVariable String categorie) {
		return "form";
	}

	@PostMapping("/items/addNew/{categorie}")
	public RedirectView add(Element elm, @SessionAttribute List<Categorie> categories, RedirectAttributes attrs,
			@PathVariable String categorie) {
		Categorie cat = getCategorieByName(categorie, categories);
		if (cat.addItem(elm)) {
			attrs.addFlashAttribute("msg", CssMessage.SuccessMessage("Element <b>" + elm + "</b> ajouté"));
		} else {
			attrs.addFlashAttribute("msg", CssMessage.ErrorMessage("Cet élément existe déjà !"));
		}
		attrs.addFlashAttribute("categorie", categorie);
		return new RedirectView("/");
	}

	@GetMapping("/items/inc/{categorie}/{nom}")
	public RedirectView incAction(@PathVariable String nom, @PathVariable String categorie,
			@SessionAttribute List<Categorie> categories) {
		getCategorieByName(categorie, categories).getElementByName(nom).inc();
		return new RedirectView("/");
	}

	@GetMapping("/items/dec/{categorie}/{nom}")
	public RedirectView decAction(@PathVariable String nom, @PathVariable String categorie,
			@SessionAttribute List<Categorie> categories) {
		getCategorieByName(categorie, categories).getElementByName(nom).inc();
		return new RedirectView("/");
	}

	@GetMapping("/items/delete/{categorie}/{nom}")
	public RedirectView deleteAction(@PathVariable String nom, @PathVariable String categorie,
			@SessionAttribute List<Categorie> categories, RedirectAttributes attrs) {
		getCategorieByName(categorie, categories).deleteItem(new Element(nom));
		attrs.addFlashAttribute("msg", CssMessage.SuccessMessage("L'Element <b>" + nom + "</b> a été supprimé."));
		return new RedirectView("/");
	}
}
