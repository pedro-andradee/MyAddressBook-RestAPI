<template>
  <footer class="footer">
    <div class="d-flex footer-container">
      <img
        class="img-responsive img-logo"
        src="@/assets/address-book-logo.png"
        alt="Adress Book Logo"
      />
      <span class="logo-text">MyAddressBook</span>
      <h6 class="text-uppercase fw-bold mb-4">MyAddressBook</h6>
    </div>
  </footer>
</template>

<script>
export default {};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Kalam&display=swap");

.footer-container {
  background-color: #1b2531;
  flex-direction: column;
  align-items: center;
}

.img-logo {
  max-width: 8em;
  margin-top: 1.2em;
}

.logo-text {
  font-family: "Kalam", cursive;
  font-size: 2rem;
  color: #5fa8d3;
  margin: 0;
}
</style>