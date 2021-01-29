package org.sid.elearning.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Article  extends AuditModel implements Serializable  {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom_article;
    //@Column(columnDefinition = "text")
    @Lob
    private String description;
    private Boolean status;



   /* @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    //@JoinColumn(name = "categorie_id")
    @JsonBackReference
    private Categorie cat; */

}
