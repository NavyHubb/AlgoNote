package com.ssafy.algonote.problem.repository;

import com.ssafy.algonote.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long>{

}
