package com.security.jwt.controller;


import com.security.jwt.entity.AuthRequest;
import com.security.jwt.entity.UserInfo;
import com.security.jwt.service.JwtService;
import com.security.jwt.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/welcome")
    public  String welcome(){
        return "welcome";
    }
    @GetMapping("/get")
    public String data(){
        return "getData";
    }
    @PostMapping("/add")
    public String add(@RequestBody UserInfo userInfo){
       return userInfoService.adduser(userInfo);
    }
    @PostMapping("/login")
    public  String addUser(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
      if(authenticate.isAuthenticated()){
          return  jwtService.generateToken(authRequest.getName());
      }else{
          throw new UsernameNotFoundException("Invalid UserName");
      }
    }


    @GetMapping("/getUser")
    public List<UserInfo>  getAlluser(){
        return  userInfoService.getAllUser();
    }

    @GetMapping("/getUser/{id}")
    public UserInfo getUser(@PathVariable Long id){
        return userInfoService.getUser(id);
    }
}
