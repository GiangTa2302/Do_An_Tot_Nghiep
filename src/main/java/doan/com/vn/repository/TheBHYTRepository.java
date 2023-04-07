package doan.com.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.TheBHYT;

@Repository
public interface TheBHYTRepository extends JpaRepository<TheBHYT, String>{

}
