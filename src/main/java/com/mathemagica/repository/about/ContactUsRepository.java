package com.mathemagica.repository.about;

import com.mathemagica.model.about.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Azael on 2017/08/03.
 */
@Repository("contactUsRepository")
public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {
}
