package org.sid.elearning.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.elearning.Models.Cour;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Folder extends AuditModel implements Serializable {
@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    private String contentType;

    private String url;

    public Folder(String fileName, String contentType, String url) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.url = url;
    }
}
