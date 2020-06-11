package marketplace.marketplace2;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path="/") // This means URL's start with /demo (after Application path)
public class MainController {
  @Autowired 
  private ProductRepository productRepository;
  @Autowired 
  private UserRepository userRepository;
  @Autowired 
  private CartRepository cartRepository;
  @Autowired 
  private CartProductRelationRepository cartProductRelationRepository;
 
  
  @PostMapping(path="/user/create") // Map ONLY POST Requests
  public @ResponseBody Integer addNewUser (@RequestBody Map params) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
	String name = (String) params.get("name");
	String last_name = (String) params.get("last_name");

    User user = new User();
    user.setName(name);
    user.setLastName(last_name);
    userRepository.save(user);

    Cart c = new Cart();
    c.setUserId(user.getUserId());
    cartRepository.save(c);
    
    return user.getUserId();
  }
  
  
  @PostMapping(path="/user/cart") // Map ONLY POST Requests
  public @ResponseBody ArrayList<Product> getUserCart (@RequestBody Map params) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
	int user_id = (int) params.get("user_id");

	Cart cart = cartRepository.findByUserId(user_id);
	
	Iterable<CartProductRelation> prods = cartProductRelationRepository.findAllByCartId(cart.getCartId());

	ArrayList<Product> products = new ArrayList<Product>();
	
	for(CartProductRelation p: prods) {
		Product prod = (Product) productRepository.findById(p.getProductId()).get();
		products.add(prod);		
	}
	
    return products;    
  }
  
  
  
  @PostMapping(path="/user/add_product") // Map ONLY POST Requests
  public @ResponseBody String addProduct (@RequestBody Map params) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
	int user_id = (int) params.get("user_id");
	int product_id = (int) params.get("product_id");

	//Optional<Product> product = productRepository.findById(product_id);
	Cart cart = cartRepository.findByUserId(user_id);

	CartProductRelation crp = new CartProductRelation();
	crp.setProductId(product_id);
	crp.setCartId(cart.getCartId());
	cartProductRelationRepository.save(crp);
	
    return "Cart updated";
  }
  
  
  @PostMapping(path="/user/checkout") // Map ONLY POST Requests
  public @ResponseBody int checkout (@RequestBody Map params) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
	int user_id = (int) params.get("user_id");

	Cart cart = cartRepository.findByUserId(user_id);
	
	Iterable<CartProductRelation> prods = cartProductRelationRepository.findAllByCartId(cart.getCartId());

	ArrayList<Integer> prices = new ArrayList<Integer>();

	for(CartProductRelation p: prods) {
		Product prod = (Product) productRepository.findById(p.getProductId()).get();
	    prices.add(prod.getPrice());
		
	}

    int sum = 0;
    for(Integer price : prices)
        sum += price;
        
    cartProductRelationRepository.deleteAllByCartId(cart.getCartId());

    return sum;    
  }
  
  
  
  
 
  @PostMapping(path="/product/new") // Map ONLY POST Requests
  public @ResponseBody String addNewProduct (@RequestBody Map params) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
	String name = (String) params.get("name");
	String category = (String) params.get("category");
	String description = (String) params.get("description");
	int price = (int) params.get("price");

    Product p = new Product();
    p.setName(name);
    p.setCategory(category);
    p.setDescription(description);
    p.setPrice(price);

    productRepository.save(p);
    
    return "Saved";
  }
  
  @PostMapping(path="/product/id") // Map ONLY POST Requests
  public @ResponseBody Optional<Product> getProductById (@RequestBody Map params) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
	int product_id = (int) params.get("product_id");
	
    return productRepository.findById(product_id);
  }
  
  @GetMapping(path="/product/all")
  public @ResponseBody Iterable<Product> getAllProducts() {
    // This returns a JSON or XML with the users
    return productRepository.findAll();
  }
}