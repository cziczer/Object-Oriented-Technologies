package pl.edu.agh.iisg.to.dao;

import java.util.*;

import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;

import javax.persistence.PersistenceException;

public class StudentDao extends GenericDao<Student> {

    public Optional<Student> create(final String firstName, final String lastName, final int indexNumber) {
        try {
            save(new Student(firstName, lastName, indexNumber));
            return findByIndexNumber(indexNumber);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Student> findByIndexNumber(final int indexNumber) {
        try {
            Student student = currentSession().createQuery("SELECT s FROM Student s WHERE s.indexNumber = :indexNumber", Student.class)
                    .setParameter("indexNumber", indexNumber).getSingleResult();
            return Optional.of(student);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Map<Course, Float> createReport(final Student student) {
        Map<Course, Float> report = new HashMap<>();
        Map<Course, List<Float>> courseGrades = new HashMap<>();
        for(Grade grade : student.gradeSet()){
            if(!courseGrades.containsKey(grade.course())) courseGrades.put(grade.course(), new ArrayList<>());
            courseGrades.get(grade.course()).add(grade.grade());
        }
        courseGrades.forEach((course, grades) -> {
            report.put(course, grades.stream().reduce(0.0f, Float::sum)/grades.size());
        });
        return Collections.emptyMap();
    }

}
