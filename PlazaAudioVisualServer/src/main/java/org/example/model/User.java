package org.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@ToString
public class User implements UserDetails {
    private String id;
    @Column(unique = true)
    private String username;
    private String name;
    private String lastname;
    @Column(unique = true)
    private String email;
    @Column(name = "phone_number")
    private String phonenumber;
    private String password;
    private String image;
    private String description;
    @ToString.Exclude
    @Column(name = "user_roles")
    private Set<UserRol> roles;
    @ToString.Exclude
    @Column(name = "media")
    private Set<Media> medias;
    @ToString.Exclude
    @Column(name = "login")
    private Set<Login> logins;

    public User(String username, String name, String lastName, String email, String phonenumber, String password, String image, String description, Set<UserRol> roles, Set<Media> medias, Set<Login> logins) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.name = name;
        this.lastname = lastName;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.image = image;
        this.description = description;
        this.roles = roles;
        this.medias = medias;
        this.logins = logins;
    }

    @Id
    @NotBlank(message = "El id no puede estar vac??o")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "El primer nombre del usuario no puede estar vac??o")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank(message = "El apelliodo del usuario no puede estar vac??o")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    @Email(regexp = ".*@.*\\..*", message = "Email debe ser un email valido")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min = 9, max = 15)
    @NotBlank(message = "El n??mero de tel??fono no puede estar vac??o")
    public String getPhoneNumber() {
        return phonenumber;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @NotBlank(message = "La password no puede estar vac??a")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    public Set<UserRol> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRol> roles) {
        this.roles = roles;
    }

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE)
    public Set<Media> getMedias() {
        return medias;
    }

    public void setMedias(Set<Media> medias) {
        this.medias = medias;
    }

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE)
    public Set<Login> getLogins() {
        return logins;
    }

    public void setLogins(Set<Login> logins) {
        this.logins = logins;
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.name())).collect(Collectors.toList());
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }
}
