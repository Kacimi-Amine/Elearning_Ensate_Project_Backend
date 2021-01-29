package org.sid.elearning.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor

public class Classe extends AuditModel implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String intitulerclass;


    @ManyToMany(fetch = FetchType.EAGER , cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "classe_join_module",
               joinColumns = @JoinColumn(name = "classe_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id"))
    private List<Module> module;
    /*public void addmodule(Module mod) {
                module.add(mod);
        //mod.get().add(this);
    } */
}
