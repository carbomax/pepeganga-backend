package uy.com.pepeganga.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import uy.com.pepeganga.userservice.service.system_configuration.IConfigurationsSystemService;

@Component
public class InitSystemConfig  implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    IConfigurationsSystemService configSevice;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        configSevice.createJsonFile();
    }

}