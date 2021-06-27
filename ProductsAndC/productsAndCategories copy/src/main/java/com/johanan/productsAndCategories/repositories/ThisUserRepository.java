package com.johanan.productsAndCategories.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.johanan.productsAndCategories.models.User;

@Repository
public interface ThisUserRepository extends CrudRepository<User, Long>{
	User findByEmail(String email);
	boolean existsByEmail(String email);
}
