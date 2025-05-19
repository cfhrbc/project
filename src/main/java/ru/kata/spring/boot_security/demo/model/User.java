package ru.kata.spring.boot_security.demo.model;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @Fetch(FetchMode.JOIN)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )



    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<House> houses = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id", nullable = false)
    private Work work;

    @ManyToMany
    @JoinTable(
            name = "user_educations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "education_id")
    )
    private Set<Education> educations = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialMedia> socialMedias = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Family> families = new ArrayList<>();

    public User(String name, String password, String surname, String email, int age) {
        this.name = name;
        this.password = password;
        this.surname = surname;
        this.email = email;
        this.age = age;
    }

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
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Set<Education> getEducations() {
        return educations;
    }

    public void setEducations(Set<Education> educations) {
        this.educations = educations;
    }

    public List<SocialMedia> getSocialMedias() {
        return socialMedias;
    }

    public void setSocialMedias(List<SocialMedia> socialMedias) {
        this.socialMedias = socialMedias;
    }

    public List<Family> getFamilies() {
        return families;
    }

    public void setFamilies(List<Family> families) {
        this.families = families;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var roles = getRoles();
        var authorities = new ArrayList<SimpleGrantedAuthority>();
        for (var role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var user = (User) o;
        return id == user.id && age == user.age && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, surname, email, age, roles);
    }
}


