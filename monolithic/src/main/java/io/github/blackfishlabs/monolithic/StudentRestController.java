package io.github.blackfishlabs.monolithic;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentRestController {

    @Autowired
    private StudentRepository studentRepository;

    @PostConstruct
    public void init() {
        studentRepository.save(new Student(1l, "John", 11111, "john@john.com", new Date()));
        studentRepository.save(new Student(2l, "Steve", 22222, "steve.stevent@st.com", new Date()));
        studentRepository.save(new Student(3l, "Mary", 33333, "mary@robinson.com", new Date()));
        studentRepository.save(new Student(4l, "Kate", 44444,"kate@kate.com", new Date()));
        studentRepository.save(new Student(5l, "Diana", 55555,"diana@doll.com", new Date()));
    }

    @ApiOperation("Retorna a lista de alunos")
    @Secured("ROLE_USER")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Student>> getAll() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    @ApiOperation("Retorna as informações do aluno pelo id")
    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        if (studentRepository.existsById(id)) {
            return new ResponseEntity<>(studentRepository.getOne(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Cria um novo aluno")
    @Secured("ROLE_MANAGER")
    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
    }

    @ApiOperation("Atualiza as informações do aluno pelo id")
    @Secured("ROLE_MANAGER")
    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student student) {
        if (studentRepository.existsById(id)) {
            return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @ApiOperation("Remove um determinado aluno pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.delete(studentRepository.getOne(id));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Student>> findByNome(@PathVariable String name) {
        return new ResponseEntity<>(studentRepository.findByNameContaining(name), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/birth/month")
    public ResponseEntity<List<Student>> findByBirthDateAtMonth() {
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return new ResponseEntity<>(studentRepository.findByBirthDateAtMonth(month), HttpStatus.OK);
    }
}
