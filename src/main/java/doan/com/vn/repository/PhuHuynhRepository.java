package doan.com.vn.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.PhuHuynh;

@Repository
public interface PhuHuynhRepository extends JpaRepository<PhuHuynh, String>{
    Page<PhuHuynh> findByDeletedFalse(Pageable pageable);
    
    List<PhuHuynh> findByDeletedFalse();
}
