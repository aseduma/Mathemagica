package com.mathemagica.repository.nav;

import com.mathemagica.model.nav.Nav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Azael on 2017/08/02.
 */
@Repository("navRepository")
public interface NavRepository extends JpaRepository<Nav, Long> {
    List<Nav> findAllByEnabled(Boolean enabled);
}
