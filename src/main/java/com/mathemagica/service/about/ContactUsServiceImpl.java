package com.mathemagica.service.about;

import com.mathemagica.model.about.ContactUs;
import com.mathemagica.repository.about.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Azael on 2017/08/03.
 */
@Service("contactUsService")
public class ContactUsServiceImpl implements ContactUsService{
    @Autowired
    private ContactUsRepository contactUsRepository;

    @Override
    public void save(ContactUs contactUs) {
        contactUsRepository.save(contactUs);
    }
}
