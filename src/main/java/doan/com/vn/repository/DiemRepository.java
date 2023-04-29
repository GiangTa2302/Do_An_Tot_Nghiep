package doan.com.vn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.Diem;
import doan.com.vn.entity.DiemPK;

@Repository
public interface DiemRepository extends JpaRepository<Diem, DiemPK>{
    @Query(value = "select * from diem where ma_hs = ?1 and hoc_ky = ?2", nativeQuery = true)
    List<Diem> findByHocSinhAndHocKy(String maHS, Integer hocKy);
    
    @Query(value = "select d.* from diem d join hoc_sinh hs "
            + "on d.ma_hs = hs.ma_hs "
            + "where hs.ten_lop = ?1 and d.ma_mon = ?2 and d.hoc_ky = ?3", nativeQuery = true)
    List<Diem> findByLopAndMon(String tenLop, String maMon, Integer hocKy);
    
    
}
