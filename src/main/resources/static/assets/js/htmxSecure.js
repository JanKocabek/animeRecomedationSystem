 document.body.addEventListener('htmx:configRequest', e => {
            const t = document.querySelector('meta[name="_csrf"]').content;
            const h = document.querySelector('meta[name="_csrf_header"]').content;
            e.detail.headers[h] = t;
        });