package doan.com.vn.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public class Base implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "created_date")
    private Date createdDate;
    
    @Column(name = "deleted_date")
    private Date deletedDate;
    
    private boolean deleted;
}
