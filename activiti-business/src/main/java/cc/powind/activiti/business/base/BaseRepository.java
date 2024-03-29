package cc.powind.activiti.business.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseRepository <T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
