package com.codebrewers.onlinebookstore.service;

import com.codebrewers.onlinebookstore.dto.RegistrationDTO;

public interface IUserService {

    String userRegistration(RegistrationDTO registrationDTO);
}