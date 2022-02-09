https://app.travis-ci.com/DeathBarmaglot/api.svg?branch=master
#Blog
##level 1:
###У нас есть следующая сущность. Пост -> id, title, content
+ GET /api/v1/posts - получение всех постов

+ POST /api/v1/posts - добавление нового поста, пост передается в формате JSON

+ PUT /api/v1/posts/{id} - модификация поста, новая версия поста передается в формате JSON

+ DELETE /api/v1/posts/{id} - удаление поста по id Используем Spring boot + Spring Data Jpa

##level 2:
###Покрываем все выше тестами через MockMvc,в дальнейшем стараемся писать тесты до написания нового функционала.
####Добавляем поддержку еще нескольких запросов

+ GET /api/v1/posts?title=:title - поиск постов с тайтлом :title

+ GET /api/v1/posts?sort=title - возвращаем все посты отсортированные по тайтлу

##level 3: 
###Добавляем в сущность пост поле star. С помощью него мы будем управлять списком топ постов.

+ GET /api/v1/posts/star - получить все топ посты.

+ PUT /api/v1/posts/{id}/star - отметить пост как топ.

+ DELETE /api/v1/posts/{id}/star - убрать отметку топ поста.

##level 4:
###Добавляется сущность комментарий (на данный момент аннонимеых) id, text, creationDate
####(не передается с клиента а генерируется в приложении), postId REST запросы для работы с комментами

+ POST /api/v1/posts/1/comments

+ GET /api/v1/posts/{id}/comments

+ GET /api/v1/posts/{postId}/comment/{commentId}

##level 5:
+ GET /api/v1/posts/1/full - возвращает JSON поста с ид = 1, и всеми вложенными комментариями в него

##level 6:
Добавляется новая entity Tag (java, spring, interview, history, nasa, apollo)

Relation b/w posts & tags @ManyToMany

Разработать Controller  REST api task 7:tags.
GET, POST, DELETE, findAll article with tag (Добавить таг, удалить таг, получить все таги)

filter tags Получить посты по тагу (нескольким тагам).
@RequestParam multi (передач нескольких параментров в запрос, работой с набором фильтров.)

##level 7:
Spring security & JWT.
построение аутентификации\авторизации через JWT токены
Идея в том чтобы все могли просматривать посты и комментарии к ним.
Но добавлять (удалять, редактировать) посты могли только пользователи с ролью админ.
А добавлять комментарии, пользователи с ролью пользователь.
возможность удаления и редактирования пользователями своих постов