package com.management.webservice.dto;

import jakarta.validation.constraints.Email;

public record PasswordResetRequest(@Email String email) {

}
