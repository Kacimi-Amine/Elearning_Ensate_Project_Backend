package org.sid.elearning.Repositories;

import org.sid.elearning.Models.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("*")
public interface FolderRepository extends JpaRepository<Folder,Long> {
}
