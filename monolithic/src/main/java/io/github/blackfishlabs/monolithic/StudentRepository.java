package io.github.blackfishlabs.monolithic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByNameContaining(String nome);

    @Query("SELECT a FROM Student a WHERE MONTH(a.birth) = :month")
    List<Student> findByBirthDateAtMonth(@Param("month") Integer month);
}
