<template>
  <div id="app">
    <div class="nav-bar">
      <b-row class="nav-border">
        <img id="logo-image" src="https://img.icons8.com/color/48/000000/wolfram-alpha.png">
        <h2><router-link to="/" class="logo">Dhundho</router-link></h2>
        <router-link to="about" id="about-link">About Us</router-link>
      </b-row>
      
    </div>
    <router-view/>
  </div>
</template>

<style lang="scss">
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#about-link {
    color: #333333;
    position: relative;
    left: 74%;
    font-size: 18px;
}
.nav-bar {
    margin: 0% 6% 1% 6%;
    border-bottom-left-radius: 10px;
    border-bottom-right-radius: 10px;
}
#logo-image {
    margin-top: 0%;
    position: relative;
    top: -4px;
}
.nav-border {
  padding-top: 1%;
  background: #f7f7f7;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}
.logo {
  color: black;
}
</style>
