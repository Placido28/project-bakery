<template>
    <nav class="navbar">
      <div class="logo">
        <router-link to="/">🍰 MiTienda</router-link>
      </div>
      <ul class="nav-links" :class="{ open: isOpen }">
        <li><router-link to="/">Inicio</router-link></li>
        <li><router-link to="/productos">Productos</router-link></li>
        <li><router-link to="/destacados">Destacados</router-link></li>
        <li><router-link to="/contacto">Contacto</router-link></li>
      </ul>
      <div class="icons">
        <input type="text" placeholder="Buscar..." v-model="searchQuery" @keyup.enter="buscar" />
        <router-link to="/carrito" class="cart">
          🛒 <span v-if="cartCount > 0">{{ cartCount }}</span>
        </router-link>
        <router-link to="/login">🔒</router-link>
        <button class="menu-btn" @click="toggleMenu">☰</button>
      </div>
    </nav>
  </template>
  
  <script>
  export default {
    data() {
      return {
        isOpen: false,
        searchQuery: "",
        cartCount: 0, // Simulación, puede ser dinámico desde Vuex o Pinia
      };
    },
    methods: {
      toggleMenu() {
        this.isOpen = !this.isOpen;
      },
      buscar() {
        this.$router.push({ path: "/buscar", query: { q: this.searchQuery } });
      },
    },
  };
  </script>
  
  <style scoped>
  .navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
    background: #ffcccb;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  }
  .nav-links {
    display: flex;
    list-style: none;
    gap: 20px;
  }
  .icons {
    display: flex;
    align-items: center;
    gap: 10px;
  }
  .cart span {
    background: red;
    color: white;
    border-radius: 50%;
    padding: 3px 6px;
    font-size: 12px;
  }
  .menu-btn {
    display: none;
  }
  @media (max-width: 768px) {
    .nav-links {
      display: none;
    }
    .menu-btn {
      display: block;
    }
  }
  </style>
  