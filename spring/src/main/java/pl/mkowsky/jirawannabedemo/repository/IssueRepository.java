package pl.mkowsky.jirawannabedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mkowsky.jirawannabedemo.model.Issue;

@Repository
@Transactional
public interface IssueRepository extends JpaRepository<Issue, Long> {
}
