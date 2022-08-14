package com.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.product.dto.ProductDTO;
import com.product.services.CategoryService;
import com.product.services.ProductService;
//import javax.validation.Valid;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/products")
public class MainController {

    private ProductService productService;


    private static final Logger LOG = Logger.getLogger(MainController.class.getName());

    @Autowired
    public MainController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
    }

    @GetMapping("/getAllProducts")
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productPage = productService.getAllProduct();
        return productPage;
    }


    @PostMapping(value = "/addProduct")
    public ResponseEntity<?> addProduct(ProductDTO productDTO) {
        LOG.log(Level.INFO, "/ {0}",productDTO.getName());
        productService.addProduct(productDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteToDo(@PathVariable("id") Integer id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ProductDTO productDTO) {
		productService.saveProduct(productDTO,id);
		return new ResponseEntity<>(HttpStatus.OK);

	}
}