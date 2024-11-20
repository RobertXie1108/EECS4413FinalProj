package dao;

import java.util.List;

import model.Cart;

public interface CartDAO {
    boolean addItemToCart(Cart cart);
    boolean updateCartItem(Cart cart);
    boolean removeItemFromCart(int cartId);
    List<Cart> getCartItemsByUserId(int userId);
    boolean clearCart(int userId);
}
