
  
  
  package com.pfe.ClientRest;
  
  import org.springframework.beans.factory.annotation.Autowired; import
  org.springframework.context.annotation.Configuration; import
  org.springframework.core.annotation.Order; import
  org.springframework.security.config.annotation.authentication.builders.
  AuthenticationManagerBuilder; import
  org.springframework.security.config.annotation.web.builders.HttpSecurity;
  import org.springframework.security.config.annotation.web.configuration.
  EnableWebSecurity; import
  org.springframework.security.config.annotation.web.configuration.
  WebSecurityConfigurerAdapter;
  
  @Configuration
  
  @EnableWebSecurity
  
  public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Override protected void configure(HttpSecurity http) throws Exception {
  
  http.authorizeRequests() 
  .antMatchers("/resources/static/welcomecss.css",
  "/ressources/static/welcomeNav.css").permitAll()
  .antMatchers("/welcome","/uploadFile").authenticated()
  .antMatchers("/updateList","/edit/{id}").hasRole("VALIDATOR")
  .antMatchers("/uploadOneFile","/getTlAdj1","/CiomcHtml","/getcross2","/getTlAdj2","/CiomcPdf","/getcrossed").hasRole("DEMANDER")
  .antMatchers("/ListFile").hasAnyRole("VALIDATOR","DEMANDER")
  .and() .formLogin() .loginPage("/index") .successForwardUrl("/welcome")
  .permitAll() .and() .logout() .permitAll()
  .and()
  .exceptionHandling().accessDeniedPage("/403")
  .and()
  .csrf().disable();
  
  
  
  }
  
  @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
  throws Exception { auth.inMemoryAuthentication()
  .withUser("userD").password("{noop}password").roles("DEMANDER")
  .and()
  .withUser("userV").password("{noop}123456").roles("VALIDATOR");}
  
  }
 
  
  
 
 