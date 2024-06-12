package idusw.springboot.egyblog.repository;

import idusw.springboot.egyblog.entity.BlogEntity;
import idusw.springboot.egyblog.model.BlogDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    Optional<BlogEntity> findByIdx(BlogDto dto);
}
