<template>
    <div class="checkout">
        <h1>Finalizar Compra</h1>
        <form @submit.prevent="submitOrder">
            <div>
                <label>Nombre Completo:</label>
                <input v-model="name" type="text" required />
            </div>
            <div>
                <label>Dirección:</label>
                <input v-model="address" type="text" required />
            </div>
            <div>
                <label>Método de pago:</label>
                <select v-model="paymentMethod">
                    <option value="tarjeta">Tarjeta de crédito</option>
                    <option value="paypal">PayPal</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Pagar</button>
        </form>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const name = ref('');
const address = ref('');
const paymentMethod = ref('tarjeta');

const submitOrder = () => {
    alert('¡Compra realizada con éxito!');
};
</script>

<style scoped lang="scss">
.checkout {
    text-align: center;
}

form {
    display: flex;
    flex-direction: column;
    gap: 10px;
    max-width: 400px;
    margin: auto;
}
</style>
