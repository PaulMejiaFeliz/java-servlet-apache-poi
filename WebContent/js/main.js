var app = new Vue({
    el: '#app',
    data: {
        fields: [ 'title', 'price', 'quantity', 'date', 'edit_product' ],
        products: [],
        form: {
            title: '',
            price: null,
            quantity: null
        },
        axios: null,
        currentProduct: null
    },
    mounted () {
        this.axios  = axios.create({
            baseURL: 'http://localhost:8084'
        });
        this.axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
        this.fetchProducts();
    },
    methods: {
        fetchProducts() {
            this.axios.get('/products')
            .then(
                response => (
                    this.products = response.data.reverse()
                )
            ) 
        },
        saveProduct(evt) {
            evt.preventDefault();
            url = '/products';
            const params = new URLSearchParams();
            params.append('title', this.form.title);
            params.append('price', this.form.price);
            params.append('quantity', this.form.quantity);
            if (this.currentProduct != null) {
                params.append('id', this.currentProduct.id);
                url = url + '/' + this.currentProduct.id;
            }
            this.axios({
                method: 'post',
                url: url,
                data: params
            })
            .then(response => {
                this.fetchProducts();
                this.resetForm(evt);
            });
        },
        resetForm(evt) {
            evt.preventDefault();
            this.form.title = '';
            this.form.price = null;
            this.form.quantity = null;
            this.currentProduct = null;
        },
        getProduct(product) {
            console.log(product);
            this.axios.get('/products/' + product.id)
            .then(
                response => {
                    this.form.title = response.data.title;
                    this.form.price = response.data.price;
                    this.form.quantity = response.data.quantity;
                    this.currentProduct = response.data;
                }
            ) 
        }
    }
});