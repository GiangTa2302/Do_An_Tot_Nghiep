package doan.com.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.Diem;
import doan.com.vn.entity.DiemPK;

@Repository
public interface DiemRepository extends JpaRepository<Diem, DiemPK>{
    
}
