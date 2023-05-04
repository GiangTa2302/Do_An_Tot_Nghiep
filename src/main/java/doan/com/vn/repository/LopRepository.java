package doan.com.vn.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.Lop;

@Repository
public interface LopRepository extends JpaRepository<Lop, String> {    
    Page<Lop> findByTenKhoiAndDeletedFalse(Integer khoi, Pageable pageable);
    
    List<Lop> findByDeletedFalse();
    @Query("SELECT l FROM Lop l JOIN l.giaoVien gv WHERE gv.maGV = ?1")
    Page<Lop> findByGiaoVienCN(String maGV, Pageable pageable);
    
    @Query("SELECT l FROM GiaoVien gv JOIN gv.lops l WHERE gv.maGV = ?1")
    List<Lop> findByGiaoVien(String maGV);
    
    @Query("SELECT l FROM Lop l JOIN l.giaoVien gv WHERE gv.maGV = ?1")
    List<Lop> findByGiaoVienCN(String maGV);
    
    Long countByDeletedFalse();
   
}
