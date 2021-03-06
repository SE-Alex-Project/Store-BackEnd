package Software.storeBackEnd.entities;

import net.minidev.json.JSONObject;

public class Employee {
    String firstname, lastname, hashedPassword, email, storeId, role, salary;

    /*{
       "email":user email
       "firstName": user first name
       "lastName": user last name
       "password": user hashed password
       "store":"storeId"
       "erole":role
       "salary":salary
   }*/
    public Employee(JSONObject employee) {
        this.email = employee.getAsString("email");
        this.firstname = employee.getAsString("firstName");
        this.lastname = employee.getAsString("lastName");
        this.hashedPassword = employee.getAsString("password").hashCode() + "";
        this.storeId = employee.getAsString("store");
        this.role = employee.getAsString("erole");
        this.salary = employee.getAsString("salary");
    }
    
    public Employee() {
		super();
	}
    

	public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getStoreId() {
        return storeId;
    }
    
    public String getRole() {
		return role;
	}

	public String getSalary() {
		return salary;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}
    
}
