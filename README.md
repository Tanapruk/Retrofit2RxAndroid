# Retrofit2RxAndroid
An example structure of using retrofit2 with RxAndroid and its error handling

### Tools use:
1. Retrofit2
2. RxAndroid and RxJava
 

### Why this ?
* In Retrofit1, if you do not place retrofit stuff inside the Activity class, you have to callback from another class. And I'm lazy so I use [Otto](http://square.github.io/otto/). However, it is now deprecated and is recommended to use RxAndroid instead.
* Fortunately, Retrofit2 came out and officially supported RxAndroid. I take a while to get a hang of it. I kind of understanding some of its basic, so I put it in to this repo.

### RxAndroid
* Use `Single` instead of Observable because we want only a response per url.
* Use `compose()` with `Transformer` to chain method with a repetitive task like `subscribeOn()` , `observeOn()` and `onErrorResultNext()`.
* Throw `Single.error(`exception`)` whenever there is problem and it will go to the `onError()` in the subscription on the MainActivity. We will handle exception from there.



