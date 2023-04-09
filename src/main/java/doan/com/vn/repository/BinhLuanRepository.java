package doan.com.vn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.BinhLuan;

@Repository
public interface BinhLuanRepository extends JpaRepository<BinhLuan, Integer>{
    
    @Query(value = "select count(*) from binh_luan where ma_bg = ?1", nativeQuery = true)
    long countByMaBG(Integer maBG);
    
    @Query(value = "select * from binh_luan where ma_bg = ?1", nativeQuery = true)
    List<BinhLuan> findByMaBG(Integer maBG);
}
