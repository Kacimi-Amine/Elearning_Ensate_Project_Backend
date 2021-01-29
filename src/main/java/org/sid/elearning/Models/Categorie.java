package org.sid.elearning.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Categorie   extends AuditModel implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String nom;
    private String url;

   /* @Column(name = "created_at", nullable = true, updatable = false)
    @CreatedDate
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = true)
    @LastModifiedDate
    private Date updatedAt; */

    @OneToMany(cascade = {
            CascadeType.ALL
    })
    private List<Article> Articles;

    /*@OneToMany(mappedBy = "cat")
    private Collection<Article> Articles ;*/
}
