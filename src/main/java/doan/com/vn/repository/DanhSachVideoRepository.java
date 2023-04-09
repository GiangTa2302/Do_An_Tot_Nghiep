package doan.com.vn.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.DanhSachVideo;

@Repository
public interface DanhSachVideoRepository extends JpaRepository<DanhSachVideo, Integer>{
    @Query(value = "select * from danh_sach_video where ma_bg = ?1", nativeQuery = true)
    Page<DanhSachVideo> findByMaBG(Integer maBG, Pageable pageable);
    
    @Query(value = "select * from danh_sach_video where ma_bg = ?1", nativeQuery = true)
    List<DanhSachVideo> findByMaBG(Integer maBG);
}
