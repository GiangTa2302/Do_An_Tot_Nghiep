package doan.com.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.Ban;

@Repository
public interface BanRepository extends JpaRepository<Ban, Integer>{

}
