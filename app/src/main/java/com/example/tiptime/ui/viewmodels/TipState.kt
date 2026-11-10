package com.example.tiptime.ui.viewmodels

data class TipState (
    val amountInput: String = "",
    val tipInput: String = "15",
    val roundUp: Boolean = false,
    val tip: String = "",
    val total: String = "",
)

