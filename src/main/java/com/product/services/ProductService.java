package com.product.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.dto.ProductDTO;
import com.product.exceptions.DataNotFoundException;
import com.product.model.Category;
import com.product.model.Product;
import com.product.repository.CategoryRepository;
import com.product.repository.ProductRepository;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProductDTO> getAllProduct() {
        return  productRepository.findAll().stream()
                .map(x -> modelMapper.map(x, ProductDTO.class)).collect(Collectors.toList());
    }

    public void saveProduct(ProductDTO productDTO,Integer id) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Optional<Product> checkProduct = Optional.ofNullable(productRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Product with id: " + id + " is not found")));
        
        if(checkProduct.isPresent()) {
        Product product = modelMapper.map(productDTO, Product.class);
        Category category = categoryRepository.findByCategoryName(product.getCategory().getCategoryName());
        product.setCategory(category);
        productRepository.save(product);
        }
    }
    
    
    public ResponseEntity<Product> addProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Category category = product.getCategory();
        product.setCategory(category);
        Product p =  productRepository.save(product); 
      return new ResponseEntity<Product>(p,HttpStatus.OK);
    }

    public ResponseEntity<?> deleteProduct(Integer id) {
    	if (productRepository.findById(id).isPresent()) {
    		 productRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
  
    }

	public ProductDTO getProductById(Integer id) {
		Optional<Product> product = Optional.ofNullable(productRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Product with id: " + id + " is not found")));
		if (product.isPresent()) {
			return modelMapper.map(product, ProductDTO.class);
		} else {
			return null;
		}
	}
    
    
    public  List<Product> getProductss(Integer id) {
    	return productRepository.findAll();
      
    }
}