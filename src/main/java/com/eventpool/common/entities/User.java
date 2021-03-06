package com.eventpool.common.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;

import com.eventpool.common.type.Gender;

@Entity
@Table(name="USER", uniqueConstraints = { @UniqueConstraint (columnNames = "EMAIL")})
public class User extends IdEntity{
	
	private static final String SALT = "cewuiqwzie"; // This is required for password change through Spring security
	
	public User(){
		
	}
	
	public User(String email, String password,String fname,String lname) {
	    // By default account enabled should be false, once the user clicks on confirmation email, this flag should be set
	    // to true
	    accountExpired = false;

	    this.email = email;
	    this.password = encode(password);
	    this.fname = fname;
	    this.lname = lname;
	    this.userName = fname +" "+lname;
	    this.memberShipExp = new Date();
	    this.memberShipType = 1;
	  }
	 public String encode(String password) {
		    return new Md5PasswordEncoder().encodePassword(password, SALT);
	 }
	 public void setTemporaryPassword(String password) {
		  this.password = encode(password);
	  }
	 public Boolean Authenticate(String password) {
		    if (this.password.equals(encode(password))) {
		      return true;
		    }
		    System.out.println("Original password is: " + this.password);
		    System.out.println("Password entered  is: " + encode(password));
		    return false;
		  }
	 public void updatePassword( String newPass1) {
		    password = encode(newPass1);
	}

	
	@Column(name = "FNAME")
	private String fname;
	
	@Column(name = "ACCOUNT_ENABLED")
	  boolean accountEnabled =true; // This would depend upon business requirements

	@Column(name = "ACCOUNT_EXPIRED")
	  boolean accountExpired = false;
	
	@Column(name = "PREFIX")
	private String prefix;
	
	@Column(name = "SUFFIX")
	private String suffix;
	 
	@Column(name = "LNAME")
	private String lname;
	
	@Column(name = "USERNAME")
	private String userName;
	
	@Column(name="PASSWORD")
	@NotNull
	private String password;

	
	@Column(name = "EMAIL")
	private String email;

	@Column(name = "COMPANY")
	private String company;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "WORK_PHONE")
	private String workPhone;

	@Column(name = "BLOG")
	private String blog;
	
	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "ALT_EMAIL")
	private String altEmail;
	
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(referencedColumnName="ID",name= "HOME_ADDRESS")
	private Address homeAddress;

	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(referencedColumnName= "ID",name="OFFICE_ADDRESS")
	private Address officeAddress;

	@Column(name = "GENDER")
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "COMPANY_URL")
	private String companyUrl;

	@Column(name = "MEMBERSHIP_TYPE")
	private Integer memberShipType = 1;
	
	@Column(name = "MEMBERSHIP_EXP")
	private Date memberShipExp;

	@Column(name = "TOTAL_POINTS")
	private Integer totalPoints;

	
/*	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "MEMBERSHIP_TYPE", insertable=false,updatable=false)
	private MemberShip memberShip;
*/	
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate = new Date();

	@Column(name = "MODIFIED_DATE", nullable = false)
	private Date modifiedDate = new Date();

	@Column(name = "JOB_TITLE")
	private String jobtitle;

	
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	
		
	  public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}



	public enum Roles implements GrantedAuthority {
		    ROLE_USER, ROLE_ADMIN;
		    public String getAuthority() {
		      return name();
		    }
		  }
	  
	  @Transient
	  private Roles[] roles = new Roles[]{Roles.ROLE_USER}; // Roles of this user -- This is required for Spring Security
	  
	  public Roles[] getRoles() {
		    return roles;
		  }

		  public void setRoles(Roles... roles) {
		    this.roles = roles;
		  }
		public boolean isAccountEnabled() {
			return accountEnabled;
		}
		public void setAccountEnabled(boolean accountEnabled) {
			this.accountEnabled = accountEnabled;
		}
		public boolean isAccountExpired() {
			return accountExpired;
		}
		public void setAccountExpired(boolean accountExpired) {
			this.accountExpired = accountExpired;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public String getSuffix() {
			return suffix;
		}

		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}

		public String getWorkPhone() {
			return workPhone;
		}

		public void setWorkPhone(String workPhone) {
			this.workPhone = workPhone;
		}

		public String getBlog() {
			return blog;
		}

		public void setBlog(String blog) {
			this.blog = blog;
		}

		public Integer getMemberShipType() {
			return memberShipType;
		}

		public void setMemberShipType(Integer memberShipType) {
			this.memberShipType = memberShipType;
		}

		public Date getMemberShipExp() {
			return memberShipExp;
		}

		public void setMemberShipExp(Date memberShipExp) {
			this.memberShipExp = memberShipExp;
		}

/*		public MemberShip getMemberShip() {
			return memberShip;
		}

		public void setMemberShip(MemberShip memberShip) {
			this.memberShip = memberShip;
		}
*/
		public Integer getTotalPoints() {
			return totalPoints;
		}

		public void setTotalPoints(Integer totalPoints) {
			this.totalPoints = totalPoints;
		}

		public String getJobtitle() {
			return jobtitle;
		}

		public void setJobtitle(String jobtitle) {
			this.jobtitle = jobtitle;
		}

		public Address getHomeAddress() {
			return homeAddress;
		}

		public void setHomeAddress(Address homeAddress) {
			this.homeAddress = homeAddress;
		}

		public Address getOfficeAddress() {
			return officeAddress;
		}

		public void setOfficeAddress(Address officeAddress) {
			this.officeAddress = officeAddress;
		}
		  
		  
}
