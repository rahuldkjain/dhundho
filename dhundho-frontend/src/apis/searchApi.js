import makeApiCalls from './makeApiCalls.js'
export default {
    searchItem(callback, payload) {
        var url = '/hello?query=' + payload
        makeApiCalls.makeGetRequest(url, callback)
    },
    // fetchCartItems(callback, emailId) {
    //     var url = '/Cart/cart/usercart?emailId=' + emailId
    //     makeApiCalls.makeGetRequest(url, callback)
    // },
    // deleteCartItem(callback, cartItemId) {
    //     var url = '/Cart/cart?cartItemId=' + cartItemId
    //     makeApiCalls.makeDeleteRequest(url, callback)
    // },
    // putCartItemList(callback, payload) {
    //     var url = '/Cart/cart'
    //     makeApiCalls.makePutRequest(url, callback, payload)
    // },
}