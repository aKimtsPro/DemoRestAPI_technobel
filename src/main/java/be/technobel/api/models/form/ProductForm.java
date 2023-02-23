package be.technobel.api.models.form;

import be.technobel.api.models.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductForm {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @Positive
    private double price;

    public Product toEntity(){
        Product product = new Product();

        product.setBrand(brand);
        product.setModel(model);
        product.setPrice(price);

        return product;
    }

}
