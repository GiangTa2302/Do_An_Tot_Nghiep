package doan.com.vn.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper=false)
public class Person extends Base {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "ho_dem", columnDefinition = "NVARCHAR(200)")
    private String hodem;
    
    @Column(length = 100, columnDefinition = "NVARCHAR(200)")
    private String ten;
    
    @Column(name = "ngay_sinh")
    private Date ngaySinh;
    
    @Column(name = "gioi_tinh")
    private boolean gioiTinh;
    
    @Column(name = "tinh", columnDefinition = "NVARCHAR(200)")
    private String tinh;
    
    @Column(name = "quan", columnDefinition = "NVARCHAR(200)")
    private String quan;
    
    @Column(name = "phuong", columnDefinition = "NVARCHAR(200)")
    private String phuong;
}
