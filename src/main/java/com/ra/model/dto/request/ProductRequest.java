package com.ra.model.dto.request;

import com.ra.model.entity.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductRequest {
    @NotBlank(message = "ProductName không được trống")
    private String name;
    private String description;
    @NotNull(message = "price không được trống")
    private Double price;
    @Min(value = 1, message = "quantity không được trống")
    private int quantity;
    @NotBlank(message = "image không được trống")
    private String image;
    private Category category;
}
