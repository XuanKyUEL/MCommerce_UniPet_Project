package com.unipet7.mcommerce.utils;

import com.unipet7.mcommerce.models.Addresses;

public interface DetailCheckoutHelper {
    void onPaymentMethodSelected(String paymentMethod);
    void onAddressSelected(Addresses address);
}
