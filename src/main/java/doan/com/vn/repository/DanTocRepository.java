package doan.com.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.DanToc;

@Repository
public interface DanTocRepository extends JpaRepository<DanToc, Integer>{

}
