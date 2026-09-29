package com.eden.studentsys222;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import com.eden.service.StudentService;
import com.eden.service.AuthService;
import com.eden.entity.Student;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:ormtest;MODE=MySQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class Studentsys222ApplicationTests {
    @Autowired StudentService students;
    @Autowired AuthService auth;

    @Test
    void contextLoads() {
    }

    @Test
    void studentLifecycle() {
        Student first = new Student();
        first.setName("Alice");
        first.setAvatar("/uploads/alice.png");
        assertTrue(students.add(first));
        Student second = new Student();
        second.setName("Bob");
        assertTrue(students.add(second));
        assertEquals(2, students.count());
        assertEquals(second.getId(), students.list().get(0).getId());
        assertEquals(first.getId(), students.page(2, 1).get(0).getId());
        assertEquals(first.getId(), students.search("lic").get(0).getId());
        Student edit = new Student();
        edit.setId(first.getId());
        edit.setName("Alice updated");
        assertTrue(students.update(edit));
        assertEquals("Alice updated", students.getById(first.getId()).getName());
        assertEquals("/uploads/alice.png", students.getById(first.getId()).getAvatar());
        edit.setId(Long.MAX_VALUE);
        assertFalse(students.update(edit));
        assertTrue(students.delete(second.getId()));
        assertFalse(students.delete(second.getId()));
        assertFalse(students.batchDelete(List.of()));
        assertTrue(students.batchDelete(List.of(first.getId(), Long.MAX_VALUE)));
        assertEquals(0, students.count());
    }

    @Test
    void registrationAndLogin() {
        assertTrue(auth.register("orm-user", "secret"));
        assertFalse(auth.register("orm-user", "other"));
        assertEquals("USER", auth.login("orm-user", "secret").getRole());
        assertNull(auth.login("orm-user", "wrong"));
        assertNull(auth.login("missing", "secret"));
    }

}
