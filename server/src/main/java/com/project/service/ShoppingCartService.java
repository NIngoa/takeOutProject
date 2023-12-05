package com.project.service;

import com.project.dto.ShoppingCartDTO;
import com.project.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService{
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> listShoppingCart();
}
