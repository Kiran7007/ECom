package com.ecom.sample.utils

sealed class Navigator {
    object Idle : Navigator()
    object NavigateToCartScreen : Navigator()
}
