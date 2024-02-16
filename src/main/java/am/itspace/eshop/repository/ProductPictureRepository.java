package am.itspace.eshop.repository;

import am.itspace.eshop.entity.Product;
import am.itspace.eshop.entity.ProductPicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductPictureRepository extends JpaRepository<ProductPicture, Integer> {

    List<ProductPicture> findAllByProduct(Product product);

}
