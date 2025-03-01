<template>
    <div class="cart">
        <h1>Carrito de Compras</h1>
        <div v-if="cartItems.length">
            <CartItem v-for="item in cartItems" :key="item.id" :item="item" />
            <div class="total">
                <h3>Total: ${{ totalPrice.toFixed(2) }}</h3>
                <router-link to="/checkout" class="btn btn-success">Proceder al pago</router-link>
            </div>
        </div>
        <p v-else>Tu carrito está vacío.</p>
    </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useCartStore } from '@/stores/cart';
import CartItem from '@/components/CartItem.vue';

const cartStore = useCartStore();
const cartItems = computed(() => cartStore.items);
const totalPrice = computed(() => cartStore.totalPrice);
</script>

<style scoped lang="scss">
.cart {
    text-align: center;
}

.total {
    margin-top: 20px;
}
</style>