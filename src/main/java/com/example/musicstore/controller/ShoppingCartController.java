package com.example.musicstore.controller;

import com.example.musicstore.service.ShoppingCartService;
import com.example.musicstore.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final SongService productService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, SongService productService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("/shoppingCart");
        modelAndView.addObject("products", shoppingCartService.getProductsInCart());
        modelAndView.addObject("total", shoppingCartService.getTotal().toString());
        modelAndView.addObject("itemCount", shoppingCartService.getItemCount());
        return modelAndView;
    }

    @GetMapping("/shoppingCart/addProduct/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(shoppingCartService::addProduct);
        return shoppingCart();
    }

@GetMapping("/shoppingCart/removeProduct/{productId}")
public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
System.out.println("---------------------Before removing: " + shoppingCartService.getProductsInCart());
productService.findById(productId).ifPresent(product -> {
    shoppingCartService.removeProduct(product);
    System.out.println("Removed product: " + product);
});
System.out.println("After removing: " + shoppingCartService.getProductsInCart());
    return shoppingCart();
}

    @GetMapping("/shoppingCart/checkout")
    public ModelAndView checkout() {
        shoppingCartService.checkout();
        ModelAndView modelAndView = new ModelAndView("checkout");
        modelAndView.addObject("message", "Checkout successful!");
        return modelAndView;
    }
}