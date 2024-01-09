package com.moulamanager.api.services.product;

import com.moulamanager.api.exceptions.product.ProductAlreadyExistsException;
import com.moulamanager.api.exceptions.product.ProductNotFoundException;
import com.moulamanager.api.models.ProductModel;
import com.moulamanager.api.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testFindAll() {
        Pageable pageable = mock(Pageable.class);
        Page<ProductModel> page = mock(Page.class);
        when(productRepository.findAll(pageable)).thenReturn(page);

        assertEquals(page, productService.findAll(pageable));
    }

    @Test
    public void testFindById() {
        ProductModel product = new ProductModel();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertEquals(product, productService.findById(1L));
    }

    @Test
    public void testFindByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findById(1L));
    }

    @Test
    public void testSave() {
        ProductModel product = new ProductModel();
        product.setBarcode("1234567890123");
        when(productRepository.existsByBarcode(product.getBarcode())).thenReturn(false);
        when(productRepository.save(product)).thenReturn(product);

        assertEquals(product, productService.save(product));
    }

    @Test
    public void testSaveAlreadyExists() {
        ProductModel product = new ProductModel();
        product.setBarcode("1234567890123");
        when(productRepository.existsByBarcode(product.getBarcode())).thenReturn(true);

        assertThrows(ProductAlreadyExistsException.class, () -> productService.save(product));
    }

}