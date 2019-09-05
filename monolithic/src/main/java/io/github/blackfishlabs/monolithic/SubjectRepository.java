package io.github.blackfishlabs.monolithic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @RestResource(path = "/start/after/current")
    @Query("select d from Subject d where d.start > CURRENT_DATE")
    List<Subject> findByStartDateAfterCurrent();

}
