package org.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "login")
@ToString
public class Login {
    private String id;
    private String token;
    private Date instant;
    @ToString.Exclude
    private User user;

    public Login(String token, Date instant, User user) {
        this.id = UUID.randomUUID().toString();
        this.token = token;
        this.instant = instant;
        this.user = user;
    }

    public Login(String token, User user) {
        this.id = UUID.randomUUID().toString();
        this.token = token;
        this.instant = Date.from(Instant.now());
        this.user = user;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotBlank(message = "El token no puede estar vacío")
    @Column(length = 512)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NotNull(message = "El instante no puede ser nula")
    public Date getInstant() {
        return instant;
    }

    public void setInstant(Date instance) {
        this.instant = instance;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "El usuario no puede estar nulo")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
