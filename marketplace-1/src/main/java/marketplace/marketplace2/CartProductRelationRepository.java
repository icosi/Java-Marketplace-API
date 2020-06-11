package marketplace.marketplace2;
import marketplace.marketplace2.CartProductRelation;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRelationRepository extends CrudRepository<CartProductRelation, Integer>{

	Iterable<CartProductRelation> findAllByCartId(int cart_id);
	@Transactional
	void deleteAllByCartId(int cart_id);

}
