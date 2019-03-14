package net.seokgi.web;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.seokgi.domain.User;
import net.seokgi.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String login() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String find(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if(user == null || !password.equals(user.getPassword())) {
			return "redirect:/users/loginForm";
		}
		session.setAttribute("sessionUser", user);
		
		return "redirect:/";
	}
	
	@PostMapping("")
	public String create(User user) {
		System.out.println(user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String listAll(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/form")
	public String form() {
		
		return "/user/form";
	}
	
	@GetMapping("/profile")
	public String profile(User user) {
		return "/user/profile";
	}	
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		User sessionUser = (User)session.getAttribute("sessionUser");
		
		if(sessionUser == null) {
			return "redirect:/users/login";
		}
		
		if(!id.equals(sessionUser.getId())) {
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
		
		Optional<User> op = userRepository.findById(sessionUser.getId());
		User user = null;
		if(op.isPresent()) {
			user = op.get();
		}
		model.addAttribute("user", user);
		System.out.println(user);
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
		User sessionUser = (User)session.getAttribute("sessionUser");
		
		if(sessionUser == null) {
			return "redirect:/users/login";
		}
		
		if(!id.equals(sessionUser.getId())) {
			throw new IllegalStateException("you can't update the another user");
		}
		
		Optional<User> userOptional = userRepository.findById(sessionUser.getId());
		User user = null;
		if(userOptional.isPresent()) {
			user = userOptional.get();
		}
		
		user.update(updatedUser);
		userRepository.save(user);
		
		
		return "redirect:/users";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionUser");
		
		return "redirect:/";
	}
	
	
	
}
