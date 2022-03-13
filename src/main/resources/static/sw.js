self.addEventListener('install', function(event) {
    event.waitUntil(
        caches.open('v1').then(function(cache) {
            return cache.addAll([
                '/',
                '/index.html',
                '/built/bundle.js',
                '/images/BG.png',
                '/images/gameName.png',
                '/fonts/ShortStack-Regular.ttf'
            ])
        }).catch(function(err) {
            console.log(err);
        })
    )
})

self.addEventListener('fetch', function(event) {
    event.respondWith(
        caches.match(event.request).then(function(response) {
            return response || fetch(event.request)
        })
    )
})