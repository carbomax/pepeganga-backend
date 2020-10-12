package com.startcode.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.startcode.Models.User;

@Repository
public interface IUserRepo extends JpaRepository<User, Integer>  {

	User findByName(String name);
}
