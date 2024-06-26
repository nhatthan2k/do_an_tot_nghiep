package com.ra.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRegister {
    @Size(min = 6, max = 100, message = "số kí tự không chính xác")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "không chứa kí tự đặc biệt")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "email bạn chưa đúng định dạng")
    private String email;
    @NotBlank(message = "FullName không được trống")
    private String fullName;
    @NotBlank(message = "Password không được trống")
    private String password;
    private String avatar;
    @Pattern(regexp = "^0[1-9]\\d{8}$", message = "Số điện thoại chưa được định dạng đúng")
    private String phone;
    @NotBlank(message = "Address không được trống")
    private String address;
}
