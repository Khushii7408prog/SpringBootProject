package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entites.Contact;
import com.smart.entites.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;

	// common data
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();

		System.out.println("USERNAME : " + userName);
		User user = userRepository.getUserByUserName(userName);
		System.out.println("user : " + user);
		model.addAttribute("user", user);
		// get the user using usernamw

	}

	// home dashborad
	@RequestMapping("/index")
	public String dashbord(Model model, Principal principal) {

		String userName = principal.getName();

		System.out.println("USERNAME : " + userName);
		User user = userRepository.getUserByUserName(userName);
		System.out.println("user : " + user);
		model.addAttribute("user", user);
		model.addAttribute("title", "User Dashbord");

		// get the user using usernamw
		return "normal/user_dashbord";

	}

	// open add from handler
	@GetMapping("/add-contact")
	public String OpenContactFrom(Model model) {
		model.addAttribute("title", "Add Conact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}

	// process-contact add contact form
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Principal principal,HttpSession session) {
		try {
			String name = principal.getName();
			User user = this.userRepository.getUserByUserName(name);

			/*
			 * if(3>2) { throw new Exception(); }
			 */
			
			// Proceesing and uploading file..
			if (file.isEmpty()) {
				// if file is empty try mesg
				System.out.println("Image not uploaded");
			}

			else {
				// if

				contact.setImage(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println("Image is Uploaded");
			}

			contact.setUser(user);
			user.getContacts().add(contact);
			// this.userRepository().save(user);

			this.userRepository.save(user);
			System.out.println("user :::" + user);
			System.out.println("DATA :::" + contact);
			System.out.println("Data Add to data base!!");
		//message success......
			session.setAttribute("message", new Message("Your contact is added !! add more..", "success"));
		} catch (Exception e) {

			System.out.println("Error" + e.getMessage());
			e.printStackTrace();
			//message error
			session.setAttribute("message", new Message("Some went wrong !! Try again..", "danger"));

		}
		return "normal/add_contact_form";
	}

	
	
	// show contact handler
	@GetMapping("/show-contacts")
	public String showContacts(Model model,Principal principal) {
		model.addAttribute("title", "Show User Contants");
		String userName = principal.getName();
		User user	=this.userRepository.getUserByUserName(userName);
		
		List<Contact> contacts		= this.contactRepository.findContactByUser(user.getId());

model.addAttribute("contacts", contacts);
return "normal/show-contacts";
	}
	
	
}
