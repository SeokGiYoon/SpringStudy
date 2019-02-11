package net.seokgi.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.seokgi.domain.User;

@Controller
public class UserController {
	
	private List<User> users = new ArrayList<User>();
	
	@PostMapping("/create")
	public String create(User user) {
		System.out.println(user);
		users.add(user);
		return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String listAll(Model model) {
		model.addAttribute("users", users);
;		return "list";
	}
}
