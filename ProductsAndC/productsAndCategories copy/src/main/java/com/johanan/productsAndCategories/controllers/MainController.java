package com.johanan.productsAndCategories.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.johanan.productsAndCategories.models.Category;
import com.johanan.productsAndCategories.models.Product;
import com.johanan.productsAndCategories.models.User;
import com.johanan.productsAndCategories.services.MainService;
import com.johanan.productsAndCategories.services.UserService;
import com.johanan.productsAndCategories.validators.UserValidators;

@Controller
public class MainController {
	@Autowired
	private MainService mainServe;
	@Autowired 
	private UserService uServe;
	@Autowired
	private UserValidators uValid;
	
	@GetMapping("/login")
	public String login(@ModelAttribute("user")User user) {
		return "login.jsp";
	}
	@PostMapping("/register/create")
	public String createUser(@Valid @ModelAttribute("user")User user, BindingResult result, HttpSession session) {
		uValid.validate(user, result);
		if(result.hasErrors()) {
			return "login.jsp";
		}else {
			User newUser = this.uServe.registerThisUser(user);
			session.setAttribute("user_id", newUser.getId());
			return "redirect:/mainPage";
		}
	}
	@PostMapping("/login/user")
	public String loginUser(@RequestParam("loginEmail")String email, @RequestParam("loginPass")String password, RedirectAttributes redirects, HttpSession sesh) {
		if(!this.uServe.authenticateUser(email, password)) {
			redirects.addFlashAttribute("loginError","Invalid Username or password.");
			return "redirect:/login";
		}
		User user = this.uServe.getByEmail(email);
		sesh.setAttribute("user_id", user.getId());
		return "redirect:/mainPage";
		
	}
	@GetMapping("/mainPage")
	public String mainP(Model model, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/login";
		}
		Long userId = (Long)session.getAttribute("user_id");
		User users = this.uServe.findOneId(userId);
		model.addAttribute("user", users);
		List<Product> products = mainServe.allProducts();
		List<Category> categories = mainServe.allCategory();
		model.addAttribute("cat", categories);
		model.addAttribute("prods", products);
		return "mainP.jsp";
	}
	@GetMapping("/products/new")
	public String newProduct(@ModelAttribute("product")Product product, HttpSession session, Model model) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/login";
		}
		Long userId = (Long)session.getAttribute("user_id");
		User users = this.uServe.findOneId(userId);
		model.addAttribute("user", users);
		return "newProd.jsp";
	}
	@PostMapping("/product/create")
	public String createProduct(@Valid @ModelAttribute("product")Product product, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return "newProd.jsp";
		}else {
			Long userId = (Long)session.getAttribute("user_id");
			User thisUserAdd = this.uServe.findOneId(userId);
			product.setUserss(thisUserAdd);
			mainServe.createProd(product);
			return "redirect:/mainPage";
		}
	}
	@GetMapping("/categories/new")
	public String newCategory(@ModelAttribute("category")Category category, HttpSession session, Model model) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/login";
		}
		Long userId = (Long)session.getAttribute("user_id");
		User users = this.uServe.findOneId(userId);
		model.addAttribute("user", users);
		return "newCat.jsp";
	}
	@PostMapping("/category/create")
	public String createCategory(@Valid @ModelAttribute("category")Category category, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return "newCat.jsp";
		}else {
			Long userId = (Long)session.getAttribute("user_id");
			User thisUserAddCat = this.uServe.findOneId(userId);
			category.setUsered(thisUserAddCat);
			mainServe.createCat(category);
			return "redirect:/mainPage";
		}
	}
	@GetMapping("/products/{id}")
	public String showProd(@PathVariable("id") Long id, Model model, @ModelAttribute("product")Product product, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/login";
		}
		Long userId = (Long)session.getAttribute("user_id");
		User users = this.uServe.findOneId(userId);
		model.addAttribute("user", users);
		
		Product products = mainServe.findOneProduct(id);
		List<Category> cate = mainServe.catAddNull(products);
		model.addAttribute("cate", cate);
		model.addAttribute("product", products);
		return "showProduct.jsp";
	}
	@PostMapping("/product/{id}")
	public String addCategory(@RequestParam("categories")Long catId, @PathVariable("id")Long prodId) {
		Product thisProd = mainServe.findOneProduct(prodId);
		Category thisCat = mainServe.findOneCategory(catId);
		mainServe.addCategoryToProduct(thisProd, thisCat);
		return "redirect:/mainPage";
	}
	@GetMapping("category/{id}")
	public String showCat(@PathVariable("id") Long id, Model model, @ModelAttribute("category")Category category, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/login";
		}
		Long userId = (Long)session.getAttribute("user_id");
		User users = this.uServe.findOneId(userId);
		model.addAttribute("user", users);
		
		Category categories = mainServe.findOneCategory(id);
		List<Product> products = mainServe.onlyAddIfNotUsed(categories);
		model.addAttribute("product", products);
		model.addAttribute("category", categories);
		return "showCat.jsp";
	}
	@PostMapping("/category/{id}")
	public String addProduct(@RequestParam("products")Long prodId, @PathVariable("id")Long catId) {
		Product thisProd = mainServe.findOneProduct(prodId);
		Category thisCat = mainServe.findOneCategory(catId);
		mainServe.addProductToCategory(thisCat, thisProd);
		return "redirect:/mainPage";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	@GetMapping("/delete/product/{id}")
	public String deleteProduct(@PathVariable("id")Long id) {
			mainServe.deleteProd(id);
			return "redirect:/mainPage";
	}
	@GetMapping("/delete/category/{id}")
	public String deleteCategory(@PathVariable("id")Long id) {
		mainServe.deleteCat(id);
		return "redirect:/mainPage";
	}
}
//products/" + prodId;
