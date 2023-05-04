package doan.com.vn.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.HocSinh;

@Repository
public interface HocSinhRepository extends JpaRepository<HocSinh, String>{
    @Query(value = "select * from hoc_sinh where deleted = 'false' and ten_lop = ?1", nativeQuery = true)
    Page<HocSinh> findAllStudentByClass(String tenLop ,Pageable pageable);
    
    @Query(value = "select * from hoc_sinh where deleted = 'false' and ten_lop = ?1", nativeQuery = true)
    List<HocSinh> findAllStudentByClass(String tenLop);
    
    List<HocSinh> findByDeletedFalse();
    
    Long countByDeletedFalse();
}
