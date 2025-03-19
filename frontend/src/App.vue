<script setup lang="ts">

</script>

<template>
  <router-view/>
</template>

<style>
#body {
  background-color: #ffe6e6; /*Fondo rosa pastel*/
  color: #333; /*Color de texto oscuro para contraste*/
  font-family: 'Poppins', sans-serif; /*Fuente moderna y limpia*/
}
#app {
  font-family: Avenir, Arial, Helvetica, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  position: relative;
  overflow: hidden;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
