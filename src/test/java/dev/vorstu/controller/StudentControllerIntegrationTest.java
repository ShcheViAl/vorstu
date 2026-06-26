package dev.vorstu.controller;

import dev.vorstu.entity.Student;
import dev.vorstu.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp(){
        studentRepository.deleteAll();
    }

    @Test
    void getAllStudentsTest() throws Exception{
        Student student = Student.builder()
                .fio("Иванов Иван")
                .phoneNumber("+7888")
                .group("ИФСТ-21")
                .build();
        studentRepository.save(student);

        mockMvc.perform(get("/api/students")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].fio", is("Иванов Иван")));
    }

    @Test
    void createStudentTest() throws Exception {
        String requestJson = """
                {
                    "fio": "Петров Петр Петрович",
                    "group": "АБВ23",
                    "phoneNumber": "+788167"
                }
                """;

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.fio", is("Петров Петр Петрович")));
    }

    @Test
    void changeStudentTest() throws Exception {
        Student student = Student.builder()
                .fio("Старое Имя")
                .group("Старая Группа")
                .phoneNumber("000000000")
                .build();
        student = studentRepository.save(student);

        String requestJson = """
                {
                    "fio": "Новое Имя",
                    "group": "Новая Группа",
                    "phoneNumber": "111111111"
                }
                """;

        mockMvc.perform(put("/api/students/" + student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fio", is("Новое Имя")))
                .andExpect(jsonPath("$.group", is("Новая Группа")));
    }

    @Test
    void deleteStudentTest() throws Exception {
        Student student = Student.builder()
                .fio("Студент для удаления")
                .group("УДАЛИТЬ")
                .phoneNumber("666666")
                .build();
        student = studentRepository.save(student);
        Long id = student.getId();

        mockMvc.perform(delete("/api/students/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fio", is("Студент для удаления")));

        assertFalse(studentRepository.findById(id).isPresent());
    }
}
