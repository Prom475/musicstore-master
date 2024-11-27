package com.example.musicstore.controller;

import com.example.musicstore.model.Cart;
import com.example.musicstore.model.ItemType;
import com.example.musicstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String viewCart(Model model) {
        Cart cart = new Cart(); // Replace this with session-based cart fetching
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam ItemType itemType, @RequestParam Long itemId) {
        Cart cart = new Cart(); // Replace this with session-based cart fetching
        cartService.addToCart(cart, itemType, itemId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long cartId, @RequestParam Long cartItemId) {
        cartService.removeFromCart(cartId, cartItemId);
        return "redirect:/cart";
    }
}
