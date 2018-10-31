package com.cac.CamEmotion.model;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * 
 * 类说明: 示例代码
 * <P>
 *     用于基本框架演示dao相关的数据库操作和表单校验功能
 * </P>
 *
 * @author zhangsh
 * @data 2017年1月18日
 */
public class Sample {
    private Long id;

    @NotBlank(message="{valid.name}")
    private String name;

    @Length(min=4, max=20, message="{valid.password}")
    private String password;

    @NotBlank(message="{valid.required}")
    @Email(message="{valid.email}")
    private String email;

    @NotNull(message="{valid.required}")
    private boolean married;
    
    @Min(value=18, message="{valid.ageMin}")
    @Max(value=100, message="{valid.ageMax}")
    private Integer age;

    @NotNull(message="{valid.required}")
    @Past(message="{valid.birthday}")
    private Date birthday;

    @Pattern(regexp="^[a-zA-Z]{2,}$", message="{valid.address}")
    private String address;
    
    @Size(min=1, message="{valid.likesMin}")
    private String[] likes;

    @com.cac.CamEmotion.validator.Tel(message="{valid.tel}", min=3)
    private String tel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

	public boolean isMarried() {
		return married;
	}

	public void setMarried(boolean married) {
		this.married = married;
	}

	public String[] getLikes() {
		return likes;
	}

	public void setLikes(String[] likes) {
		this.likes = likes;
	}
}