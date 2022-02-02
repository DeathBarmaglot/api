var messageApi = Vue.resource('http://localhost:8080/api/v1/posts');

Vue.component('post-row',{
    props:['post'],
    template: '<div>{{ post.id }} {{ post.title }} {{ post.content }}</div>'
});

Vue.component('posts-list',{
    props:['posts'],
    template: '<div> <post-row v-for="post in posts" :key="post.id" :post="post" /></div>',
    created: function() {
        messageApi.get().then(result =>
            result.json().then(data => // .forEach(post => this.posts.push(post))))
                data.forEach(e=> console.log(e))))
    }
});

var app = new Vue({
    el: '#app',
    template: '<posts-list :posts="posts" />',
    data: {posts: []}
});