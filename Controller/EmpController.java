package com.example.Employee.Details.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Employee.Details.Entity.EmpEntity;
import com.example.Employee.Details.Repository.EmpRepository;
import com.example.Employee.Details.Service.EmpService;

@Controller
public class EmpController {

    @Autowired
    private EmpRepository repo;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/Register")
    public String showRegisterForm() {
        return "Register";
    }

    @GetMapping("/UserDashboard")
    public String showEmployeeForm() {
        return "UserDashboard";
    }

    @GetMapping("/Admin")
    public String showAdminForm() {
        return "Admin";
    }

    @GetMapping("/NewAdminRegister")
    public String showNewAdminRegisterForm() {
        return "NewAdminRegister";
    }

    @PostMapping("/login")
    public String login(EmpEntity Entity, RedirectAttributes redirectAttributes) {
        List<EmpEntity> user = repo.findByUsername(Entity.getUsername());
        for (EmpEntity enti : user) {
            String fullname = enti.getUsername();
            String pass = enti.getPassword();
            String check = enti.getCatagory();

            if (fullname.equals(Entity.getUsername()) && pass.equals(Entity.getPassword()) && check == null) {
                System.out.println("passwords mathched");
                redirectAttributes.addAttribute("username", Entity.getUsername());
                return "redirect:/UserDashboard";
            } else if (fullname.equals(Entity.getUsername()) && pass.equals(Entity.getPassword())) {
                redirectAttributes.addAttribute("username", Entity.getUsername());
                return "redirect:/Admin";
            } else {
                System.out.println("passwords doesnt match");
                return "redirect:/login?error=wrong";
            }
        }
        return "redirect:/login?error=register";
    }

    @PostMapping("/NewAdminRegister")
    public String NewAdminRegister(@ModelAttribute("user") EmpEntity Entity, RedirectAttributes redirectAttributes) {
        System.out.println("enteress........");
        List<EmpEntity> user = repo.findByUsername(Entity.getUsername());
        if (user.isEmpty()) {
            repo.save(Entity);
            return "redirect:/NewAdminRegister";
        }
        redirectAttributes.addAttribute("error", true);
        return "redirect:/";

    }

    @PostMapping("/Register")
    public String register(@ModelAttribute("user") EmpEntity Entity, RedirectAttributes redirectAttributes) {
        System.out.println("enteress........");
        List<EmpEntity> user = repo.findByUsername(Entity.getUsername());
        if (user.isEmpty()) {
            repo.save(Entity);
            return "redirect:/login";
        }
        redirectAttributes.addAttribute("error", true);
        return "redirect:/";

    }

    @GetMapping("/api/user/{username}")
    @ResponseBody
    public EmpEntity getUserDetails(@PathVariable String username) {
        return repo.findByUsername(username).get(0);
    }

    // API endpoint for updating user details
    @PutMapping("/api/user/{username}")
    @ResponseBody
    public String updateUserDetails(@PathVariable String username, @RequestBody EmpEntity updatedUser) {
        List<EmpEntity> users = repo.findByUsername(username);
        if (!users.isEmpty()) {
            EmpEntity user = users.get(0);
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            repo.save(user);
            return "User updated successfully";
        }
        return "User not found";
    }

    // API endpoint for deleting user
    @DeleteMapping("/api/user/{username}")
    @ResponseBody
    public String deleteUser(@PathVariable String username) {
        List<EmpEntity> users = repo.findByUsername(username);
        if (!users.isEmpty()) {
            repo.delete(users.get(0));
            return "User deleted successfully";
        }
        return "User not found";
    }

    @GetMapping("/api/employees")
    @ResponseBody
    public List<EmpEntity> getAllEmployees() {
        return repo.findAll();
    }

    @GetMapping("/api/employee/{id}")
    @ResponseBody
    public Optional<EmpEntity> getEmployeeById(@PathVariable Long id) {
        return repo.findById(id);
    }

    @PutMapping("/api/employee/{id}")
    @ResponseBody
    public String updateEmployee(@PathVariable Long id, @RequestBody EmpEntity updatedEmployee) {
        Optional<EmpEntity> optionalEmployee = repo.findById(id);
        if (optionalEmployee.isPresent()) {
            EmpEntity employee = optionalEmployee.get();
            employee.setName(updatedEmployee.getName());
            employee.setRole(updatedEmployee.getRole());
            employee.setSalary(updatedEmployee.getSalary());
            employee.setContactNumber(updatedEmployee.getContactNumber());
            employee.setEmail(updatedEmployee.getEmail());
            employee.setUsername(updatedEmployee.getUsername());
            repo.save(employee);
            return "Employee updated successfully";
        }
        return "Employee not found";
    }

    @DeleteMapping("/api/employee/{id}")
    @ResponseBody
    public String deleteEmployee(@PathVariable Long id) {
        Optional<EmpEntity> optionalEmployee = repo.findById(id);
        if (optionalEmployee.isPresent()) {
            repo.delete(optionalEmployee.get());
            return "Employee deleted successfully";
        }
        return "Employee not found";
    }

}