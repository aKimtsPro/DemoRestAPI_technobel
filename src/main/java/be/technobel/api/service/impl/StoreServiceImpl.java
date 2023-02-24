package be.technobel.api.service.impl;

import be.technobel.api.exceptions.ResourceNotFoundException;
import be.technobel.api.models.dto.StoreDTO;
import be.technobel.api.models.entity.Product;
import be.technobel.api.models.entity.Store;
import be.technobel.api.models.form.ChangeOpeningsForm;
import be.technobel.api.repository.ProductRepository;
import be.technobel.api.repository.StoreRepository;
import be.technobel.api.service.StoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    public StoreServiceImpl(StoreRepository storeRepository, ProductRepository productRepository) {
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<StoreDTO> getAll() {
        return storeRepository.findAll().stream()
                .map(StoreDTO::toDto)
                .toList();
    }

    @Override
    public StoreDTO getOne(Long id) {
        return storeRepository.findById(id)
                .map(StoreDTO::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(Store.class, id));
    }

    @Override
    public void delete(Long id) {
//        try {
//            storeRepository.deleteById(id);
//        }
//        catch (EmptyResultDataAccessException ex){
//            throw new RessourceNotFoundException(ex, Store.class, id);
//        }

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Store.class, id));

        storeRepository.delete( store );
    }

    @Override
    public void changeOpenings(Long id, ChangeOpeningsForm form) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Store.class, id));

        if( form.getOpensAt() != null )
            store.setOpensAt( form.getOpensAt() );

        if ( form.getClosesAt() != null )
            store.setClosesAt( form.getClosesAt() );

        storeRepository.save( store );
    }

    @Override
    public void addProductToStore(Long storeId, Long productId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(Store.class, storeId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(Product.class, productId));

        if( store.getAvailableProducts().contains( product ) )
            throw new RuntimeException("product already contained");

        store.getAvailableProducts().add(product);

        storeRepository.save(store);

    }
}
