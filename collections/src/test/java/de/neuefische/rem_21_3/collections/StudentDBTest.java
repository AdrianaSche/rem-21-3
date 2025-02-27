package de.neuefische.rem_21_3.collections;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

class StudentDBTest {

    @Test
    public void testSetupDBWithoutAnyStudents() {
        // GIVEN
        // init db with empty array
        List<Student> students = Collections.emptyList();
        StudentDB studentDB = new StudentDB(students);

        // WHEN
        int actualAmountOfStudents = studentDB.list().size();

        // THEN
        int expectedAMountOfStudentsInDB = 0;
        assertEquals(expectedAMountOfStudentsInDB, actualAmountOfStudents);
    }

    @Test
    public void testSetupDBWithStudentsLength() {
        // GIVEN
        // init db with non empty array
        Student studentKlaus = new Student("Klaus", 1);
        Student studentMarie = new Student("Marie", 2);
        List<Student> students = Arrays.asList(studentKlaus, studentMarie);
        StudentDB studentDB = new StudentDB(students);

        // WHEN
        int actualAmountOfStudents = studentDB.list().size();

        // THEN
        int expectedAMountOfStudentsInDB = 2;
        assertEquals(expectedAMountOfStudentsInDB, actualAmountOfStudents);
    }

    @Test
    public void testSetupDBWithStudentsEquals() {
        // GIVEN
        List<Student> dbStudents = Arrays.asList(new Student("Klaus",1), new Student("Marie",2));
        StudentDB studentDB = new StudentDB(dbStudents);

        // WHEN
        List<Student> actualStudents = studentDB.list();

        // THEN
        List<Student> expectedStudents = Arrays.asList(new Student("Klaus",1), new Student("Marie",2));
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    public void testStudentDBWithStudentsToString() {
        // GIVEN
        Student studentKlaus = new Student("Klaus",1);
        Student studentMarie = new Student("Marie",2);
        List<Student> students = Arrays.asList(studentKlaus, studentMarie);
        StudentDB studentDB = new StudentDB(students);

        // WHEN
        String actualStudentDBString = studentDB.toString();

        // THEN
        String expectedStudentString = "[id=1, name=Klaus],[id=2, name=Marie]";
        assertEquals(expectedStudentString, actualStudentDBString);
    }

    @Test
    public void testRandomStudent() {
        // GIVEN
        Student studentKlaus = new Student("Klaus",1);
        Student studentMarie = new Student("Marie",2);
        Student studentJohn = new Student("John",3);
        List<Student> students = Arrays.asList(studentKlaus, studentMarie, studentJohn);
        StudentDB studentDB = new StudentDB(students);

        boolean klausSelected = false;
        boolean marieSelected = false;
        boolean johnSelected = false;

        // WHEN - repeated calls to random student to prove that any of the students will be selected at least once
        int maxLoops = 100;

        int i = maxLoops;
        while (!klausSelected || !marieSelected || !johnSelected) {
            Student actualRandomStudent = studentDB.getRandomStudent();

            // THEN
            assertNotNull(actualRandomStudent);
            if (!klausSelected) {
                klausSelected = studentKlaus.equals(actualRandomStudent);
            }
            if (!marieSelected) {
                marieSelected = studentMarie.equals(actualRandomStudent);
            }
            if (!johnSelected) {
                johnSelected = studentJohn.equals(actualRandomStudent);
            }

            --i;

            if (i == 0) {
                // if students where not randomly selected the "while" will loop infinitely, add additional break
                fail("Some students not randomly selected within " + maxLoops + " invocations");
            }
        }

        // THEN - At least any of the students should be randomly selected
    }

    @Test
    public void testAddStudent() {
        // GIVEN
        Student studentKlaus = new Student("Klaus",1);
        Student studentMarie = new Student("Marie",2);
        Student studentJohn = new Student("John",3);
        List<Student> students = Arrays.asList(studentKlaus, studentMarie, studentJohn);
        StudentDB studentDB = new StudentDB(students);

        // WHEN
        Student studentKlara = new Student("Klara",4);
        studentDB.add(studentKlara);
        List<Student> actual = studentDB.list();

        // THEN
        List<Student> expected = Arrays.asList(studentKlaus, studentMarie, studentJohn, studentKlara);
        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveStudent() {
        // GIVEN
        Student studentKlaus = new Student("Klaus",1);
        Student studentMarie = new Student("Marie",2);
        Student studentJohn = new Student("John",3);
        Student studentKlara = new Student("Klara",4);
        List<Student> students = Arrays.asList(studentKlaus, studentMarie, studentJohn, studentKlara);
        StudentDB studentDB = new StudentDB(students);

        // WHEN
        studentDB.remove(studentMarie);
        List<Student> actual = studentDB.list();

        // THEN
        List<Student> expected = Arrays.asList(studentKlaus, studentJohn, studentKlara);
        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveUnknownStudent() {
        // GIVEN
        Student studentKlaus = new Student("Klaus",1);
        Student studentMarie = new Student("Marie",2);
        Student studentJohn = new Student("John",3);
        Student studentKlara = new Student("Klara",4);
        List<Student> students = Arrays.asList(studentKlaus, studentMarie, studentJohn, studentKlara);
        StudentDB studentDB = new StudentDB(students);

        // WHEN
        Student studentUnknown = new Student("Unknown",5);
        studentDB.remove(studentUnknown);
        List<Student> actual = studentDB.list();

        // THEN
        List<Student> expected = Arrays.asList(studentKlaus, studentMarie, studentJohn, studentKlara);
        assertEquals(expected, actual);
    }
}
