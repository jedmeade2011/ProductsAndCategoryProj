package com.johanan.productsAndCategories.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.johanan.productsAndCategories.models.Category;
import com.johanan.productsAndCategories.models.Product;
import com.johanan.productsAndCategories.repositories.CategoryRepository;
import com.johanan.productsAndCategories.repositories.ProductRepository;

@Service
public class MainService {
	private ProductRepository pRepo;
	private CategoryRepository cRepo;
	
	public MainService(ProductRepository pRepo, CategoryRepository cRepo) {
		this.pRepo = pRepo;
		this.cRepo = cRepo;
	}
	public List<Product> allProducts(){
		return this.pRepo.findAll();
	}
	public List<Category> allCategory(){
		return this.cRepo.findAll();
	}
	public List<Product> onlyAddIfNotUsed(Category cat){
		return this.pRepo.findByCategoriesNotContains(cat);
	}
	public List<Category> catAddNull(Product prod){
		return this.cRepo.findByProductsNotContains(prod);
	}
	public Product findOneProduct(Long id) {
		return this.pRepo.findById(id).orElse(null);
	}
	public Category findOneCategory(Long id) {
		return this.cRepo.findById(id).orElse(null);
	}
	public Product createProd(Product newProd) {
		return this.pRepo.save(newProd);
	}
	public Category createCat(Category newCat) {
		return this.cRepo.save(newCat);
	}
	public void addCategoryToProduct(Product product, Category category) {
		List<Category> categories = product.getCategories();
		categories.add(category); 
		this.pRepo.save(product);
	}
	public void addProductToCategory(Category category, Product product) {
		List<Product> products = category.getProducts();
		products.add(product);
		this.cRepo.save(category);
	}
	public void deleteProd(Long id) {
		this.pRepo.deleteById(id);
	}
	public void deleteCat(Long id) {
		this.cRepo.deleteById(id);
	}


}
