package doan.com.vn.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.MonHoc;

@Repository
public interface MonHocRepository extends JpaRepository<MonHoc, String>{
    Page<MonHoc> findByDeletedFalse(Pageable pageable);
    
    List<MonHoc> findByDeletedFalse();
}
