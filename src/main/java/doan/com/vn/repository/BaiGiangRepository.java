package doan.com.vn.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.BaiGiang;

@Repository
public interface BaiGiangRepository extends JpaRepository<BaiGiang, Integer>{
    Page<BaiGiang> findByDeletedFalse(Pageable pageable);
    
    List<BaiGiang> findByDeletedFalse();
}
