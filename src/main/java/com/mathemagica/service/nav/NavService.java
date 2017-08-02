package com.mathemagica.service.nav;

import com.mathemagica.model.nav.Nav;
import com.mathemagica.model.user.User;

import java.util.List;

/**
 * Created by Azael on 2017/08/02.
 */
public interface NavService {
    List<Nav> createNav(User user, String activeMenu);
}
