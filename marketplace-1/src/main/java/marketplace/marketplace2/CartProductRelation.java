package marketplace.marketplace2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="cart_product_relation")
public class CartProductRelation {
	@Id
	@Column(name="relation_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer relationId;
	
	@Column(name="cart_id")
	private Integer cartId;
	
	@Column(name="product_id")
	private int productId;

	public Integer getRelationId() {
		return relationId;
	}

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer integer) {
		this.cartId = integer;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int product_id) {
		this.productId = product_id;
	}

	@Override
	public String toString() {
		return "CartProductRelation [relationId=" + relationId + ", cartId=" + cartId + ", productId=" + productId
				+ "]";
	}

	

}
