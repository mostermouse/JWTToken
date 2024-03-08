package com.jwt.jwttoken;

import com.jwt.jwttoken.Service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;

@SpringBootTest
class JwtTokenApplicationTests {
    @Autowired
    public JwtService jwtService;

    @Test
    void contextLoads() {
    }


    @Test
    void tokenCreate(){
        var claims = new HashMap<String , Object>();
        claims.put("user_id" , 923);

        var expireAt = LocalDateTime.now().plusMinutes(10);
        //var exprieAt = LocalDateTime.now().plusSeconds(20);
        var jwtToken = jwtService.create(claims,expireAt);
        System.out.println(jwtToken);
    }



    @Test
    void tokenValidation(){
        var token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo5MjMsImV4cCI6MTcwOTg2NDM5MX0.CIoFyWil4TYX47qO1x7kFVYrziXm6qpiKSG2SEOLnfw";
        jwtService.validation(token);
    }
}
