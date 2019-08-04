<template>
  <div class="home-container">
    <!-- <HelloWorld msg="Kuch bhi dhundho"/> -->
    <b-row>
      <b-col>
        <b-form-input
          id="search-input"
          type="text"
          v-model="searchQuery"
          required
          placeholder="Search product"
          @keyup.enter="searchCall()"
        ></b-form-input>
      </b-col>
      <b-col>
        <b-button variant="success" id="search-button" @click="searchCall()">Search</b-button>
      </b-col>
    </b-row>
    <div class="d-flex justify-content-center mb-3 loader">
      <b-spinner label="Loading..." v-if="showLoader"></b-spinner>
    </div>
    <p class="result-metric-text" v-if="products.length">About {{products.length}} results ({{searchTimeElapsed}} seconds)</p>
    <div v-for="product in products" class="product-card">
      <b-img left :src="product.images[0]" class="product-image"></b-img>
      <h2 class="product-name">{{product.title}}</h2>
      <h6 class="product-attributes" v-if="product.color">Colors: {{product.color}}</h6>
      <h6 class="product-attributes" v-if="product.size">Sizes: {{product.size}}</h6>
      <h6 class="product-rating" v-if="product.avgRating">Rating: {{product.avgRating}}/5</h6>
      <span class="product-in-stock" v-if="product.stock && product.stock.toLowerCase() === 'in stock'">{{product.stock}}</span>
      <span class="product-out-of-stock" v-if="product.stock && product.stock.toLowerCase() === 'out of stock'">{{product.stock}}</span>
      <div v-for="(value, property) in product.attributes">
        <b>{{property}}</b>: {{value}}
      </div>
    </div>
   
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'
import { mapGetters } from 'vuex'
export default {
  name: 'home',
  // components: {
  //   HelloWorld
  // }
  data () {
    return {
      searchQuery: "",
      perPage: 5,
      currentPage: 1,
      products: [],
      showLoader: false,
      searchStartTime: null,
      searchEndTime: null,
      searchTimeElapsed: 0.0
    }
  },
  watch: {
    getSearchResult: function() {
      this.searchEndTime = new Date()
      this.searchTimeElapsed = (this.searchEndTime.getTime() - this.searchStartTime.getTime())/1000
      this.products = this.getSearchResult
      this.showLoader = false
    }
  },
  methods: {
    searchCall: function () {
      this.showLoader = true
      setTimeout(function(){
      }, 1000)
      this.searchStartTime = new Date()
      this.$store.dispatch('search', this.searchQuery)
    }
  },  
  computed: {
    ...mapGetters(['getSearchResult']),
    rows() {
        return this.products.length
      }
  }
}
</script>

<style lang="scss">
  #search-input {
    width: 122%;
    margin-left: 20%;
    height: 50px;
    font-size: 20px;
    background: url(https://img.icons8.com/color/48/000000/search.png) no-repeat;
    background-position: 98%;
    background-size: 4%;
  }
  .home-container {
    margin: 2% 10%;
  }
  #search-button {
    margin-left: 2%;
    width: 30%;
    height: 50px;
    font-size: 20px;
  }
  .product-card {
    border: 1px solid #f7f7f7;
    -webkit-box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
    height: 150px;
    margin: 4% 0%;
    width: 93%;
  }
  .product-image {
    width: 27%;
    height: 150px;
    padding: 1%;
  }
  .product-name {
    text-align: left;
    margin: 1% 0% 0% 30%;
    font-size: 16px;
  }
  .loader {
    margin-top: 5%;
  }
  .result-metric-text {
    color: grey;
    text-align: left;
    margin-left: 10%;
    margin-top: -4%;
  }
  .product-attributes {
    text-align: left;
    margin: 0.5% 0% 0.5% 30%;
  }
  .product-rating {
    text-align: left;
    margin: 1% 0% 0% 30%;
  }
  .product-out-of-stock {
    color: orangered;
    background: #f7f7f7;
    padding: 3px 27px;
    position: relative;
    left: 26%;
    .in-stock {
      background: green !important;
      color: white !important;
    }
  }
  .product-in-stock {
    background: green !important;
    color: white !important;
    padding: 3px 27px;
    position: relative;
    left: 26%;
  }
</style>
