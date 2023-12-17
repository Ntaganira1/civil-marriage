package com.civilMarriageSystem.Util;

import com.civilMarriageSystem.Config.SecurityConfiguration;
import com.civilMarriageSystem.Domain.User;
import com.civilMarriageSystem.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

    @Component
    public class Initializer {
        private final Logger Log = LoggerFactory.getLogger(Initializer.class);

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private SecurityConfiguration enconder;
        @PostConstruct
        public void init() {
            initAdminCreation();
            System.out.println("-----------> Admin Initialization is Successful");
        }
        private void initAdminCreation() {
            User user = new User(0,true,enconder.getPasswordEncoder().encode("admin"),"ROLE_ADMIN","admin@admin.com","Admin","",null,null);
            try {
                    userRepository.save(user);
            } catch (Exception e) {
            }
        }
}
