package com.mathemagica.service.nav;

import com.mathemagica.model.nav.Nav;
import com.mathemagica.model.user.User;
import com.mathemagica.model.user.UserRole;
import com.mathemagica.repository.nav.NavRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Azael on 2017/08/02.
 */
@Service("navService")
public class NavServiceImpl implements NavService{

    @Autowired
    private NavRepository navRepository;

    @Override
    public List<Nav> createNav(User user, String activeMenu) {
        List<Nav> navs = new ArrayList<>();

        List<Nav> navList = navRepository.findAllByEnabled(true);
        navs.addAll(navList.stream().filter(nav -> nav.getRole().getRole().equals("ANONYMOUS")).collect(Collectors.toList()));

        if(user != null) {
            for (Nav nav : navList) {
                for(UserRole userRole : user.getUserRoles()){
                    if(userRole.getRole().getRole().equals(nav.getRole().getRole())){
                        navs.add(nav);
                        break;
                    }
                }
            }
        }

        for (Nav nav : navs) {
            if(nav.getName().equals(activeMenu)){
                nav.setCssClass("active");
            } else if (nav.getCssClass().equals("active")) {
                nav.setCssClass(nav.getHref().equals("#") ? "parent" : "");
            }
        }

        return navs;
    }
}
