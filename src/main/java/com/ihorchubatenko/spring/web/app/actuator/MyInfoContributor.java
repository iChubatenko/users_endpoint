package com.ihorchubatenko.spring.web.app.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class MyInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("name", "Users Endpoint");
        builder.withDetail("version", "1.0");
    }
}
