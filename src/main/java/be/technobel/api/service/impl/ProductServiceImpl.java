package be.technobel.api.service.impl;

import be.technobel.api.models.dto.ProductDTO;
import be.technobel.api.models.entity.Product;
import be.technobel.api.models.form.ProductForm;
import be.technobel.api.repository.ProductRepository;
import be.technobel.api.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream()
                .map( ProductDTO::toDto )
                .toList();
    }

    @Override
    public void create(ProductForm form) {
        productRepository.save( form.toEntity() );
    }

    @Override
    public void update(Long id, ProductForm form) {
        Product product = productRepository.findById(id)
                .orElseThrow(); // TODO préciser

        product.setBrand( form.getBrand() );
        product.setModel( form.getModel() );
        product.setPrice( form.getPrice() );

        productRepository.save(product);
    }

    @Override
    public void patchPrice(Long id, double price) {

        Product product = productRepository.findById(id)
                .orElseThrow(); // TODO préciser

        product.setPrice( price );

        productRepository.save(product);


    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
        // TODO préciser la situation
    }
}
