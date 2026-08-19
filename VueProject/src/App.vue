<script setup lang="ts">
const year = new Date().getFullYear();
</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary mb-3">
    <div class="container">
      <div class="collapse navbar-collapse justify-content-center" id="navbarSupportedContent">
        <ul class="navbar-nav mb-2 mb-lg-0">
          <li class="nav-item">
            <RouterLink class="nav-link" active-class="active" to="/" aria-current="page">
              <i class="fa-solid fa-dice"></i> Open packs
            </RouterLink>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="https://github.com/Lamer007/PokemonCards" target="_blank">
              <i class="fa-brands fa-github"></i>
            </a>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link" active-class="active" to="/Collection">
              Collection <i class="fa-solid fa-layer-group"></i>
            </RouterLink>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <div class="container">
      <RouterView />
  </div>
  <footer class="text-center mt-3">
  </footer>
</template>
