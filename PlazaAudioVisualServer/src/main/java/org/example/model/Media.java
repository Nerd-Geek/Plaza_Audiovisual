package org.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "media")
@ToString
public class Media {
    private String id;
    private Double size;
    private String type;
    private String name;
    private String description;
    private int dimension;
    private User user;

    public Media(Double size, String type, String name, String description, int dimension) {
        this.id = UUID.randomUUID().toString();
        this.size = size;
        this.type = type;
        this.name = name;
        this.description = description;
        this.dimension = dimension;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    @NotNull(message = "El usuario no puede estar nulo")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
