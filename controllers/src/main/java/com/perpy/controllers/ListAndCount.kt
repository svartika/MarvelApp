package com.perpy.controllers

data class ListAndCount<T>(
        val list: List<T>,
        val total: Int
)