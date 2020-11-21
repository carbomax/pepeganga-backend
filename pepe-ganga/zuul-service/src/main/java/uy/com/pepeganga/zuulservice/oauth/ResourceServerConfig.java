package uy.com.pepeganga.zuulservice.oauth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import uy.com.pepeganga.business.common.utils.enums.RoleType;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
@EnableResourceServer
@EnableWebSecurity
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStorage());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/pepeganga/security/oauth/token").permitAll()
                .antMatchers(HttpMethod.POST,"/pepeganga/notification/**").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/pepeganga/upload/api/file/**",
                                    "/pepeganga/notification/**").permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/pepeganga/products/**",
                                    "/pepeganga/user/**",
                                    "/pepeganga/consuming/**"
                                    )
                .hasAnyRole(RoleType.ADMIN.name(),RoleType.INVITED.name(), RoleType.SELLER.name())
                .antMatchers("*", "/pepeganga/meli/api/**")
                .hasAnyRole(RoleType.ADMIN.name(),RoleType.SELLER.name())
                .antMatchers(HttpMethod.POST, "/**")
                .hasAnyRole(RoleType.ADMIN.name(),RoleType.INVITED.name(), RoleType.SELLER.name())
                .antMatchers(HttpMethod.PUT, "/**")
                .hasAnyRole(RoleType.ADMIN.name(),RoleType.INVITED.name(), RoleType.SELLER.name())
                .antMatchers(HttpMethod.DELETE, "/**")
                .hasAnyRole(RoleType.ADMIN.name(),RoleType.INVITED.name(), RoleType.SELLER.name())
                .antMatchers(HttpMethod.OPTIONS, "*")
                .hasAnyRole(RoleType.ADMIN.name(),RoleType.INVITED.name(), RoleType.SELLER.name())
                .anyRequest().authenticated()
                .and().cors().configurationSource(corsConfigurationSource());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Arrays.stream(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.toList()));
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource basedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        basedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return basedCorsConfigurationSource;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterBeanConfiguration(){
        FilterRegistrationBean<CorsFilter>  bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
    @Bean
    public JwtTokenStore tokenStorage() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey("secret_key");
        return tokenConverter;
    }
}
