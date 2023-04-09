package doan.com.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

}
