package com.codebrewers.onlinebookstore.controller;

import com.codebrewers.onlinebookstore.dto.LoginDTO;
import com.codebrewers.onlinebookstore.dto.RegistrationDTO;
import com.codebrewers.onlinebookstore.dto.ResponseDTO;
import com.codebrewers.onlinebookstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@CrossOrigin(exposedHeaders = "Authorization")
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> userRegistration(@Valid @RequestBody RegistrationDTO registrationDTO, BindingResult bindingResult, HttpServletRequest request) throws MessagingException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        String message = userService.userRegistration(registrationDTO,request.getHeader("Referer"));
        ResponseDTO responseDTO = new ResponseDTO(message);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity userLogin(@Valid @RequestBody LoginDTO logInDTO, BindingResult bindingResult, HttpServletResponse response){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        String userLogin = userService.userLogin(logInDTO);
        response.setHeader("Authorization",userLogin);
        return new ResponseEntity("LOGIN SUCCESSFUL", HttpStatus.OK);
    }

    @PostMapping("/forget/password")
    public ResponseEntity getResetPassword(@RequestParam("emailID") String emailID,HttpServletRequest httpServletRequest) throws MessagingException {
        String resetPassword = userService.resetPasswordLink(emailID,httpServletRequest.getHeader("Referer"));
        ResponseDTO response = new ResponseDTO(resetPassword);
        return new ResponseEntity(response,HttpStatus.OK);
    }

    @PostMapping("/confirm/password/")
    public ResponseEntity resetPassword(@RequestParam(name = "password") String password,@RequestParam(value = "token",defaultValue = "") String urlToken){
        String resetPassword = userService.resetPassword(password,urlToken);
        ResponseDTO response = new ResponseDTO(resetPassword);
        return new ResponseEntity(response,HttpStatus.OK);
    }

    @PostMapping("/resend/email/{emailID}")
    public ResponseEntity<ResponseDTO> resendMail(@PathVariable(name = "emailID")String emailID,HttpServletRequest request) throws MessagingException {
        String resentMail = userService.sendVerificationMail(emailID,request.getHeader("Referer"));
        ResponseDTO response = new ResponseDTO(resentMail);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/verify/mail")
    public ResponseEntity verifyEmail(@RequestParam(name="token",defaultValue = "") String token){
        String verifyEmail = userService.verifyEmail(token);
        return new ResponseEntity(verifyEmail,HttpStatus.OK);
    }

}
