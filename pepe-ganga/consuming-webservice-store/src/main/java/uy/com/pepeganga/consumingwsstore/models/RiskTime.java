package uy.com.pepeganga.consumingwsstore.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RiskTime {
    @Value("${risk_time}")
    private Integer riskTime;

    public Integer getRiskTime() {
        return riskTime;
    }

    public void setRiskTime(Integer riskTime) {
        this.riskTime = riskTime;
    }
}
