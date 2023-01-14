package com.tpe.repository;

import com.tpe.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long > {
    boolean existsByEmail(String email);
    // Spring Data JPA içinde existById() var fakat Spring Data JPA bize sondaki eki istediğimiz değişken ismi ile
    //değiştirmemize izin veriyor, mevcut metodu bu şekilde türetebiliyoruz.
}
