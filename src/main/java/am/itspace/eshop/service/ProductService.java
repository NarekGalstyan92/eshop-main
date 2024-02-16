package am.itspace.eshop.service;

import am.itspace.eshop.entity.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    List<Product> findAll();

    Product findById(int id);

}
