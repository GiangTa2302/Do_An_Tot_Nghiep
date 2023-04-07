package doan.com.vn.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.Lop;

@Repository
public interface LopRepository extends JpaRepository<Lop, String> {    
    Page<Lop> findByTenKhoiAndDeletedFalse(int khoi, Pageable pageable);
    
    List<Lop> findByDeletedFalse();
}
