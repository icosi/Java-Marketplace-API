package marketplace.marketplace2;
import marketplace.marketplace2.Cart;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import antlr.collections.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer>{

	Cart findByUserId(int user_id);
}
