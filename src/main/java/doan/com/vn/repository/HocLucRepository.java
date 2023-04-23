package doan.com.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.HocLuc;

@Repository
public interface HocLucRepository extends JpaRepository<HocLuc, Integer>{

}
