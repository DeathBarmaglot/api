await (await (await fetch('/api/v1/posts/')).json())

fetch('/api/v1/posts/', {method: 'GET', headers: {'Content-type':'application/json'}}).then(result => console.log(result))

fetch('/api/v1/posts/', {method: 'POST', headers: {'Content-type':'application/json'}, body: JSON.stringify({title: 'foo', content: 'bar', star: true })}).then(result => console.log(result))