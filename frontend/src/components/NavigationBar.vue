<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container">
            <router-link class="navbar-brand" to="/">Ecommerce</router-link>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <router-link class="nav-link" to="/">Inicio</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link class="nav-link" to="/shop">Tienda</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link class="nav-link" to="/cart">
                            🛒 Carrito <span class="badge bg-primary">{{ cartCount }}</span>
                        </router-link>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useCartStore } from '@/stores/cart';

const cartStore = useCartStore();
const cartCount = computed(() => cartStore.items.length);
</script>

<style scoped lang="scss">
.navbar {
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}
</style>