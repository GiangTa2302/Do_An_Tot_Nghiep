package doan.com.vn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import doan.com.vn.utils.RoleName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "[User]")
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends Base{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    private String account;
    
    @Pattern(regexp = "")
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleName roleName;
}
