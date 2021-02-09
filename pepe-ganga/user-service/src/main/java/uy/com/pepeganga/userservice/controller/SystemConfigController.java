package uy.com.pepeganga.userservice.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.userservice.models.system_config.SystemConfig;
import uy.com.pepeganga.userservice.service.system_configuration.IConfigurationsSystemService;

@RestController
@RequestMapping("/api/systemConfig")
public class SystemConfigController {

    @Autowired
    private IConfigurationsSystemService configService;

    @GetMapping("/create")
    public boolean createFile() {
        return configService.createJsonFile();
    }

    @PutMapping("/update")
    public JSONObject updateAttributesFromFile(@RequestBody SystemConfig model) {
        return configService.updateAttributesFromJsonFile(model);
    }

    @GetMapping
    public JSONObject readAllJsonFile() {
        return configService.readAllJsonFile();
    }

}
